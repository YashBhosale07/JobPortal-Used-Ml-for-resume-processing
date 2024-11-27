package in.yash.exceptionHandling;

public class JobSeekerNotPresentException extends RuntimeException{

    public JobSeekerNotPresentException(String message) {
        super(message);
    }
}
