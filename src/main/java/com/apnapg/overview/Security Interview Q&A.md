# 🔐 Security Interview Q&A

---

## 1️⃣ Why didn’t you use Spring Security default password?
**Interviewer:** Why not use Spring default generated password?  
**Answer:**  
Spring default password is only for development testing. It is not secure and not usable in production.

In my project, I implemented:
- Custom user table
- BCrypt password hashing
- Account lock system
- JWT authentication
- Refresh token rotation

So default password system is not suitable for real production application.

---

## 2️⃣ Why not use formLogin()?
**Interviewer:** Why didn’t you use Spring Security formLogin?  
**Answer:**  
formLogin is session-based authentication. It stores session in server memory.

My project is stateless REST API.  
So I used JWT because:
- No session storage
- Scalable
- Suitable for React frontend
- Works well with microservices

---

## 3️⃣ What is JWT?
**Interviewer:** What is JWT?  
**Answer:**  
JWT means JSON Web Token.  
It is a compact, signed token that contains user identity and role.

It has 3 parts:
- Header
- Payload
- Signature

It is signed using secret key. So server can verify token integrity.

---

## 4️⃣ Why JWT instead of Session?
**Interviewer:** Why JWT instead of session?  
**Answer:**  
Session is stored in server memory. If we scale to multiple servers, session sharing becomes complex.

JWT is stateless. Server does not store session. Token itself contains authentication information.  
So it is scalable and cloud-friendly.

---

## 5️⃣ Where do you store access token?
**Answer:**  
Access token is stored in frontend memory (React state).  
Not in localStorage for better security.  
It is short-lived (15 minutes).

---

## 6️⃣ Why use Refresh Token?
**Interviewer:** Why not just use long access token?  
**Answer:**  
If access token is long-lived and stolen, attacker can use it for long time.

So we:
- Keep access token short (15 min)
- Use refresh token to get new access token

This improves security.

---

## 7️⃣ Why store refresh token in HttpOnly cookie?
**Answer:**  
Refresh token is sensitive.  
If stored in localStorage, JavaScript can read it.  
If XSS attack happens, attacker can steal it.

HttpOnly cookie prevents JavaScript access.  
So even if XSS happens, token cannot be stolen.

---

## 8️⃣ Why hash refresh token in DB?
**Answer:**  
If database is compromised, attacker should not get real refresh tokens.  
So I store only SHA-256 hash of token.  
Just like password hashing.  
This protects against database leak attacks.

---

## 9️⃣ What is Token Rotation?
**Answer:**  
When refresh token is used:
- Old token is revoked
- New refresh token is generated

This prevents token reuse attack.  
If someone tries to reuse old token, system detects attack.

---

## 🔟 What is Refresh Token Reuse Attack?
**Answer:**  
If attacker steals refresh token and tries to use it after user already used it,  
System detects that old token is revoked.  
Then all tokens of that user are revoked.  
User is forced to login again.

---

## 1️⃣1️⃣ Why short access token?
**Answer:**  
Access token is stored in memory and sent in every request.  
If stolen, attacker can use it.  
So we keep it short-lived.  
Even if stolen, damage window is small.

---

## 1️⃣2️⃣ What happens when access token expires?
**Answer:**  
Frontend detects `401 Unauthorized`.  
Then frontend calls `/api/auth/refresh`.  
Browser automatically sends HttpOnly refresh token.  
Server validates refresh token and issues new access token.

---

## 1️⃣3️⃣ Why stateless but refresh token stored in DB?
**Answer:**  
Access token is stateless.  
But refresh token is stateful for security control.

Because we want:
- Logout invalidation
- Token revocation
- Session limit
- Reuse detection

So refresh token must be stored in database.

---

## 1️⃣4️⃣ What is account lock mechanism?
**Answer:**  
If user enters wrong password multiple times:
- Failed attempts increment
- After 5 attempts, account locked
- After 15 minutes, auto unlock

This prevents brute force attack.

---

## 1️⃣5️⃣ Why use BCrypt?
**Answer:**  
BCrypt is adaptive hashing algorithm.  
It automatically adds salt.  
It is slow by design.  
Slow hashing prevents brute force attacks.

---

## 1️⃣6️⃣ What is XSS?
**Answer:**  
Cross-Site Scripting attack.  
Attacker injects malicious JavaScript.  
That script can steal tokens if stored in localStorage.  
That is why we use HttpOnly cookie.

---

## 1️⃣7️⃣ What is CSRF?
**Answer:**  
Cross-Site Request Forgery.  
It tricks browser to send request automatically.

JWT API is stateless.  
We disable CSRF because we don’t use session authentication.

---

## 1️⃣8️⃣ Why disable CSRF?
**Answer:**  
CSRF protection is needed for session-based authentication.  
Since we use JWT stateless authentication, CSRF protection is not required.

---

## 1️⃣9️⃣ What is difference between Authentication and Authorization?
**Answer:**
- Authentication = Who are you
- Authorization = What can you access

JWT verifies authentication.  
Role-based access verifies authorization.

---

## 2️⃣0️⃣ What security improvements did you implement?
**Answer:**  
In my project I implemented:
- JWT stateless authentication
- Refresh token rotation
- HttpOnly cookie storage
- Token hashing
- Account lock mechanism
- Role-based authorization
- Logout invalidation
- Token reuse detection

---

### 🎯 If interviewer asks:
**"Explain your security architecture"**  
You say:  
My system uses stateless JWT authentication with short-lived access tokens and rotating refresh tokens stored as hashed values in HttpOnly cookies. I implemented account locking, token revocation, and role-based access control for production-level security.