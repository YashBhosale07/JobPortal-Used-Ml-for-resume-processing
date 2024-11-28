package in.yash.service.impl;

import in.yash.dto.ApplyForJobRequest;
import in.yash.exceptionHandling.CannotApplyForJob;
import in.yash.exceptionHandling.JobPostNotFoundException;
import in.yash.model.JobPost;
import in.yash.model.JobSeekerEntities.ApplyForJob;
import in.yash.model.JobSeekerEntities.JobSeeker;
import in.yash.model.User;
import in.yash.repo.JobPostRepo;
import in.yash.service.JobSeekerService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JobPostRepo jobPostRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<JobPost> seeAllJobs() {
        List<JobPost>jobs=jobPostRepo.findAll();
        return jobs;
    }

    @Override
    @Transactional
    public String applyForJob(Long jobId, ApplyForJobRequest applyForJobRequest) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();
        JobPost jobPost=jobPostRepo.findById(jobId).orElseThrow(()->new JobPostNotFoundException("Job is not present"));
        ApplyForJob apply=modelMapper.map(applyForJobRequest, ApplyForJob.class);
        apply.setAppliedDate(new Date());
        apply.setJobPost(jobPost);
        JobSeeker jobSeeker = u.getJobseeker_user();
        if (jobSeeker != null) {
            jobSeeker = entityManager.merge(jobSeeker);
        }
        apply.setJobSeeker(jobSeeker);
        if(jobPost.getApplyForJobs()==null){
            jobPost.setApplyForJobs(new ArrayList<>());
        }
        jobPost.getApplyForJobs().add(apply);
        if(u.getJobseeker_user().getApplyForJob()==null){
            u.getJobseeker_user().setApplyForJob(new ArrayList<>());
        }
        u.getJobseeker_user().getApplyForJob().add(apply);
        sendResumeEmailToRecruiter(jobSeeker,jobPost,applyForJobRequest);

        return "Job application submitted successfully.";
    }

    private void sendResumeEmailToRecruiter(JobSeeker jobSeeker, JobPost jobPost, ApplyForJobRequest applyForJobRequest) {
        try{
            String toEmail=jobPost.getHrEmail();
            String contact=applyForJobRequest.getContactNumber();
            String name=applyForJobRequest.getFullName();
            String email=jobSeeker.getJobseeker_user().getEmail();
            String subject="New job Application "+jobPost.getTitle();
            String body = "Dear HR,"+ name +" has applied for the position. Please find the attached resume."+"Contact Details are" +
                    "email:->"+email+ " phoneNumber "+contact;
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message,true);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            byte[] resumeData = jobSeeker.getResumeLink(); // Get resume from the database
            if (resumeData != null) {
                mimeMessageHelper.addAttachment("Resume_" + name + ".pdf",
                        new ByteArrayDataSource(resumeData, "application/pdf"));
            }

            javaMailSender.send(message);
        }catch (Exception e){
            throw new CannotApplyForJob("Something went wrong.Try again later");

        }
    }
}
