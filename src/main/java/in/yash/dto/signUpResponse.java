package in.yash.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class signUpResponse {

    private String name;
    private String email;
    private String role;

}
