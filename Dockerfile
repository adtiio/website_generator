FROM eclipse-termurin:17-jdk-alpine

VOLUME /trial
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080