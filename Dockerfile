# Etapa de build
FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/api-comunidade.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/api-comunidade.jar"]
