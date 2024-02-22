package completeJwt.Service.Impl;

import completeJwt.Domain.RefreshToken;
import completeJwt.Domain.User;
import completeJwt.Mapper.RefreshTokenMapper;
import completeJwt.Repository.RefreshTokenRepo;
import completeJwt.Repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenImpl
{
    private final RefreshTokenMapper refreshTokenMapper;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserRepository userRepository;
    public long refreshTokenvalidity=5*60*60*10000;

    public RefreshTokenImpl(RefreshTokenMapper refreshTokenMapper, RefreshTokenRepo refreshTokenRepo, UserRepository userRepository) {
        this.refreshTokenMapper = refreshTokenMapper;
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepository = userRepository;
    }


    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username).get();
        RefreshToken refreshToken1 = user.getRefreshToken();
        if (refreshToken1 == null) {
            refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenvalidity))
                    .user(user)
                    .build();
        }
           else{
                refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenvalidity));
            }
            user.setRefreshToken(refreshToken1);
            refreshTokenRepo.save(refreshToken1);
            return refreshToken1;
        }

    public RefreshToken VerifyRefreshToken(String refreshToken)
    {
        RefreshToken refreshToken1= refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("Token does not exist in DB"));
                    if ((refreshToken1.getExpiry().compareTo(Instant.now())<0))
                    {
                        refreshTokenRepo.delete(refreshToken1);
                        throw new RuntimeException("Refresh Token because token is expired !!");
                    }
                        return refreshToken1;


    }
}
