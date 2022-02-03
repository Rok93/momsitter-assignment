FROM openjdk:8-jdk-alpine

COPY momsitter-api/build/libs/momsitter-api.jar momsitter-app.jar

ENTRYPOINT ["java", "-jar", "momsitter-app.jar"]