package com.warehouse.service;

import com.warehouse.model.AppUser;
import com.warehouse.repository.AppUserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Print statements for debugging
        System.out.println("Logging in: " + appUser.getUsername());
        System.out.println("Assigned role: " + appUser.getRole());
        
        return appUser;
    }
}
