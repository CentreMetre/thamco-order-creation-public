//package com.thamco.shop.order.creation.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import java.io.IOException;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfig
//{
//
//    @Value("${okta.oauth2.issuer}")
//    private String issuer;
//    @Value("${okta.oauth2.client-id}")
//    private String clientId;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/order/create").authenticated() // secure /order/create path
//                .anyRequest().permitAll() // allow other paths to be called
//            )
//            .oauth2Login(oauth2 -> oauth2
//                .loginPage("/login") //
//                .defaultSuccessUrl("/", true) // redirect after successful login
//            )
//            .logout(logout -> logout
//                .logoutUrl("/logout") // logout URL
//                .logoutSuccessUrl("/") // redirect after logout
//            )
//            .exceptionHandling(exception -> exception
//                    .authenticationEntryPoint(authenticationEntryPoint()
//            ));
//
//        return http.build();
//    }
//
//        // Define how to handle authentication failures
//    private AuthenticationEntryPoint authenticationEntryPoint() {
//        return (request, response, authException) -> {
//            response.setStatus(401);  // Send 401 Unauthorized status
//            response.sendRedirect("/login");  // Redirect to the login page
//        };
//    }
//
//    private LogoutHandler logoutHandler() {
//        return (request, response, authentication) -> {
//            try {
//                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        };
//    }
//}