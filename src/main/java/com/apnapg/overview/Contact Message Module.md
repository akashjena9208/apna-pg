# Apna PG – Contact Message Module

## Project Overview

* The Contact Message module allows tenants, owners, or users to **submit contact messages or queries** to the PG system administrators.
* Admins can **view all messages** and **mark them as resolved**.
* This module ensures **validation, service layer handling, and proper response management** using Spring Boot and DTOs.

---

# 🧾 Contact Message Backend API

## 🔑 Key Features

* Submit contact messages using REST API.
* Retrieve all contact messages.
* Mark a message as resolved.
* All actions are handled through `ContactMessageService`.

---

## API Endpoints

### 1️⃣ Submit Contact Message

* **URL:** `/api/contact/submit`
* **Method:** `POST`
* **Request Body (ContactMessageDTO):**

```json
{
  "name": "Akash Jena",
  "email": "akash.jena@example.com",
  "subject": "Query about PG",
  "message": "Is there a room available next month?"
}
```

* **Response:** Returns the saved `ContactMessageDTO` with message ID and status.

### 2️⃣ Get All Contact Messages

* **URL:** `/api/contact/all`
* **Method:** `GET`
* **Response Example:**

```json
[
  {
    "id": 1,
    "name": "Akash Jena",
    "email": "akash.jena@example.com",
    "subject": "Query about PG",
    "message": "Is there a room available next month?",
    "resolved": false,
    "timestamp": "2025-10-21T06:45:00"
  },
  {
    "id": 2,
    "name": "Pritinada Pradhan",
    "email": "pritinada@example.com",
    "subject": "Complaint",
    "message": "Water leakage in my room",
    "resolved": true,
    "timestamp": "2025-10-21T06:50:00"
  }
]
```

### 3️⃣ Mark Message as Resolved

* **URL:** `/api/contact/{id}/resolve`
* **Method:** `PUT`
* **Path Variable:** `id` → Contact message ID to mark resolved
* **Response:** Returns the updated `ContactMessageDTO` with `resolved` set to `true`

---

## 🏗️ Backend Structure Diagram

```
Frontend (Tenant/Owner/User)
-----------------------------------------
| Submit contact message                |
| Request resolution of message (Admin)|
-----------------------------------------
            |
            v
Backend (Spring Boot)
-----------------------------------------
Controller: ContactMessageController
-----------------------------------------
@PostMapping /submit       -> submitMessage(ContactMessageDTO)
@GetMapping /all           -> getAllMessages()
@PutMapping /{id}/resolve -> markResolved(id)
|
v
Service Layer: ContactMessageService
-----------------------------------------
1. Save new message
2. Retrieve all messages
3. Update message resolved status
|
v
Database Table: contact_messages
-----------------------------------------
| id | name | email | subject | message | resolved | timestamp |
```

---

✅ **Summary**

* Backend handles **all contact message logic**, including submission, retrieval, and resolution.
* Uses **service layer** for modular business logic.
* Supports both **tenant/owner actions** and **admin actions**.
* Proper DTO usage ensures clean data transfer and validation.
