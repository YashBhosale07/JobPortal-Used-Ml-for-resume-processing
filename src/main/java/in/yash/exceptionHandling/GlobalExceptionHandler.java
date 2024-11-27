package in.yash.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse>userAlreadyExistException(UserAlreadyExistException userAlreadyExistException){
        ErrorResponse errorResponse=new ErrorResponse(userAlreadyExistException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<ErrorResponse>jobPostNotFoundException(JobPostNotFoundException jobPostNotFoundException){
        ErrorResponse errorResponse=new ErrorResponse(jobPostNotFoundException.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationsForJobNotFoundException.class)
    public ResponseEntity<ErrorResponse>applicationsForJobNotFoundException(ApplicationsForJobNotFoundException applicationsNotFoundException){
        ErrorResponse errorResponse=new ErrorResponse(applicationsNotFoundException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobSeekerNotPresentException.class)
    public ResponseEntity<ErrorResponse>JobSeekerNotPresentException(JobSeekerNotPresentException jobSeekerNotPresentException){
        ErrorResponse errorResponse=new ErrorResponse(jobSeekerNotPresentException.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

}
