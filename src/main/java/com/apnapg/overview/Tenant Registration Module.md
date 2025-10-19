# Apna PG – Tenant Registration Module

## Project Overview
- The Tenant Registration module allows tenants to register in the Apna PG system by providing personal details and uploading their Aadhaar document. Tenant data is stored in PostgreSQL, and uploaded Aadhaar files are stored in a configurable directory.
- This module allows tenants to register in the Apna PG system. Tenants provide personal details and upload their Aadhaar document during registration. The module stores tenant information in PostgreSQL and saves the Aadhaar file in a configurable uploads directory.
- I developed a Tenant Registration module for my Apna PG project. It allows tenants to register by providing personal details and uploading their Aadhaar during the same request. I used Spring Boot with DTOs, service layer, and file storage service. I ensured proper validation and logging, and I handled the multipart request correctly by converting JSON from string to DTO using ObjectMapper. This module is now fully working and production-ready, and it can be easily extended for security and role-based access in the future

# 🧾 Tenant Registration API
## 🔑 Key Features
- Register tenant with personal details and Aadhaar upload
- Validates fields using Jakarta Bean Validation
- Stores tenant in PostgreSQL
- Stores Aadhaar file in local uploads folder
- Logs registration process for debugging
- Handles multipart JSON + file requests correctly
---
## 📝 Notes
- *Maximum file size*: 5MB (configurable in application.yml)
- *Allowed file formats*: PDF / JPG / PNG
- *Passwords* should be encrypted before saving (future enhancement)
- *Logging* enabled for debugging and monitoring
---

## API Endpoint

### Register Tenant
- **URL:** `/api/tenants/register`
- **Method:** `POST`
- **Content-Type:** `multipart/form-data`

## 📬 Postman Usage

- *Request Type*: POST
- *URL*: http://localhost:8080/api/tenants/register
- *Body → form-data*:
    - tenant → Text → JSON string of tenant info
    - aadhaarFile → File → Upload Aadhaar document
- Click *Send*

---



#### Request – Form Data
| Key          | Type | Description                                         |
|--------------|------|-----------------------------------------------------|
| tenant       | Text | Tenant details as JSON string (see example below)  |
| aadhaarFile  | File | Aadhaar document (PDF/JPG/PNG)                     |

#### Tenant JSON Example
```json
{
  "firstName": "Akash",
  "lastName": "Jena",
  "email": "akash.jena@example.com",
  "phoneNumber": "9876543210",
  "gender": "MALE",
  "occupation": "WORKING_PROFESSIONAL",
  "dateOfBirth": "1995-05-10",
  "address": "123 Main Street, Bangalore",
  "emergencyContactName": "Jane Doe",
  "emergencyContactNumber": "9876543211",
  "password": "StrongPassword123"
}
```

````
{
  "id": 2,
  "firstName": "Akash",
  "lastName": "Jena",
  "email": "akash.jena@example.com",
  "phoneNumber": "9876543210",
  "aadhaarUrl": "/uploads/1760835680331_user.jpg"
}
````
### Structure Diagram
````
Frontend (Tenant) 
-----------------------------------------
| Fill registration form                |
| - First Name, Last Name               |
| - Email, Phone Number                 |
| - Gender, Occupation                  |
| - Date of Birth, Address              |
| - Emergency Contact Name & Number     |
| - Password                            |
| Upload Aadhaar File                    |
-----------------------------------------
            |
            | (multipart/form-data POST)
            v
Backend (Spring Boot)
-----------------------------------------
Controller: TenantController
-----------------------------------------
@RequestPart("tenant") → TenantRegistrationDTO (JSON)
@RequestPart("aadhaarFile") → MultipartFile
            |
            v
Service: TenantService
-----------------------------------------
1. Check if email already exists
2. Save Aadhaar file using FileStorageService
   → returns file path (aadhaarUrl)
3. Create User entity with tenant-provided password
4. Map TenantRegistrationDTO + Aadhaar path + User → Tenant entity
5. Save Tenant in database
            |
            v
Response: TenantResponseDTO
-----------------------------------------
| id                                  |
| firstName                           |
| lastName                            |
| email                               |
| phoneNumber                          |
| aadhaarUrl (path to uploaded file)   |
-----------------------------------------
Frontend receives response and confirms registration

````