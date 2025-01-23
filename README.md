

### `README.md`
```markdown
# Customer Contacts API

## Problem Statement

We are a Telecom operator. In our database, we are starting to store phone numbers
associated to customers (1 customer: N phone numbers) and we will provide
interfaces to manage them.
We need to provide the below capabilities:
• get all phone numbers
• get all phone numbers of a single customer
• activate a phone number

## Challenge
Provide API specifications for the above functions/capabilities.
Provide a REST API implementation of the formulated specifications.
You can assume the phone numbers as a static data structure that is initialised when
your program runs.

## Prerequisites
- Java 21
- Maven
- Docker
- Docker Compose
- Gitops (Github Action)
- AWS Cloud Access

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
5. **Swagger API Documentation:**
   Open your browser and navigate to `http://localhost:8080/swagger-ui.html`. 

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

Swagger
- Swagger API Documentation: Open your browser and navigate to `http://localhost:8080/swagger-ui.html`.

## Running the Application in AWS Cloud
13.211.152.243:8080/swagger-ui.html
 
endpoints
- GET /api/v1/phone-numbers
- GET /api/v1/phone-numbers/{customerId}
- POST /api/v1/phone-numbers/{phoneNumber}/activate

## Additional Information
- The application uses PostgreSQL as the database for docker environment
- The Docker Compose file sets up both the application and the PostgreSQL database.
- Ensure that the `target` directory is not excluded by `.dockerignore` to include the JAR file in the Docker build context.
- Locally we are using in memeory Database H2 to test and run all the integration test as well 
```
