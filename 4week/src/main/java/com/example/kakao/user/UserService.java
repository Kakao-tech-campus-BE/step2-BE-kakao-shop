package com.example.kakao.user;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao._core.errors.exception.InternalServerErrorException;
import com.example.kakao._core.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public UserResponse.FindById findById(Integer id){
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new BadRequestException("회원 아이디를 찾을 수 없습니다. : "+id)
        );
        return new UserResponse.FindById(userPS);
    }

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        try {
            userJPARepository.save(requestDTO.toEntity());
        } catch (Exception e) {
            throw new InternalServerErrorException("unknown server error");
        }
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new BadRequestException("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new BadRequestException("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS);
    }

    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if (userOP.isPresent()) {
            throw new BadRequestException("동일한 이메일이 존재합니다 : " + email);
        }
    }


    @Transactional
    public void updatePassword(UserRequest.UpdatePasswordDTO requestDTO, Integer id) {
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new BadRequestException("회원 아이디를 찾을 수 없습니다. : "+id)
        );

        // 의미 있는 setter 추가
        String encPassword =
                passwordEncoder.encode(requestDTO.getPassword());
        userPS.updatePassword(encPassword);
    } // 더티체킹 flush
}
