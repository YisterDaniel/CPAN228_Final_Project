package com.warehouse.controller;

import com.warehouse.model.AppUser;
import com.warehouse.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // <-- changed from @RestController
@RequestMapping("/register-alt")
public class UserRegistrationController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String role,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (appUserRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "register"; // Stay on register page
        }

        AppUser newUser = new AppUser(username, passwordEncoder.encode(password), role);
        appUserRepository.save(newUser);

        redirectAttributes.addFlashAttribute("success", "Registration successful! You can now log in.");
        return "redirect:/login";
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }
}
