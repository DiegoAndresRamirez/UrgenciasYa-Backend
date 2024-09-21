

# Use Case Documentation and Requirements

## 1. Actor Identification

### Main Actors
- **End User**: Person who uses the application to search for EPS, hospitals, and manage medical appointments.
- **Administrator**: Responsible for managing the system, including user, EPS, and hospital administration.
- **External System**: Third-party services that may interact with the application (e.g., messaging systems like Twilio).

## 2. Use Cases

### Use Case 1: User Registration
- **Actor**: End User
- **Description**: Allows users to register in the application.
- **Main Flow**:
    1. The user accesses the registration page.
    2. Completes the registration form with name, email, password, EPS, and identification document.
    3. Submits the form.
    4. The system validates the data and creates a new user.
    5. The user receives a confirmation message.
- **Alternative Scenarios**:
    - **Validation Error**: If the data is incorrect, a specific error message is displayed.
- **Preconditions**: The user must not be registered previously.
- **Postconditions**: The user is registered in the system and can log in.

### Use Case 2: Log In
- **Actor**: End User
- **Description**: Allows users to log into the application.
- **Main Flow**:
    1. The user accesses the login page.
    2. Enters their email and password.
    3. The system validates the credentials.
    4. The user is redirected to the main page of the application.
- **Alternative Scenarios**:
    - **Incorrect Credentials**: An error message is displayed.
- **Preconditions**: The user must be registered.
- **Postconditions**: The user accesses their profile and functionalities.

### Use Case 3: Search EPS
- **Actor**: End User
- **Description**: Allows users to search for nearby EPS.
- **Main Flow**:
    1. The user selects the option to search for EPS.
    2. Enters their location (can be manual or via GPS).
    3. The system displays a list of available EPS.
- **Alternative Scenarios**:
    - **No EPS Found**: A message is displayed indicating that no EPS are available in the location.
- **Preconditions**: The user must be authenticated.
- **Postconditions**: The user sees the list of EPS.

### Use Case 4: Create Urgent Appointment
- **Actor**: End User
- **Description**: Allows users to request an urgent appointment.
- **Main Flow**:
    1. The user selects an EPS and a hospital.
    2. Completes the appointment request form.
    3. Submits the request.
    4. The system generates an appointment and sends confirmation to the user.
- **Alternative Scenarios**:
    - **Hospital Full**: The user is informed that there is no availability.
- **Preconditions**: The user must be authenticated and have found an EPS.
- **Postconditions**: The appointment is created and registered in the system.

### Use Case 5: Manage Users (Admin)
- **Actor**: Administrator
- **Description**: Allows administrators to manage registered users.
- **Main Flow**:
    1. The administrator accesses the admin panel.
    2. Selects the option to manage users.
    3. Views the list of registered users.
    4. Can add, edit, or delete users.
- **Alternative Scenarios**:
    - **Edit Error**: A message is displayed if the data is invalid.
- **Preconditions**: The administrator must be authenticated.
- **Postconditions**: Changes are saved, and the update is reflected in the list.

## 3. Functional Requirements

1. **User Authentication**: The system must allow user registration and login.
2. **EPS Management**: Allow administrators to add, edit, and delete EPS in the system.
3. **Hospital Search**: Users must be able to search for hospitals based on their location and EPS.
4. **Appointment Management**: Users must be able to create, view, and cancel urgent appointments.
5. **User Interaction**: Provide a chat system for users to communicate with each other.

## 4. Non-Functional Requirements

1. **Performance**:
    - **Acceptance Criterion**: The API response time must not exceed 200 ms under normal load.

2. **Security**:
    - **Acceptance Criterion**: Implement JWT authentication and password encryption with BCrypt. Sensitive data must be protected.

3. **Usability**:
    - **Acceptance Criterion**: The user interface must be intuitive and accessible, with an average learning time of less than 30 minutes.

4. **Availability**:
    - **Acceptance Criterion**: The system must have 99.9% uptime during peak hours.

5. **Scalability**:
    - **Acceptance Criterion**: The application must be able to handle up to 10,000 concurrent users without degrading performance.

6. **Maintainability**:
    - **Acceptance Criterion**: The code must follow coding standards and contain sufficient documentation to facilitate maintainability.

## 5. Documentation Review and Validation

It Is essential that all use cases cover the necessary scenarios and that the requirements are well defined. A review with the technical team should be conducted to ensure alignment with user expectations and needs.

