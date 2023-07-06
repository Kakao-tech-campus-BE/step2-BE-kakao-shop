package com.example.kakaoshop.user;

import com.example.kakaoshop.global.error.CustomException;
import com.example.kakaoshop.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJPARepository userRepository;

    public void checkJoin(UserRequest.JoinDTO joinDTO) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?!.*\\s).*$";

        // 이메일 양식 조건 확인
        if (!isRightEmailType(joinDTO.getEmail())) {
            throw new CustomException(ErrorCode.WRONG_EMAIL_TYPE);
        }

        // 비밀번호 조건 확인
        if (Pattern.matches(passwordRegex, joinDTO.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD_TYPE);
        }

        // 이메일 존재 여부 확인
        if (isDuplicateEmail(joinDTO.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_MEMBER_EMAIL);
        }

        if (!isInPasswordRange(joinDTO.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD_LENGTH);
        }
    }

    public void checkEmail(UserRequest.checkEmailDTO checkEmailDTO) {
        if (isDuplicateEmail(checkEmailDTO.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_MEMBER_EMAIL);
        }

        if (isRightEmailType(checkEmailDTO.getEmail())) {
            throw new CustomException(ErrorCode.WRONG_EMAIL_TYPE);
        }
    }

    public void checkLogin(UserRequest.LoginDTO loginDTO) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?!.*\\s).*$";

        // 이메일 양식 조건 확인
        if (!isRightEmailType(loginDTO.getEmail())) {
            throw new CustomException(ErrorCode.WRONG_EMAIL_TYPE);
        }

        // 비밀번호 조건 확인
        if (Pattern.matches(passwordRegex, loginDTO.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD_TYPE);
        }

        if (!isInPasswordRange(loginDTO.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD_LENGTH);
        }
    }

    private boolean isDuplicateEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightEmailType(String email) {
        String emailRegex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        return Pattern.matches(emailRegex, email);
    }

    private boolean isInPasswordRange(String password) {
        return 8 <= password.length() && password.length() <= 20;
    }
}
