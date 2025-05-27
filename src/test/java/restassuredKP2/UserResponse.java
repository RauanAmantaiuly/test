package restassuredKP2;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
