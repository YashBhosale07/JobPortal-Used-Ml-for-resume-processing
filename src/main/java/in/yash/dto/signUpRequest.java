package in.yash.dto;

import in.yash.model.JobSeekerEntities.EducationDetails;
import in.yash.model.JobSeekerEntities.JobSeeker;
import in.yash.model.JobSeekerEntities.ProfessionalDetails;
import in.yash.model.JobSeekerEntities.WorkExperience;
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
    private ProfessionalDetails professionalDetails;
    private WorkExperience workExperience;
    private EducationDetails educationDetails;
}
