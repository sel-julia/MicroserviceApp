# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /eureka-server

COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

# Runtime stage
FROM eclipse-temurin:17-jre-alpine-3.21

WORKDIR /eureka-server

COPY --from=build /eureka-server/target/*.jar eureka-server.jar

CMD ["java","-jar","eureka-server.jar"]