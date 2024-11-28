package in.yash.dto;

import lombok.Data;

@Data
public class ApplyForJobRequest {
    private String email;
    private String contactNumber;
    private String fullName;
}
