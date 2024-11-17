package in.yash.restcontroller;
import in.yash.dto.signUpRequest;
import in.yash.dto.signUpResponse;
import in.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@RequestBody signUpRequest request){
        signUpResponse response=userService.createUser(request);
        return new ResponseEntity<signUpResponse>(response, HttpStatus.CREATED);
    }

}
