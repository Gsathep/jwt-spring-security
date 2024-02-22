package completeJwt.Service.Impl;

import completeJwt.Domain.JwtRespone;
import completeJwt.Domain.RefreshToken;
import completeJwt.Domain.RefreshTokenRequst;
import completeJwt.Domain.User;
import completeJwt.Mapper.UserMappper;
import completeJwt.Service.DTO.UserDTO;
import completeJwt.Repository.UserRepository;
import completeJwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMappper userMappper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserImpl(UserRepository userRepository, UserMappper userMappper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMappper = userMappper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMappper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user1 = userRepository.save(user);
        return userMappper.toDto(user1);
    }

}
