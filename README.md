# Payment Processing System

A microservices-based payment processing system built with **Spring Boot**, **Spring Cloud**, **Kafka**, **MySQL**, and **JWT Authentication** — demonstrating distributed system design patterns, event-driven architecture, and secure API design.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot, Spring Security, Spring Cloud Gateway |
| Service Discovery | Eureka |
| Inter-service Communication | OpenFeign |
| Messaging | Apache Kafka (KRaft Mode) |
| Database | MySQL + Spring Data JPA |
| Authentication | JWT |
| Build Tool | Maven |

---

## 🏗️ Architecture Overview


<img width="501" height="259" alt="image" src="https://github.com/user-attachments/assets/73df016c-4962-44b8-809c-6eaa90bb8dec" />



---

## 📦 Services

### 1. Discovery Service (Eureka Server)
Handles service registration and discovery.
- Eureka Service Registry
- Dynamic service discovery

---

### 2. API Gateway
Single entry point for all client requests.
- Request routing
- JWT authentication filter
- Load-balanced routing via Eureka

---

### 3. User Service
Manages user registration and authentication.
- User registration & login
- BCrypt password encryption
- JWT token generation
- Role-based access control (`USER`, `ADMIN`)

---

### 4. Order Service
Handles order lifecycle and status management.
- Create and fetch orders
- Feign Client → Payment Service (synchronous)
- Kafka consumer for payment status updates
- Eventual consistency handling

---

### 5. Payment Service
Handles payment processing with idempotency guarantees.
- Initiate and track payments
- Kafka event publishing
- Idempotency via `Idempotency-Key` header — prevents duplicate processing, enables safe retries

---

## 🔄 Service Communication

| Type | Direction | Method |
|---|---|---|
| Synchronous | Order → Payment Service | OpenFeign |
| Asynchronous | Payment → Order Service | Kafka Events |

**Kafka Event Flow:**

<img width="317" height="335" alt="image" src="https://github.com/user-attachments/assets/8f6f2943-80d5-46e2-b52b-fedc892d9f2a" />



---

## 🔐 Security

Implemented with **Spring Security + JWT**.
- Stateless authentication
- JWT token validation at the API Gateway
- BCrypt password encryption
- Role-based endpoint authorization

---

## 💾 Database Design

Each microservice owns its own database (loose coupling):

| Service | Database |
|---|---|
| User Service | `user_db` |
| Order Service | `order_db` |
| Payment Service | `payment_db` |

---

## ⚡ Distributed Transaction Handling

Uses **Kafka-based eventual consistency** — order status is updated asynchronously after payment completion, mirroring real-world distributed system patterns (Saga pattern).

---

## 🧪 Testing

Unit tests implemented with **JUnit** + **Mockito**.
- Payment service business logic
- Idempotency validation
- Repository mocking

---

## 📬 API Reference

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Register a new user | Public |
| `POST` | `/auth/login` | Login and receive JWT | Public |
| `POST` | `/orders?userId=1&amount=500` | Create an order | JWT Required |
| `POST` | `/payments/initiate` | Initiate a payment | JWT + `Idempotency-Key` header |---

## 🔐 Security

Implemented with **Spring Security + JWT**.
- Stateless authentication
- JWT token validation at the API Gateway
- BCrypt password encryption
- Role-based endpoint authorization

---

## 💾 Database Design

Each microservice owns its own database (loose coupling):

| Service | Database |
|---|---|
| User Service | `user_db` |
| Order Service | `order_db` |
| Payment Service | `payment_db` |

---

## ⚡ Distributed Transaction Handling

Uses **Kafka-based eventual consistency** — order status is updated asynchronously after payment completion, mirroring real-world distributed system patterns (Saga pattern).

---

## 🧪 Testing

Unit tests implemented with **JUnit** + **Mockito**.
- Payment service business logic
- Idempotency validation
- Repository mocking

---

## 📬 API Reference

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Register a new user | Public |
| `POST` | `/auth/login` | Login and receive JWT | Public |
| `POST` | `/orders?userId=1&amount=500` | Create an order | JWT Required |
| `POST` | `/payments/initiate` | Initiate a payment | JWT + `Idempotency-Key` header |
