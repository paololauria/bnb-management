# Stage 1: Build (No JAR file)

FROM openjdk:11-jdk AS builder

WORKDIR /bnb

# Build your backend application (excluding JAR generation)

# ... (Your build commands)

# Generate the JAR file (assuming your build process generates it in the current directory)
COPY ./*.jar /bnb/security-0.1.0.jar

# Stage 2: Runtime (Slim image with JAR file)

FROM alpine:latest

WORKDIR /bnb

# Copy the JAR file from the build stage (no change)
COPY --from=builder /bnb/security-0.1.0.jar /bnb/security-0.1.0.jar

# Expose and run the application (no change)
EXPOSE 8080
CMD ["java", "-jar", "security-0.1.0.jar"]
