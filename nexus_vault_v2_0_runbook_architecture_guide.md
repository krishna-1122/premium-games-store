# NEXUS VAULT v2.0 — Enterprise Microservices Games Store

A full-stack, multi-service digital game storefront built with **Java 21**, **Spring Boot 3.3.5**, **Spring Security**, and **MySQL 8.0**, designed for bare-metal EC2 deployment and containerized orchestration on **Docker Swarm**.

---

## 1. Architecture & Microservices Overview

The application is split into **4 independent Spring Boot microservices** backed by **MySQL 8.0**:

| Service Module | Port | Database Schema | Responsibility |
| :--- | :--- | :--- | :--- |
| **`storefront-ui`** | `8080` | None (Stateless UI) | Spring Security login/logout, Thymeleaf UI, genre filter, routes requests to backend APIs |
| **`catalog-service`** | `8081` | `games_db` | Stores & seeds 6 AAA titles (`games` table), filters by genre, validates promo codes |
| **`order-service`** | `8082` | `orders_db` | Validates game & promo via `catalog-service`, calculates discounts, generates `NXV-XXXX-XXXX` activation keys (`game_orders` table) |
| **`review-service`** | `8083` | `reviews_db` | Stores & seeds player ratings and reviews (`game_reviews` table) |

---

## 2. Environment & Prerequisites (Ubuntu 24.04 EC2)

### System Packages Required
Install Java 21 JDK, Apache Maven, MySQL Server 8.0, and Git:
```bash
sudo apt update && sudo apt install -y openjdk-21-jdk maven mysql-server git
```

### EC2 Security Group Inbound Rules
Configure the following inbound TCP rules on your EC2 Security Group:
* **Port `22` (SSH):** Administrative terminal access
* **Port `8080` (HTTP):** Public browser access to `storefront-ui` *(ports `8081`–`8083` and `3306` remain private to localhost / internal Docker overlay networks)*

---

## 3. MySQL Database Configuration

1. **Start & Enable MySQL:**
   ```bash
   sudo systemctl enable --now mysql
   ```

2. **Create the Dedicated JDBC Application User:**
   Ubuntu 24.04 configures `root@localhost` with `auth_socket` by default, which rejects TCP/JDBC connections from Java. Create a dedicated `gamestore` user with full schema creation privileges:
   ```bash
   sudo mysql -e "CREATE USER IF NOT EXISTS 'gamestore'@'localhost' IDENTIFIED BY 'root123'; GRANT ALL PRIVILEGES ON *.* TO 'gamestore'@'localhost' WITH GRANT OPTION; FLUSH PRIVILEGES;"
   ```
   > **Docker / Remote DB Note:** When running MySQL in a container or on a separate host, grant access to `'gamestore'@'%'` instead of `'localhost'`.

3. **Auto-Created Schemas & Tables:**
   Manual `CREATE DATABASE` or `CREATE TABLE` statements are **not** required. Each backend service configures `createDatabaseIfNotExist=true` and `spring.jpa.hibernate.ddl-auto=update`:
   * **`games_db.games`** — Auto-created and seeded with 6 AAA titles on first startup if empty.
   * **`orders_db.game_orders`** — Auto-created on startup; stores completed orders and activation keys.
   * **`reviews_db.game_reviews`** — Auto-created and seeded with 3 sample reviews on first startup if empty.

---

## 4. Environment Variables (Configurable Overrides)

All microservices support environment variable overrides with local defaults, allowing the exact same JARs to run both directly on EC2 and inside Docker containers:

| Variable | Default Value | Used By | Purpose |
| :--- | :--- | :--- | :--- |
| `DB_HOST` | `localhost` | `catalog`, `order`, `review` | MySQL hostname (`mysql-db` in Docker Swarm) |
| `DB_USER` | `gamestore` | `catalog`, `order`, `review` | MySQL JDBC username |
| `DB_PASSWORD` | `root123` | `catalog`, `order`, `review` | MySQL JDBC password |
| `CATALOG_SERVICE_URL` | `http://localhost:8081` | `order-service`, `storefront-ui` | Base URL for Catalog REST API |
| `ORDER_SERVICE_URL` | `http://localhost:8082` | `storefront-ui` | Base URL for Order REST API |
| `REVIEW_SERVICE_URL` | `http://localhost:8083` | `storefront-ui` | Base URL for Review REST API |

---

## 5. Build & Startup Procedure (EC2 Host)

### Step 1: Clone & Compile All 4 Modules
```bash
git clone https://github.com/krishna-1122/premium-games-store.git
cd premium-games-store
mvn clean package -DskipTests
```
This builds 4 executable Spring Boot fat JARs:
* `catalog-service/target/catalog-service-2.0.0.jar`
* `order-service/target/order-service-2.0.0.jar`
* `review-service/target/review-service-2.0.0.jar`
* `storefront-ui/target/storefront-ui-2.0.0.jar`

### Step 2: Start Services in Dependency Order
Launch the 3 backend APIs first (`catalog` $\rightarrow$ `order` $\rightarrow$ `review`), giving Hibernate ~12 seconds per service to initialize schemas before starting `storefront-ui`:
```bash
DB_USER=gamestore DB_PASSWORD=root123 nohup java -jar catalog-service/target/catalog-service-2.0.0.jar > catalog.log 2>&1 &
sleep 12
DB_USER=gamestore DB_PASSWORD=root123 nohup java -jar order-service/target/order-service-2.0.0.jar > order.log 2>&1 &
sleep 12
DB_USER=gamestore DB_PASSWORD=root123 nohup java -jar review-service/target/review-service-2.0.0.jar > review.log 2>&1 &
sleep 12
nohup java -jar storefront-ui/target/storefront-ui-2.0.0.jar > ui.log 2>&1 &
```

### Step 3: Stop All Running Services
```bash
pkill -f "catalog-service|order-service|review-service|storefront-ui"
```

---

## 6. Verification & Application Credentials

### Health Check Endpoints (Spring Boot Actuator)
```bash
curl -s http://localhost:8081/actuator/health
curl -s http://localhost:8082/actuator/health
curl -s http://localhost:8083/actuator/health
curl -s http://localhost:8080/actuator/health
```
Expected response from each service:
```json
{"status":"UP"}
```

### Storefront Login & Promo Codes
Access the storefront at `http://<EC2_PUBLIC_IP>:8080`:
* **Gamer Account 1:** `krishna` / `nexus123`
* **Gamer Account 2:** `gamer` / `gamer123`
* **Active Promo Codes:** `NEXUS20` (20% discount) and `GAMER10` (10% discount)

### Troubleshooting Logs
If any service fails to start or bind to its port, inspect its dedicated log file in the project root:
```bash
tail -n 50 catalog.log   # or order.log / review.log / ui.log
```