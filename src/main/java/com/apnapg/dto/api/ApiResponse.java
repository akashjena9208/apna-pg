package com.apnapg.dto.api;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message,
        Instant timestamp
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, Instant.now());
    }
}


//package com.apnapg.dto.api;
//
//import java.time.Instant;
//
//public record ApiResponse<T>(
//        boolean success,
//        int status,
//        String message,
//        T data,
//        Instant timestamp
//) {
//
//    public static <T> ApiResponse<T> success(int status, String message, T data) {
//        return new ApiResponse<>(true, status, message, data, Instant.now());
//    }
//
//    public static <T> ApiResponse<T> success(int status, String message) {
//        return new ApiResponse<>(true, status, message, null, Instant.now());
//    }
//}
