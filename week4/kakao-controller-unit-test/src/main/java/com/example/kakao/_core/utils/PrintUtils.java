package com.example.kakao._core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PrintUtils {
    public static String getPrettyString(String str) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode jsonNode = objectMapper.readTree(str);
        return objectMapper.writeValueAsString(jsonNode);
    }
}