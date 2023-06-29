package com.example.kakao.unkown;

import com.example.kakao.user.StringArrayConverter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StringArrayConverterTest {

    @Test
    public void convertToDatabaseColumn_test(){
        // given
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        // when
        StringArrayConverter sac = new StringArrayConverter();
        String result = sac.convertToDatabaseColumn(roles);

        // then
        System.out.println("테스트 : "+result);
    }

    @Test
    public void convertToEntityAttribute_test(){
        // given
        String dbData = "ROLE_USER,ROLE_ADMIN";

        // when
        StringArrayConverter sac = new StringArrayConverter();
        List<String> result = sac.convertToEntityAttribute(dbData);

        // then
        System.out.println("테스트 : "+result);
    }

}
