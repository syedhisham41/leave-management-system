# 🚀 Leave Request Service
### Leave Management System

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Server](https://img.shields.io/badge/Server-Core%20Java%20HttpServer-1f425f)
![Database](https://img.shields.io/badge/Database-SQLite-003B57?logo=sqlite&logoColor=white)
![JDBC](https://img.shields.io/badge/Persistence-JDBC-4c1?logo=databricks&logoColor=white)
![Flyway](https://img.shields.io/badge/Migrations-Flyway-CC0200?logo=flyway&logoColor=white)
![Queue](https://img.shields.io/badge/Event%20Stream-RabbitMQ-FF6600?logo=rabbitmq&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-Jackson-000000?logo=json&logoColor=white)
![Auth](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)
![Config](https://img.shields.io/badge/Config-Dotenv-8A2BE2)
![Architecture](https://img.shields.io/badge/Architecture-Microservice-3A6EA5)

A standalone microservice responsible for **managing employee leave applications**, **leave balances**, **approvals**, **audit logs**, and consuming employee events for automatic **leave balance lifecycle management**.

This service is part of the **Leave Management System (LMS)** ecosystem and communicates with other microservices (employee-service, auth-service) via **HTTP and RabbitMQ**.

---

## 📌 Features
### ✅ Leave Request Management

- Apply for leave (multiple leave types supported)
- Half-day support (FIRST_DAY_HALF, LAST_DAY_HALF, BOTH, NONE)
- Overlap prevention by service logic
- Auto leave-day calculation based on dates & half-day flags
- Cancel leave (employee only)

---

### ✅ Leave Approval Flow

- Managers/Admins can approve or reject leave
- Internal system auto-rejects leave if not acted upon before start date
- Approver comments stored in both request & audit logs

---

### ✅ Leave Balance Management

- Stores per-employee leave balance across leave types
- Automatically created when employee is created
- Automatically deleted when employee is deleted
- Updated after leave approval/rejection

---

### ✅ Audit Logging

Every action on a leave request is logged:
- APPLIED
- APPROVED
- REJECTED
- CANCELLED
- AUTO_REJECTED

Includes metadata: performed_by, comments, leave_days, approver comments, timestamps.

---

### ✅ Secure Access Control

**Employees:** Apply, view own leave & audit logs, cancel own leave

**Managers:** Approve/reject, view team leave, team audit logs

**Admins:** Full access

**Internal:** Service-to-service actions using fixed secret header

---

### ✅ Event Consumption

Consumes RabbitMQ events from employee-service:

**EmployeeCreatedEvent** → auto-create leave balances

**EmployeeDeletedEvent** → auto-delete leave balances

*This service does not publish any events.*

---

## 🛠 Tech Stack

| Component                       | Technology / Approach                                                          |
|---------------------------------|-------------------------------------------------------------------------------|
| **Language**                    | Java 21                                                                       |
| **Server**                      | `com.sun.net.httpserver.HttpServer` (lightweight embedded HTTP server)        |
| **Database**                    | SQLite (file-based relational DB)                                             |
| **DB Migrations**               | Flyway (schema versioning for leave requests & leave balances)                |
| **Persistence Layer**           | JDBC + DAO pattern                                                            |
| **JSON Processing**             | Jackson                                                                       |
| **Config Management**           | Dotenv                                                                         |
| **Security**                    | JWT-based authentication + internal service secret (`X-Service-Auth`)         |
| **Event Communication**         | RabbitMQ (consumes `EmployeeCreatedEvent` & `EmployeeDeletedEvent`)           |
| **Event Consumption Modes**     | RabbitMQ auto-consumer with listener registry                                 |
| **Event Handlers**              | Automatic LeaveBalance creation/deletion on employee lifecycle events         |
| **Validation Layer**            | Centralized validator utilities (dates, overlaps, quotas, leave types, etc.)  |
| **Leave Rules Engine**          | Custom logic for accrual, overlap prevention, half-day validation             |
| **Response Formatting**         | Standardized `DTO_api_response` wrappers                                      |
| **Exception Handling**          | Centralized ExceptionMapperManager + specific exception mappers               |
| **Service-to-Service Calls**    | Internal HTTP client wrapper (for Employee Service lookup)                    |
| **Swagger / API Docs**          | OpenAPI YAML + embedded Swagger UI handler                                    |

---

## 📂 Project Structure
```bash
.
├── data
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── config
│   │   │   ├── constants
│   │   │   │   └── enums
│   │   │   ├── dao
│   │   │   │   ├── factory
│   │   │   │   │   └── impl
│   │   │   │   ├── impl
│   │   │   │   │   └── sqlite
│   │   │   │   └── interfaces
│   │   │   ├── db
│   │   │   ├── dto
│   │   │   ├── event
│   │   │   │   ├── base
│   │   │   │   ├── consumer
│   │   │   │   ├── core
│   │   │   │   ├── listener
│   │   │   │   ├── registry
│   │   │   │   └── types
│   │   │   ├── exceptions
│   │   │   │   ├── exception
│   │   │   │   └── mapper
│   │   │   ├── handler
│   │   │   │   └── swagger_ui
│   │   │   ├── leave_request_service_runner
│   │   │   ├── model
│   │   │   ├── service
│   │   │   └── utils
│   │   │       ├── auth
│   │   │       ├── dao
│   │   │       ├── dto
│   │   │       ├── handler
│   │   │       │   ├── common
│   │   │       │   └── validator
│   │   │       ├── http
│   │   │       └── service
│   │   │           ├── proxy
│   │   │           └── validator
│   │   └── resources
│   │       ├── db
│   │       │   └── migration
│   │       ├── specs
│   │       └── swagger-ui
│   └── test
│       ├── java
│       └── resources
.
```

---

## 🔐 Authentication & Authorization

| Operation              | Employee     | Manager            | Admin | Internal        |
|------------------------|--------------|--------------------|-------|-----------------|
| Apply Leave            | ✔️ Self      | ✔️ Self           | ✔️    | ✖               |
| Cancel Leave           | ✔️ Self      | ✔️ Self           | ✔️    | ✔️ Auto-cancel   |
| Approve/Reject         | ✖            | ✔️ (team only)     | ✔️    | ✖               |
| Fetch Leave Requests   | ✔️ Self      | ✔️ Self + Team     | ✔️    | ✔️               |
| Fetch Audit Logs       | ✔️ Self      | ✔️ Self + Team     | ✔️    | ✔️               |

JWT-based authentication using:

`Authorization: Bearer <token>`

Internal requests use:

`X-Service-Auth: <SERVICE_SECRET>`

---

## 📨 RabbitMQ Event Consumption

This service listens to two events via RabbitMQ consumers:

1️⃣ **EmployeeCreatedEvent**
```json
{
  "employee_id": 101,
  "emp_department_id": "D001"
}
```


➡ Automatically creates leave balances for all supported leave types.

2️⃣ **EmployeeDeletedEvent**
```json
{
  "employee_id": 101
}
```

➡ Deletes all leave balances associated with the employee.

*No events are published by this service.*

--- 

## ⚙️ Environment Variables

The service uses **.env (Dotenv)**:

```env
# ===== Database =====
DB_URL=jdbc:sqlite:leave-request-service/data/Leave-management.db
DB_USER=
DB_PASSWORD=
MIGRATION_LOCATION=classpath:db/migration

# ===== Security =====
JWT_SECRET=changeme
JWT_EXPIRY_MILLIS=86400000
SERVICE_SECRET=changeme

# ===== Internal Service URLs =====
EMPLOYEE_SERVICE_URL=http://localhost:8080/
LEAVE_REQUEST_SERVICE_URL=http://localhost:8081/
AUTH_SERVICE_URL=http://localhost:8082/
```

*RabbitMQ config remains inside code (not env-based).*

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

## ▶️ Running the Leave-request Service Locally

Follow the steps below to run the leave-request Service in a local development environment.

### 1. Clone the Repository
Make sure you have cloned the LMS monorepo or the standalone `leave-request-service` folder.
```bash
git clone https://github.com/syedhisham41/leave-management-system.git
```
### 2. Navigate to the Leave-request Service
```bash
cd leave-request-service
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

`src/main/java/leave_request_service_runner/Runner.java`

*(Or run directly from your IDE’s “Run” button.)*

### 6. Service Startup
Once started, the Auth Service will be available at:
`http://localhost:8081/`

### 7. Verify the Service

You can confirm the service is running by accessing:
- Swagger UI 
    - `http://localhost:8081/docs/`

Everything should now be up and running locally.

---

## 📄 OpenAPI Documentation (Swagger UI)

The Leave-request Service exposes interactive API documentation using **Swagger UI**.

### 🔍 Swagger UI
Access the UI at:
**`http://localhost:8081/docs/`**

### 📘 OpenAPI Specification
Raw YAML specification is available at:
**`http://localhost:8081/specs/openapi.yaml`**

---