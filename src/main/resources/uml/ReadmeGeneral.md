# UrgenciasYa-Backend

# Urgencias Ya

## Project Description
**Urgencias Ya** is an application designed to help users find Health Promoting Entities (EPS) that offer emergency services. The application allows users to find the closest EPS with the fewest number of people, interact with others in the emergency room via chat, and manage service tickets, optimizing the process of finding medical care and providing a clearer overview of where to go based on wait times at health centers.

## Architecture
The application has been developed using **Java Spring Boot** with a hexagonal architecture. The project structure is as follows:


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
│   	└── TwilioService
│
├── common
│   └── util
│   	└── enums
│
├── domain
│   └── model
│   	└── keys
│
└── infrastructure
	├── config
	├── handleError
	└── persistence
```


## Prerequisites
To run the application, ensure you have the following technologies installed:

- **Java** (JDK 21)
- **Spring Boot**
- **MySQL**
- **Postman**

## Dependencies
Below is the list of dependencies and versions used in the development of **Urgencias Ya**:

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
2. **Spring Data JPA**: Simplifies database interactions using the JPA specification.
3. **Spring Boot Starter Validation**: Provides tools for validating input data.
4. **Spring Boot Starter Web**: Enables the creation of a RESTful API.
5. **MySQL Connector/J**: Facilitates connection to MySQL databases.
6. **Lombok**: Simplifies code by reducing repetitive methods.
7. **Spring Boot Starter Test**: Tools for unit and integration testing.
8. **Swagger Annotations and Springdoc OpenAPI UI**: Enable documentation and interactive visualization of the API.

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

#### c. Database Configuration
1. Install MySQL from the [official site](https://dev.mysql.com/downloads/mysql/).
2. Create a new database:
```sql
CREATE DATABASE urgencias_db;
```
3. Configure the `application.properties` file:
   In the `src/main/resources/application.properties` file, add the database connection configuration:
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
Ensure that the `application.properties` file is correctly configured for database connections and other relevant components.

## Execution Commands
To start the application, ensure you are in the project directory and run:
```bash
mvn spring-boot:run
```

## Database Explanation
The **Urgencias Ya** database manages information about users, hospitals, EPS (Health Promoting Entities), medical shifts, and emergency contacts. Below are the main tables described:

### 1. Table: `users`
- **Description**: Stores information about registered users.
- **Main Fields**:
    - `id`: Unique identifier (UUID).
    - `name`: User's name.
    - `eps`: EPS the user belongs to.
    - `password`: User's password.
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
    - `name`: Hospital name.
    - `rating`: Rating.
    - `morning_peak`, `afternoon_peak`, `night_peak`: Peak demand times.
    - `howtogetthere`: Directions.
    - `town_id`: Location.
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
- **Description**: Information about locations.
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
        "message": "Eps created successfully"
      }
      ```

### 2. Endpoints

for Hospitals
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

### 3. Shift Endpoints
- **Create an Emergency Shift**
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
        "message": "Successful registration"
      }
      ```

---

This `README.md` file provides a complete guide to run, maintain, and expand the **Urgencias Ya** application, ensuring that any user can efficiently understand its operation and structure.

### **Problem Statement**:

During a medical emergency, it is crucial to access appropriate care quickly. However, users often face the following challenges:

- Uncertainty about which hospital to go to.
- Lack of information on wait times and congestion levels at medical centers.
- Anxiety about finding the nearest hospital or the quickest route.

Long waits and overcrowded emergency rooms can worsen a patient's condition and hinder communication with medical staff due to high demand.

---

### **Project Scope**:

#### **Short Term**:
- **Real-Time Information**: Develop a web application that displays the nearest emergency medical care centers based on the user's location.
- **Congestion Levels**: Provide up-to-date information on congestion and wait times at each medical center.
- **Travel Directions**: Offer directions and estimated travel times to the selected care center via car or public transportation using Waze and Google Maps.
- **Emergency Contact**: Connect to an emergency hotline or contact in case the user cannot travel independently.
- **Health Insurance Filters**: Allow users to filter care centers based on their health insurance provider (EPS) or SISBEN classification.

#### **Medium Term**:
- **Personal Information Integration**: Incorporate each user's personal information (in accordance with the "Personal Data Protection Law"), such as underlying diseases, socioeconomic status, location, age, gender, health insurance provider, and contact details. This information can be sent to the hospital to expedite care.
- **Service Information**: Integrate data about the services provided by each hospital.
- **Accident Support**: Add a feature that helps users involved in traffic accidents find hospitals that can treat them based on their insurance policy or SOAT.
- **User Login**: Create a login system for users who authorize the processing of their data to expedite care upon arrival at the medical center.

#### **Long Term**:
- **Expand Coverage**: Expand the application's coverage to national and Latin American levels.
- **Additional Features**: Incorporate features like medical appointment reminders, visit history, and priority appointment requests.
- **Partnerships**: Establish partnerships with insurance companies and medical service providers to enhance the user experience.
- **Endorsement**: Obtain approval from the Ministry of Health for implementation in all emergency care centers in the country.

---

### **Out of Scope**:
- The application will **not** provide direct medical care or replace existing emergency services.
- It will **not** integrate with the internal systems of hospitals or medical care centers.
- **No** transportation or ambulance services will be offered directly through the application.
- We **cannot guarantee** the quality of care provided by the hospital once the user is attended internally.

### **Who We Are**:

- **Mission**: To provide the population with up-to-date and reliable information about the nearest and least congested medical care centers, improving their quality of life and safety in emergency and urgent situations.

- **Vision**: To be the leading web platform in the country by 2028, endorsed by the Ministry of Health, providing up-to-date and reliable information to facilitate quick and efficient access to emergency medical care centers.

- **Values**: Transparency, Innovation, Commitment, Collaboration, Trust, Quality.

