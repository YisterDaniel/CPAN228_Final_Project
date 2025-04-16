package com.distribution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("warehouse_user")
            .password("warehouse_pass")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @SuppressWarnings("deprecation") // Only for testing/demo. Use BCrypt in production.
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
