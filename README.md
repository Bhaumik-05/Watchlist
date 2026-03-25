# 🎬 Movie Watchlist API

A RESTful backend API built with **Spring Boot** that lets users manage their personal movie watchlists and reviews. Admins manage the movie catalogue while authenticated users can track what they want to watch and leave reviews.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Framework | Spring Boot |
| Language | Java |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security (Basic Auth + BCrypt) |
| Build Tool | Maven |

---

## 📁 Project Structure

```
src/main/java/com/movies/watchlist/
│
├── controller/         # REST endpoints
│   ├── MovieController.java
│   ├── ReviewController.java
│   ├── UserController.java
│   └── WatchlistController.java
│
├── service/            # Business logic
│   ├── MovieService.java
│   ├── ReviewService.java
│   ├── UserService.java
│   └── WatchlistService.java
│
├── repository/         # Database queries (Spring Data JPA)
│   ├── MovieRepository.java
│   ├── ReviewRepository.java
│   ├── UserRepository.java
│   └── WatchlistRepository.java
│
├── entity/             # JPA entities (DB tables)
│   ├── Movie.java
│   ├── Review.java
│   ├── User.java
│   └── Watchlist.java
│
├── dto/                # Data Transfer Objects
│   ├── MovieDTO.java
│   ├── ReviewDTO.java
│   └── WatchlistDTO.java
│
├── enums/              # Fixed value sets
│   ├── Role.java        (USER, ADMIN)
│   └── WatchStatus.java (PLANNED, WATCHED)
│
├── security/
│   └── CustomUserDetailsService.java
│
├── config/
│   └── SecurityConfig.java
│
└── exception/
    └── GlobalExceptionHandler.java
```

---

## ⚙️ Setup & Running

### Prerequisites
- Java 17+
- PostgreSQL
- Maven (or use the included `mvnw` wrapper)

### 1. Clone the repository
```bash
git clone https://github.com/your-username/movie-watchlist.git
cd movie-watchlist
```

### 2. Configure the database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/watchlist_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the application
```bash
./mvnw spring-boot:run
```

The API will start at `http://localhost:8080`

> ⚠️ Never run with `javac WatchlistApplication.java` — always use Maven.

---

## 🔐 Authentication

This API uses **HTTP Basic Authentication**.

| Role | Can Do |
|------|--------|
| `ADMIN` | Add, update, delete movies |
| `USER` | Manage watchlist, write reviews |
| Public | Register only |

In Postman: `Authorization` tab → `Basic Auth` → enter username & password.

---

## 📡 API Endpoints

### 👤 Users

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/users/register` | None | Register a new user |

**Register User:**
```json
{
  "username": "john",
  "password": "pass123",
  "role": "USER"
}
```

**Register Admin:**
```json
{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

### 🎬 Movies

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/movies` | ADMIN | Add a new movie |
| GET | `/movies` | Any logged-in user | Get all movies |
| GET | `/movies/{id}` | Any logged-in user | Get movie by ID |
| PUT | `/movies/{id}` | ADMIN | Update a movie |
| DELETE | `/movies/{id}` | ADMIN | Delete a movie |

**Request Body (POST / PUT):**
```json
{
  "title": "Inception",
  "genre": "Sci-Fi",
  "releaseYear": 2010
}
```

---

### 📋 Watchlist

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/watchlist` | USER | Add movie to watchlist |
| GET | `/watchlist/user/{userId}` | USER | Get user's watchlist |
| PUT | `/watchlist/{id}?status=WATCHED` | USER | Update watch status |
| DELETE | `/watchlist/{id}` | USER | Remove from watchlist |

**Request Body (POST):**
```json
{
  "user": { "id": 1 },
  "movie": { "id": 1 },
  "status": "PLANNED"
}
```

**Status values:** `PLANNED` | `WATCHED`

---

### ⭐ Reviews

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/reviews` | USER | Add a review |
| GET | `/reviews/movie/{movieId}` | Any logged-in user | Get reviews for a movie |
| DELETE | `/reviews/{id}` | USER | Delete a review |

**Request Body (POST):**
```json
{
  "user": { "id": 1 },
  "movie": { "id": 1 },
  "rating": 9,
  "comment": "Amazing movie!"
}
```

> Rating must be between **1 and 10**.

---

## 🗄️ Database

### Flush all data (PostgreSQL / pgAdmin4)
```sql
TRUNCATE TABLE watchlist, reviews, movies, users RESTART IDENTITY CASCADE;
```

### Entity Relationships
```
User ──< Watchlist >── Movie
User ──< Review   >── Movie
```

---

## 🛡️ Security Notes

- Passwords are hashed using **BCrypt** before storing
- Password field is **write-only** — never returned in API responses
- CSRF protection is disabled (stateless REST API)
- Role-based access control enforced at the route level

---

## 🚀 Recommended Next Steps

- [ ] Implement DTO mapping to return clean API responses
- [ ] Replace Basic Auth with **JWT tokens**
- [ ] Add pagination for `GET /movies` and watchlist endpoints
- [ ] Connect a React frontend

---

## 📄 License

This project is for educational purposes.
