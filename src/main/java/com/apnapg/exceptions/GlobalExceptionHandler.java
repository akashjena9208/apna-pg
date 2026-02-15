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
            HttpServletRequest req) {
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
