# Stage 1: Build stage
FROM maven:3.9.9-amazoncorretto-24 AS build

WORKDIR /app

# Copy Maven files first (for dependency caching)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build project (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run stage
FROM amazoncorretto:24

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (8888 is default for config-service, change if needed)
EXPOSE 8888

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
