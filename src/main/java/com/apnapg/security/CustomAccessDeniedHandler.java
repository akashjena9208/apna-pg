//package com.apnapg.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void handle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AccessDeniedException accessDeniedException
//    ) throws IOException {
//
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.setContentType("application/json");
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("success", false);
//        body.put("status", 403);
//        body.put("error", "FORBIDDEN");
//        body.put("message", "You do not have permission to access this resource");
//        body.put("path", request.getRequestURI());
//        body.put("timestamp", LocalDateTime.now());
//
//        response.getOutputStream()
//                .println(objectMapper.writeValueAsString(body));
//    }
//}
package com.apnapg.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("status", 403);
        body.put("error", "FORBIDDEN");
        body.put("message", "You do not have permission to access this resource");
        body.put("path", request.getRequestURI());
        body.put("timestamp", LocalDateTime.now().toString()); // ✅ FIX

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
