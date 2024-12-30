FROM maven:latest AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/ProjectDevOps-0.0.1-SNAPSHOT.jar ProjectDevOps.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080
