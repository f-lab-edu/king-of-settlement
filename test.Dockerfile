FROM eclipse-temurin:21-jdk-alpine


ARG JAR_FILE=/build/libs/king-of-settlement.jar

COPY ${JAR_FILE} /king-of-settlement.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "/king-of-settlement.jar"]

EXPOSE 8080

