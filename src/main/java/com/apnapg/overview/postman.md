# 🏠 APNA PG -- Production Level Backend System

A Complete Multi-Role PG Management Backend\
Built with Spring Boot, JWT, PostgreSQL, WebSocket, Role-Based Security

------------------------------------------------------------------------

# 🚀 Tech Stack

-   Java 21
-   Spring Boot 3
-   Spring Security (JWT + Refresh Token)
-   PostgreSQL
-   JPA / Hibernate
-   WebSocket (Real-time Chat)
-   Multipart File Upload
-   Role-Based Access Control
-   Production Exception Handling
-   Cookie-based Refresh Token

------------------------------------------------------------------------

# 🔐 AUTHENTICATION FLOW

## 1️⃣ Register Owner

POST http://localhost:8080/api/owners/register

``` json
{
  "firstName": "Akash",
  "lastName": "Jena",
  "email": "owner@example.com",
  "password": "Password@123",
  "phoneNumber": "9876543210",
  "address": "KIIT Road Bhubaneswar Odisha",
  "businessName": "Akash Premium PG",
  "gstNumber": "22ABCDE1234F1Z5"
}
```

------------------------------------------------------------------------

## 2️⃣ Login

POST http://localhost:8080/api/auth/login

``` json
{
  "email": "owner@example.com",
  "password": "Password@123"
}
```

------------------------------------------------------------------------

## 3️⃣ Refresh Token

POST http://localhost:8080/api/auth/refresh

------------------------------------------------------------------------

## 4️⃣ Logout

POST http://localhost:8080/api/auth/logout

------------------------------------------------------------------------

# 🏢 PG MODULE

## Create PG

POST http://localhost:8080/api/pgs

``` json
{
  "name": "Akash Premium PG Deluxe",
  "address": "KIIT Road Near Patia Square Bhubaneswar",
  "city": "Bhubaneswar",
  "contactNumber": "9876543210",
  "rentPerMonth": 7500,
  "totalRooms": 12
}
```

------------------------------------------------------------------------

## Search PG

GET http://localhost:8080/api/pgs/search?city=Bhubaneswar&page=0&size=5

------------------------------------------------------------------------

# 🛏 ROOM MODULE

## Create Room

POST http://localhost:8080/api/rooms/{pgId}

``` json
{
  "roomNumber": "101",
  "totalBeds": 4
}
```

------------------------------------------------------------------------

# 👨‍🎓 TENANT MODULE

## Register Tenant (Multipart Form)

POST http://localhost:8080/api/tenants/register

form-data: - data (Text JSON) - aadhaar (File)

``` json
{
  "firstName": "Rahul",
  "lastName": "Sharma",
  "email": "tenant@example.com",
  "phoneNumber": "9876543211",
  "gender": "MALE",
  "occupation": "STUDENT",
  "dateOfBirth": "2000-05-10",
  "address": "Patia Bhubaneswar Odisha",
  "emergencyContactName": "Ramesh Sharma",
  "emergencyContactNumber": "9876543222",
  "password": "Password@123"
}
```

------------------------------------------------------------------------

# 📝 COMPLAINT MODULE

## Create Complaint

POST http://localhost:8080/api/complaints

``` json
{
  "pgId": 1,
  "message": "Water issue in bathroom."
}
```

------------------------------------------------------------------------

# ⭐ REVIEW MODULE

## Add Review

POST http://localhost:8080/api/reviews

``` json
{
  "tenantId": 1,
  "pgId": 1,
  "rating": 5,
  "comment": "Very clean and secure PG."
}
```

------------------------------------------------------------------------

# 💬 CHAT MODULE

WebSocket Endpoint: ws://localhost:8080/ws

Send to: /app/chat.send

------------------------------------------------------------------------

# 📌 Project Status

✅ Auth Complete\
✅ Owner Complete\
✅ PG Complete\
✅ Room Complete\
✅ Tenant Complete\
✅ Complaint Complete\
✅ Review Complete\
✅ Chat Complete

------------------------------------------------------------------------

👨‍💻 Author: Akash Jena
