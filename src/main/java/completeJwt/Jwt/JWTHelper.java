package completeJwt.Jwt;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JWTHelper {
    public Long JWT_TOKEN_VALIDITY = 86400000L;

    public String secret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJjhjhhjhggfdfdgvhbhghfgfffzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ";

    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true; // Token is valid
        } catch (Exception e) {
            throw  new AuthenticationCredentialsNotFoundException("jwt token is not valid: " + token);
        }
    }


    public String getUserNameFromJwtToken(String Token) {
        Claims claim= Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(Token).getPayload();
        return claim.getSubject();
    }
}
