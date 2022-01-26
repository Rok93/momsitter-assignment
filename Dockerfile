FROM adoptopenjdk:8-jdk-hotspot

COPY momsitter-api/build/libs/momsitter-api.jar momsitter-app.jar

ENTRYPOINT ["java", "-jar", "momsitter-app.jar"]