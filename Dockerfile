FROM maven:3-eclipse-temurin-22 AS build

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

COPY --from=build /target/Urgencias-Ya-0.0.1-SNAPSHOT.jar urgencias-ya.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "urgencias-ya.jar"]