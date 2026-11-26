# NEXUS VAULT — Premium Games Store (Microservices)

Enterprise Java 21 + Spring Boot 3.3 microservices application designed for EC2 deployment and Docker Swarm orchestration.

## Architecture
- **`storefront-ui` (Port 8080):** Server-side rendered gaming storefront (Spring Boot + Thymeleaf).
- **`catalog-service` (Port 8081):** REST API managing game inventory & seeding AAA titles (`games_db`).
- **`order-service` (Port 8082):** REST API processing orders & communicating with `catalog-service` (`orders_db`).
- **MySQL 8.0 (Port 3306):** Relational datastore.

## Quick Start on Ubuntu EC2
```bash
sudo apt update && sudo apt install -y openjdk-21-jdk maven mysql-server
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root123'; FLUSH PRIVILEGES;"
mvn clean package -DskipTests
```
