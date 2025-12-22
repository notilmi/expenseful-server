# Expenseful Server

Expenseful Server is a Spring Boot (Java 17) backend for managing personal expenses and statements. It uses Spring WebMVC, Spring Data JPA, PostgreSQL, Flyway for database migrations (migrations stored in `src/main/resources/db/migration`), Spring Security with JWT, and exposes OpenAPI docs via springdoc.

This README provides quickstart instructions for local development, configuration, build and run, and helpful tips for debugging and testing.

---

## Table of contents

- Project overview
- Requirements
- Configuration / environment variables
- Run with Docker Compose (Postgres)
- Run locally with Maven
- Build a runnable jar
- Running tests
- API documentation
- Useful notes

---

## Project overview

- Java: 17
- Build: Maven (wrapper included)
- Frameworks / libraries: Spring Boot, Spring Data JPA, Spring Security, Flyway, springdoc OpenAPI, JJWToken (io.jsonwebtoken), ModelMapper, Lombok
- Database: PostgreSQL
- Migrations: `src/main/resources/db/migration` (Flyway)


## Requirements

- Java 17 JDK installed
- Maven or use the included Maven wrapper (`mvnw` / `mvnw.cmd` on Windows)
- Docker & Docker Compose (optional, recommended for running Postgres locally)


## Configuration / environment variables

The application reads configuration from `src/main/resources/application.properties` and many properties are expected to be supplied via environment variables.

Important environment variables:

- DATASOURCE_URL - JDBC URL for PostgreSQL (example: `jdbc:postgresql://localhost:5432/mydatabase`)
- DATASOURCE_USERNAME - DB username (example: `myuser`)
- DATASOURCE_PASSWORD - DB password (example: `secret`)
- JWT_SECRET_KEY - Secret key used to sign JWT tokens
- APP_ALLOWED_ORIGINS - Allowed CORS origins (comma-separated)

Notes:
- The property `spring.flyway.enabled` is set to `false` by default in `application.properties`. To enable automatic database migrations on startup, set `spring.flyway.enabled=true` (for example via environment variable or a profile).
- By default the app runs on port 8080 (Spring Boot default) unless overridden by `server.port`.


## Run with Docker Compose (Postgres)

A simple `compose.yaml` is included to start a PostgreSQL instance for local development. It maps port 5432 and creates a sample database/user.

Example steps (Windows PowerShell):

1. Start Postgres with the provided compose file:

```powershell
docker compose -f compose.yaml up -d
```

2. Export or set required environment variables (PowerShell example):

```powershell
$env:DATASOURCE_URL = "jdbc:postgresql://localhost:5432/mydatabase"
$env:DATASOURCE_USERNAME = "myuser"
$env:DATASOURCE_PASSWORD = "secret"
$env:JWT_SECRET_KEY = "a-very-secret-key"
$env:APP_ALLOWED_ORIGINS = "http://localhost:3000"
```

3. Run the application with the Maven wrapper (Windows):

```powershell
.\mvnw.cmd spring-boot:run
```

Or build and run the jar (see next section).


## Run locally with Maven

Using the included Maven wrapper (Windows):

```powershell
.\mvnw.cmd spring-boot:run
```

Or with an installed Maven:

```powershell
mvn spring-boot:run
```


## Build a runnable jar

To package the application as an executable jar:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Then run:

```powershell
java -jar target\expenseful-server-0.0.1-SNAPSHOT.jar
```


## Running tests

Run unit/integration tests with Maven:

```powershell
.\mvnw.cmd test
```


## API documentation

The project includes springdoc OpenAPI. With the app running you can access the OpenAPI JSON and the Swagger UI:

- OpenAPI JSON: http://localhost:8080/api-docs
- Swagger UI: http://localhost:8080/swagger-ui.html or http://localhost:8080/swagger-ui/index.html

(These endpoints assume the app runs on port 8080 and the `springdoc.api-docs.path` property remains `/api-docs`.)


## Useful notes & troubleshooting

- Migrations: Flyway migration scripts are located under `src/main/resources/db/migration`. If you rely on Flyway to initialize the DB, enable it by setting `spring.flyway.enabled=true`.
- Database connection issues: verify `DATASOURCE_URL`, `DATASOURCE_USERNAME`, `DATASOURCE_PASSWORD`, and that Postgres is reachable on the configured host/port.
- JWT: Make sure `JWT_SECRET_KEY` is set before starting the app. If missing, authentication may fail in secured endpoints.
- CORS: Configure `APP_ALLOWED_ORIGINS` to include the client origins during development.
- Actuator: The project contains `spring-boot-starter-actuator` — actuator endpoints can be enabled/secured via application properties if needed.


## Development tips

- Use the Maven wrapper included in the repo to ensure a consistent Maven version across environments.
- Enable `spring.flyway.enabled=true` only when you're ready to run migrations automatically on startup.
- When changing model classes, re-run `mvn clean package` to ensure generated classes (if any) and compilation are up to date.


## Where to find things in the codebase

- Main application: `src/main/java/org/ilmi/expensefulserver/ExpensefulServerApplication.java`
- Config: `src/main/java/org/ilmi/expensefulserver/config`
- Domain models: `src/main/java/org/ilmi/expensefulserver/domain`
- Security: `src/main/java/org/ilmi/expensefulserver/security`
- DB migrations: `src/main/resources/db/migration`


## License & contributors

This project currently does not contain a formal license declaration in the pom.xml. Add a LICENSE file and update `pom.xml` if you wish to declare a license.


---

If you'd like, I can:
- Add a small example Postman collection / curl snippets for common endpoints (login, create statement, list statements)
- Add a CONTRIBUTING.md with development workflows and git hooks
- Enable Flyway by default in a dev profile and add a sample `application-dev.properties`

Tell me which of the above you'd like and I'll implement it.
