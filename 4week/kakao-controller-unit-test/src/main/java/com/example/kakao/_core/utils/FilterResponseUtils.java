package com.example.kakao._core.utils;

import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception403;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse resp, Exception401 e) throws IOException {
        resp.setStatus(e.status().value()); //401
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper(); //직접 objectmapper로 json으로 컨버팅, 버퍼로 전달
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception403 e) throws IOException {
        resp.setStatus(e.status().value()); //403
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }
}
