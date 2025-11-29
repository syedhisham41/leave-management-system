# 🛡️ Authentication Service 
### Leave Management System

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Server](https://img.shields.io/badge/Server-Core%20Java%20HttpServer-1f425f)
![Database](https://img.shields.io/badge/Database-SQLite-003B57?logo=sqlite&logoColor=white)
![JDBC](https://img.shields.io/badge/Persistence-JDBC-4c1?logo=databricks&logoColor=white)
![Flyway](https://img.shields.io/badge/Migrations-Flyway-CC0200?logo=flyway&logoColor=white)
![Queue](https://img.shields.io/badge/Event%20Stream-RabbitMQ-FF6600?logo=rabbitmq&logoColor=white)
![Auth](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)
![Config](https://img.shields.io/badge/Config-Dotenv-8A2BE2)
![Architecture](https://img.shields.io/badge/Architecture-Microservice-3A6EA5)


The **Auth Service** is a core microservice in the **Leave Management System (LMS)**.  
It handles authentication, authorization, secure password hashing and storage, and JWT token generation.

---

This service is intentionally lightweight and framework-free, built using:

- **Java 21**
- **com.sun.net.httpserver.HttpServer**
- **SQLite** with Flyway (script-based & Java-based migrations)
- **Jackson** for JSON parsing
- **java-jwt (Auth0)** for JWT generation
- **Dotenv** for environment configuration
- **Apache HttpClient** for inter-service HTTP calls
- **RabbitMQ** for event-based communication with `employee-service`
- **Custom centralized exception handling** with pluggable exception mappers

---

### 📌 Purpose

- **Employee-Only Signup**  
  - Users can register only if their details already exist in the Employee Service.  
  - Ensures LMS remains an internal corporate platform, not a public registration portal.  

- **Secure Login**  
  - Validates user credentials.  
  - Issues JWT tokens with claims such as `employeeId`, `role`, and `expiry`.  
  - Uses:
    - PBKDF2 hashing
    - Random salt
    - Secure parameterized checks

- **Internal-Only APIs**  
  - Designed specifically for inter-service communication between Employee Service and Leave Request Service.  

- **Debug Utilities**  
  - Contains a development-only debug API to inspect:
    - recent exceptions
    - exception count

---


## 📂 Directory Structure (Inside auth-service/)

```bash
auth-service/
├── data/                          # SQLite DB files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── auth_service_runner/      # Main server bootstrap
│   │   │   ├── config/                   # Environment config, property loading
│   │   │   ├── constants/                # Enums, constant values
│   │   │   ├── dao/                      # DAO layer
│   │   │   │   ├── interfaces/           # DAO contracts
│   │   │   │   └── impl/sqlite/          # SQLite DAO implementations
│   │   │   ├── db/                       # Flyway Java-based migrations
│   │   │   ├── dto/                      # Request/response DTOs
│   │   │   ├── event/                    # Event model & consumers (future use)
│   │   │   ├── exceptions/               # Custom exceptions & mappers
│   │   │   ├── handler/                  # HTTP Handlers (signup/login/debug)
│   │   │   │   └── swagger_ui/           # Static Swagger UI handler
│   │   │   ├── model/                    # Domain models (User, Roles)
│   │   │   ├── service/                  # Core business logic
│   │   │   └── utils/                    # Utilities
│   │   │       ├── dto/                  # Internal helper DTOs
│   │   │       ├── handler/              # Handler helpers
│   │   │       ├── http/                 # HTTP helper classes
│   │   │       └── service/proxy/        # Employee-service proxy (HTTP)
│   │   └── resources/
│   │       ├── db/migration/             # Flyway SQL migrations
│   │       ├── specs/                # OpenAPI spec (YAML)
│   │       └── swagger-ui/               # Static Swagger UI bundle
│   └── test/
│       └── java/                         # Unit tests (JUnit 5. Planned)

```

## 🛠 Tech Stack

| Component                 | Technology / Approach                                 |
|---------------------------|--------------------------------------------------------|
| **Language**              | Java 21                                                |
| **Server**                | `com.sun.net.httpserver.HttpServer`                   |
| **Database**              | SQLite                                                 |
| **DB Migrations**         | Flyway (SQL-based + Java-based migrations)             |
| **JSON Processing**       | Jackson                                                |
| **Security / JWT**        | `java-jwt` (Auth0)                                     |
| **Config Management**     | Dotenv                                                 |
| **Inter-service HTTP**    | Apache HttpClient                                      |
| **Event Communication**   | RabbitMQ (EmployeeCreated / EmployeeDeleted events)    |
| **Event Consumers**       | Custom consumer layer (`EventConsumer<T>` pattern)     |
| **Exception Handling**    | Centralized ExceptionMapperManager + pluggable mappers |

---

## 🔗 Inter-Service Communication

The Auth Service interacts with **employee-service** using both **HTTP** and **RabbitMQ event-based** communication.

### 📡 1. HTTP-Based Validation (During Signup & Login)

The service verifies employee identity before allowing signup by calling employee-service:

- `GET http://localhost:8080/employee/get?id={id}`
- `GET http://localhost:8080/employee/get?email={email}`

These requests are sent through the internal proxy:

**`utils.service.proxy.Utils_employee_service_proxy`**

All inter-service HTTP requests include a secure internal header: 
- **`X-Service-Auth: {SERVICE_SECRET}`**

*This ensures only trusted microservices can communicate.*

---
### 📨 2. Event-Based Communication (RabbitMQ)

Auth Service also listens to **asynchronous events** published by employee-service:

- **EmployeeDeletedEvent** → Auth Service removes the corresponding user record.

Consumer example:
- **`event.consumer.EmployeeDeletedEventConsumer`**

*This enables real-time data synchronization without tight coupling.*

---

## 🧩 Centralized Exception Handling

The Auth Service uses a custom, extensible **exception mapping system** to ensure consistent error responses across all endpoints.

### ✔️ ExceptionMapperManager

The `ExceptionMapperManager` is the core component responsible for:

- Maintaining a list of registered exception mappers  
- Routing exceptions to the correct mapper based on type  
- Storing the **last 10 exceptions** for debugging  
- Tracking the **total exception count**  
- Supporting a `debug` mode for verbose logging  

### 🔌 Exception Mappers

The service includes multiple specialized mappers, each responsible for handling specific categories of exceptions:

- **ServiceExceptionMapper**
- **DBExceptionMapper**
- **JSONExceptionMapper**
- **ParameterExceptionMapper**

Each mapper implements the shared `ExceptionMapper` interface, ensuring:

- Clear separation of concerns  
- Predictable API error structure (`DTO_api_response`)  
- Easy extension for new exception types  

---

## 🚀 API Endpoints

### POST /auth/signup
Registers a new user (only if employee exists).

### POST /auth/login
Authenticates user and returns JWT token.

### GET /debug/exceptions   (dev-only)
Returns last 10 exceptions.

---

## 🔐 JWT Authentication

- Algorithm: HS256  
- Expiry: Configurable (`JWT_EXPIRY_MILLIS`)  
- Secret: Loaded from `.env` (`JWT_SECRET`)  

All protected endpoints require:

`Authorization: Bearer <token>`

---

## ▶️ Running the Auth Service Locally

Follow the steps below to run the Auth Service in a local development environment.

### 1. Clone the Repository
Make sure you have cloned the LMS monorepo or the standalone `auth-service` folder.
```bash
git clone https://github.com/syedhisham41/leave-management-system.git
```
### 2. Navigate to the Auth Service
```bash
cd auth-service
```
### 3. Open the Project in VS Code (or your preferred IDE)

### 4. Configure Environment Variables
- Create a `.env` file in the project root by copying the provided template:
```bash
cp .env.example .env
```
- Update values if needed (database path, JWT secret, service URLs, etc.).

### 5. Run the Service
Run the main entry class:
`src/main/java/auth_service_runner/Auth_Runner.java`
*(Or run directly from your IDE’s “Run” button.)*

### 6. Service Startup
Once started, the Auth Service will be available at:
`http://localhost:8082/`

### 7. Verify the Service

You can confirm the service is running by accessing:
- Swagger UI 
    - `http://localhost:8082/docs/`

Everything should now be up and running locally.

---

## 📄 OpenAPI Documentation (Swagger UI)

The Auth Service exposes interactive API documentation using **Swagger UI**.

### 🔍 Swagger UI
Access the UI at:
**`http://localhost:8082/docs/`**

### 📘 OpenAPI Specification
Raw YAML specification is available at:
**`http://localhost:8082/specs/openapi.yaml`**

---

## ⚙️ Environment Variables

This service loads configuration from a `.env` file.  
Copy the provided template and update values as needed:

```bash
cp .env.example .env
```

The `.env.example` file documents every required variable for running the Auth Service (database, JWT, service URLs, and internal secrets).

---


## 🔐 Database Schema (`users` Table)
Created via Flyway:

```sql
CREATE TABLE IF NOT EXISTS users (
  emp_id INTEGER PRIMARY KEY,
  email TEXT UNIQUE NOT NULL,
  password_hash TEXT NOT NULL,
  role TEXT NOT NULL CHECK(role IN ('EMPLOYEE', 'MANAGER', 'ADMIN')) DEFAULT 'EMPLOYEE',
  is_active BOOLEAN DEFAULT TRUE,
  last_login_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  salt TEXT NOT NULL
);

```
