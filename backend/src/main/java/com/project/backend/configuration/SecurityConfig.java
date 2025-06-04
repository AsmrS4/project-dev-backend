package com.project.backend.configuration;

import com.project.backend.utils.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
