# JobConnect — Job Portal and Recruitment Management System

Learning/demo project built with Java 17, Spring Boot, JDBC, MySQL, HTML, CSS, and JavaScript.

## Features
- Read jobs from MySQL using a Spring Boot REST API.
- Search jobs by title, company, or skills.
- Submit applications and save them to MySQL.
- View saved applications through `GET /api/applications`.

## Setup
1. In MySQL, run: `CREATE DATABASE jobconnect;`
2. Open `src/main/resources/application.properties` and set your MySQL username and password.
3. Open this folder in VS Code. Ensure JDK 17+ and Maven are installed.
4. In the project terminal run: `mvn spring-boot:run`
5. Open `http://localhost:8080`.

Tables and three demo jobs are created at startup.

## API endpoints
- `GET /api/jobs`
- `GET /api/jobs?search=java`
- `POST /api/applications` with JSON: `{"jobId":1,"applicantName":"Your Name","email":"you@example.com"}`
- `GET /api/applications`

This is a learning project, not production-ready. Add authentication, authorization, automated tests, and stronger validation before real deployment. Use sample data for public demos.
