package com.example.kakao.user;


import com.example.kakao._core.errors.exception.user.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//@SpringBootTest
@DisplayName("유저_서비스_테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserJPARepository userJPARepository;

    private BCryptPasswordEncoder testPasswordEncoder = new BCryptPasswordEncoder();

    @DisplayName("유저_생성_테스트_성공")
    @Test
    public void user_join_test() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = createUserJoinRequest();
        User user = joinDTO.toEntity();
        int fakeId = 2;
        ReflectionTestUtils.setField(user, "id", fakeId);
        ReflectionTestUtils.setField(user, "password", testPasswordEncoder.encode(user.getPassword()));

        // mocking
        given(passwordEncoder.encode(joinDTO.getPassword()))
                .willReturn(testPasswordEncoder.encode(joinDTO.getPassword()));
        given(userJPARepository.save(any())).willReturn(user);
        given(userJPARepository.findById(fakeId)).willReturn(Optional.of(user));

        // when
        int userId = userService.join(joinDTO);

        // then
        User findUser = userJPARepository.findById(fakeId).get();

        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());
    }

    @DisplayName("유저_생성_테스트_실패_알수없는_에러")
    @Test
    public void user_join_test_fail_unknown_error() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = createUserJoinRequest();
        User user = joinDTO.toEntity();
        int fakeId = 2;
        ReflectionTestUtils.setField(user, "id", fakeId);
        ReflectionTestUtils.setField(user, "password", testPasswordEncoder.encode(user.getPassword()));

        // mocking
        given(userJPARepository.save(any())).willThrow(new RuntimeException("알 수 없는 에러"));

        // when & then
        assertThatThrownBy(() -> userService.join(joinDTO)).isInstanceOf(UserException.UserSaveException.class);

    }

    @DisplayName("유저_로그인_테스트_성공")
    @Test
    public void user_login_test() {
        // given
        UserRequest.LoginDTO loginDTO = createUserLoginRequest();
        User user = User.builder()
                .email(loginDTO.getEmail())
                .password(testPasswordEncoder.encode("meta12!"))
                .build();

        // mocking
        given(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
                .willReturn(testPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword()));
        given(userJPARepository.findByEmail(loginDTO.getEmail()))
                .willReturn(Optional.of(user));

        // when
        String jwtToken = userService.login(loginDTO);

        // then
        assertThat(jwtToken.startsWith("Bearer ")).isEqualTo(true);
    }

    @DisplayName("유저_로그인_테스트_실패_없는_이메일")
    @Test
    public void user_login_test_fail_email_not_found() {
        // given
        UserRequest.LoginDTO loginDTO = createUserLoginRequest();

        // mocking
        given(userJPARepository.findByEmail(loginDTO.getEmail()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.login(loginDTO)).isInstanceOf(UserException.UserNotFoundByEmailException.class);

    }

    @DisplayName("유저_로그인_테스트_실패_비밀번호_불일치")
    @Test
    public void user_login_test_fail_wrong_password() {
        // given
        UserRequest.LoginDTO loginDTO = createUserLoginRequest();
        User user = User.builder()
                .email(loginDTO.getEmail())
                .password(testPasswordEncoder.encode("wrongPassword"))
                .build();

        // mocking
        given(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
                .willReturn(testPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword()));
        given(userJPARepository.findByEmail(loginDTO.getEmail()))
                .willReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> userService.login(loginDTO)).isInstanceOf(UserException.UserPasswordMismatchException.class);
    }


    @DisplayName("중복_이메일_체크_테스트")
    @Test
    public void email_check_test() {
        // given
        String email = "ssarmango@nate.com";

        // mocking
        given(userJPARepository.findByEmail(email)).willReturn(Optional.empty());

        // when & then
        userService.sameCheckEmail(email);
    }

    @DisplayName("중복_이메일_체크_테스트_실패_중복_존재")
    @Test
    public void email_check_test_fail_already_exist() {
        // given
        String email = "ssarmango@nate.com";
        User user = User.builder().email(email).build();

        // mocking
        given(userJPARepository.findByEmail(email)).willReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> userService.sameCheckEmail(email)).isInstanceOf(UserException.SameEmailExistException.class);
    }

    private UserRequest.JoinDTO createUserJoinRequest() {
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setEmail("ssarmango@nate.com");
        joinDTO.setPassword("meta12!");
        joinDTO.setUsername("ssarmango");
        return joinDTO;
    }

    private UserRequest.LoginDTO createUserLoginRequest() {
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssarmango@nate.com");
        loginDTO.setPassword("meta12!");
        return loginDTO;
    }
}
