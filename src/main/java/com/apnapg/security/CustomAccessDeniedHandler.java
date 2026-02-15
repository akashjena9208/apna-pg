package com.apnapg.security;

import com.apnapg.dto.api.ApiError;
import com.apnapg.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex) throws IOException {

        log.warn("FORBIDDEN | URI={} | {}", request.getRequestURI(), ex.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiError error = ApiError.of(
                HttpStatus.FORBIDDEN.value(),
                ErrorCode.FORBIDDEN,
                "You do not have permission to access this resource",
                request.getRequestURI()
        );

        objectMapper.writeValue(response.getWriter(), error);
    }
}



//package com.apnapg.security;
//
//import com.apnapg.dto.api.ApiError;
//import com.apnapg.enums.ErrorCode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//    private final ObjectMapper objectMapper;
//
//    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void handle(HttpServletRequest request,
//                       HttpServletResponse response,
//                       AccessDeniedException ex) throws IOException {
//
//        log.warn("Forbidden: {} | {}", request.getRequestURI(), ex.getMessage());
//
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
////
////        ApiError error = ApiError.of(
////                HttpStatus.FORBIDDEN.value(),
////                ErrorCode.FORBIDDEN,
////                "You do not have permission to access this resource",
////                request.getRequestURI()
////        );
//        ApiError error = ApiError.of(
//                HttpStatus.FORBIDDEN.value(),
//                ErrorCode.FORBIDDEN.name(),   // ✅ FIX
//                "You do not have permission to access this resource",
//                request.getRequestURI()
//        );
//
//
//        objectMapper.writeValue(response.getWriter(), error);
//    }
//}



/*

Client Request
     ↓
Spring Security Filter Chain  ← (YOUR 401/403 HAPPENS HERE)
     ↓
DispatcherServlet
     ↓
Controller
     ↓
GlobalExceptionHandler works HERE


❓ “If you already have a Global Exception Handler, why do you still use AuthenticationEntryPoint and AccessDeniedHandler?”

Answer: In Spring Boot, exception handling happens at different layers of the request lifecycle. A @RestControllerAdvice (GlobalExceptionHandler) only works after the request reaches the controller layer.But security failures occur before that, inside the Spring Security filter chain.
So they must be handled separately.

🔄 Request Flow (Explain This in Interview)

1- Client sends request
2- Request enters Spring Security Filter Chain
3- Authentication & authorization happen here
4- If security fails → request is blocked before reaching controller
5- Only successful requests go to:
       -  DispatcherServlet
       -  Controller
        - Service layer
So:

Scenario	Where exception occurs	Who handles it
Invalid JWT / No token	Security Filter	AuthenticationEntryPoint
User lacks role	Security Filter	AccessDeniedHandler
Validation error (@Valid)	Controller	GlobalExceptionHandler
DB constraint error	Service/Repo	GlobalExceptionHandler
Business exception	Service	GlobalExceptionHandler



🚫 Why GlobalExceptionHandler cannot catch Security exceptions

Because:Spring Security throws AuthenticationException and AccessDeniedException inside filters Filters run before DispatcherServlet

@RestControllerAdvice is part of Spring MVC, not Security
So the request never reaches MVC → Global handler never triggers.

🏗 Production Architecture Principle
We separate concerns by layer:
Layer	Responsibility
Security Layer	Who are you? What can you access?
Controller Layer	Input validation
Service Layer	Business logic
Persistence Layer	Data operations
Each layer has its own exception handling strategy.

🧠 The Interview “One Line Summary”
“GlobalExceptionHandler handles application-level exceptions after the request reaches the controller, while AuthenticationEntryPoint and AccessDeniedHandler handle security exceptions thrown earlier in the filter chain. Since security runs before MVC, those exceptions never reach the global handler, so we must handle them separately to maintain consistent API error responses.”

🎯 Bonus (Shows Senior Understanding)
Using all three ensures consistent JSON responses across security failures and business errors, which is critical for frontend clients and API consumers.



*/