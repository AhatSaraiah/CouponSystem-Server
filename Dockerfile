# Build stage
FROM maven:3.9-eclipse-temurin-26 AS build
WORKDIR /app

# Resolve dependencies first so they are cached between builds
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw -B -q dependency:go-offline

COPY src ./src
RUN ./mvnw -B -q clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# curl powers the container healthcheck below
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && apt-get clean \
 && groupadd --system app && useradd --system --gid app app
COPY --from=build /app/target/*.jar app.jar
RUN chown app:app app.jar
USER app

# Render, Railway and Fly inject PORT; the app defaults to 9096 locally
ENV PORT=9096
EXPOSE 9096

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD ["sh", "-c", "curl -fsS \"http://localhost:${PORT}/actuator/health\" || exit 1"]

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
