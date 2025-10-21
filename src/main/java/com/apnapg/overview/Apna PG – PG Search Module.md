# Apna PG – PG Search Module

## Project Overview

* The PG Search module allows tenants or users to **search for PGs** based on location (city) and maximum rent.
* This module ensures **efficient search queries, mapping to response DTOs, and proper handling of empty results** using Spring Boot, service layer, and mappers.

---

# 🧾 PG Search Backend API

## 🔑 Key Features

* Search PGs by city and maximum rent.
* Returns PG details using `PGResponse` DTO.
* Handles cases where no PGs are found gracefully with HTTP 404.
* Utilizes `PGMapper` to map entity to response DTO.

---

## API Endpoints

### 1️⃣ Search PGs

* **URL:** `/api/pgs/search`
* **Method:** `GET`
* **Query Parameters:**

    * `city` → City name to search
    * `rentMax` → Maximum rent

**Example Request:**

```
GET /api/pgs/search?city=Delhi&rentMax=10000
```

**Response Example (PGs found):**

```json
[
  {
    "id": 1,
    "name": "Sunrise PG",
    "address": "MG Road, Delhi",
    "city": "Delhi",
    "contactNumber": "9876543210",
    "rentPerMonth": 8000,
    "totalRooms": 10,
    "imageUrls": ["C:/uploads/169000000_pg1.jpg"]
  },
  {
    "id": 2,
    "name": "Comfort PG",
    "address": "Connaught Place, Delhi",
    "city": "Delhi",
    "contactNumber": "9876543211",
    "rentPerMonth": 9500,
    "totalRooms": 8,
    "imageUrls": ["C:/uploads/169000001_pg2.jpg"]
  }
]
```

**Response Example (No PGs found):**

```
HTTP 404 Not Found
No PGs found for city: Delhi within rent: 5000
```

---

## 🏗️ Backend Structure Diagram

```
Client (Tenant/User)
-----------------------------------------
| Send GET request /api/pgs/search      |
| Query params: city, rentMax           |
-----------------------------------------
            |
            v
Backend (Spring Boot)
-----------------------------------------
Controller: PGController
-----------------------------------------
@GetMapping("/search")
RequestParam city, rentMax
|
v
Service Layer: PGService
-----------------------------------------
1. Execute search query by city and rentMax
2. Return list of PG entities
|
v
Mapper: PGMapper
-----------------------------------------
Map PG entity -> PGResponse DTO
|
v
Response
-----------------------------------------
- Return 200 OK with list of PGResponse if found
- Return 404 NOT FOUND with message if list is empty
```

---

✅ **Summary**

* Backend handles **searching PGs efficiently** based on city and rent.
* Uses **service and mapper layers** to separate business logic and data transformation.
* Proper HTTP response handling for both successful and empty search results.
* Modular design: `Controller → Service → Mapper → Response`.
