# 🧠 Quiz Microservice

A dedicated microservice for **creating, managing, and running live quizzes in real time**.  
Part of a larger system that includes a separate **User Account Service** for authentication and user management.

> 🔗 **Works with**: [User Account Service](https://github.com/KirillMikhailov442/kafoor-backend-users) *(replace with actual link if available)*

## 🛠 Tech Stack
- Java 21
- Spring Boot
- Spring JPA
- Spring Security
- MySQL
- Gradle
- Swagger
- SocketIO


## 🎯 Purpose
This service handles:
- Quiz lifecycle (create, update, delete)
- Questions & answer options management
- Participant (member) registration
- Real-time quiz execution (start/finish)
- Live rating data

🔐 **Authentication**: Delegates to the **User Account Service** via JWT validation.  
👥 **User data**: Fetched from the User Service as needed (e.g., by user ID).


## 🗃 Database
- **Type**: MySQL
- **Schema**: Managed by Hibernate (`spring.jpa.hibernate.ddl-auto=validate` in production)
- **Owned entities**: `Quiz`, `Question`, `Option`, `Member`, `QuestionsOption`

> ❗ **No user data stored here** — only user IDs (foreign keys). Full user profiles are managed by the **User Account Service**.


## 🔌 Integration with User Account Service
- **Authentication**: Validates JWT issued by User Service |
- **User lookup (by ID)**: REST call or async message (e.g., Kafka) |
- **Ownership checks**: Compares `user_id` from JWT with resource owner |

> 💡 This service is **stateless** and **does not store passwords or sensitive user data**.

---

## 🚀 Local Development
### Prerequisites
- Java 21+
- Gradle 8+
- MySQL 8+
- Running **User Account Service** (e.g., on `localhost:8081`)

### Run
```bash
git clone https://github.com/kafoor/quiz-service.git
cd quiz-service
./gradlew bootRun
```

## 📖 API Documentation
Open in browser:
http://localhost:8081/swagger-ui.html
> ⚠️ Make sure the User Service is running — this service validates tokens against its public key or introspection endpoint.

## 📄 License
The project is available under the MIT License. See the LICENSE file for details.


Deviloped with ❤️ for easy video communication.