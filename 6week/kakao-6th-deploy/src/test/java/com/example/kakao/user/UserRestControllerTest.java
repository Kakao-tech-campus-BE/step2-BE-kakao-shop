package com.example.kakao.user;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.security.JWTProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;


    @DisplayName("/check")
    @Nested
    public class check {
        @DisplayName("이메일 중복 체크 통합 테스트")
        @Test
        public void check_test() throws Exception {
            //given
            UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
            requestDTO.setEmail("thisIsNewEmail@nate.com");

            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions resultActions = mvc.perform(
                    post("/check")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // console
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            System.out.println("테스트 : " + responseBody);

            // verify
            resultActions.andExpect(jsonPath("$.success").value("true"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("실패 케이스")
        @Nested
        class failCases {
            @DisplayName("이메일 중복 체크시 동일한 이메일이 이미 존재하는 경우 예외 발생 통합 테스트")
            @Test
            public void check_alreadyExists_test() throws Exception {
                //given
                UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
                requestDTO.setEmail("ssarmango@nate.com");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/check")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("이메일 중복 체크시 이메일 양식이 아닌 경우 통합 테스트")
            @Test
            public void check_invalidEmailForm_test() throws Exception {
                //given
                UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
                requestDTO.setEmail("ssarmangonate.com");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/check")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }
        }
    }

    @DisplayName("/join")
    @Nested
    public class join {
        @DisplayName("회원가입 통합 테스트")
        @Test
        public void join_test() throws Exception {
            //given
            UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
            requestDTO.setEmail("thisIsNewEmail@nate.com");
            requestDTO.setPassword("meta1234!");
            requestDTO.setUsername("newUserName");

            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions resultActions = mvc.perform(
                    post("/join")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // console
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            System.out.println("테스트 : " + responseBody);

            // verify
            resultActions.andExpect(jsonPath("$.success").value("true"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("실패 케이스")
        @Nested
        class failCases {

            @DisplayName("회원가입 시 이메일 형식이 아닌 경우 예외 발생 통합 테스트")
            @Test
            public void join_invalidEmailForm_test() throws Exception {
                //given
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("thisIsNewEmailnate.com");
                requestDTO.setPassword("meta1234!");
                requestDTO.setUsername("newUserName");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/join")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("회원가입 시 비밀번호 형식이 아닌 경우 예외 발생 통합 테스트")
            @Test
            public void join_invalidPasswordForm_test() throws Exception {
                //given
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("thisIsNewEmail@nate.com");
                requestDTO.setPassword("meta1234");
                requestDTO.setUsername("newUserName");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/join")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("회원가입 시 비밀번호 길이가 잘못된 경우 예외 발생 통합 테스트")
            @Test
            public void join_invalidPasswordLength_test() throws Exception {
                //given
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("thisIsNewEmail@nate.com");
                requestDTO.setPassword("me12!");
                requestDTO.setUsername("newUserName");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/join")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("회원가입 시 동일한 이메일이 존재하는 경우 예외 발생 통합 테스트")
            @Test
            public void join_alreadyExistsEmail_test() throws Exception {
                //given
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("ssarmango@nate.com");
                requestDTO.setPassword("meta1234!");
                requestDTO.setUsername("newUserName");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/join")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다 : ssarmango@nate.com"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("회원가입 시 username의 길이가 잘못된 경우 예외 발생 통합 테스트")
            @Test
            public void join_invalidUsernameLength_test() throws Exception {
                //given
                UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
                requestDTO.setEmail("ssarmango@nate.com");
                requestDTO.setPassword("meta1234!");
                requestDTO.setUsername("short");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/join")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }
        }
    }
    @DisplayName("/login")
    @Nested
    public class login {
        @DisplayName("로그인 통합 테스트")
        @Test
        public void login_test() throws Exception {
            //given
            UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
            requestDTO.setEmail("ssarmango@nate.com");
            requestDTO.setPassword("meta1234!");

            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions resultActions = mvc.perform(
                    post("/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // console
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
            System.out.println("테스트 : " + responseBody);
            System.out.println("테스트 : " + responseHeader);

            // verify
            resultActions.andExpect(jsonPath("$.success").value("true"));
            Assertions.assertTrue(responseHeader.startsWith(JWTProvider.TOKEN_PREFIX));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        }

        @DisplayName("실패 케이스")
        @Nested
        class failCases {
            @DisplayName("로그인 시 이메일 형식이 아닌 경우 예외 발생 통합 테스트")
            @Test
            public void login_invalidEmailForm_fail_test() throws Exception {
                //given
                UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
                requestDTO.setEmail("ssarmangonate.com");
                requestDTO.setPassword("meta1234!");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/login")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
                System.out.println("테스트 : " + responseBody);
                System.out.println("테스트 : " + responseHeader);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                Assertions.assertNull(responseHeader);
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("로그인 시 비밀번호 형식이 아닌 경우 예외 발생 통합 테스트")
            @Test
            public void login_invalidPasswordForm_fail_test() throws Exception {
                //given
                UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
                requestDTO.setEmail("ssarmango@nate.com");
                requestDTO.setPassword("meta1234");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/login")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
                System.out.println("테스트 : " + responseBody);
                System.out.println("테스트 : " + responseHeader);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                Assertions.assertNull(responseHeader);
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("로그인 시 비밀번호 길이가 잘못된 경우 예외 발생 통합 테스트")
            @Test
            public void login_invalidPasswordLength_fail_test() throws Exception {
                //given
                UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
                requestDTO.setEmail("ssarmango@nate.com");
                requestDTO.setPassword("me12!");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/login")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
                System.out.println("테스트 : " + responseBody);
                System.out.println("테스트 : " + responseHeader);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                Assertions.assertNull(responseHeader);
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("로그인 시 회원이 아닌 이메일로 로그인할 때 예외 발생 통합 테스트")
            @Test
            public void login_notUserEmail_fail_test() throws Exception {
                //given
                UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
                requestDTO.setEmail("anonymous@nate.com");
                requestDTO.setPassword("meta1234!");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/login")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
                System.out.println("테스트 : " + responseBody);
                System.out.println("테스트 : " + responseHeader);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다 : anonymous@nate.com"));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                Assertions.assertNull(responseHeader);
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }

            @DisplayName("로그인 시 비밀번호가 잘못된 경우 예외 발생 통합 테스트")
            @Test
            public void login_wrongPassword_fail_test() throws Exception {
                //given
                UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
                requestDTO.setEmail("ssarmango@nate.com");
                requestDTO.setPassword("meta12345!");

                String requestBody = om.writeValueAsString(requestDTO);

                // when
                ResultActions resultActions = mvc.perform(
                        post("/login")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

                // console
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
                System.out.println("테스트 : " + responseBody);
                System.out.println("테스트 : " + responseHeader);

                // verify
                resultActions.andExpect(jsonPath("$.success").value("false"));
                resultActions.andExpect(jsonPath("$.response").isEmpty());
                resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못입력되었습니다 "));
                resultActions.andExpect(jsonPath("$.error.status").value("400"));
                Assertions.assertNull(responseHeader);
                resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            }
        }
    }
}
