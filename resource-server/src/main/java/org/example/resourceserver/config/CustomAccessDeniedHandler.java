package org.example.resourceserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.resourceserver.error.Error;
import org.example.resourceserver.error.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        Error error = new Error("Forbidden", HttpStatus.FORBIDDEN.value());
        Message message = new Message(false, error);

        response.getOutputStream().println(
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message)
        );
    }
}
