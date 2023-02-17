FROM eclipse-temurin:17.0.5_8-jre-alpine

RUN mkdir /app
WORKDIR /app

COPY build/libs/libs ./libs
COPY build/libs/*.jar app-books.jar

CMD ["java", "-jar", "app-books.jar"]