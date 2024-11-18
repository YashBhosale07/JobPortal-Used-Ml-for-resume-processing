package in.yash.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobPost")
public class JobPostController {

    @GetMapping("/checkone")
    public ResponseEntity<String>checkone(){
        return new ResponseEntity<>("one check", HttpStatus.OK);
    }
    @GetMapping("/checktwo")
    public ResponseEntity<String>checktwo(){
        return new ResponseEntity<>("two check", HttpStatus.OK);
    }

}
