# Stage 1: Build stage
FROM maven:3.9.9-amazoncorretto-24 AS build  


WORKDIR /app


# Copy Maven files for dependency resolution
COPY pom.xml ./
COPY .mvn .mvn

# Copy application source code
COPY src src

# Build the project and create the executable JAR
RUN mvn clean install -DskipTests

# Stage 2: Run stage
FROM amazoncorretto:24

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8081
EXPOSE 8081

# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "app.jar"]
