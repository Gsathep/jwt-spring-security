package completeJwt.Service.DTO;

import completeJwt.Domain.User;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.Instant;
@Data
public class RefreshTokenDTO
{

    private String RefreshToken;

    private Instant expiry;


    private User user;
}
