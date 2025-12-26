# Movie Ticket App (React + Spring Boot + MySQL)

## Backend
- Java 17, Spring Boot 3, JPA, MySQL
- Start:
  1. Create database `movieticket` in MySQL
  2. Edit `backend/src/main/resources/application.properties` with your MySQL username/password
  3. In `backend/`, run: `./mvnw spring-boot:run` (or `mvn spring-boot:run`)
- Endpoints:
  - POST /api/auth/register {name,email,password}
  - POST /api/auth/login {email,password} -> {token}
  - GET /api/movies
  - GET /api/movies/{id}/shows
  - GET /api/wallet/balance  (header: X-Auth-Token)
  - POST /api/wallet/deposit {amount} (header: X-Auth-Token)
  - POST /api/bookings {showId,seats} (header: X-Auth-Token)
  - GET /api/bookings (header: X-Auth-Token)

## Frontend
- React with React Router + Axios
- Start:
  1. In `frontend/`, run `npm install`
  2. Start dev server: `npm start` (port 3000)
- Pages:
  - /register
  - /login
  - / (Movies list)
  - /movies/:id/shows
  - /wallet
  - /book/:showId
  - /bookings
