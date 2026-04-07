package com.fc.springsecurity_example2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("msg", "登陆失败: "+exception.getMessage());
        map.put("status", 500);
        response.setContentType("application/json;charset=utf-8");
        String str = new ObjectMapper().writeValueAsString(map);
        response.getWriter().println(str);
    }
}
