package com.example.computerstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class SpringSecurityConfig {
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
