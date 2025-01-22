# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the project’s jar file into the container
COPY target/customer-contacts-0.0.1-SNAPSHOT.jar app.jar

ENV Spring_Profiles_Active=docker

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]