package com.example.shop.controller;

import com.example.shop.model.*;
import com.example.shop.repo.*;
import com.example.shop.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {
    private final ProductRepository products;
    private final UserRepository users;
    private final OrderRepository orders;
    private final CartService cart;

    public ShopController(ProductRepository products, UserRepository users, OrderRepository orders, CartService cart) {
        this.products = products; this.users = users; this.orders = orders; this.cart = cart;
    }

    public record CartLine(Product product, int quantity, BigDecimal subtotal) {}

    @ModelAttribute("cartCount")
    public int cartCount() { return cart.count(); }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("products", (q == null || q.isBlank()) ? products.findAll() : products.findByNameContainingIgnoreCase(q));
        model.addAttribute("q", q);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", products.findById(id).orElseThrow());
        return "product-detail";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id) {
        if (products.existsById(id)) cart.add(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cart.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartLine> lines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> e : cart.getItems().entrySet()) {
            Product p = products.findById(e.getKey()).orElse(null);
            if (p == null) continue;
            BigDecimal sub = p.getPrice().multiply(BigDecimal.valueOf(e.getValue()));
            lines.add(new CartLine(p, e.getValue(), sub));
            total = total.add(sub);
        }
        model.addAttribute("lines", lines);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/checkout")
    @Transactional
    public String checkout(Authentication auth) {
        if (cart.getItems().isEmpty()) return "redirect:/cart";
        AppUser user = users.findByUsername(auth.getName()).orElseThrow();
        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> e : cart.getItems().entrySet()) {
            Product p = products.findById(e.getKey()).orElse(null);
            if (p == null) continue;
            if (p.getStock() < e.getValue()) return "redirect:/cart?error=stock";
            p.setStock(p.getStock() - e.getValue());
            products.save(p);
            order.getItems().add(new OrderItem(order, p.getName(), p.getPrice(), e.getValue()));
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(e.getValue())));
        }
        order.setTotal(total);
        orders.save(order);
        cart.clear();
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String myOrders(Authentication auth, Model model) {
        AppUser user = users.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("orders", orders.findByUserOrderByCreatedAtDesc(user));
        return "orders";
    }
}
