# 🧠 Kafoor Backend — Quiz Management Service

A dedicated microservice for **creating, managing, and running live quizzes in real time** as part of the **Kafoor** online quiz platform. Handles the full quiz lifecycle, questions, participants, and live scoring — while delegating authentication and user data to the [User Account Service](https://github.com/KirillMikhailov442/kafoor-backend-users).


## 📌 Key Features

- ✅ Full quiz lifecycle: create, update, delete, publish  
- ❓ Manage questions and multiple-choice options  
- 👥 Participant (member) registration for live quizzes  
- ⚡ Real-time quiz execution with live updates (via WebSocket / Socket.IO)  
- 📊 Live rating and score tracking  
- 🔐 JWT-based authentication (delegated to User Service)  
- 🔄 Automated database migrations with Flyway  
- 📄 API documentation via Swagger UI (OpenAPI 3)  
- 🧪 Input validation using Bean Validation  

> ❗ **No user data stored locally** — only user IDs are referenced. Full profiles are fetched from the User Account Service when needed.


## 🛠 Tech Stack

- **Language**: Java 23
- **Framework**: Spring Boot 3.x  
- **Real-time**: Spring WebSocket / Socket.IO  
- **Database**: MySQL 8.0  
- **ORM**: Spring Data JPA + Hibernate (`ddl-auto=validate` in production)  
- **Security**: Spring Security + JWT (validated against User Service)  
- **Migrations**: Flyway  
- **Build Tool**: Gradle  
- **API Docs**: Springdoc OpenAPI (Swagger UI)  
- **Inter-service communication**: REST calls to User Service (e.g., by user ID)  


## 🚀 Quick Start

### Prerequisites
- **User Account Service** must be running (e.g., on `http://localhost:8082`)  
- Docker and Docker Compose *(recommended)*  
- OR locally: JDK 23+, MySQL 8.0, Gradle 8+

### Run with Docker Compose (Recommended)

1. Clone the repository:
```bash
   git clone https://github.com/KirillMikhailov442/kafoor-backend-quizzes.git
   cd kafoor-backend-quizzes
```

2. Start the services:
```bash
docker-compose up --build
```
3. The service will be available at:
🔗 http://localhost:8082

4. Explore the API documentation:
📘 http://localhost:8082/swagger-ui.html

## 🔐 Security

Protected endpoints require the Authorization header:
> Authorization: Bearer <access_token>
* Tokens are issued on login and can be refreshed via /auth/update-tokens.
* Passwords are securely hashed using BCrypt.

## 📖 API Documentation

Open in browser: http://localhost:8082/swagger-ui.html

## 📄 License

This project is licensed under the MIT License.
See LICENSE for details.