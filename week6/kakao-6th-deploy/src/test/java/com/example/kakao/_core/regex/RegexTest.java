package com.example.kakao._core.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void korean_is_ok_test() throws Exception {
        String value = "한글";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    @Test
    public void korean_is_not_ok_test() throws Exception {
        String value = "abc";
        boolean result = Pattern.matches("^[^ㄱ-ㅎㅏ-ㅣ가-힣]*$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    // chatgpt
    @Test
    public void only_email_ok_test(){
        String value = "ssar@nate.com";
        boolean result = Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }

    @Test
    public void comb_num_alpha_special_not_blank_test(){
        String value = "s6!안";
        boolean result = Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", value);
        System.out.println("테스트 : " + result);

        Assertions.assertTrue(result);
    }
}
