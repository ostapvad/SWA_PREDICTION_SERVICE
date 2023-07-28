package swa.weather_app.prediction_service.service;

import swa.weather_app.prediction_service.entity.HourPrediction;
import swa.weather_app.prediction_service.entity.WeatherMeasurement;
import swa.weather_app.prediction_service.entity.WeatherPrediction;
import swa.weather_app.prediction_service.error.NotEnoughDataToPredict;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionService {


    WeatherPrediction computePrediction(LocalDateTime curTime, String city,  List<WeatherMeasurement> weatherMeasurementList) throws NotEnoughDataToPredict;
    public HourPrediction AveragePrediction(List<WeatherMeasurement> receivedMeasurements);
}
