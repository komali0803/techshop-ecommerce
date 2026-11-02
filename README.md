# TechShop - Full-Stack E-Commerce (Spring Boot)

A full-stack e-commerce web app built with Spring Boot, Spring Security, Spring Data JPA, Thymeleaf and H2.

## Features
- Browse and search products, product detail page
- Session-based shopping cart (add / remove)
- User registration and login (BCrypt, Spring Security)
- Checkout that creates an order and reduces stock
- Order history per user
- Admin panel (add / edit / delete products), restricted to ADMIN role
- Sample data seeded on first run

## Tech Stack
Java 17, Spring Boot 3.3, Spring Web, Spring Data JPA, Spring Security, Thymeleaf, H2 database, Maven

## Run
```
mvn spring-boot:run
```
Open http://localhost:8080

## Demo accounts
| Role  | Username | Password |
|-------|----------|----------|
| Admin | admin    | admin123 |
| User  | user     | user123  |

## Structure
`model` (entities) · `repo` (JPA repositories) · `service` (cart, user details) · `controller` (shop, auth, admin) · `config` (security, seed data) · `templates` (Thymeleaf pages)
