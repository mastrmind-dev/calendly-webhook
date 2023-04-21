FROM amazoncorretto:17-alpine
COPY build/libs/calendly-webhook-poc-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]