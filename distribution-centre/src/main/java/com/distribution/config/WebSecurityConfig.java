package com.distribution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disables CSRF protection for simplicity, you might want to enable it for production
            .authorizeHttpRequests()
                .antMatchers("/searchItems", "/request").authenticated()  // Specify the paths that need authentication
                .anyRequest().permitAll()  // Allow other requests to be accessed without authentication
            .and()
            .httpBasic();  // Use HTTP Basic Authentication

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password")  // {noop} means plain text (for testing only)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
