/// ///package com.apnapg.config;
/// ///
/// ///import org.springframework.context.annotation.Configuration;
/// ///import org.springframework.messaging.simp.config.MessageBrokerRegistry;
/// ///import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
/// ///import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
/// ///import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/// ///
/// ///@Configuration
/// ///@EnableWebSocketMessageBroker
/// ///public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
/// ///
/// ///    @Override
/// ///    public void configureMessageBroker(MessageBrokerRegistry config) {
/// ///        config.enableSimpleBroker("/topic"); // messages sent to /topic/...
/// ///        config.setApplicationDestinationPrefixes("/app"); // messages sent to /app/...
/// ///    }
/// ///
/// ///    @Override
/// ///    public void registerStompEndpoints(StompEndpointRegistry registry) {
/// ///        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
/// ///    }
/// ///}
/// /package com.apnapg.config;
/// /import com.apnapg.security.WebSocketAuthInterceptor;
/// /import com.apnapg.service.JwtService;
/// /import lombok.RequiredArgsConstructor;
/// /import org.springframework.context.annotation.Configuration;
/// /import org.springframework.messaging.simp.config.ChannelRegistration;
/// /import org.springframework.messaging.simp.config.MessageBrokerRegistry;
/// /import org.springframework.web.socket.config.annotation.*;
/// /
/// /@Configuration
/// /@EnableWebSocketMessageBroker
/// /@RequiredArgsConstructor
/// /public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
/// /
/// /    private final JwtService jwtService;
/// /    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
/// /
/// /
/// /    @Override
/// /    public void configureMessageBroker(MessageBrokerRegistry registry) {
/// /
/// /        registry.enableSimpleBroker("/topic", "/queue");
/// /        registry.setApplicationDestinationPrefixes("/app");
/// /        registry.setUserDestinationPrefix("/user");
/// /    }
/// /
/// /    @Override
/// /    public void registerStompEndpoints(StompEndpointRegistry registry) {
/// /
/// /        registry.addEndpoint("/ws")
/// /                .setAllowedOriginPatterns("*")
/// /                .withSockJS();
/// /    }
/// /
/// /    @Override
/// /    public void configureClientInboundChannel(ChannelRegistration registration) {
/// /        registration.interceptors(webSocketAuthInterceptor);
/// /    }
/// /
/// /}
//package com.apnapg.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import com.apnapg.security.WebSocketAuthChannelInterceptor;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final WebSocketAuthChannelInterceptor webSocketAuthChannelInterceptor;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/topic", "/queue");
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.setUserDestinationPrefix("/user");
//    }
//
//    @Override
//    public void registerStompEndpoints(
//            org.springframework.web.socket.config.annotation.StompEndpointRegistry registry) {
//
//        registry.addEndpoint("/ws")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(webSocketAuthChannelInterceptor);
//    }
//
//}
package com.apnapg.config;

import com.apnapg.security.WebSocketAuthChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketAuthChannelInterceptor authInterceptor;

    // =============================================
    // Register WebSocket endpoint
    // =============================================
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // ⚠ restrict in production
                .withSockJS(); // optional but useful for fallback
    }

    // =============================================
    // Configure message broker
    // =============================================
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Client subscribes to these prefixes
        registry.enableSimpleBroker("/topic", "/queue");

        // Messages sent from client must start with /app
        registry.setApplicationDestinationPrefixes("/app");

        // For private messaging
        registry.setUserDestinationPrefix("/user");
    }

    // =============================================
    // Attach JWT Auth Interceptor
    // =============================================
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authInterceptor);
    }
}
