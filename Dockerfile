FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/desafio-0.0.1-SNAPSHOT.jar fcoManagerapp.jar
ENTRYPOINT ["java", "-jar", "/fcoManagerapp.jar"]