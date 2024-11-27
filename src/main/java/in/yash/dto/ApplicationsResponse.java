package in.yash.dto;

import in.yash.model.JobSeekerEntities.EducationDetails;
import in.yash.model.JobSeekerEntities.ProfessionalDetails;
import in.yash.model.JobSeekerEntities.WorkExperience;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationsResponse {
    private String name;
    private String email;
    private WorkExperience workExperience;
    private ProfessionalDetails professionalDetails;
    private EducationDetails educationDetails;
    private Date applyiedDate;

}
