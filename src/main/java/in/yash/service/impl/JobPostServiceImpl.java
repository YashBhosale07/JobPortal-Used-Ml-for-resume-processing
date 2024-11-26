package in.yash.service.impl;

import in.yash.dto.JobPostRequest;
import in.yash.exceptionHandling.JobPostNotFoundException;
import in.yash.model.JobPost;
import in.yash.model.Recruiter;
import in.yash.model.User;
import in.yash.repo.JobPostRepo;
import in.yash.repo.RecruitierRepo;
import in.yash.service.JobPostService;
import in.yash.utils.ROLE;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobPostServiceImpl implements JobPostService {


    @Autowired
    private JobPostRepo jobPostRepo;

    @Autowired
    private RecruitierRepo recruitierRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public String createAJob(JobPostRequest jobPostRequest) {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

            User user = (User) authentication.getPrincipal();

            if (user.getRole() != ROLE.RECRUITER) {
                throw new IllegalArgumentException("Only recruiters can create job posts");
            }

            Recruiter recruiter = recruitierRepo.findById(user.getRecruiter().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Recruiter not found"));

            JobPost jobPost = mapper.map(jobPostRequest, JobPost.class);
            jobPost.setHrEmail(user.getEmail());
            jobPost.setJobStatus("Open");
            jobPost.setPostedDate(new Date());
            jobPost.setRecruiter(recruiter);

            if (recruiter.getJobPost() == null) {
                recruiter.setJobPost(new ArrayList<>());
            }
            recruiter.getJobPost().add(jobPost);

            jobPostRepo.save(jobPost);

            return "Job post saved successfully";
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException("Error occurred while creating the job post");
        }
    }

    @Override
    @Transactional
    public String updateJobStatus(Long id) {
        JobPost jobPost=jobPostRepo.findById(id).orElseThrow(
                ()->new JobPostNotFoundException("Job is not present"));
        jobPost.setJobStatus("Closed");
        jobPostRepo.save(jobPost);
        return "Job Status updated to closed";
    }

    @Override
    @Transactional
    public List<JobPost> getAllJobs() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user= (User) authentication.getPrincipal();
        List<JobPost> jobPosts=jobPostRepo.findByRecruiterId(user.getRecruiter().getId());
        return jobPosts;
    }
}
