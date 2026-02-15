# 🏠 APNA PG -- Full Stack PG Management System

A production-grade backend application built using **Spring Boot**,
**Java 17**, and **PostgreSQL** that manages PG (Paying Guest)
operations including:

-   Owner registration & PG management
-   Room allocation system
-   Tenant onboarding with Aadhaar upload
-   Complaint system
-   JWT Authentication + Refresh Token rotation
-   OAuth2 (Google Login)
-   WebSocket-based Chat
-   Role-based Authorization (OWNER / TENANT / ADMIN)
-   Dockerized Deployment
-   AWS EC2 Ready

------------------------------------------------------------------------

## 🌐 Base URL

``` text
http://localhost:8080
```

## 📖 Swagger UI

``` text
http://localhost:8080/swagger-ui/index.html
```

------------------------------------------------------------------------

# 🚀 Tech Stack

-   Java 17
-   Spring Boot 3
-   Spring Security (JWT + OAuth2)
-   Spring Data JPA (Hibernate)
-   PostgreSQL
-   WebSocket (STOMP)
-   Docker
-   AWS EC2 Ready
-   Maven
-   Lombok
-   SLF4J Logging

------------------------------------------------------------------------

# 🏗️ Project Architecture (3-Tier Layered Design)

    ┌──────────────────────────────┐
    │      Controller Layer        │  ← REST API Endpoints
    └──────────────┬───────────────┘
                   │
    ┌──────────────▼───────────────┐
    │        Service Layer         │  ← Business Logic
    └──────────────┬───────────────┘
                   │
    ┌──────────────▼───────────────┐
    │      Repository Layer        │  ← Database Access
    └──────────────┬───────────────┘
                   │
    ┌──────────────▼───────────────┐
    │        PostgreSQL DB         │
    └──────────────────────────────┘

------------------------------------------------------------------------

# 🔐 Security Architecture

### Authentication Flow

    Login Request
       ↓
    AuthService
       ↓
    Password Verification
       ↓
    Generate JWT Access Token
       ↓
    Generate Refresh Token (Stored as HASH in DB)
       ↓
    Return:
       - Access Token (JSON)
       - Refresh Token (HttpOnly Cookie)

### Security Features

-   Stateless JWT Authentication
-   Refresh Token Rotation
-   Refresh Token Hashing (SHA-256)
-   Token Reuse Detection
-   Role-based Access Control
-   Account Lock after 5 failed attempts
-   OAuth2 Google Login
-   HttpOnly Secure Cookies

------------------------------------------------------------------------
# 🔐 Authentication APIs (Postman Examples)

## 1️⃣ Owner Login

### POST `/api/auth/login`

### Request
```json
{
  "email": "owner@example.com",
  "password": "Password@123"
}
```

### Response
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "email": "owner@example.com",
    "role": "OWNER",
    "provider": "LOCAL",
    "expiresAt": "2026-02-20T22:43:20Z"
  },
  "message": "Login successful"
}
```

Refresh token stored automatically in HttpOnly Cookie.

---
## 2️⃣ Refresh Access Token

### POST `/api/auth/refresh`

(No body required — cookie auto-sent)

### Response
```json
{
  "success": true,
  "data": {
    "accessToken": "newAccessTokenHere",
    "tokenType": "Bearer",
    "expiresAt": "2026-02-20T23:00:00Z"
  },
  "message": "Token refreshed"
}
```

---
# 🏢 PG Module APIs

## 3️⃣ Create PG (OWNER)

### POST `/api/pgs`

```json
{
  "name": "Akash Premium PG",
  "address": "KIIT Road",
  "city": "Bhubaneswar",
  "contactNumber": "9876543210",
  "rentPerMonth": 6000,
  "totalRooms": 10
}
```

---
## 4️⃣ Search PG

### GET `/api/pgs/search?city=Bhubaneswar&page=0&size=5`

```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 3,
        "name": "Akash Premium PG",
        "city": "Bhubaneswar",
        "rentPerMonth": 6000
      }
    ],
    "page": 0,
    "totalPages": 1
  }
}
```

---
# 🏠 Room Module APIs

## 5️⃣ Create Room

### POST `/api/rooms/pg/3`

```json
{
  "roomNumber": "101A",
  "totalBeds": 4
}
```

---
# 👤 Tenant Module APIs

## 6️⃣ Register Tenant (Multipart)

### POST `/api/tenants/register`

Form-data:
- data (JSON string)
- aadhaar (file)

Example JSON inside `data`:

```json
{
  "firstName": "Ravi",
  "lastName": "Kumar",
  "email": "ravi@example.com",
  "phoneNumber": "9876543210",
  "gender": "MALE",
  "occupation": "STUDENT",
  "dateOfBirth": "2000-05-12",
  "address": "Patia",
  "emergencyContactName": "Rajesh",
  "emergencyContactNumber": "9123456789",
  "password": "Password@123"
}
```

---
## 7️⃣ Allocate Room (OWNER)

### PUT `/api/tenants/2/allocate/1`

Response:
```json
{
  "success": true,
  "data": "Room allocated"
}
```

---
## 8️⃣ Vacate Room (TENANT)

### PUT `/api/tenants/vacate`

---
# 📢 Complaint Module

## 9️⃣ Create Complaint

### POST `/api/complaints`

```json
{
  "pgId": 3,
  "message": "Water leakage in bathroom"
}
```

---
# 💬 Chat Module (WebSocket)

Endpoint:
```
ws://localhost:8080/ws
```

Topic:
```
/topic/pg/{pgId}
```

---

------------------------------------------------------------------------

## 7️⃣ Chat Module (WebSocket)

WebSocket Endpoint:

    ws://localhost:8080/ws

Topic Example:

    /topic/pg/{pgId}

------------------------------------------------------------------------

# 🐳 Run with Docker

### Build Image

``` bash
docker build -t apna-pg .
```

### Run Container

``` bash
docker run -p 8080:8080 apna-pg
```

------------------------------------------------------------------------

# 🗄️ Database Configuration (PostgreSQL)

Example:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/apnapg
spring.datasource.username=postgres
spring.datasource.password=postgres
```

------------------------------------------------------------------------

# 🔥 Production Features

-   Refresh Token Hashing
-   Role-based API protection
-   Secure File Upload (Aadhaar)
-   Prevent Duplicate Active Complaints
-   Optimistic Locking Ready
-   Pagination & Sorting
-   Manual DTO Mapping (Builder Pattern)
-   Clean Architecture

------------------------------------------------------------------------

# ☁ Deployment Ready

-   Dockerized
-   AWS EC2 Compatible
-   CI/CD Ready
-   Environment Variable Support

------------------------------------------------------------------------

# 🙌 Author

**Akash Jena**\
Full Stack Java Developer\
Email: akashjena9208@gmail.com

------------------------------------------------------------------------

# ⭐ Why This Project Stands Out

This is not a basic CRUD project.

It demonstrates:

-   Enterprise Security Architecture
-   OAuth2 Integration
-   Token Rotation System
-   Real-time WebSocket Communication
-   Multi-role Access Control
-   Clean Layered Design
-   Production-level Thinking

------------------------------------------------------------------------
