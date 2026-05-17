# ElitePerformance API

Spring Boot 3 REST API for GlobalSync's annual employee performance bonus workflow. It uses Java 17, Maven, Spring JDBC with `NamedParameterJdbcTemplate`, MySQL, Spring Security 6, BCrypt passwords, and JWT role-based authorization.

## Features

- Public registration and login with JWT responses
- Role-based access for `EMPLOYEE`, `MANAGER`, and `ADMIN`
- KPI validation for all seven PRD score fields
- Bonus tier calculation: Gold 20%, Silver 12%, Bronze 5%, No Tier 0%
- JDBC repository interfaces plus MySQL implementations
- Transactional performance calculation that creates the review, bonus record, and audit log together
- Global exception responses with validation details
- `schema.sql` with all required tables and sample data

## Run Locally

Prerequisites:

- Java 17+
- Maven 3.9+
- MySQL running locally

Create the database:

```sql
CREATE DATABASE IF NOT EXISTS elite_performance;
```

Update `src/main/resources/application.properties` if your MySQL username or password differs from `root` / `password`.

Start the API:

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`. `schema.sql` creates the tables and inserts sample employees and users.

Sample credentials:

| Username | Password | Role |
| --- | --- | --- |
| employee | password | EMPLOYEE |
| manager | password | MANAGER |
| admin | password | ADMIN |

## Endpoints

### Register

`POST /api/auth/register`

```json
{
  "employeeId": 1,
  "username": "new.employee",
  "password": "password",
  "role": "EMPLOYEE"
}
```

### Login

`POST /api/auth/login`

```json
{
  "username": "manager",
  "password": "password"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "manager",
  "role": "MANAGER",
  "employeeId": 2
}
```

Use the token in protected calls:

```http
Authorization: Bearer <token>
```

### Submit KPI and Calculate Bonus

`POST /api/performances/calculate`

Roles: `MANAGER`, `ADMIN`

```json
{
  "employeeId": 1,
  "reviewYear": 2026,
  "taskCompletion": 24,
  "attendance": 15,
  "teamCollaboration": 14,
  "problemSolving": 14,
  "communication": 9,
  "leadership": 9,
  "clientSatisfaction": 10
}
```

### My Bonus Records

`GET /api/bonuses/my`

Role: `EMPLOYEE`

### All Bonus Records

`GET /api/bonuses/all`

Role: `ADMIN`

### Employees

`GET /api/employees`

Role: `ADMIN`

`GET /api/employees/{id}`

Roles: `MANAGER`, `ADMIN`

## Validation Rules

- KPI fields cannot be negative or exceed their maximum values.
- `reviewYear` cannot be in the future.
- Employee must exist.
- A duplicate review for the same employee and year returns HTTP 409.

## Tests

```bash
mvn test
```

The current tests cover KPI aggregation, score validation, category mapping, and bonus math.
