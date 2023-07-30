ARG SERVICE_PORT=8081
FROM openjdk:17-oracle
COPY target/*.jar predictionService.jar
EXPOSE $SERVICE_PORT
ENTRYPOINT ["java", "-jar", "/predictionService.jar"]
HEALTHCHECK	--interval=10s --timeout=3s --start-period=15s \
			CMD wget --quiet --tries=1 --spider http://localhost:$SERVICE_PORT || exit 1