# Product Service (Spring Boot + MongoDB + Testcontainers)

A robust, production-grade Spring Boot microservice implementing full CRUD operations for Products, backed by MongoDB using Spring Data MongoDB, and verified with integration tests using Testcontainers.

---

## 🛠 Features

- **Spring Boot 3.x REST API**: Complete CRUD endpoints for product management.
- **Spring Data MongoDB**: Model mapping with `@Document`, validation annotations, and repository abstractions.
- **Jakarta Validation**: Built-in request body validations (`@NotBlank`, `@NotNull`, `@Min(0)`).
- **Global Exception Handling**: Centralized advice returning clean, structured error responses (HTTP 400, 404).
- **Testcontainers Integration**: Automated integration tests spin up a disposable MongoDB container (`mongo:7.0`) via Docker.
- **Maven Wrapper**: Standalone `mvnw` / `mvnw.cmd` included for zero-friction setup.

---

## 📂 Project Structure

```
product-service
├── .mvn/wrapper/
│   ├── maven-wrapper.jar
│   └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
├── .gitignore
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.productservice
│   │   │       ├── ProductServiceApplication.java
│   │   │       ├── controller
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ProductController.java
│   │   │       ├── model
│   │   │       │   └── Product.java
│   │   │       ├── repository
│   │   │       │   └── ProductRepository.java
│   │   │       └── service
│   │   │           └── ProductService.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com.example.productservice
│               └── ProductControllerIntegrationTest.java
```

---

## 🚀 REST API Endpoints

Both `/api/products` and `/products` URL paths are supported.

| Method | Endpoint | Description | Status Code |
|---|---|---|---|
| `POST` | `/api/products` | Create a new product | `201 Created` |
| `GET` | `/api/products` | Retrieve all products | `200 OK` |
| `GET` | `/api/products/{id}` | Retrieve product by ID | `200 OK` |
| `PUT` | `/api/products/{id}` | Update existing product | `200 OK` |
| `DELETE` | `/api/products/{id}` | Delete product by ID | `204 No Content` |

### Sample Request Payloads

#### 1. Create Product (`POST /api/products`)
```json
{
    "name": "Laptop",
    "description": "HP Victus Gaming Laptop",
    "price": 75000,
    "quantity": 10
}
```
**Response (201 Created):**
```json
{
    "id": "664f3e...",
    "name": "Laptop",
    "description": "HP Victus Gaming Laptop",
    "price": 75000.0,
    "quantity": 10
}
```

#### 2. Update Product (`PUT /api/products/{id}`)
```json
{
    "name": "HP Victus",
    "description": "Updated gaming laptop",
    "price": 72000,
    "quantity": 15
}
```

---

## 🏃 Running the Application

### Option A: Using Docker for MongoDB
Run a local MongoDB container:
```bash
docker run -d --name product-mongodb -p 27017:27017 mongo:7
```

### Option B: Local MongoDB Service
Ensure MongoDB is running on default port `27017`.

### Start Spring Boot
Using Maven wrapper:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
The server will start on port `8081` (`http://localhost:8081`).

---

## 🧪 Running Integration Tests (Testcontainers)

Make sure **Docker Desktop** is running. Then execute:
```bash
# Windows
.\mvnw.cmd test

# Linux / macOS
./mvnw test
```

Testcontainers will automatically:
1. Pull and start `mongo:7.0` container.
2. Bind Spring Data MongoDB to the container's dynamic port.
3. Run MockMvc REST integration tests.
4. Stop and dispose of the container.

---

## 💻 Importing into Spring Tools for Eclipse (STS)

1. Open **Spring Tools for Eclipse**.
2. Go to **File -> Import...**
3. Select **Maven -> Existing Maven Projects** and click **Next**.
4. Browse to:
   ```
   c:\New folder\product-service
   ```
5. Select `pom.xml` and click **Finish**.
6. Right-click `ProductServiceApplication.java` -> **Run As -> Spring Boot App**.
7. Right-click `ProductControllerIntegrationTest.java` -> **Run As -> JUnit Test**.

---

## 📋 Assignment Submission Text (Archi's Academy)

Copy and paste the following into the **“Submit your assignment”** box:

```text
Build Product Service

Implemented a Product Service using Spring Boot with REST APIs and MongoDB integration.

Features:
- Product CRUD REST APIs
- Spring Data MongoDB persistence layer
- Input validation (NotBlank, Min, NotNull)
- Product management service layer
- Exception handling and error response formatting
- Integration testing using Testcontainers with MongoDB 7.0

REST Endpoints:
POST   /api/products
GET    /api/products
GET    /api/products/{id}
PUT    /api/products/{id}
DELETE /api/products/{id}

GitHub Repository:
https://github.com/praneeth476/product-service
```
