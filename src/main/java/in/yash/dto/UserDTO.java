package in.yash.dto;


import in.yash.utils.ROLE;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private ROLE role;

}

