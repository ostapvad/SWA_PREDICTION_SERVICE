FROM openjdk:17-oracle
COPY target/*.jar predictionService.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/predictionService.jar"]