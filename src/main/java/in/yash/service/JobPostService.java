package in.yash.service;

import in.yash.dto.JobPostRequest;
import in.yash.model.JobPost;

import java.util.List;

public interface JobPostService {
    String createAJob(JobPostRequest jobPostRequest);
    String updateJobStatus(Long id);
    List<JobPost>getAllJobs();
}
