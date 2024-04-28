FROM eclipse-temurin:17-jdk-alpine
COPY ./target/*.jar app.jar

MAINTAINER Grigory Perov

ENV TZ=Europe/Moscow

ENTRYPOINT ["java", "-jar", "app.jar"]