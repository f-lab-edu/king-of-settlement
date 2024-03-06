FROM eclipse-temurin:21-jdk-alpine


ARG JAR_FILE=/build/libs/king-of-settlement.jar

COPY ${JAR_FILE} /king-of-settlement.jar

ENTRYPOINT ["java", "-jar", "/king-of-settlement.jar"]

EXPOSE 8080

