
# 🏗 APNA PG BACKEND – INTERNAL EXECUTION FLOW DIAGRAM

This document explains how data flows internally between layers in the entire project.

Architecture Style:
```
Controller → Service → Repository → Database
Controller ← Service ← Repository ← Database
```
Security Layer sits before Controller.

------------------------------------------------------------
🔐 1️⃣ AUTH MODULE FLOW
------------------------------------------------------------
---
```
LOGIN FLOW

Client
   ↓
POST /api/auth/login
   ↓
Security Filter (JwtAuthenticationFilter skipped for login)
   ↓
AuthController.login()
   ↓
AuthService.login()
   ↓
UserRepository.findByEmail()
   ↓
PasswordEncoder.matches()
   ↓
JwtService.generateAccessToken()
   ↓
RefreshTokenService.createRefreshToken()
   ↓
Hash refresh token → Save in DB
   ↓
Return:
   - Access Token (JSON Body)
   - Refresh Token (HttpOnly Cookie)
```
---
```
REFRESH TOKEN FLOW

Client
   ↓
POST /api/auth/refresh
   ↓
Read refreshToken from Cookie
   ↓
AuthController.refresh()
   ↓
RefreshTokenService.rotateRefreshToken()
   ↓
Validate hash in DB
   ↓
Generate new access token
   ↓
Generate new refresh token
   ↓
Update DB
   ↓
Return new Access Token + Set new Cookie

```
---
------------------------------------------------------------
🏠 2️⃣ OWNER MODULE FLOW
------------------------------------------------------------
```
REGISTER OWNER

Client
   ↓
POST /api/owners/register
   ↓
OwnerController
   ↓
OwnerService.registerOwner()
   ↓
UserRepository.save()
   ↓
OwnerRepository.save()
   ↓
Return OwnerResponseDTO
```

------------------------------------------------------------
🏢 3️⃣ PG MODULE FLOW
------------------------------------------------------------
```
CREATE PG

Client (OWNER)
   ↓
POST /api/pgs
   ↓
SecurityFilter (Validate JWT)
   ↓
PGController.createPG()
   ↓
PGService.createPG()
   ↓
OwnerRepository.findById()
   ↓
PGRepository.save()
   ↓
Return PGResponse
```
```
SEARCH PG

Client
   ↓
GET /api/pgs/search
   ↓
PGController.searchPGs()
   ↓
PGService.searchPGs()
   ↓
PGRepository.findByCityIgnoreCase()
   ↓
Map Entity → DTO
   ↓
Return PageResponseDTO
```

------------------------------------------------------------
🛏 4️⃣ ROOM MODULE FLOW
------------------------------------------------------------
```
CREATE ROOM

Client (OWNER)
   ↓
POST /api/rooms/{pgId}
   ↓
RoomController
   ↓
RoomService.createRoom()
   ↓
RoomRepository.save()
   ↓
Return RoomResponseDTO
```

GET ROOM AVAILABILITY
```
Client
   ↓
GET /api/rooms/availability/{pgId}
   ↓
RoomController
   ↓
RoomService.getRoomAvailability()
   ↓
RoomRepository.findByPgId()
   ↓
Calculate occupancy
   ↓
Return List<RoomAvailabilityDTO>
```

------------------------------------------------------------
👤 5️⃣ TENANT MODULE FLOW
------------------------------------------------------------
```
REGISTER TENANT (Multipart)

Client
   ↓
POST /api/tenants/register
   ↓
TenantController (Multipart parsing)
   ↓
ObjectMapper → Convert JSON String → DTO
   ↓
TenantService.registerTenant()
   ↓
FileStorageService.store()
   ↓
UserRepository.save()
   ↓
TenantRepository.save()
   ↓
Return TenantResponseDTO
```

ALLOCATE ROOM
```
Client (OWNER)
   ↓
PUT /api/tenants/{tenantId}/allocate/{roomId}
   ↓
SecurityFilter (JWT validation)
   ↓
TenantController.allocateRoom()
   ↓
TenantService.allocateRoom()
   ↓
SecurityUtils.getCurrentUserId()
   ↓
RoomRepository.findByIdForUpdate()
   ↓
Validate ownership
   ↓
Update availableBeds
   ↓
Set tenant.room
   ↓
Transaction commit
```

VACATE ROOM
```
Client (TENANT)
   ↓
PUT /api/tenants/vacate
   ↓
TenantService.vacateRoom()
   ↓
Increase availableBeds
   ↓
Remove tenant.room
   ↓
Transaction commit
```

------------------------------------------------------------
📢 6️⃣ COMPLAINT MODULE FLOW
------------------------------------------------------------
```
CREATE COMPLAINT

Client (TENANT)
   ↓
POST /api/complaints
   ↓
ComplaintController
   ↓
ComplaintService.createComplaint()
   ↓
SecurityUtils.getCurrentUserId()
   ↓
TenantRepository.findByUserId()
   ↓
Validate PG match
   ↓
ComplaintRepository.save()
   ↓
Return ComplaintResponseDTO
```

UPDATE STATUS
```
Client (OWNER)
   ↓
PUT /api/complaints/{id}/status
   ↓
ComplaintService.updateStatus()
   ↓
Validate PG ownership
   ↓
Update status
   ↓
Transaction commit
```

------------------------------------------------------------
💬 7️⃣ CHAT MODULE FLOW (WEBSOCKET)
------------------------------------------------------------
```
CONNECT FLOW

Client
   ↓
WebSocket CONNECT
   ↓
WebSocketAuthChannelInterceptor
   ↓
Extract JWT
   ↓
Validate token
   ↓
Set StompPrincipal
```

SEND MESSAGE FLOW
```
Client
   ↓
/app/chat.send
   ↓
ChatWebSocketController
   ↓
ChatService.sendMessage()
   ↓
ChatRepository.save()
   ↓
messagingTemplate.convertAndSendToUser()
   ↓
Delivered to recipient

```
REST CHAT HISTORY
```
Client
   ↓
GET /api/chat/history
   ↓
ChatController
   ↓
ChatService.getChatHistory()
   ↓
ChatRepository queries
   ↓
Return DTO
```

------------------------------------------------------------
🧱 LAYER RESPONSIBILITIES
------------------------------------------------------------
```
Controller:
- Handles HTTP
- Validates DTO
- Calls Service

Service:
- Business Logic
- Security checks
- Transaction control

Repository:
- Database queries
- JPA interaction

Entity:
- Database mapping

DTO:
- API contract (no entity leakage)

Security Layer:
- JWT validation
- Role verification
- Method security
```

------------------------------------------------------------
🚀 OVERALL REQUEST FLOW
------------------------------------------------------------
```
Client
   ↓
Security Filter (JWT Validation)
   ↓
Controller
   ↓
Service (Business Logic)
   ↓
Repository
   ↓
Database
   ↓
Repository
   ↓
Service
   ↓
Controller
   ↓
JSON Response

```
END OF DOCUMENT
