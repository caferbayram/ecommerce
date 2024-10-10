FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18-alpine
WORKDIR /app
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]
