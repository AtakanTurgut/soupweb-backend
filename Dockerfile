# Base image
FROM openjdk:17-jdk-slim

# Working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/soupweb-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
