# ---- Build Stage ----
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application; adjust -DskipTests as needed
RUN mvn clean package -DskipTests

# ---- Runtime Stage ----
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/Fitness-Exercise-Api app.jar

# Expose the port your app listens on (default 8080)
EXPOSE 8080

# Run the Spring Boot application; the app will pick up environment variables at runtime
ENTRYPOINT ["java", "-jar", "app.jar"]
