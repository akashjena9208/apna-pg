////package com.apnapg.security;
////
////import com.apnapg.service.JwtService;
////import lombok.RequiredArgsConstructor;
////import org.springframework.messaging.*;
////import org.springframework.messaging.simp.stomp.*;
////import org.springframework.messaging.support.ChannelInterceptor;
////import org.springframework.messaging.support.MessageHeaderAccessor;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.stereotype.Component;
////
////@Component
////@RequiredArgsConstructor
////public class WebSocketAuthInterceptor implements ChannelInterceptor {
////
////    private final JwtService jwtService;
////    private final CustomUserDetailsService userDetailsService;
////
////    @Override
////    public Message<?> preSend(Message<?> message, MessageChannel channel) {
////
////        StompHeaderAccessor accessor =
////                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
////
////        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
////
////            String token = accessor.getFirstNativeHeader("Authorization");
////
////            if (token != null && token.startsWith("Bearer ")) {
////
////                token = token.substring(7);
////
////                String email = jwtService.extractEmail(token);
////
////                var userDetails =
////                        userDetailsService.loadUserByUsername(email);
////
////                accessor.setUser(
////                        new UsernamePasswordAuthenticationToken(
////                                userDetails,
////                                null,
////                                userDetails.getAuthorities()
////                        )
////                );
////            }
////        }
////
////        return message;
////    }
////}
//package com.apnapg.security;
//
//import com.apnapg.service.JwtService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {
//
//    private final JwtService jwtService;
//    private final CustomUserDetailsService userDetailsService;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//
//            String authHeader = accessor.getFirstNativeHeader("Authorization");
//
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//
//                String token = authHeader.substring(7);
//
//                try {
//                    String email = jwtService.extractEmail(token);
//
//                    var userDetails =
//                            userDetailsService.loadUserByUsername(email);
//
//                    var authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails,
//                                    null,
//                                    userDetails.getAuthorities()
//                            );
//
//                    accessor.setUser(authentication);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                } catch (Exception ex) {
//                    log.warn("WebSocket JWT invalid: {}", ex.getMessage());
//                }
//            }
//        }
//
//        return message;
//    }
//}
package com.apnapg.security;

import com.apnapg.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                String email = jwtService.extractEmail(token);

                CustomUserDetails userDetails =
                        (CustomUserDetails) userDetailsService.loadUserByUsername(email);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                accessor.setUser(
                        new StompPrincipal(userDetails.getUserId().toString())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("WebSocket authenticated for userId: {}", userDetails.getUserId());
            }
        }

        return message;
    }

}
