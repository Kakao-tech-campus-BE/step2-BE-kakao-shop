package com.example.kakao.user;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                () -> new Exception400("회원 아이디를 찾을 수 없습니다. : "+id)
        );
        return new UserResponse.FindById(userPS);
        // userps로 응답안하고 dto로 바꿔서 응답했음
    }

    // join 메서드에 @Transactional 어노테이션이 있는 이유는 해당 메서드가
    // 데이터베이스에 변경을 가하는 작업을 수행하기 때문입니다.
    // Transactional 어노테이션을 사용하면 해당 메서드 내에서 일어난
    // 데이터베이스 작업들은 하나의 트랜잭션으로 묶여서 실행되며, 모든 작업이
    // 성공적으로 완료되었을 때만 변경 내용이 커밋되고, 하나라도 실패하면 변경
    // 내용이 롤백되어 이전 상태로 돌아갑니다. 이를 통해 데이터의 일관성과
    // 무결성을 보장할 수 있습니다.
    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());
        // 첫번 째로 동일한 이메일인지 확인
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        // 두 번 째로 passwordencoder를 해준다 // 해시한 값을 넣고
        try { 
            userJPARepository.save(requestDTO.toEntity());
            // save할 때 toentity로 변경
        } catch (Exception e) {
            throw new Exception500("unknown server error");
            // 혹시나 오류가 발생하면 throw를 던진다
            // 만약 throw를 안던지면 예외 처리가 안됨
            // 클라이언트로 잘못된 응답을 줘야함
            // 500을 터트리기만 하면 됨
        }
    }

    // 반면에 login() 메서드는 데이터베이스에 변경을 가하는 작업을
    // 수행하지 않습니다. findByEmail() 메서드를 통해 데이터베이스 조회만을
    // 수행하고 있고, passwordEncoder.matches() 메서드를 통해 패스워드를
    // 비교하고 있습니다. 따라서 데이터베이스에 변경이 필요하지 않기 때문에
    // 트랜잭션이 없어도 됩니다.
    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                // 이메일 조회를 한다
                () -> new Exception400("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );
        // db에서 주어진 이메일 주소를 가진 사용자 조회

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            // 기존값, 해시값 비교 동일하지 않으면 400 던짐
            throw new Exception400("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS); // 토큰 생성
        // updatepassword가 있다면 더티채팅은 되지만 트랜잭션이 없으니까
        // flush가 안일어난다
    }

    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if (userOP.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다 : " + email);
        }
    }


    @Transactional
    public void updatePassword(UserRequest.UpdatePasswordDTO requestDTO, Integer id) {
        // 업데이트를 하기 전에 해당 id가 있는지 확인해야함
        // 클라이언트로 받은 id를 신뢰할 수 없으니까
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new Exception400("회원 아이디를 찾을 수 없습니다. : "+id)
        );// 영속화

        // 있으면 받은 pw를 update
        // 의미 있는 setter 추가
        String encPassword =
                passwordEncoder.encode(requestDTO.getPassword());
        userPS.updatePassword(encPassword); // 영속화 되어있는 객체를 변경
    } // 더티체킹(영속화 되어있는 객체의 변경을 감지) em.flush

}
