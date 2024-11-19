package in.yash.restcontroller;
import in.yash.dto.loginRequest;
import in.yash.dto.loginResponse;
import in.yash.dto.signUpRequest;
import in.yash.dto.signUpResponse;
import in.yash.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody loginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        loginResponse response=userService.loginUp(request);
        Cookie cookie=new Cookie("refreshToken",response.getRefreshToken());
        cookie.setHttpOnly(true);
        servletResponse.addCookie(cookie);
        return new ResponseEntity<loginResponse>(response, HttpStatus.FOUND);

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?>refreshToken(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null || cookies.length == 0) {
            return new ResponseEntity<>("No cookies found", HttpStatus.BAD_REQUEST);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                stringBuilder.append(cookie.getValue());
                break;
            }
        }
        if (stringBuilder.length() == 0) {
            return new ResponseEntity<>("Refresh token is missing or invalid", HttpStatus.BAD_REQUEST);
        }
        loginResponse response=userService.generateNewAcessToken(stringBuilder.toString());
        return new ResponseEntity<loginResponse>(response, HttpStatus.FOUND);

    }

}
