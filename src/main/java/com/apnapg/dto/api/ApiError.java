package com.apnapg.dto.api;

import com.apnapg.enums.ErrorCode;

import java.time.Instant;

public record ApiError(
        boolean success,
        int status,
        ErrorCode error,
        String message,
        String path,
        Instant timestamp
) {

    public static ApiError of(
            int status,
            ErrorCode error,
            String message,
            String path) {

        return new ApiError(
                false,
                status,
                error,
                message,
                path,
                Instant.now()
        );
    }
}


//package com.apnapg.dto.api;
//import com.apnapg.enums.ErrorCode;
//
//import java.time.Instant;
//
//public record ApiError(
//        boolean success,
//        int status,
//        ErrorCode error,
//        String message,
//        String path,
//        Instant timestamp
//) {
//
//    public static ApiError of(int status, ErrorCode error, String message, String path) {
//        return new ApiError(false, status, error, message, path, Instant.now());
//    }
//}
