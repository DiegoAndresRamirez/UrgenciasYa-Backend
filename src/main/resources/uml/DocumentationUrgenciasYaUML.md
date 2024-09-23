# UML Diagram Documentation **Urgencias Ya**

This document provides a detailed description of the entities and relationships represented in the UML diagram for the **Urgencias Ya** project.

## Entities

### 1. **EmergencyEntity**
- **Description**: Represents an emergency entity with contact information.
- **Attributes**:
    - `id` (Long): Unique identifier for the emergency.
    - `name` (String): Name of the contact person.
    - `phone` (String): Contact phone number.

### 2. **Eps**
- **Description**: Represents Health Promotion Entities (EPS).
- **Attributes**:
    - `id` (Integer): Unique identifier for the EPS.
    - `name` (String): Name of the EPS.

### 3. **Hospital**
- **Description**: Represents information about hospitals.
- **Attributes**:
    - `id` (Long): Unique identifier for the hospital.
    - `url_image` (String): URL of the hospital's image.
    - `phone_number` (String): Hospital phone number.
    - `name` (String): Name of the hospital.
    - `rating` (Float): Rating of the hospital.
    - `morning_peak` (Integer): Morning peak hours.
    - `afternoon_peak` (Integer): Afternoon peak hours.
    - `night_peak` (Integer): Night peak hours.
    - `howtogetthere` (String): Instructions on how to get to the hospital.
    - `latitude` (Float): Latitude of the hospital.
    - `longitude` (Float): Longitude of the hospital.

### 4. **HospitalEps**
- **Description**: Relationship between hospitals and EPS.
- **Attributes**:
    - `id` (HospitalEpsId): Unique identifier for the relationship between hospital and EPS.

### 5. **RoleEntity**
- **Description**: Represents roles assigned to users.
- **Attributes**:
    - `id` (Long): Unique identifier for the role.
    - `code` (String): Code for the role.

### 6. **Shift**
- **Description**: Represents assigned medical shifts.
- **Attributes**:
    - `id` (Long): Unique identifier for the shift.
    - `shiftNumber` (String): Number of the shift.
    - `estimatedTime` (LocalDateTime): Estimated date and time for the shift.
    - `status` (StatusShift): Status of the shift (e.g., pending, completed).

### 7. **Towns**
- **Description**: Represents information about towns.
- **Attributes**:
    - `id` (Integer): Unique identifier for the town.
    - `name` (String): Name of the town.

### 8. **UserEntity**
- **Description**: Represents users registered in the system.
- **Attributes**:
    - `id` (Long): Unique identifier for the user.
    - `name` (String): Name of the user.
    - `eps` (String): EPS the user belongs to.
    - `password` (String): User's password.
    - `email` (String): User's email address.
    - `document` (String): User's identification document.

## Relationships

- **EmergencyEntity** has a one-to-one relationship with **UserEntity**.
- **Eps** has a one-to-many relationship with **HospitalEps**.
- **Hospital** has a one-to-many relationship with **HospitalEps**.
- **Hospital** belongs to a single **Towns**.
- **RoleEntity** has a one-to-many relationship with **UserEntity**.
- **Shift** belongs to a single **UserEntity**.
- **Shift** belongs to a single **Hospital**.
- **Shift** belongs to a single **Eps**.



This document provides an overview of the entities and their relationships within the **Urgencias Ya** system. The entities are designed to optimize the management of information regarding users, EPS, hospitals, medical shifts, and emergency contacts, thereby facilitating access to health services.
