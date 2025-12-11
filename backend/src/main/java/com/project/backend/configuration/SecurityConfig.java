package com.project.backend.configuration;

import com.project.backend.utils.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableAsync
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtFilter filter;
    private final AuthEntryPoint authEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(
                            "http://localhost:5000",
                            "http://localhost:5001",
                            "http://localhost:5002",
                            "http://localhost:5003",
                            "http://localhost:5004",
                            "http://localhost:5005"
                            ));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/auth/logout").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/event/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.PUT,"/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.DELETE,"/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers("/api/history/event/**").hasAnyAuthority("MANAGER","CLIENT")
                                .requestMatchers("/api/history/tickets/**").hasAuthority("CLIENT")
                                .requestMatchers("/api/booking/**").hasAuthority("CLIENT")
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public ReviewMapper reviewMapper() {
        return new ReviewMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
    @Bean
    public EventMapper eventMapper() {
        return new EventMapper();
    }
    @Bean
    public ImageMapper imageMapper() {
        return new ImageMapper();
    }

    @Bean
    public BookingMapper bookingMapper() {
        return new BookingMapper();
    }
}
