FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/ms-payment-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9093
CMD ["java", "-jar", "app.jar"]