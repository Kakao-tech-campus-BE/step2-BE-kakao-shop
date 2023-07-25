package com.example.kakao.user;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final UserService userService;
    // globalexcep 마찬가지로 dependency injection

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO,
                                  Errors errors, HttpServletRequest request) {
        // valid가 있으면 joindto에서 조건을 만족하지 않는다면 erros한테 넘김


        // 유효성 검사 - 따로 빼낼 예정 (다음 주)
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            ); // 첫 번째는 body, 두 번째는 status
        }
        try {
            userService.join(requestDTO);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        } // request를 날리는 이유는 에러로그 남기려고
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO,
                                   Errors errors, HttpServletRequest request) {
        // 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        try {
            String jwt = userService.login(requestDTO);
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
            // 메소드를 호출하여 사용자 로그인을 시도합니다. 이 메소드는
            // 사용자의 이메일과 비밀번호를 인자로 받아 로그인을 시도하고,
            // 로그인에 성공하면 JWT(Jason Web Token) 문자열을 반환합니다.

            // 로그인이 성공한 경우, ResponseEntity를 사용하여 응답을
            // 생성합니다. ResponseEntity.ok()는 HTTP 응답 상태 코드
            // 200(OK)를 의미합니다. header(JWTProvider.HEADER, jwt)
            // 는 응답 헤더에 JWT 토큰을 추가하는 역할을 합니다.
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
        // 사용자 로그인을 시도하고, 로그인이 성공하면 JWT 토큰을 헤더에
        // 추가하여 성공적인 응답을 반환합니다. 만약 로그인실패하면
        // RuntimeException이 발생하고, 이를 globalExceptionHandler를
        // 통해 처리하여 오류 메시지를 반환합니다.
    }


    @PostMapping("/users/{id}/update-password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Integer id, // 경로에 있는 id를 변수로 받아옴
            @RequestBody @Valid UserRequest.UpdatePasswordDTO requestDTO, Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails, // 인증필요
            HttpServletRequest request) {

        // 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 로그인된 id랑 /id/랑 동일하면 권한있음
        // 권한 체크 (디비를 조회하지 않아도 체크할 수 있는 것)
        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기
        try {
            userService.updatePassword(requestDTO, id);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 클라이언트로 부터 전달된 데이터는 신뢰할 수 없다.
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Integer id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        // 권한 체크 (디비를 조회하지 않아도 체크할 수 있는 것)
        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다:" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기
        try {
            UserResponse.FindById responseDTO = userService.findById(id);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 이메일 중복체크 (기능에는 없지만 사용중)
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.EmailCheckDTO emailCheckDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> user) {
        return ResponseEntity.ok().header(JWTProvider.HEADER, "").body(ApiUtils.success(null));
    }
}
