# 🔐 APNA PG Backend -- Security Interview Q&A Guide

## 1️⃣ Why did you use JWT instead of Spring formLogin?

**Answer:**\
formLogin is session-based authentication. It stores session data in
server memory.\
My project is a stateless REST API built for React frontend, so I used
JWT for scalability and stateless authentication.

------------------------------------------------------------------------

## 2️⃣ What is JWT?

**Answer:**\
JWT (JSON Web Token) is a signed token that contains user identity and
role.\
It has 3 parts: Header, Payload, Signature.\
The signature ensures token integrity.

------------------------------------------------------------------------

## 3️⃣ Why short-lived Access Token?

**Answer:**\
If stolen, attacker can misuse it.\
So we keep it short-lived (15 minutes) to reduce damage window.

------------------------------------------------------------------------

## 4️⃣ Why use Refresh Token?

**Answer:**\
Instead of making access token long-lived, we use refresh token to
generate new access tokens securely.

------------------------------------------------------------------------

## 5️⃣ Why store Refresh Token in HttpOnly Cookie?

**Answer:**\
HttpOnly prevents JavaScript access.\
Protects against XSS attacks.

------------------------------------------------------------------------

## 6️⃣ Why hash Refresh Token in database?

**Answer:**\
To protect against database leak attacks.\
Even if DB is compromised, raw token cannot be used.

------------------------------------------------------------------------

## 7️⃣ What is Token Rotation?

**Answer:**\
When refresh token is used: - Old token is revoked - New refresh token
is generated

Prevents reuse attacks.

------------------------------------------------------------------------

## 8️⃣ What is Refresh Token Reuse Attack?

**Answer:**\
If an attacker tries to reuse an already rotated token, system detects
it and revokes all sessions.

------------------------------------------------------------------------

## 9️⃣ Why disable CSRF?

**Answer:**\
CSRF protection is needed for session-based apps.\
JWT APIs are stateless, so CSRF is not required.

------------------------------------------------------------------------

## 🔟 What security features did you implement?

-   JWT stateless authentication\
-   Refresh token rotation\
-   HttpOnly cookie storage\
-   Refresh token hashing\
-   Account lock mechanism\
-   Role-based authorization\
-   Logout invalidation\
-   Reuse detection

------------------------------------------------------------------------

# 🎯 How to Explain in Interview (Short Version)

"My system uses stateless JWT authentication with short-lived access
tokens and rotating refresh tokens stored as hashed values in HttpOnly
cookies. I implemented account locking, token revocation, and role-based
access control for production-level security."
