# Apna PG – Review & Complaint Module

## Project Overview

* The Review & Complaint module allows tenants to **submit reviews** for PGs and **file complaints** regarding issues in the PGs.
* Owners and admins can **view complaints**, track status, and take necessary action.
* This module ensures **validation, proper DTO usage, and service layer handling** using Spring Boot.

---

# 🧾 Review & Complaint Backend API

## 🔑 Key Features

* Tenants can add reviews to PGs.
* Tenants can create complaints for PG issues.
* Admins/Owners can update complaint status.
* Fetch all reviews for a specific PG.
* Fetch all complaints for a specific PG or tenant.

---

## 📝 Notes

* Validation is applied on ReviewDTO using `@Valid`.
* Complaint creation and status update handled via DTOs.
* All endpoints return **ResponseEntity** with relevant entity or list.
* Modular service layer: `ReviewService` and `ComplaintService` manage all business logic.

---

## API Endpoints

### 1️⃣ Add Review

* **URL:** `/api/reviews`
* **Method:** `POST`
* **Request Body (ReviewDTO):**

```json
{
  "tenantId": 1,
  "pgId": 2,
  "rating": 5,
  "comment": "Great PG with clean rooms!"
}
```

* **Response:** `Review` entity with details of the saved review.

### 2️⃣ Get Reviews for a PG

* **URL:** `/api/pgs/{pgId}/reviews`
* **Method:** `GET`
* **Response Example:**

```json
[
  {
    "id": 1,
    "tenantId": 1,
    "pgId": 2,
    "rating": 5,
    "comment": "Great PG with clean rooms!",
    "timestamp": "2025-10-21T06:00:00"
  }
]
```

### 3️⃣ Create Complaint

* **URL:** `/api/complaints`
* **Method:** `POST`
* **Request Body (ComplaintDTO):**

```json
{
  "tenantId": 1,
  "pgId": 2,
  "title": "Water Leakage",
  "description": "Water leakage in bathroom"
}
```

* **Response:** `Complaint` entity with saved complaint details.

### 4️⃣ Update Complaint Status

* **URL:** `/api/complaints/{id}/status`
* **Method:** `PUT`
* **Request Body (ComplaintStatusUpdateDTO):**

```json
{
  "status": "RESOLVED"
}
```

* **Response:** `Complaint` entity with updated status.

### 5️⃣ Get Complaints for a PG

* **URL:** `/api/pgs/{pgId}/complaints`
* **Method:** `GET`
* **Response Example:**

```json
[
  {
    "id": 1,
    "tenantId": 1,
    "pgId": 2,
    "title": "Water Leakage",
    "description": "Water leakage in bathroom",
    "status": "PENDING",
    "timestamp": "2025-10-21T06:10:00"
  }
]
```

### 6️⃣ Get Complaints for a Tenant

* **URL:** `/api/tenants/{tenantId}/complaints`
* **Method:** `GET`
* **Response Example:**

```json
[
  {
    "id": 1,
    "tenantId": 1,
    "pgId": 2,
    "title": "Water Leakage",
    "description": "Water leakage in bathroom",
    "status": "PENDING",
    "timestamp": "2025-10-21T06:10:00"
  }
]
```

---

## 🏗️ Backend Structure Diagram

```
Frontend (Tenant / Owner)
-----------------------------------------
| Submit Review / Complaint            |
| Update Complaint Status (Owner/Admin)|
-----------------------------------------
            |
            v
Backend (Spring Boot)
-----------------------------------------
Controller: ReviewComplaintController
-----------------------------------------
@PostMapping /reviews           -> addReview(ReviewDTO)
@GetMapping /pgs/{pgId}/reviews -> getReviews(pgId)
@PostMapping /complaints       -> createComplaint(ComplaintDTO)
@PutMapping /complaints/{id}/status -> updateComplaintStatus(id, ComplaintStatusUpdateDTO)
@GetMapping /pgs/{pgId}/complaints -> getPGComplaints(pgId)
@GetMapping /tenants/{tenantId}/complaints -> getTenantComplaints(tenantId)
|
v
Service Layer
-----------------------------------------
ReviewService -> add/get reviews
ComplaintService -> create/update/get complaints
|
v
Database Tables
-----------------------------------------
Review Table: id, tenantId, pgId, rating, comment, timestamp
Complaint Table: id, tenantId, pgId, title, description, status, timestamp
```

---

✅ **Summary**

* Backend handles all **reviews and complaints** logic including creation, retrieval, and status updates.
* Modular design: `Controller → Service → Model → Database`.
* Supports both **tenant actions** and **owner/admin actions**.
