package in.yash.restcontroller;

import in.yash.dto.ApplicationsResponse;
import in.yash.dto.JobPostRequest;
import in.yash.model.JobPost;
import in.yash.model.JobSeekerEntities.ApplyForJob;
import in.yash.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobPost")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

   @PostMapping("/post")
    public ResponseEntity<?>postAJob(@RequestBody JobPostRequest jobPostRequest) {
       String name=jobPostService.createAJob(jobPostRequest);
       return new ResponseEntity<String>(name,HttpStatus.CREATED);
   }

   @GetMapping("/getJobById/{jobId}")
   public ResponseEntity<?>getJobPostById(@PathVariable Long jobId){
       JobPost jobPost=jobPostService.getJobById(jobId);
       return new ResponseEntity<JobPost>(jobPost,HttpStatus.FOUND);
   }

   @PutMapping("/updateStatus/{jobId}")
    public ResponseEntity<?>updateJobStatus(@PathVariable Long jobId){
       String status=jobPostService.updateJobStatus(jobId);
       return new ResponseEntity<String>(status,HttpStatus.OK);
   }

   @GetMapping("/getAllJobPosts")
    public ResponseEntity<?>getAllJobs(){
       List<JobPost>jobPosts =jobPostService.getAllJobs();
       return new ResponseEntity<>(jobPosts,HttpStatus.FOUND);
   }

   @DeleteMapping("/deleteJobById/{jobId}")
    public ResponseEntity<?>deleteJobById(@PathVariable Long jobId){
       String status=jobPostService.deleteJob(jobId);
       return new ResponseEntity<String>(status,HttpStatus.OK);
   }

   @GetMapping("/getJobApplications/{jobId}")
    public ResponseEntity<?>getJobApplications(@PathVariable Long jobId){
       List<ApplicationsResponse>applications =jobPostService.getJobApplications(jobId);
       return new ResponseEntity<>(applications,HttpStatus.FOUND);
   }

}
