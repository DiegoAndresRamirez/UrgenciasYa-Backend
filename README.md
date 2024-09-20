# UrgenciasYa-Backend

# Urgencias Ya

## Project Description
**Urgencias Ya** is an application designed to help users find EPS (Health Promoting Entities) that offer emergency services. The application allows users to locate the nearest EPS, check those with fewer people, interact with others in the emergency room via chat, and manage care tickets, optimizing the process of seeking medical attention and providing a clearer view of where to go with wait times at health facilities.

## Architecture
The application is developed using **Java Spring Boot** with a hexagonal architecture. The project structure is as follows:


```
application
│
├── controller
│   ├── generic
│   ├── impl
│   │   └── TwilioController
│   └── interfaces
│
├── dto
│   ├── request
│   └── response
│
├── exception
│
├── service
│   ├── IModel
│   ├── crud
│   └── impl
│       └── TwilioService
│
├── common
│   └── util
│       └── enums
│
├── domain
│   └── model
│       └── keys
│
└── infrastructure
    ├── config
    ├── handleError
    └── persistence
```
## Prerequisites
To run the application, make sure you have the following technologies installed:

- **Java** (JDK 21)
- **Spring Boot**
- **MySQL**
- **Postman**

## Dependencies
Below are the dependencies and versions used in the development of **Urgencias Ya**:

- **Spring Boot Starter Parent**: 3.3.3
- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Validation**
- **Spring Boot Starter Web**
- **MySQL Connector/J**: runtime
- **Lombok**: optional
- **Spring Boot Starter Test**: test
- **Swagger Annotations**: 2.2.22
- **Springdoc OpenAPI UI**: 1.7.0

### Technological Decisions
1. **Spring Boot**: Enables rapid creation of Java applications with automatic configurations.
2. **Spring Data JPA**: Simplifies interaction with databases using the JPA specification.
3. **Spring Boot Starter Validation**: Provides tools for validating input data.
4. **Spring Boot Starter Web**: Allows the creation of a RESTful API.
5. **MySQL Connector/J**: Facilitates the connection to MySQL databases.
6. **Lombok**: Simplifies code writing by reducing the need for repetitive methods.
7. **Spring Boot Starter Test**: Tools for unit and integration testing.
8. **Swagger Annotations and Springdoc OpenAPI UI**: Enable documenting and visualizing the API interactively.

## Installation Instructions

### 1. Clone the Repository
Run the following command in your terminal:
```bash
git clone https://github.com/DiegoAndresRamirez/UrgenciasYa-Backend/
```

### 2. Set Up the Development Environment
Make sure you have the following tools installed:

#### a. Java Development Kit (JDK)
- Download the JDK from the [official Oracle site](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or from [AdoptOpenJDK](https://adoptopenjdk.net/).
- Verify the installation:
```bash
java -version
```

#### b. Maven
- Install Maven following the instructions on the [official Maven site](https://maven.apache.org/install.html).
- Verify the installation:
```bash
mvn -version
```

#### c. Database Setup
1. Install MySQL from the [official site](https://dev.mysql.com/downloads/mysql/).
2. Create a new database:
```sql
CREATE DATABASE urgencias_db;
```
3. Configure the `application.properties` file:
   In the `src/main/resources/application.properties` file, add the database connection settings:
   ```properties
   spring.application.name=Urgencias-Ya
   spring.datasource.url=jdbc:mysql://<your_host>:<your_port>/UrgenciasYa
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

### 3. Build and Run the Application
Navigate to the project directory and run:
```bash
mvn spring-boot:run
```
Access the application at `http://localhost:8080`.

## Configuration
Ensure that the `application.properties` file is correctly set up for database connections and other relevant components.

## Execution Commands
To start the application, make sure you're in the project directory and run:
```bash
mvn spring-boot:run
```

## Database Explanation
The **Urgencias Ya** database manages information about users, hospitals, EPS (Health Promoting Entities), medical appointments, and emergency contacts. Below is a description of the main tables:

### 1. Table: `users`
- **Description**: Stores information about registered users.
- **Main Fields**:
    - `id`: Unique identifier (UUID).
    - `name`: User's name.
    - `eps`: EPS the user belongs to.
    - `password`: Password.
    - `email`: Email address.
    - `document`: Identification document.
    - `emergency_id`: Emergency contact.
    - `role_id`: Assigned role.

### 2. Table: `emergencyContact`
- **Description**: Emergency contact information.
- **Main Fields**:
    - `id`: Unique identifier (UUID).
    - `name`: Contact's name.
    - `phone`: Phone number.

### 3. Table: `hospital`
- **Description**: Information about hospitals.
- **Main Fields**:
    - `id`: Unique identifier (auto-incremented ID).
    - `url_image`: Image URL.
    - `phone_number`: Phone number.
    - `name`: Hospital's name.
    - `rating`: Rating.
    - `morning_peak`, `afternoon_peak`, `night_peak`: Peak times.
    - `howtogetthere`: Directions.
    - `town_id`: Town.
    - `eps_id`: Associated EPS.

### 4. Table: `eps`
- **Description**: Information about EPS.
- **Main Fields**:
    - `id`: Unique identifier (auto-incremented ID).
    - `name`: EPS name.
    - `hospitalsEps`: Associated hospital.

### 5. Table: `shift`
- **Description**: Assigned medical shifts.
- **Main Fields**:
    - `id`: Unique identifier (auto-incremented ID).
    - `ShiftNumber`: Shift number.
    - `estimatedTime`: Estimated date and time.
    - `status`: Shift status.
    - `user_id`: Assigned user.
    - `hospital_id`: Corresponding hospital.
    - `eps_id`: Corresponding EPS.

### 6. Table: `towns`
- **Description**: Information about towns.
- **Main Fields**:
    - `id`: Unique identifier (auto-incremented ID).
    - `name`: Town name.

### 7. Table: `role`
- **Description**: Roles assigned to users.
- **Main Fields**:
    - `id`: Unique identifier (UUID).
    - `code`: Role code.

## API Endpoints
Below are the main API endpoints for **Urgencias Ya**.

### 1. EPS Endpoints
- **Get All EPS**
    - **Method**: GET
    - **Endpoint**: `/api/v1/eps`
    - **Request Example**:
      ```http
      GET /api/v1/eps HTTP/1.1
      Host: localhost:8080
      ```
    - **Response Example**:
      ```json
      [
        { "name": "EPS Salud Total" },
        { "name": "Coomeva EPS" }
      ]
      ```

- **Create an EPS**
    - **Method**: POST
    - **Endpoint**: `/api/v1/eps`
    - **Request Example**:
      ```http
      POST /api/v1/eps HTTP/1.1 
      Host: localhost:8080 
      Content-Type: application/json
      {
        "name": "Nueva EPS"
      }
      ```
    - **Response Example**:
      ```json
      {
        "code": 201,
        "status": "CREATED",
        "message": "Eps successfully created"
      }
      ```
### 2. Endpoints

of Hospitals
- **Get Hospitals by EPS and Town**
    - **Method**: GET
    - **Endpoint**: `/api/v1/hospitals`
    - **Request Example**:
      ```http
      GET /api/v1/hospitals?eps=EPS Salud Total&town=Bogotá&latitude=4.6108&longitude=-74.0817 HTTP/1.1
      Host: localhost:8080
      ```
    - **Response Example**:
      ```json
      [
        {
          "id": 1,
          "name": "Hospital General",
          "url_image": "http://example.com/hospital.jpg",
          "phone_number": "123456789",
          "rating": 4.5
        }
      ]
      ```

### 3. Shifts Endpoints
- **Create an Urgency Shift**
    - **Method**: POST
    - **Endpoint**: `/api/shifts/create`
    - **Request Example**:
      ```http
      POST /api/shifts/create HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      {
        "document": "123456789",
        "hospitalId": 1,
        "epsId": 1
      }
      ```
    - **Response Example**:
      ```json
      {
        "id": 1,
        "ShiftNumber": "A001",
        "estimatedTime": "2024-09-20T10:00:00",
        "status": "PENDING"
      }
      ```

### 4. Town Endpoints
- **Get All Towns**
    - **Method**: GET
    - **Endpoint**: `/api/v1/town`
    - **Request Example**:
      ```http
      GET /api/v1/town HTTP/1.1
      Host: localhost:8080
      ```
    - **Response Example**:
      ```json
      [
        { "name": "Bogotá" },
        { "name": "Medellín" }
      ]
      ```

### 5. User Endpoints
- **Register a New User**
    - **Method**: POST
    - **Endpoint**: `/api/v1/user/register`
    - **Request Example**:
      ```http
      POST /api/v1/user/register HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      {
        "name": "Juan Pérez",
        "email": "juan.perez@example.com",
        "password": "password123",
        "eps": "EPS Salud Total",
        "document": "123456789"
      }
      ```
    - **Response Example**:
      ```json
      {
        "code": 201,
        "status": "CREATED",
        "message": "Registration successful"
      }
      ```

---

This `README.md` file provides a comprehensive guide for running, maintaining, and extending the **Urgencias Ya** application, ensuring that any user can understand its functionality and structure efficiently.      