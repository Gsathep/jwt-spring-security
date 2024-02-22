package completeJwt.Service.DTO;

import completeJwt.Domain.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Role role;
}
