package com.example.kakao._core.utils;

import com.example.kakao._core.security.ForbiddenException;
import com.example.kakao._core.security.NotAuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse resp, NotAuthorizationException e) throws IOException {
        resp.setStatus(e.getErrorCode().getStatus().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.getErrorCode().getMessage());
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, ForbiddenException e) throws IOException {
        resp.setStatus(e.getErrorCode().getStatus().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.getErrorCode().getMessage());
        resp.getWriter().println(responseBody);
    }
}
