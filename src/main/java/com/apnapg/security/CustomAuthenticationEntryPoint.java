////package com.apnapg.security;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import org.springframework.http.HttpStatus;
////import org.springframework.security.core.AuthenticationException;
////import org.springframework.security.web.AuthenticationEntryPoint;
////import org.springframework.stereotype.Component;
////
////import java.io.IOException;
////import java.time.LocalDateTime;
////import java.util.LinkedHashMap;
////import java.util.Map;
////
////@Component
////public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
////
////    private final ObjectMapper objectMapper = new ObjectMapper();
////
////    @Override
////    public void commence(
////            HttpServletRequest request,
////            HttpServletResponse response,
////            AuthenticationException authException
////    ) throws IOException {
////
////        response.setStatus(HttpStatus.UNAUTHORIZED.value());
////        response.setContentType("application/json");
////
////        Map<String, Object> body = new LinkedHashMap<>();
////        body.put("success", false);
////        body.put("status", 401);
////        body.put("error", "UNAUTHORIZED");
////        body.put("message", "Authentication is required to access this resource");
////        body.put("path", request.getRequestURI());
////        body.put("timestamp", LocalDateTime.now());
////
////        response.getOutputStream()
////                .println(objectMapper.writeValueAsString(body));
////    }
////}
//
//package com.apnapg.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Component
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    private final ObjectMapper objectMapper;
//
//    public CustomAuthenticationEntryPoint() {
//        this.objectMapper = new ObjectMapper();
//        this.objectMapper.registerModule(new JavaTimeModule());
//    }
//
//    @Override
//    public void commence(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException authException
//    ) throws IOException {
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("success", false);
//        body.put("status", 401);
//        body.put("error", "UNAUTHORIZED");
//        body.put("message", authException.getMessage());
//        body.put("path", request.getRequestURI());
//        body.put("timestamp", LocalDateTime.now());
//
//        response.getWriter().write(objectMapper.writeValueAsString(body));
//    }
//}
//
package com.apnapg.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("status", 401);
        body.put("error", "UNAUTHORIZED");
        body.put("message", "Full authentication is required to access this resource");
        body.put("path", request.getRequestURI());
        body.put("timestamp", LocalDateTime.now().toString()); // ✅ FIX

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
