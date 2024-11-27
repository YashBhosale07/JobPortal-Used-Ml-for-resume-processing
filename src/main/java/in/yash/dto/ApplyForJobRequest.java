package in.yash.dto;

import lombok.Data;

@Data
public class ApplyForJobRequest {
    private String coverLetter;
    private String email;
    private String contactNumber;
}
