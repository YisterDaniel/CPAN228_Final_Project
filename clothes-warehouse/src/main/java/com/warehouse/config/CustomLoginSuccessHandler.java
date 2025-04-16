package com.warehouse.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Log roles granted to the authenticated user
        authentication.getAuthorities().forEach(auth -> 
            System.out.println("Granted Authority: " + auth.getAuthority())
        );

        // Check user role and redirect based on that
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/items");
                return;
            } else if (role.equals("ROLE_EMPLOYEE")) {
                response.sendRedirect("/addItem");
                return;
            }
        }

        // Default fallback (e.g., ROLE_USER or unknown)
        response.sendRedirect("/listItems");
    }
}

