package in.yash.exceptionHandling;

public class CannotApplyForJob extends RuntimeException{
    public CannotApplyForJob(String message) {
        super(message);
    }
}
