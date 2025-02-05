# RestApiTemplate

This project is a template for future Spring Boot REST API projects. It provides a basic structure and configuration to get you started quickly. Below are the details and instructions on how to customize this template for your own project.

## Features

- **OAuth2 Authentication & JWT Authorization**: Supports authentication via Google and GitHub OAuth2 providers and uses JWT for authorization.
- **Caching**: Uses Hazelcast for caching to improve performance, demonstrated in the Grocery Service.
- **MongoDB Database**: Connects to a MongoDB database for data storage.
- **Swagger OpenAPI Documentation**: Provides API documentation and client code generation using Swagger.
- **Google Cloud Storage**: Connects to Google Cloud Storage to upload and retrieve files.

## Project Structure

- `src/main/java/ch/restapiTemplate/`
  - `controllers/`: Contains the REST controllers.
  - `models/`: Contains the data models.
  - `repositories/`: Contains the repository interfaces.
  - `services/`: Contains the service classes.
  - `config/`: Contains the configuration classes.
- `src/main/resources/`
  - `application.properties`: Configuration properties for the application.
  - `swagger.json`: Swagger API documentation.
- `pom.xml`: Maven configuration file.

## Customization

### Project Name

Replace all instances of `<projectName>` with the actual name of your project. This includes:

- `src/main/resources/application.properties`
- `src/main/resources/swagger.json`
- `pom.xml`
- `src/main/java/ch/restapiTemplate/config/SwaggerConfig.java`

### Dependencies

The `pom.xml` file includes various dependencies such as Spring Boot, MongoDB, Hazelcast, and Swagger. Add or remove dependencies as needed for your project.

### Configuration

- **Application Properties**: Update `src/main/resources/application.properties` with your specific configuration settings, such as database connection details, OAuth2 settings, and JWT secret.
- **Swagger Configuration**: Update `src/main/java/ch/restapiTemplate/config/SwaggerConfig.java` to customize the Swagger API documentation.

### Security

The project includes basic security configuration using OAuth2 and JWT. Customize the security settings in `src/main/java/ch/restapiTemplate/config/SecurityConfig.java` and `src/main/java/ch/restapiTemplate/config/JwtConfig.java`.

### Caching

The project uses Hazelcast for caching. Update the caching configuration in `src/main/java/ch/restapiTemplate/config/HazelcastConfiguration.java` as needed.

## Running the Project

To run the project, use the following Maven command:

```sh
./mvnw spring-boot:run