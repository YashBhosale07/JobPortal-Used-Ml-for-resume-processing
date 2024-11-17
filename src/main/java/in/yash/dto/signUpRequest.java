package in.yash.dto;

import in.yash.utils.ROLE;
import lombok.Data;

@Data
public class signUpRequest {
    private String name;
    private String email;
    private String password;
    private ROLE role;
}
