package com.project.backend.configuration;

import com.project.backend.utils.mapper.BookingMapper;
import com.project.backend.utils.mapper.EventMapper;
import com.project.backend.utils.mapper.ImageMapper;
import com.project.backend.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter filter;
    private final AuthEntryPoint authEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/auth/logout").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.PUT,"/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.DELETE,"/api/event/**").hasAuthority("MANAGER")
                                .requestMatchers("/api/history/event/**").hasAuthority("MANAGER")
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
