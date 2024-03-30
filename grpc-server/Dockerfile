# Use a base image with Amazon Corretto Java 11 installed
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/grpc-server-0.0.1-SNAPSHOT.jar /app

# Expose the port that the Spring Boot application will run on
EXPOSE 9090

# Define the command to run your application
CMD ["java", "-jar", "grpc-server-0.0.1-SNAPSHOT.jar"]
