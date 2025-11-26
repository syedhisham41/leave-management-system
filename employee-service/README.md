# 👥 Employee Service
### Leave Management System

<img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat-square">
<img src="https://img.shields.io/badge/JDBC-Database-blue?style=flat-square">
<img src="https://img.shields.io/badge/RabbitMQ-Event--Driven-orange?style=flat-square">
<img src="https://img.shields.io/badge/SQLite-3-blue?style=flat-square">
<img src="https://img.shields.io/badge/Flyway-Migrations-green?style=flat-square">
<img src="https://img.shields.io/badge/Jackson-JSON-yellow?style=flat-square">

Employee Service is a microservice of the **Leave Management System (LMS)**, built in **Java 21** using **JDBC** and **SQLite**, providing CRUD operations for employees, department fetching, and manager checks. The service also publishes employee creation and deletion events to **RabbitMQ** for asynchronous processing by other services like `leave-request-service` and `auth-service`.

---

## 🛠 Tech Stack

| Component                   | Technology / Approach                                           |
|-----------------------------|------------------------------------------------------------------|
| **Language**                | Java 21                                                          |
| **Server**                  | `com.sun.net.httpserver.HttpServer` (lightweight core HTTP)      |
| **Database**                | SQLite (file-based relational DB)                               |
| **DB Migrations**           | Flyway (SQL migrations for employees & departments)              |
| **Persistence Layer**       | JDBC + DAO pattern                                               |
| **JSON Processing**         | Jackson                                                          |
| **Config Management**       | Dotenv                                                           |
| **Event Communication**     | RabbitMQ (EmployeeCreated / EmployeeDeleted events)              |
| **Event Publishing Modes**  | Configurable: `HTTP` or `RABBITMQ`                              |
| **Event Dispatcher**        | Pluggable `EventPublisher` abstraction                           |
| **Validations**             | Central validation utilities (email, department, dates, roles)   |
| **Search & Filtering**      | Custom query builder (pagination + sorting)                     |
| **Response Formatting**     | Standardized `DTO_api_response` wrappers                       |
| **Exception Handling**      | Centralized ExceptionMapperManager + specific exception mappers  |
| **Debug Tools**             | `/debug/exceptions` endpoint                                     |

---

## 📌 Purpose — Employee Service
### Centralized Employee Master Data

Acts as the source of truth for all employee-related information inside the Leave Management System (LMS).
Stores personal details, department info, joining date, and corporate email.
Ensures that every operation (leave requests, approvals, authentication, notifications) depends on validated employee data.

---

### Employee Lifecycle Management

Supports fully validated, strict CRUD operations through:

- `/employee/add`
- `/employee/update/{id}`
- `/employee/delete/{id}`
- `/employee/get`

Enforces:

- Department existence checks  
- Email, name, date validations  
- Pagination, sorting, and search filters  
- Manager existence checks before updates/deletes  

---

### Department Directory & Search

Provides a clean department lookup API (`/department`) to support UIs and other microservices.
Includes:

- ID-based search  
- Name-based broad search  
- Sorting  
- Pagination  

Uses pre-seeded departments via Flyway migrations to ensure consistency.

---

### Manager Hierarchy & Validation

Offers APIs to determine:

- Manager for any employee (`/manager`)
- Whether an employee is a manager (`/manager/check`)

Critical for:

- Leave approval flow  
- Access control in other services  
- Building reporting structures across departments  

---

### Event-Driven Notifications

Publishes events to RabbitMQ or HTTP (configurable):

- `EmployeeCreatedEvent`
- `EmployeeDeletedEvent`

Allows `leave-request-service` to automatically:

- Create initial leave balances when an employee is created 
- Clean up leave balances when an employee is deleted  

Allows `auth-service` to automatically:

- Clean up employee authentication records when an employee is deleted

Ensures loose coupling and microservice autonomy.

---

### Internal-Only, Trusted Service

- Optimized for intra-system communication.
- Avoids exposing unnecessary APIs publicly.
- Designed as a backend-only module with strict error mappings and consistent response contracts.

---

### Debug Utilities

Includes a development-friendly `/debug/exceptions` endpoint.

Helps developers view:

- Most recent exceptions  
- Total exception count  

Useful during API development, testing, and integration.

---

## ⚡ Features — Employee Service
### 🧩 Complete Employee Lifecycle Management

- Create, update, delete, and fetch employee records.
- Strong validations on email, department, joining date, and role mappings.
- Consistent response structures using `DTO_api_response_*`.

---

### 📂 Department Directory APIs

- Retrieve department details with pagination, sorting, and search functionality.
- Ensures consistent department identifiers (e.g., ENG, HR, FIN) throughout the LMS.
- Acts as a foundational data provider for the Leave Service UI and validations.

---

### 👨‍💼 Manager Hierarchy & Checks

- Determine the manager responsible for any employee via department mapping.
- Validate whether an employee is a manager and return their role/department.
- Enables role-based actions in approval workflows and cross-microservice checks.

---

### 📨 Event-Driven Architecture (EMPLOYEE_CREATED / EMPLOYEE_DELETED)

- Publishes events to RabbitMQ (or HTTP fallback) whenever an employee is created or deleted.
    - Ensures the Leave Request Service reacts instantly:
        - Auto-create leave balances  
        - Cleanup leave data when an employee is removed  
    - Ensures the Auth Service reacts instantly:
        - Cleanup auth data when an employee is deleted

Decouples microservices while maintaining data integrity.

---

### 📊 Flexible Querying & Filtering

Fetch employees by:

- id  
- email  
- name  
- department_id  

Supports sorting, pagination, and search — useful for dashboards and admin tools.

---

### 🛠️ Dev-Only Debug Endpoints

The `/debug/exceptions` API surfaces:

- Last thrown exceptions  
- Total exception count  

Helpful for diagnosing issues during backend development and testing.

---

### 💡 Consistent Error Handling and Codes

Every error maps to:

- A consistent message  
- A logical code  
- A clear HTTP status  

Ensures predictable behavior across all microservices using Employee Service.

---

### 🧩 Centralized Exception Handling

The Auth Service uses a custom, extensible **exception mapping system** to ensure consistent error responses across all endpoints.

#### ✔️ ExceptionMapperManager

The `ExceptionMapperManager` is the core component responsible for:

- Maintaining a list of registered exception mappers  
- Routing exceptions to the correct mapper based on type  
- Storing the **last 10 exceptions** for debugging  
- Tracking the **total exception count**  
- Supporting a `debug` mode for verbose logging  

#### 🔌 Exception Mappers

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

## 📂 Project Structure

```bash
.
├── src/main/java/
│   ├── config/                  # Application-wide configuration classes
│   ├── constants/enums/         # Enum types and constant values
│   ├── dao/                     # Data Access Objects for database interaction
│   │   ├── interfaces/          # DAO interfaces
│   │   ├── impl/sqlite/         # SQLite-specific DAO implementations
│   │   └── factory/             # Factory classes to obtain DAO instances
│   ├── db/                      # Database utilities
│   │   └── migration/           # Java-based Flyway migration classes (optional)
│   ├── dto/                     # Data Transfer Objects for API requests/responses
│   ├── employee_service_runner/ # Main entry point / runner for Employee Service
│   ├── event/                   # Event-driven architecture support
│   │   ├── base/                # Base event classes/interfaces
│   │   ├── core/                # Core event processing logic
│   │   ├── publisher/           # Event publishing classes
│   │   ├── registry/            # Event registry / subscriptions
│   │   └── types/               # Event type definitions (EmployeeCreated, etc.)
│   ├── exceptions/              # Exception handling
│   │   ├── exception/           # Custom exception classes
│   │   └── mapper/              # Exception-to-HTTP response mapping
│   ├── handler/                 # HttpHandler implementations for each API endpoint
│   ├── service/                 # Business logic / service layer
│   ├── utils/                   # Helper utilities and common functions
├── src/main/resources/
│   ├── db/
│   │   └── migration/           # SQL-based Flyway migration scripts
│   ├── specs/                   # OpenAPI/Swagger YAML or JSON specs
│   └── swagger-ui/              # Swagger UI static files
├── README.md                    # Project README
├── data/
│   └── Employee-management.db    # SQLite database file
├── .env.example                  # Environment variable template
├── pom.xml                       # Maven project descriptor

```
---

## ▶️ Running the Employee Service Locally

Follow the steps below to run the Employee Service in a local development environment.

### 1. Clone the Repository
Make sure you have cloned the LMS monorepo or the standalone `employee-service` folder.
```bash
git clone <your-repo-url>
```
### 2. Navigate to the Employee Service
```bash
cd employee-service
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
`src/main/java/employee_service_runner/Main.java`
*(Or run directly from your IDE’s “Run” button.)*

### 6. Service Startup
Once started, the Employee Service will be available at:
`http://localhost:8080/`

### 7. Verify the Service

You can confirm the service is running by accessing:
- Swagger UI 
    - `http://localhost:8080/docs/`

Everything should now be up and running locally.

---

## 📄 OpenAPI Documentation (Swagger UI)

The Auth Service exposes interactive API documentation using **Swagger UI**.

### 🔍 Swagger UI
Access the UI at:
**`http://localhost:8080/docs/`**

### 📘 OpenAPI Specification
Raw YAML specification is available at:
**`http://localhost:8080/specs/openapi.yaml`**

---

## ⚙️ Environment Variables — Employee Service

This service reads all configuration from a `.env` file.

Create it using:

```bash
cp .env.example .env
```

The `.env.example` file documents every required variable for running the **Employee Service**, including database setup, service URLs, and department/admin initialization.

---

**Database and Flyway Configuration**

```env
# JDBC connection string for your database
DB_URL=jdbc:sqlite:employee-service/data/Employee-management.db

# SQLite does not require username/password
DB_USER=
DB_PASSWORD=

# Location of Flyway migration scripts
MIGRATION_LOCATION=classpath:db/migration
```

**Department Seeding** 

The `DEPARTMENTS` variable provides a list of department codes and names.  
During application startup, Flyway uses this to **pre-seed the `departments` table**.

```
DEPARTMENTS=ENG:Engineering,HR:Human Resource,GEN:General,MKT:Marketing
```

This ensures consistent department identifiers across the entire Leave Management System (LMS).

---

**Service URL Configuration**

```env
# Base URLs for intra-service HTTP calls
EMPLOYEE_SERVICE_URL=http://localhost:8080/
LEAVE_REQUEST_SERVICE_URL=http://localhost:8081/
AUTH_SERVICE_URL=http://localhost:8082/
```

These values are used if the service is running in *HTTP event mode* instead of RabbitMQ.

---

**Admin User Initialization**

```env
# Admin user details
ADMIN_NAME=ADMIN
ADMIN_EMAIL=admin@example.com
ADMIN_DEPARTMENT=GEN
```

Although the Employee Service does **not** manage authentication,  
this section exists because the database starts **empty on first run**.

A Flyway Java migration (or setup step) can optionally create:

- an initial **Admin employee** entry  
- placed under the department defined in `ADMIN_DEPARTMENT`

This avoids situations where other services expect an admin or manager to exist.

---

**Security Configuration**
***
```env
# Secret key for signing JWT tokens (if used internally)
JWT_SECRET=changeme

# JWT expiry in milliseconds (e.g., 1 day)
JWT_EXPIRY_MILLIS=86400000

# Secret key for internal service-to-service communication
SERVICE_SECRET=changeme
```

The Employee Service itself does not issue tokens,  
but may validate them for protected endpoints.

---

**Table Related Configuration**

```env
# Departments list in CODE:Name format
DEPARTMENTS=ENG:Engineering,HR:Human Resource,GEN:General,MKT:Marketing
```

Used during initial migration to populate the departments master table.

```env
# Admin user bootstrap record
ADMIN_NAME=ADMIN
ADMIN_EMAIL=admin@example.com
ADMIN_DEPARTMENT=GEN
```

Used only when creating an initial admin record in an empty database.

---

## 🔐 Authentication & Authorization

The Employee Service uses **JWT-based authentication**, with all tokens issued and validated by the **Auth Service**.

- Every request must include:
  
  ```
  Authorization: Bearer <JWT_TOKEN>
  ```

- The token is validated using the shared `JWT_SECRET` (loaded from `.env`).
- The Employee Service **does not generate tokens** — it only verifies them.

### Role-Based Access Control (RBAC)

The service supports three roles:

| Role        | Description                                  |
|-------------|----------------------------------------------|
| `ADMIN`     | Full access to all employee operations        |
| `MANAGER`   | Can view employee data; limited modifications |
| `EMPLOYEE`  | Can only access basic self-related endpoints  |

### How It Works

- Auth Service issues JWTs containing `employee_id` and `role`.
- Employee Service extracts and verifies the role from the token.
- Access restrictions are enforced at handler level.

### Integration With Auth Service

This service relies on Auth Service for:
- Login / token generation  
- Token verification (shared secret)  
- Cross-microservice trust (`SERVICE_SECRET`)  

For the detailed architecture, internal RBAC flow, and token structure, see:  
📄 **[`/docs/employee-auth-model.md`](./docs/employee-auth-model.md)** 

