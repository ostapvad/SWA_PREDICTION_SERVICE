package swa.weather_app.prediction_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import swa.weather_app.prediction_service.entity.HourPrediction;
import swa.weather_app.prediction_service.entity.WeatherMeasurement;
import swa.weather_app.prediction_service.entity.WindData;
import swa.weather_app.prediction_service.entity.WeatherPrediction;
import swa.weather_app.prediction_service.error.NotEnoughDataToPredict;
import swa.weather_app.prediction_service.service.PredictionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PredictionController {
    @Autowired
    PredictionService predictionService;
    @Autowired
    RestTemplate restTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    @Value("${backingServiceUrl}")
    private String backingServiceUrl;

    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Weather prediction service is OK.", HttpStatus.OK);
    }

    @GetMapping("/prediction")
    public ResponseEntity<WeatherPrediction> GetCityPrediction(@RequestParam(name = "city") String city)
            throws NotEnoughDataToPredict {
        var now = LocalDateTime.now();

        var url = UriComponentsBuilder.fromHttpUrl(backingServiceUrl)
                .path("/measurements")
                .queryParam("city", city)
                .queryParam("from", now)
                .queryParam("to", now.minusDays(1)).toUriString();

        ResponseEntity<List<WeatherMeasurement>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        WeatherPrediction weatherPrediction = predictionService.computePrediction(now, city, response.getBody());
        LOGGER.info(String.format("Prediction is computed for city = %s", city));
        return ResponseEntity.status(HttpStatus.OK).body(weatherPrediction);

    }


}
