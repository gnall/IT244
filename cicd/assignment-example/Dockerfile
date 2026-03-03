# Build stage: compile and package the Java app
FROM maven:3-eclipse-temurin-17-alpine AS builder
WORKDIR /app

COPY pom.xml .
# Download dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B -q || true

COPY src ./src
RUN mvn package -DskipTests -B -q

# Run stage: minimal JRE with the built JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/cicd-assignment-app-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
