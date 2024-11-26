package in.yash.exceptionHandling;

public class JobPostNotFoundException extends RuntimeException{
    public JobPostNotFoundException(String message) {
        super(message);
    }
}
