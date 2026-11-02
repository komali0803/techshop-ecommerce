package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductRepository products;
    public AdminController(ProductRepository products) { this.products = products; }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("products", products.findAll());
        model.addAttribute("form", new Product());
        model.addAttribute("cartCount", 0);
        return "admin";
    }

    @PostMapping("/products")
    public String save(@ModelAttribute Product product) {
        products.save(product);
        return "redirect:/admin";
    }

    @GetMapping("/products/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("products", products.findAll());
        model.addAttribute("form", products.findById(id).orElseThrow());
        model.addAttribute("cartCount", 0);
        return "admin";
    }

    @PostMapping("/products/{id}/delete")
    public String delete(@PathVariable Long id) {
        products.deleteById(id);
        return "redirect:/admin";
    }
}
