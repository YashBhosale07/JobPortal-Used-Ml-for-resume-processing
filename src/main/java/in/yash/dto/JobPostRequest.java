package in.yash.dto;

import lombok.Data;

@Data
public class JobPostRequest {
    private String title;
    private String description;
    private String requiredSkills;
    private String experienceLevel;
    private String location;
    private String salary;
}
