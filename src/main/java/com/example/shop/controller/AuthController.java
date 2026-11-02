package com.example.shop.controller;

import com.example.shop.model.AppUser;
import com.example.shop.repo.UserRepository;
import com.example.shop.service.CartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final CartService cart;

    public AuthController(UserRepository users, PasswordEncoder encoder, CartService cart) {
        this.users = users; this.encoder = encoder; this.cart = cart;
    }

    @ModelAttribute("cartCount")
    public int cartCount() { return cart.count(); }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm() { return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        if (username.isBlank() || password.length() < 4) return "redirect:/register?error=invalid";
        if (users.findByUsername(username).isPresent()) return "redirect:/register?error=exists";
        users.save(new AppUser(username, encoder.encode(password), "USER"));
        return "redirect:/login?registered";
    }
}
