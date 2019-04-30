package com.bzy.springtemplate.security;

import com.bzy.springtemplate.exception.ExceptionResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler, AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        reply(response);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        reply(response);
    }

    private void reply(HttpServletResponse response) throws IOException {
        ExceptionResource resource = new ExceptionResource(UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(objectMapper.writeValueAsString(resource));
    }
}
