package swa.weather_app.prediction_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import swa.weather_app.prediction_service.entity.HourPrediction;
import swa.weather_app.prediction_service.entity.WeatherMeasurement;
import swa.weather_app.prediction_service.entity.WindData;
import swa.weather_app.prediction_service.entity.WeatherPrediction;
import swa.weather_app.prediction_service.error.NotEnoughDataToPredict;
import swa.weather_app.prediction_service.service.PredictionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
    @Autowired
    PredictionService predictionService;
    @Autowired
    RestTemplate restTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    @GetMapping()
    public ResponseEntity<WeatherPrediction> GetCityPrediction(@RequestParam(name = "city") String city)
            throws NotEnoughDataToPredict {

        LocalDateTime curTime = LocalDateTime.now();
        String getMeasurementsUrlHeader = "http://localhost:8082/measurements";
        String MeasurementsUrl = String.format("%s?city=%s&from=%s&to=%s", getMeasurementsUrlHeader, city,
                curTime.minusDays(1), curTime);
        System.out.println(MeasurementsUrl);

            ResponseEntity<List<WeatherMeasurement>> response = restTemplate.exchange(
                    MeasurementsUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

        WeatherPrediction weatherPrediction = predictionService.computePrediction(curTime, city, response.getBody());
        LOGGER.info(String.format("Prediction is computed for city = %s", city));
        return ResponseEntity.status(HttpStatus.OK).body(weatherPrediction);

    }


}
