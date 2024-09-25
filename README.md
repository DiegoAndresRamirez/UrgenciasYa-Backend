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