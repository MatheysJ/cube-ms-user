FROM maven:3.9.6-eclipse-temurin-22-alpine AS builder

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

FROM 3.9.6-eclipse-temurin-22-alpine

COPY --from=builder /app/target/*.jar /app/app.jar

WORKDIR /app

CMD ["java", "-jar", "app.jar"]