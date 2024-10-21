
# ProductParser Application

## Overview

The **ProductParser** is a Spring Boot-based application designed to parse XLSX/CSV files containing product data, detect changes (new, updated, unchanged rows), and update the database accordingly. The application follows the **N-tier architecture** for modular, maintainable code, and adheres to a **Test-Driven Development (TDD)** approach. It also uses **Hibernate Envers** for tracking change history in the database.

The application is containerized using **Docker**, making it easy to run in any environment. The database used is **PostgreSQL**.

## Features

- Parses XLSX/CSV files and identifies:
    - New rows
    - Updated rows
    - Unchanged rows
- Updates PostgreSQL database with the changes.
- Tracks historical changes using Hibernate Envers.
- Fully Dockerized for simple deployment.
- Follows N-tier architecture to maintain separation of concerns.
- Developed using a TDD approach with unit tests.

## Technologies

- **Java 21**
- **Spring Boot**
- **Spring Data Envers**
- **PostgreSQL**
- **Docker & Docker Compose**
- **JUnit & Mockito (for testing)**

## Prerequisites

- Docker & Docker Compose installed
- PostgreSQL (optional for local development if not using Docker)

## Installation

### Clone the Repository

```bash
git clone https://github.com/hasib-hossain-sm/product-parser.git
cd product-parser
```

### Build and Run with Docker

1. Build and start the containers using Docker Compose:

   ```bash
   docker compose -f .\docker-compose.yaml up
   ```

2. This will start the Spring Boot application and PostgreSQL database in Docker containers. The application will be accessible at `http://localhost:8080` (or the port you've configured).

### Access REST Endpoints

Ensure the application is running and you can access the following REST endpoints:
Added Postman Collection, example xlsx file in the helperFiles folder.

- `POST /api/products/upload` - Upload the XLSX of CSV file for parsing.

- `GET /api/products/{sku}` - Retrieve product by sku.
- `GET /api/products/` - Retrieve all products.
- `GET /api/products/change-histories` - Retrieve the change histories of all products (using Hibernate Envers).

## Configuration

### Application Configuration

Make sure your `application.properties` or `application.yml` contains the following to bind to all network interfaces:

```properties
server.address=0.0.0.0
server.port=8080
spring.datasource.url=jdbc:postgresql://db:5432/productdb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

### PostgreSQL Configuration

The PostgreSQL database is set up via Docker Compose. You can customize the database credentials in the `docker-compose.yml` file:

```yaml
services:
  db:
    image: postgres
    environment:
      POSTGRES_USER: yourusername
      POSTGRES_PASSWORD: yourpassword
      POSTGRES_DB: productdb
```

## Running Tests

The project uses **JUnit** and **Mockito** for unit and integration tests. To run tests, use the following command:

```bash
./mvnw test
```


## Future Improvements

- Add more validation on the uploaded XLSX files.
- Implement advanced error handling for file parsing issues.
- Enhance security measures, such as using JWT for authentication.