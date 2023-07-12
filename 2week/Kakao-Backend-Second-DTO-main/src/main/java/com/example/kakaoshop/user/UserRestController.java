package com.example.kakaoshop.user;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.security.JWTProvider;
import com.example.kakaoshop.global.ResponseDTO;
import com.example.kakaoshop.global.error.CustomException;
import com.example.kakaoshop.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/check")
    public ResponseEntity<?> checkEmail(@RequestBody UserRequest.checkEmailDTO checkEmailDTO) {
        userService.checkEmail(checkEmailDTO);

        ResponseDTO responseDTO = new ResponseDTO(true, null, null);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        userService.checkJoin(joinDTO);

        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(new ResponseDTO(true, null, null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        userService.checkLogin(loginDTO);

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(new ResponseDTO(true, null, null));
    }
}
