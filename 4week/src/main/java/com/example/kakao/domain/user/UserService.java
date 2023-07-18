package com.example.kakao.domain.user;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao._core.errors.exception.InternalServerErrorException;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.domain.user.dto.UserResponse;
import com.example.kakao.domain.user.dto.request.JoinDTO;
import com.example.kakao.domain.user.dto.request.LoginDTO;
import com.example.kakao.domain.user.dto.request.UpdatePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void join(JoinDTO requestDTO) {
        checkEmailDuplicated(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        try {
            userJPARepository.save(requestDTO.toEntity());
        } catch (Exception e) {
            throw new InternalServerErrorException("unknown server error");
        }
    }

    public String login(LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new BadRequestException("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new BadRequestException("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS);
    }

    public void checkEmailDuplicated(String email) {
        userJPARepository.findByEmail(email)
            .ifPresent(user -> {
                throw new BadRequestException("동일한 이메일이 존재합니다 : " + email);
            });
    }


    @Transactional
    public void updatePassword(UpdatePasswordDTO requestDTO, int id) {
        User user = userJPARepository.findById(id).orElseThrow(
                () -> new BadRequestException("회원 아이디를 찾을 수 없습니다. : " + id)
        );

        // 의미 있는 setter 추가
        // -> 현재와 같이 set 이라는 단어를 update로 바꾸는것이 어떤 의미가 있을까요 ? Lombok 의 일관성을 깨뜨리고 중복구현만 늘어나는것은 아닐까요.
        String encPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.updatePassword(encPassword);
    } // 더티체킹 flush
}
