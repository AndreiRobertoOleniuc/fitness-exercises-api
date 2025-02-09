<!-- filepath: /Users/andreioleniuc/Documents/Code/Projects/fitness-exercises-api/ReadMe.md -->

# 🚀 Fitness Exercise API

The **Fitness Exercise API** is a robust REST API built with Spring Boot to manage and serve fitness exercise data. It offers a comprehensive set of features designed to help you manage exercise information securely and efficiently.

## ✨ REST API Features

- **User Authentication & Authorization** 🔐

  - OAuth2 authentication support via Google and GitHub.
  - JWT authorization to secure API endpoints.

- **Exercise Management** 🏋️‍♂️

  - Retrieve a list of exercises.
  - Search exercises by various criteria.
  - Get detailed information about individual exercises.

- **Caching** ⚡

  - Integrated Hazelcast caching to improve response times and reduce load on your database.

- **Data Storage** 🗄️

  - Backed by MongoDB for flexible, schema-less storage of exercise data.

- **Interactive API Documentation** 📜

  - Auto-generated Swagger (OpenAPI) documentation that lets you explore and test endpoints interactively.

- **Containerized Deployment** 🐳
  - Docker and Docker Compose support for consistent deployments and local development environments.

---

## 📂 Code Structure

- **`src/main/java/ch/fitnessExerciseApi/`**

  - **`controllers/`**: REST controllers containing endpoints (e.g., handling exercise queries).
  - **`models/`**: Domain objects such as `Exercise` and `User`.
  - **`repositories/`**: Spring Data repositories to interact with MongoDB.
  - **`services/`**: Business logic and caching services to manage exercise data.
  - **`config/`**: Configuration classes for security, caching, Swagger, etc.

- **`src/main/resources/`**

  - **`application.properties`**: Main configuration file utilizing environment variables (e.g., `MONGODB_URI`, `JWT_SECRET`, OAuth credentials).
  - **`swagger.json`**: Swagger API documentation configuration.

- **`pom.xml`**: Maven project configuration with versioning and dependency management.
- **`docker-compose.yml`**: Docker Compose configuration for running the application locally.

---

## 🛠️ Customization & Configuration

### 🔧 Project Customization

- **Project Name & Branding:**  
  Update any placeholders in configuration files (`application.properties`, `swagger.json`, `pom.xml`, etc.) to reflect your preferred project naming.

- **Dependencies & Plugins:**  
  The Maven `pom.xml` file manages core dependencies for Spring Boot, MongoDB, Hazelcast, OAuth2, JWT, and Swagger. Adjust versions and add/remove dependencies as needed.

### 🔌 Environment Variables

The application requires several environment variables for secure configuration:

- `MONGODB_URI`
- `JWT_SECRET`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`
- `GITHUB_CLIENT_ID`
- `GITHUB_CLIENT_SECRET`

Update these values directly in your local environment or in your `.env` file (see below).

---

## 🏃 Running Locally

### Using Maven

Run the application directly with the Maven wrapper:

```bash
./mvnw spring-boot:run
```
