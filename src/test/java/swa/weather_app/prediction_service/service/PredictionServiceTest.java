package swa.weather_app.prediction_service.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringRunner;
import swa.weather_app.prediction_service.entity.HourPrediction;
import swa.weather_app.prediction_service.entity.WeatherMeasurement;
import swa.weather_app.prediction_service.entity.WeatherPrediction;
import swa.weather_app.prediction_service.entity.WindData;
import swa.weather_app.prediction_service.error.NotEnoughDataToPredict;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
///TODO
@RunWith(SpringRunner.class)
@SpringBootTest
class PredictionServiceTest {
    @Autowired
    private PredictionService predictionService;
    @Test
    @Description("Checks that prediction service returns correct number of prediction entities")
    void computePredictionEntitiesNumberTest() throws NotEnoughDataToPredict {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<WeatherMeasurement> measurements = List.of(WeatherMeasurement.builder().city("Odesa").humidity(100).
                pressure(35).temperature(15f).wind(new WindData(10, 15, 30)).
                time(localDateTime).build());
        String city = "Odesa";
        WeatherPrediction computedPredictions =  predictionService.computePrediction(localDateTime, city, measurements);
        assertEquals(24, computedPredictions.getPredictions().size());
    }

    @Test
    @Description("Checks if the average prediction is returned from all measurements")
    void averagePredictionCorrectReturnTest() {
        WeatherMeasurement receivedFirstMeasurement = WeatherMeasurement.builder().city("Odesa").humidity(12).
                pressure(10).temperature(20f).wind(new WindData(12, 15, 10)).build();
        WeatherMeasurement receivedSecondMeasurement = WeatherMeasurement.builder().city("Odesa").humidity(8).
                pressure(10).temperature(0f).wind(new WindData(8, 5, 10)).build();
        List<WeatherMeasurement> measurements = List.of(receivedFirstMeasurement, receivedSecondMeasurement);
        HourPrediction expectedAverage = HourPrediction.builder().humidity(10).
                pressure(10).temperature(10f).wind(new WindData(10, 10, 10)).build();
        HourPrediction hourPrediction = predictionService.AveragePrediction(measurements);

        Assertions.assertEquals(expectedAverage.toString(), hourPrediction.toString());
    }
}