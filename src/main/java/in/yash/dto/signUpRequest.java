package in.yash.dto;

import in.yash.model.JobSeeker;
import in.yash.model.Recruiter;
import in.yash.utils.ROLE;
import lombok.Data;

@Data
public class signUpRequest {
    private String name;
    private String email;
    private String password;
    private ROLE role;
    private JobSeeker jobSeeker;
    private Recruiter recruiter;
}
