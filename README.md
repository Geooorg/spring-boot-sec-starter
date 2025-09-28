# Spring Boot Security Starter

A Spring Boot application with H2 database and role-based security.

## Features

- H2 in-memory database with console access
- Spring Security with role-based access control
- Pre-configured users with different roles:
  - Admin (username: `admin`, password: `admin123`)
  - User (username: `user`, password: `user123`)
  - Guest (username: `guest`, password: `guest123`)

## API Endpoints

### Public (No authentication required)
- `GET /api/public/hello` - Public endpoint

### Guest (Requires GUEST, USER, or ADMIN role)
- `GET /api/guest/hello` - Guest endpoint

### User (Requires USER or ADMIN role)
- `GET /api/user/hello` - User endpoint

### Admin (Requires ADMIN role)
- `GET /api/admin/hello` - Admin endpoint

## Accessing H2 Console

1. Start the application
2. Navigate to: http://localhost:8080/h2-console
3. Use the following credentials:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User Name: `sa`
   - Password: `password`

## Running the Application

```bash
./mvnw spring-boot:run
```

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- H2 Database
- Lombok