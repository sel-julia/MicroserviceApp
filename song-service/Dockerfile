# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /song-service-app

COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

# Runtime stage
FROM eclipse-temurin:17-jre-alpine-3.21

WORKDIR /song-service-app

COPY --from=build /song-service-app/target/*.jar song-service.jar

CMD ["java","-jar","song-service.jar"]