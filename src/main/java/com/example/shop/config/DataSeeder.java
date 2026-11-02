package com.example.shop.config;

import com.example.shop.model.AppUser;
import com.example.shop.model.Product;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(UserRepository users, ProductRepository products, PasswordEncoder enc) {
        return args -> {
            if (users.count() == 0) {
                users.save(new AppUser("admin", enc.encode("admin123"), "ADMIN"));
                users.save(new AppUser("user", enc.encode("user123"), "USER"));
            }
            if (products.count() == 0) {
                products.save(new Product("Wireless Headphones", "Bluetooth over-ear headphones with 30h battery.", new BigDecimal("59.99"), 25, "https://picsum.photos/seed/headphones/400/300"));
                products.save(new Product("Smart Watch", "Fitness tracking, heart rate and notifications.", new BigDecimal("89.50"), 15, "https://picsum.photos/seed/watch/400/300"));
                products.save(new Product("Mechanical Keyboard", "RGB backlit keyboard with blue switches.", new BigDecimal("45.00"), 30, "https://picsum.photos/seed/keyboard/400/300"));
                products.save(new Product("Gaming Mouse", "Ergonomic mouse, 16000 DPI.", new BigDecimal("25.99"), 40, "https://picsum.photos/seed/mouse/400/300"));
                products.save(new Product("Laptop Backpack", "Water-resistant, fits 15.6 inch laptops.", new BigDecimal("34.90"), 20, "https://picsum.photos/seed/backpack/400/300"));
                products.save(new Product("USB-C Hub", "7-in-1 hub with HDMI and card reader.", new BigDecimal("29.99"), 50, "https://picsum.photos/seed/hub/400/300"));
                products.save(new Product("Portable Speaker", "Waterproof Bluetooth speaker.", new BigDecimal("39.00"), 18, "https://picsum.photos/seed/speaker/400/300"));
                products.save(new Product("Webcam 1080p", "Full HD webcam with microphone.", new BigDecimal("32.50"), 22, "https://picsum.photos/seed/webcam/400/300"));
            }
        };
    }
}
