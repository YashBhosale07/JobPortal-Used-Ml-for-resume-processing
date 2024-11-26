package in.yash.restcontroller;

import in.yash.dto.JobPostRequest;
import in.yash.model.JobPost;
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

}
