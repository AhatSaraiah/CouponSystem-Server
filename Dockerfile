# Build stage
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Skip tests to speed up build and avoid environment dependency issues during build
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9096
ENTRYPOINT ["java", "-jar", "app.jar"]
