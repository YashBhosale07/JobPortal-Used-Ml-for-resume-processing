package in.yash.service.impl;

import in.yash.dto.ApplyForJobRequest;
import in.yash.exceptionHandling.JobPostNotFoundException;
import in.yash.model.JobPost;
import in.yash.model.JobSeekerEntities.ApplyForJob;
import in.yash.model.JobSeekerEntities.JobSeeker;
import in.yash.model.User;
import in.yash.repo.JobPostRepo;
import in.yash.service.JobSeekerService;
import jakarta.persistence.EntityManager;
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
public class JobSeekerServiceImpl implements JobSeekerService {

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

        return "Job application submitted successfully.";
    }
}
