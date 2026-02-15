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

        registry.addEndpoint("/ws").setAllowedOriginPatterns("*") // ⚠ restrict in production
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
