# 🏗️ APNA PG - Complete Backend Architecture Design

------------------------------------------------------------------------

## 📌 1. High Level Architecture
```
Client (Postman / Frontend) ↓ Spring Security (JWT Filter) ↓ Controller
Layer ↓ Service Layer (Business Logic) ↓ Repository Layer (JPA) ↓
PostgreSQL Database
```
------------------------------------------------------------------------

## 🔐 2. Security Flow (JWT Based)
```
1.  Client sends request with Authorization header (Bearer Token)
2.  JwtAuthenticationFilter extracts token
3.  Token validated via JwtService
4.  User loaded via CustomUserDetailsService
5.  SecurityContextHolder updated
6.  @PreAuthorize validates roles
7.  Request proceeds to controller

Stateless Authentication → No Session Storage
```
------------------------------------------------------------------------

## 📦 3. Layer Responsibilities
```
### Controller Layer

-   Handles HTTP requests
-   Validates DTO
-   Calls Service
-   Returns ApiResponse

### Service Layer

-   Business logic
-   Validation rules
-   Ownership checks
-   Transaction management (@Transactional)

### Repository Layer

-   Extends JpaRepository
-   Handles database operations

### Security Layer

-   JWT Filter
-   Role-based access
-   CustomAccessDeniedHandler
-   CustomAuthenticationEntryPoint
```
------------------------------------------------------------------------

## 🔄 4. Data Flow (Example: Create PG)
```
POST /api/pgs

1.  JWT Filter validates OWNER
2.  Controller receives PGCreateRequest DTO
3.  Service:
    -   Validate owner
    -   Check duplicate
    -   Build PG entity
    -   Save via repository
4.  Entity converted to PGResponse DTO
5.  ApiResponse returned to client
```
------------------------------------------------------------------------

## 🏠 5. Module Overview

### ✅ Auth Module
```
-   Login
-   Refresh Token (rotation)
-   Logout
-   Cookie-based refresh token
```
### ✅ Owner Module
```
-   Register
-   Create / Update / Delete PG
-   Upload PG images
```
### ✅ Tenant Module
```
-   Register with Aadhaar upload
-   Allocate room
-   Vacate room
-   Update profile
```
### ✅ Room Module
```
-   Create room
-   Update room
-   View availability
```
### ✅ Complaint Module
```
-   Tenant create complaint
-   Owner update status
-   Prevent duplicate active complaint
```
### ✅ Chat Module
```
-   WebSocket real-time messaging
-   REST conversation history
-   Seen status tracking
```
------------------------------------------------------------------------

## 🧠 6. Transaction Handling
```
@Service classes use @Transactional

Success → Commit Exception → Rollback

Ensures data consistency.
```
------------------------------------------------------------------------

## 🗂️ 7. DTO vs Entity Flow
```
Client JSON → DTO → Entity → Database Database → Entity → DTO →
ApiResponse → Client

Prevents exposing internal database structure.
```
------------------------------------------------------------------------

## 📁 8. File Upload Security
```
-   Valid extension check
-   MIME type verification
-   Size restriction
-   UUID file naming
-   Path traversal protection
```
------------------------------------------------------------------------

## 🧩 9. Role Matrix
```
  Role     Access
  -------- --------------------------------------
  OWNER    Manage PG, Rooms, Complaints
  TENANT   Room allocation, Complaints, Reviews
  ADMIN    Manage Users
```
------------------------------------------------------------------------

## 🚀 10. Production Readiness Features
```
-   Global Exception Handling
-   Stateless JWT Security
-   Role-based Authorization
-   Validation Annotations
-   Pagination Support
-   Null-safe Mapping
-   Transaction Safety
-   Secure File Upload
-   WebSocket Authentication
```
------------------------------------------------------------------------

## 📈 System Status
```
Modules Completed: - Auth - Owner - Tenant - Room - Complaint - Chat
(REST + WebSocket) - File Upload - Pagination

Backend Status: ✅ Production-Ready (Monolithic Architecture)
```
------------------------------------------------------------------------

# 🔥 End of Architecture Design
