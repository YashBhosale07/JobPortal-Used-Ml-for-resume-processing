package in.yash.exceptionHandling;

public class ApplicationsForJobNotFoundException extends RuntimeException{
    public ApplicationsForJobNotFoundException(String message) {
        super(message);
    }
}
