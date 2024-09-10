# Land Route Calculator: Backend Developer Test

## API Description

The application exposes a RESTful API for finding land routes between countries.

### Endpoints

1. **GET /routing/{origin}/{destination}**
   - **Description**: Finds a land route between two countries.
   - **Parameters**:
     - `origin`: The origin country code (ISO 3166-1 alpha-3)
     - `destination`: The destination country code (ISO 3166-1 alpha-3)


## Building and Running the Application

To build and run the application from the command line, follow these steps:

1. **Build the application**:
   ```
   mvn clean package
   ```

2. **Run the application**:
   ```
   java -jar target/your-application-name.jar
   ```
   Replace `your-application-name.jar` with the actual name of the generated JAR file.

Alternatively, you can use the Maven `spring-boot:run` goal to build and run the application in a single step:
```
mvn spring-boot:run
```

## Testing

This project includes both unit tests and integration tests. To run all tests, use the following command:


### Unit Tests

Unit tests are located in `src/test/java` and cover individual components of the application. Key test cases include:

- `RoutingServiceTest`: Tests the core routing logic
  - Finding a route between direct neighbors
  - Finding a route through intermediate countries
  - Handling cases where no route exists
  - Handling invalid country codes

### Integration Tests

Integration tests are in `RoutingControllerIntegrationTest` and test the API endpoints directly. Key test cases include:

1. Valid route request:
   ```
   GET /routing/CZE/ITA
   Expected: 200 OK with a JSON array of country codes
   ```

2. Invalid country code:
   ```
   GET /routing/CZE/XYZ
   Expected: 400 Bad Request
   ```

3. No route available:
   ```
   GET /routing/USA/NZL
   Expected: 404 Not Found
   ```

### Manual Testing

You can also test the API manually using curl: 
```
curl -X GET http://localhost:8080/routing/CZE/ITA
```

or using swagger-ui: 
[swagger-ui](http://localhost:8080/swagger-ui/index.html#/routing-controller/getRoute)