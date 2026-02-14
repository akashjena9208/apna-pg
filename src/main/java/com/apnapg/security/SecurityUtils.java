////package com.apnapg.security;
////
////import org.springframework.security.core.context.SecurityContextHolder;
////
////public class SecurityUtils {
////
////    public static String getCurrentUserEmail() {
////        return SecurityContextHolder.getContext()
////                .getAuthentication()
////                .getName(); // email
////    }
////}
//package com.apnapg.security;
//
//import com.apnapg.entity.User;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//public final class SecurityUtils {
//
//    private SecurityUtils() {}
//
//    public static CustomUserDetails getCurrentUserDetails() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
//            throw new IllegalStateException("No authenticated user found");
//        }
//
//        return userDetails;
//    }
//
//    public static Long getCurrentUserId() {
//        return getCurrentUserDetails().getUserId();
//    }
//
//    public static String getCurrentUserRole() {
//        return getCurrentUserDetails().getRole();
//    }
//}
package com.apnapg.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserDetails getCurrentUserDetails() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("No authenticated user found");
        }

        return userDetails;
    }

    public static Long getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }

    public static String getCurrentUserRole() {
        return getCurrentUserDetails().getRole();
    }
}
