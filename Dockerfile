# Use the official OpenJDK image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the build artifact from the target directory to the container
COPY build/libs/ecommerce-0.0.1-SNAPSHOT.war app.war

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.war"]
