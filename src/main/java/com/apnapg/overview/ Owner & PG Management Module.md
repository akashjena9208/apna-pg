# Apna PG – Owner & PG Management Module

## Project Overview
- The Owner module allows PG owners to register themselves, create PGs, add rooms, and manage tenants in the Apna PG system.
- Owners can view PGs they created, allocate rooms/beds to tenants, and download tenant Aadhaar documents.
- This module ensures proper validation, file upload handling, and database consistency using Spring Boot, DTOs, service layers, and file storage.

---

# 🧾 Owner & PG Management API

## 🔑 Key Features
- Register owner with personal details and optional profile image
- Create PG with multiple images and details
- Add rooms to PG
- Fetch PGs created by the owner
- Fetch owner details by email
- View tenants in PGs and download their Aadhaar
- Allocate rooms/beds to tenants
- Handles multipart JSON + file requests correctly

---

## 📝 Notes
- *Maximum file size*: 5MB for images (configurable in `application.yml`)
- *Passwords* are encrypted using BCrypt before saving
- *Logging* enabled for debugging and monitoring
- Relationships: Owner → PG → Room → Tenant

---

## API Endpoints

### 1️⃣ Register Owner
- **URL:** `/api/owners/register`
- **Method:** `POST`
- **Content-Type:** `multipart/form-data`

**Form Data:**

| Key          | Type | Description                                         |
|--------------|------|-----------------------------------------------------|
| owner        | Text | Owner details as JSON string (see example below)   |
| profileImage | File | Optional profile image (JPG/PNG)                   |

**Owner JSON Example:**
```json
{
  "firstName": "Rahul",
  "lastName": "Sharma",
  "email": "rahul@example.com",
  "phoneNumber": "9876543210",
  "address": "MG Road, Delhi",
  "businessName": "Rahul PGs",
  "gstNumber": "GST1234567",
  "password": "password123"
}
```
### Response Example:
```
{
"id": 1,
"firstName": "Rahul",
"lastName": "Sharma",
"email": "rahul@example.com",
"phoneNumber": "9876543210",
"businessName": "Rahul PGs",
"profileImageUrl": "C:/uploads/169000000_profile.jpg"
}
```
#### ️ Create PG
```
URL: /api/owners/pg/create?ownerId=1

Method: POST

Content-Type: multipart/form-data

Form Data:

Key	Type	Description
pg	Text	PG details as JSON string (see example)
images	File	Optional multiple images of PG
```

### PG JSON Example:
```
{
"name": "Sunrise PG",
"address": "MG Road, Delhi",
"city": "Delhi",
"contactNumber": "9876543210",
"rentPerMonth": 8000,
"totalRooms": 10
}
```

### Response Example:
```
{
"id": 1,
"name": "Sunrise PG",
"address": "MG Road, Delhi",
"city": "Delhi",
"contactNumber": "9876543210",
"rentPerMonth": 8000,
"totalRooms": 10,
"imageUrls": ["C:/uploads/169000000_pg1.jpg"]
}
```
### Add Room to PG
```
URL: /api/owners/pg/{pgId}/room/add

Method: POST

Content-Type: application/json

Room JSON Example:

{
"roomNumber": "101",
"totalBeds": 3
}

```
```
Response Example:

{
"id": 1,
"roomNumber": "101",
"totalBeds": 3,
"availableBeds": 3
}

4️⃣ Get PGs of an Owner

URL: /api/owners/pg/list/{ownerId}

Method: GET

Response Example:

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
}
]

5️⃣ Get Owner by Email

URL: /api/owners/get/{email}

Method: GET

Response Example:

{
"id": 1,
"firstName": "Rahul",
"lastName": "Sharma",
"email": "rahul@example.com",
"phoneNumber": "9876543210",
"businessName": "Rahul PGs",
"profileImageUrl": "C:/uploads/169000000_profile.jpg"
}

6️⃣ Get Tenant Details by Owner

URL: /api/owners/tenants/{ownerId}

Method: GET

Response Example:

[
{
"id": 1,
"firstName": "Akash",
"lastName": "Jena",
"email": "akash.jena@example.com",
"phoneNumber": "9876543210",
"aadhaarUrl": "/uploads/1760835680331_user.jpg"
}
]

7️⃣ Download Tenant Aadhaar

URL: /api/owners/tenant/{tenantId}/download-aadhaar

Method: GET

Description: Allows owners to download tenant Aadhaar securely
```
Frontend (Owner)
-----------------------------------------
| Fill registration form                |
| - First Name, Last Name               |
| - Email, Phone Number                 |
| - Address, Business Name, GST Number |
| Upload Profile Image (optional)       |
-----------------------------------------
            |
            | (multipart/form-data POST)
            v
Backend (Spring Boot)
-----------------------------------------
Controller: OwnerController
-----------------------------------------
@RequestPart("owner") → OwnerRegistrationDTO
@RequestPart("profileImage") → MultipartFile
|
v
Service: OwnerService
-----------------------------------------
1. Check if email already exists
2. Save profile image using FileStorageService (if provided)
3. Encrypt password
4. Map DTO + User + profileImage → Owner entity
5. Save Owner in database
   |
   v
   Response: OwnerResponseDTO
-----------------------------------------
| id                |
| firstName         |
| lastName          |
| email             |
| phoneNumber       |
| businessName      |
| profileImageUrl   |
-----------------------------------------
Frontend receives response and confirms registration

PG / Room / Tenant flows follow similar service mapping for CRUD operations.
