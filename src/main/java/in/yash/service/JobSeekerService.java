package in.yash.service;

import in.yash.dto.ApplyForJobRequest;
import in.yash.model.JobPost;

import java.util.List;

public interface JobSeekerService {

    List<JobPost> seeAllJobs();

    String applyForJob(Long jobId, ApplyForJobRequest applyForJobRequest);
}
