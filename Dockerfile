FROM openjdk:8

WORKDIR /app

COPY target/spring-boot-challeng-labs0.0.1-SNAPSHOT.jar /app/spring-challeng-labs

ENTRYPOINT ["java", "spring-boot-challeng-labs", "spring-boot-challeng-labs"]
