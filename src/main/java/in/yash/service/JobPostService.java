package in.yash.service;

import in.yash.dto.ApplicationsResponse;
import in.yash.dto.JobPostRequest;
import in.yash.model.JobPost;

import java.util.List;

public interface JobPostService {
    String createAJob(JobPostRequest jobPostRequest);
    String updateJobStatus(Long id);
    List<JobPost>getAllJobs();
    JobPost getJobById(Long id);

    String deleteJob(Long jodId);

    List<ApplicationsResponse> getJobApplications(Long jobId);
}
