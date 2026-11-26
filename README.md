# NEXUS VAULT v2.0 — Enterprise Premium Games Store (Microservices)

Production-grade Java 21 + Spring Boot 3.3 microservices storefront with Spring Security authentication, player reviews, and promo discount engine.

## Microservices Architecture
- **`storefront-ui` (Port 8080):** Spring Security login/logout, genre filtering, storefront UI, and user order dashboard.
- **`catalog-service` (Port 8081):** Game catalog & dynamic promo code discount validation (`games_db`).
- **`order-service` (Port 8082):** Order processing with promo discount calculation & digital license key generation (`orders_db`).
- **`review-service` (Port 8083):** Player ratings and verified reviews microservice (`reviews_db`).
- **MySQL 8.0 (Port 3306):** Relational persistence layer.
