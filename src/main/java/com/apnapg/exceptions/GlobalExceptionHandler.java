package com.apnapg.exceptions;

import com.apnapg.dto.api.ApiError;
import com.apnapg.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // VALIDATION ERROR
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("VALIDATION ERROR | URI={} | {}", req.getRequestURI(), message);

        return build(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR,
                message,
                req);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest req) {

        log.warn("CONSTRAINT VIOLATION | URI={} | {}", req.getRequestURI(), ex.getMessage());

        return build(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR,
                ex.getMessage(),
                req);
    }

    // ===============================
    // DATABASE
    // ===============================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest req) {

        log.error("DATABASE ERROR | URI={} | {}", req.getRequestURI(), ex.getMessage(), ex);

        return build(HttpStatus.CONFLICT,
                ErrorCode.CONFLICT,
                "Database constraint violation",
                req);
    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity<ApiError> handleNotFound(
//            HttpServletRequest req) {
@ExceptionHandler(EmptyResultDataAccessException.class)
public ResponseEntity<ApiError> handleNotFound(
        EmptyResultDataAccessException ex,
        HttpServletRequest req){
        return build(HttpStatus.NOT_FOUND,
                ErrorCode.NOT_FOUND,
                "Resource not found",
                req);
    }

    // ===============================
    // SECURITY (Controller Layer)
    // ===============================

    //We Need Business-Level 403
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiError> handleForbidden(
            ForbiddenOperationException ex,
            HttpServletRequest req) {

        return build(HttpStatus.FORBIDDEN,
                ErrorCode.FORBIDDEN,
                ex.getMessage(),
                req);
    }

    //We Need Business-Level 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest req) {

        return build(HttpStatus.UNAUTHORIZED,
                ErrorCode.UNAUTHORIZED,
                ex.getMessage(),
                req);
    }
    //1️⃣ Security Layer 401(CustomAuthenticationEntryPoint) / 403(CustomAccessDeniedHandler)
    //2️⃣ Business Layer 403(ForbiddenOperationException) / 401(UnauthorizedException)
// 401 and 403 from authentication/authorization are handled at the security filter layer. If business logic needs to deny access, I use a custom domain exception rather than Spring Security exceptions, and handle it in GlobalExceptionHandler.
    //403
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> handleAccessDenied(
//            AccessDeniedException ex,
//            HttpServletRequest req) {
//
//        log.warn("ACCESS DENIED | URI={} | {}", req.getRequestURI(), ex.getMessage());
//
//        return build(HttpStatus.FORBIDDEN,
//                ErrorCode.FORBIDDEN,
//                "Access denied",
//                req);
//    }

    //401
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiError> handleAuthFailure(
//            AuthenticationException ex,
//            HttpServletRequest req) {
//
//        log.warn("AUTH FAILURE | URI={} | {}", req.getRequestURI(), ex.getMessage());
//
//        return build(HttpStatus.UNAUTHORIZED,
//                ErrorCode.UNAUTHORIZED,
//                "Authentication failed",
//                req);
//    }


    // ===============================
    // GLOBAL FALLBACK
    // ===============================
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleAll(
//            Exception ex,
//            HttpServletRequest req) {
//
//        log.error("UNEXPECTED ERROR | URI={} | {}", req.getRequestURI(), ex.getMessage(), ex);
//
//        return build(HttpStatus.INTERNAL_SERVER_ERROR,
//                ErrorCode.INTERNAL_ERROR,
//                "Something went wrong",
//                req);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();  // 🔥 IMPORTANT

        ApiError error = ApiError.of(
                500,
                ErrorCode.INTERNAL_ERROR,
                ex.getMessage(),   // show real error
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(error);
    }


    // ===============================
    // CENTRAL BUILDER
    // ===============================
    private ResponseEntity<ApiError> build(
            HttpStatus status,
            ErrorCode code,
            String message,
            HttpServletRequest req) {

        return ResponseEntity
                .status(status)
                .body(ApiError.of(
                        status.value(),
                        code,
                        message,
                        req.getRequestURI()
                ));
    }
}


//import com.apnapg.dto.api.ApiError;
//import com.apnapg.enums.ErrorCode;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // =========================
//    // VALIDATION ERRORS
//    // =========================
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
//                                                     HttpServletRequest req) {
//
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        return build(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, message, req);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex,
//                                                              HttpServletRequest req) {
//
//        return build(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, ex.getMessage(), req);
//    }
//
//    // =========================
//    // WEB ERRORS
//    // =========================
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<ApiError> handleMethodNotSupported(HttpServletRequest req) {
//        return build(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.BAD_REQUEST,
//                "HTTP method not supported", req);
//    }
//
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<ApiError> handleMediaType(HttpServletRequest req) {
//        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.BAD_REQUEST,
//                "Unsupported media type", req);
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<ApiError> handleMissingParam(Exception ex, HttpServletRequest req) {
//        return build(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage(), req);
//    }
//
//    // =========================
//    // DATABASE ERRORS
//    // =========================
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiError> handleDBConflict(HttpServletRequest req) {
//        return build(HttpStatus.CONFLICT, ErrorCode.CONFLICT,
//                "Database constraint violation", req);
//    }
//
//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity<ApiError> handleEmptyResult(HttpServletRequest req) {
//        return build(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND,
//                "Resource not found", req);
//    }
//
//    // =========================
//    // SECURITY ERRORS
//    // =========================
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> handleAccessDenied(HttpServletRequest req) {
//        return build(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN,
//                "Access denied", req);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiError> handleAuthFailure(HttpServletRequest req) {
//        return build(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED,
//                "Authentication failed", req);
//    }
//
//    // =========================
//    // CUSTOM DOMAIN ERRORS
//    // =========================
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex,
//                                                   HttpServletRequest req) {
//
//        return build(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, ex.getMessage(), req);
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex,
//                                                     HttpServletRequest req) {
//
//        return build(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, ex.getMessage(), req);
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<ApiError> handleConflict(ConflictException ex,
//                                                   HttpServletRequest req) {
//
//        return build(HttpStatus.CONFLICT, ErrorCode.CONFLICT, ex.getMessage(), req);
//    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException ex,
//                                                       HttpServletRequest req) {
//
//        return build(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, ex.getMessage(), req);
//    }
//
//    // =========================
//    // GLOBAL FALLBACK
//    // =========================
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGlobal(Exception ex, HttpServletRequest req) {
//
//        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR,
//                "Unexpected server error", req);
//    }
//
//    // =========================
//    // HELPER METHOD
//    // =========================
//    private ResponseEntity<ApiError> build(HttpStatus status,
//                                           ErrorCode code,
//                                           String message,
//                                           HttpServletRequest req) {
//
//        return ResponseEntity
//                .status(status)
//                .body(ApiError.of(
//                        status.value(),
//                        code,
//                        message,
//                        req.getRequestURI()
//                ));
//    }
//}
