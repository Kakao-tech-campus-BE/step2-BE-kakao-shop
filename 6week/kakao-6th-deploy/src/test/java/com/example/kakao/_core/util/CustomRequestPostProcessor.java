package com.example.kakao._core.util;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class CustomRequestPostProcessor implements RequestPostProcessor {
    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("User-Agent", "TestAgent");
        return request;
    }
}