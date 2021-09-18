FROM openjdk:8

WORKDIR /app

COPY target/spring-boot-challeng-labs*.jar spring-boot-challeng-labs.jar

ENTRYPOINT ["java", "spring-boot-challeng-labs", "spring-boot-challeng-labs"]
