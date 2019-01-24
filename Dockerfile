FROM openjdk:8-jdk-alpine
COPY target/spring-cloud-simple-authorization-service-0.0.1-SNAPSHOT.jar /usr/src/app/spring-cloud-simple-authorization-service-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/app
CMD ["java", "-jar", "spring-cloud-simple-authorization-service-0.0.1-SNAPSHOT.jar"]