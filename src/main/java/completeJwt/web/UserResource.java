package completeJwt.web;

import completeJwt.Config.CustomUserDetails;
import completeJwt.Domain.*;
import completeJwt.Jwt.JWTHelper;
import completeJwt.Repository.UserRepository;
import completeJwt.Service.DTO.UserDTO;
import completeJwt.Service.Impl.RefreshTokenImpl;
import completeJwt.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;
    private final CustomUserDetails customUserDetails;
    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final UserRepository userRepository;
    @Autowired
    private final RefreshTokenImpl refreshTokenImpl;

    public UserResource(UserService userService, CustomUserDetails customUserDetails, AuthenticationManager authenticationManager,
                        JWTHelper jwtHelper, UserRepository userRepository, RefreshTokenImpl refreshTokenImpl) {
        this.userService = userService;
        this.customUserDetails = customUserDetails;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userRepository = userRepository;
        this.refreshTokenImpl = refreshTokenImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.error("user already exist");
            throw new RuntimeException("user already exist");
        }
        UserDTO userDTO1 = userService.save(userDTO);
        return ResponseEntity.ok(userDTO1);
    }


//    @PostMapping("/login")
//    public ResponseEntity<JwtRespone> login(@RequestBody JwtRequst jwtRequst) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(jwtRequst.getUsername(), jwtRequst.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtHelper.generateToken(authentication);
//
//
//        User userDetails = (User) customUserDetails.loadUserByUsername(jwtRequst.getUsername());
//        RefreshToken refreshToken1 =refreshTokenImpl.createRefreshToken(userDetails.getUsername());
//        JwtRespone respone= JwtRespone.builder()
//                .token(jwt)
//                .refreshToken(String.valueOf(refreshToken1)).build();
//
//        return ResponseEntity.ok(respone);
//    }



    @GetMapping("/hi")
    public String get() {
        log.error("hello");
        return "hello JWT token";
    }
    @PostMapping("/login")
    public ResponseEntity<JwtRespone> login(@RequestBody JwtRequst jwtRequst) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequst.getUsername(), jwtRequst.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generateToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token); // Add a space after "Bearer"

        return new ResponseEntity<>(new JwtRespone(token), httpHeaders, HttpStatus.OK);
    }



//    @PostMapping("/refresh")
//    public JwtRespone refreshToken(@RequestBody RefreshTokenRequst refreshTokenRequst)
//    {
//        RefreshToken refreshToken=refreshTokenImpl.VerifyRefreshToken(refreshTokenRequst.getRefreshToken());
//
//        User user=refreshToken.getUser();
//
//        String token=this.jwtHelper.generateToken((Authentication) user);
//
//        return
//                JwtRespone.builder().
//                        refreshToken(refreshToken.getRefreshToken())
//                        .token(token)
//                        .username(user.getUsername())
//                        .build();
//    }

}



