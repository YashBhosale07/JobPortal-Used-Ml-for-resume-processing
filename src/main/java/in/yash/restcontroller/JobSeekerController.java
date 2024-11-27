package in.yash.restcontroller;

import in.yash.dto.ApplyForJobRequest;
import in.yash.model.JobPost;
import in.yash.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobSeeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @GetMapping("/allJobs")
    public ResponseEntity<?>allJobs(){
       List<JobPost>jobs = jobSeekerService.seeAllJobs();
       return new ResponseEntity<>(jobs, HttpStatus.FOUND);
    }

    @PutMapping("/applyForJob/{jobId}")
    public ResponseEntity<?>applyForJob(@PathVariable Long jobId, @RequestBody ApplyForJobRequest applyForJobRequest){
        String status=jobSeekerService.applyForJob(jobId,applyForJobRequest);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }

}
