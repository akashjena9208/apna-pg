package com.apnapg.security;

import com.apnapg.dto.api.ApiError;
import com.apnapg.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex) throws IOException {

        log.warn("UNAUTHORIZED | URI={} | {}", request.getRequestURI(), ex.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiError error = ApiError.of(
                HttpStatus.UNAUTHORIZED.value(),
                ErrorCode.UNAUTHORIZED,
                "Authentication is required to access this resource",
                request.getRequestURI()
        );

        objectMapper.writeValue(response.getWriter(), error);
    }
}


//package com.apnapg.security;
//import com.apnapg.dto.api.ApiError;
//import com.apnapg.enums.ErrorCode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    private final ObjectMapper objectMapper;
//
//    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException ex) throws IOException {
//
//        log.warn("Unauthorized: {} | {}", request.getRequestURI(), ex.getMessage());
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
////        ApiError error = ApiError.of(
////                HttpStatus.UNAUTHORIZED.value(),
////                ErrorCode.UNAUTHORIZED,
////                "Authentication is required to access this resource",
////                request.getRequestURI()
////        );
//        ApiError error = ApiError.of(
//                HttpStatus.UNAUTHORIZED.value(),
//                ErrorCode.UNAUTHORIZED.name(),   // ✅ FIX
//                "Authentication is required to access this resource",
//                request.getRequestURI()
//        );
//
//
//        objectMapper.writeValue(response.getWriter(), error);
//    }
//}
