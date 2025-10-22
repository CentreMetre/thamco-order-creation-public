# Stage 1: Build with Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests



# Stage 2: Minimal runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/creation-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 5432

CMD ["java", "-jar", "app.jar"]