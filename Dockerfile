FROM openjdk:18-alpine
WORKDIR /app
COPY ./target/ecommerce-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]