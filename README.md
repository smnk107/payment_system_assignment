**Payment Processing System**
-------------------------------------------------------------------------------------------------------------------------------------------------------
A microservices-based Payment Processing System built using Spring Boot, Spring Cloud, Kafka, MySQL, and JWT Authentication.

The system demonstrates:

REST APIs
Microservices Architecture
API Gateway
Service Discovery
Event-Driven Communication
Distributed System Consistency
Secure APIs with JWT
Idempotent Payment Processing


**🚀 Tech Stack**
Java 21
Spring Boot
Spring Security
Spring Cloud Gateway
Eureka Discovery Server
OpenFeign
Apache Kafka (KRaft Mode)
MySQL
Spring Data JPA
JWT Authentication
Maven


**🏗️ Microservices Architecture**


Client
   ↓
API Gateway
   ↓
------------------------------------------------
|                  |                           |
User Service    Order Service            Payment Service
                     ↓                           ↓
                 Kafka Consumer  ←──────── Kafka Producer
------------------------------------------------


**📌 Services

1. Discovery Service (Eureka Server)**

Responsible for service registration and discovery.

Features
Eureka Service Registry
Dynamic service discovery

**2. API Gateway
**
Acts as a single entry point for all microservices.

Features
Request routing
JWT authentication filter
Load-balanced routing using Eureka

**3. User Service
**
Handles user registration and authentication.

Features
User registration
Login authentication
BCrypt password encryption
JWT token generation
Role-based access control
Roles
USER
ADMIN

**4. Order Service
**
Handles order creation and order status management.

Features
Create order
Fetch order details
Feign Client communication with Payment Service
Kafka consumer for payment status updates
Eventual consistency handling
Order Status Flow
PENDING_PAYMENT
        ↓
CONFIRMED / PAYMENT_FAILED

**5. Payment Service
**
Handles payment processing.

Features
Initiate payment
Payment status tracking
Idempotency support
Kafka event publishing
Idempotency

Duplicate payment requests are prevented using:

Idempotency-Key header

This ensures:

No duplicate payment processing
Safe retry support



**🔄 Service Communication**


Synchronous Communication
Order Service → Payment Service
Implemented using OpenFeign
Asynchronous Communication
Payment Service publishes Kafka events
Order Service consumes payment events


**📡 Kafka Event Flow
**

Order Created
      ↓
Payment Initiated
      ↓
Payment Service publishes event
      ↓
Kafka Topic
      ↓
Order Service consumes event
      ↓
Order Status Updated


**🔐 Security
**
Implemented using Spring Security + JWT.

Features
Stateless authentication
JWT token validation
Password encryption using BCrypt
Protected APIs
Role-based authorization


**💾 Database
**

MySQL is used as the database.

Each microservice maintains its own database for loose coupling.

Databases
user_db
order_db
payment_db

**⚡ Distributed Transaction Handling
**
Kafka-based eventual consistency was implemented.
Order status is updated asynchronously after payment completion.

This approach resembles real-world distributed system design patterns.

**🧪 Testing
**

Unit testing implemented using:

JUnit
Mockito
Covered Areas
Payment service logic
Idempotency validation
Repository mocking


**📬 Sample APIs
**
Register User:
POST /auth/register

Login:
POST /auth/login

Create Order:
POST /orders?userId=1&amount=500

Initiate Payment:
POST /payments/initiate

Headers:
Idempotency-Key: unique-key

