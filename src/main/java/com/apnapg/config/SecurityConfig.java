//package com.apnapg.config;
//
//import com.apnapg.security.CustomAccessDeniedHandler;
//import com.apnapg.security.CustomAuthenticationEntryPoint;
//import com.apnapg.security.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.*;
//
//import java.util.List;
//
//@Configuration
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtFilter;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//    private final CustomAuthenticationEntryPoint authEntryPoint;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                // Disable CSRF (because we use JWT)
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // Enable CORS
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                // Stateless session (JWT based)
//                .sessionManagement(sm ->
//                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // Custom 401 & 403 handlers
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(authEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)
//                )
//
//                // Authorization rules
//                .authorizeHttpRequests(auth -> auth
//
//                        // ================= PUBLIC =================
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/api/owners/register",
//                                "/api/tenants/register",
//                                "/api/pgs/search",
//                                "/api/contact/**",
//                                "/ws/**"
//                        ).permitAll()
//
//                        // ================= OWNER =================
//                        .requestMatchers("/api/owners/**")
//                        .hasRole("OWNER")
//
//                        // ================= TENANT =================
//                        .requestMatchers("/api/tenants/**")
//                        .hasRole("TENANT")
//
//                        // ================= ANY OTHER =================
//                        .anyRequest().authenticated()
//                )
//
//                // Add JWT filter
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    // ================= CORS CONFIG =================
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("http://localhost:5173"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }
//
//    // ================= AUTH MANAGER =================
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    // ================= PASSWORD ENCODER =================
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
package com.apnapg.config;

import com.apnapg.security.CustomAccessDeniedHandler;
import com.apnapg.security.CustomAuthenticationEntryPoint;
import com.apnapg.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    // ======================================================
    // SECURITY FILTER CHAIN
    // ======================================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF (JWT based API)
                .csrf(AbstractHttpConfigurer::disable)

                // Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Exception handling
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // Authorization rules
//                .authorizeHttpRequests(auth -> auth
//
//                        // ================= PUBLIC =================
//                        .requestMatchers(
//                                "/error",
//                                "/api/auth/**",
//                                "/api/owners/register",
//                                "/api/tenants/register",
//                                "/api/pgs/search/**",
//                                "/api/contact/**",
//                                "/ws/**"
//                        ).permitAll()
//
//                        // ================= OWNER =================
//                        .requestMatchers("/api/owners/**").hasRole("OWNER")
//                        .requestMatchers("/api/pgs/**").hasRole("OWNER")
//                        .requestMatchers("/api/rooms/**").hasRole("OWNER")
//
//                        // ================= TENANT =================
//                        .requestMatchers("/api/tenants/**").hasRole("TENANT")
//                        .requestMatchers("/api/reviews/**").hasRole("TENANT")
//                        .requestMatchers("/api/complaints/**").authenticated()
//                        .requestMatchers("/api/chat/**").authenticated()
//
//                        // ================= ANY OTHER =================
//                        .anyRequest().authenticated()
//                )

                .authorizeHttpRequests(auth -> auth

                        // ================= PUBLIC =================
                        .requestMatchers(
                                "/error",
                                "/api/auth/**",
                                "/api/owners/register",
                                "/api/tenants/register",
                                "/api/pgs/search/**",
                                "/api/contact/**",
                                "/ws/**"
                        ).permitAll()

                        // ================= OWNER =================
                        .requestMatchers(HttpMethod.PUT,
                                "/api/tenants/*/allocate/*")
                        .hasRole("OWNER")

                        .requestMatchers("/api/owners/**").hasRole("OWNER")
                        .requestMatchers("/api/pgs/**").hasRole("OWNER")
                        .requestMatchers("/api/rooms/**").hasRole("OWNER")

                        // ================= TENANT =================
                        .requestMatchers("/api/tenants/me/**").hasRole("TENANT")
                        .requestMatchers(HttpMethod.PUT, "/api/tenants/vacate").hasRole("TENANT")

                        .requestMatchers("/api/reviews/**").hasRole("TENANT")

                        // ================= SHARED =================
                        .requestMatchers("/api/complaints/**").authenticated()
                        .requestMatchers("/api/chat/**").authenticated()

                        // ================= ANY OTHER =================
                        .anyRequest().authenticated()
                )


                // Add JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ======================================================
    // CORS CONFIG
    // ======================================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // ======================================================
    // AUTHENTICATION MANAGER
    // ======================================================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ======================================================
    // PASSWORD ENCODER (IMPORTANT!)
    // ======================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
