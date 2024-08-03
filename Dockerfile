# Use Maven to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory to /trial in the container
WORKDIR /trial

# Copy the contents of the current directory (your project) into the container
COPY . .

# Build the project, skipping tests
RUN mvn clean package -DskipTests

# Use a slim JDK image for running the application
FROM openjdk:17.0.1-jdk-slim

# Set the working directory to /app in the container
WORKDIR /app

# Copy the JAR file from the build stage, adjusted for the correct file name
COPY --from=build /trial/target/trial-0.0.1-SNAPSHOT.jar pickify.jar

# Expose port 8081
EXPOSE 8081

# Specify the command to run the application
ENTRYPOINT [ "java", "-jar", "pickify.jar" ]
