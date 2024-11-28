package in.yash.dto;

import in.yash.utils.ROLE;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class signUpRequest {
    private String name;
    private String email;
    private String password;
    private ROLE role;
    private String skills;
    private MultipartFile resumeFile;
    private String companiesWorkedAt;
    private String designation;
    private String projects;
    private Double experienceYears;
    private String jobPreferences;
    private String locationPreference;
    private String highestQualification;
    private String institutionName;
    private String graduationYear;
    private String companyName;
}
