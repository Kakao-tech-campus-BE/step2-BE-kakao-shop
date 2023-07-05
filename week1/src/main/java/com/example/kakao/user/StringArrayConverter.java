package com.example.kakao.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringArrayConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute.stream().map(String::valueOf).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if(dbData == null) { // JPA save는 select부터 하기 때문에, null을 체크해줘야 한다.
            return Collections.emptyList();
        } else {
            return Arrays.stream(dbData.split(SPLIT_CHAR))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        }
    }
}
