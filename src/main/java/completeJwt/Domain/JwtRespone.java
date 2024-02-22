package completeJwt.Domain;


import completeJwt.web.UserResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtRespone
{
    private String token;
//    private String refreshToken;
//    private String username;


//    public JwtRespone(String token, String refreshToken, String username) {
//        this.token = token;
//        this.refreshToken = refreshToken;
//        this.username = username;
//    }


    public JwtRespone(String token) {
        this.token = token;
    }
}
