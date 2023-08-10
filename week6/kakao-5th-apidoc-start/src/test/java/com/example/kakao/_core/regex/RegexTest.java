package com.example.kakao._core.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void 정상적인한글만된다_test() throws Exception {
        String value = "한글";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    @Test
    public void 한글은안된다_test() throws Exception {
        String value = "abc";
        boolean result = Pattern.matches("^[^ㄱ-ㅎㅏ-ㅣ가-힣]*$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    // chatgpt
    @Test
    public void 이메일형식만된다_test(){
        String value = "ssar@nate.com";
        boolean result = Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    @Test
    public void 영문숫자특수문자포함_공백안됨_test(){
        String value = "s6!안";
        boolean result = Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }
}
