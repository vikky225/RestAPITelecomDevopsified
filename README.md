

### `README.md`
```markdown
# Customer Contacts API

## Problem Statement
The Customer Contacts API is designed to manage customer contact details for a telecom company. 
It provides endpoints to create, read, update, and delete customer contact information.

## Prerequisites
- Java 21
- Maven
- Docker
- Docker Compose
- Gitops (Github Action)

## Running the Application Locally

1. **Clone the repository:**
   ```shell
   git clone https://github.com/vikky225/customer-contacts.git
   cd customer-contacts
   ```

2. **Build the application:**
   ```shell
   mvn clean package
   ```

3. **Run the application:**
   ```shell
   mvn spring-boot:run
   ```

4. **Access the application:**
   Open your browser and navigate to `http://localhost:8080`.

## Running the Application in a Dockerized Environment

1. **Clone the repository:**
   ```shell
   git clone https://github.com/vikky225/customer-contacts.git
   cd customer-contacts
   ```

2. **Build the Docker image:**
   ```shell
   docker build -t vikky225/customer-contacts-app:latest .
   ```

3. **Run the application using Docker Compose:**
   ```shell
   docker-compose up --build -d
   ```

4. **Access the application:**
   Open your browser and navigate to `http://localhost:8080`.

5. **Stop the application:**
   ```shell
   docker-compose down
   ```

## Additional Information
- The application uses PostgreSQL as the database.
- The Docker Compose file sets up both the application and the PostgreSQL database.
- Ensure that the `target` directory is not excluded by `.dockerignore` to include the JAR file in the Docker build context.
```