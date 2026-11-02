package com.example.shop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CustomerOrder order;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public OrderItem() {}
    public OrderItem(CustomerOrder order, String productName, BigDecimal price, int quantity) {
        this.order = order; this.productName = productName; this.price = price; this.quantity = quantity;
    }
    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
