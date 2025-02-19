# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /resource-service-app

COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

# Runtime stage
FROM eclipse-temurin:17-jre-alpine-3.21

WORKDIR /resource-service-app

COPY --from=build /resource-service-app/target/*.jar resource-service.jar

EXPOSE 8080

CMD ["java","-jar","resource-service.jar"]