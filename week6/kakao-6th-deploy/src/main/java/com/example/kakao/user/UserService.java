package com.example.kakao.user;

import com.example.kakao._core.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional()
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userJPARepository.save(requestDTO.toEntity());

    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(NotFoundEmailException::new);

        if (!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())) {
            throw new NotFoundPasswordException();
        }
        return JWTProvider.create(userPS);
    }

    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if (userOP.isPresent()) {
            throw new DuplicatedEmailException();
        }
    }
}