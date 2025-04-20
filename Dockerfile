# ---- Build Stage ----
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application; adjust -DskipTests as needed
RUN mvn clean package -DskipTests

# ---- Runtime Stage ----
# Use an ARM-compatible JRE image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/Fitness-Exercise-Api.jar app.jar

# Expose the port your app listens on (default 8080)
EXPOSE 8080

# Run the Spring Boot application; the app will pick up environment variables at runtime
ENTRYPOINT ["java", "-jar", "app.jar"]