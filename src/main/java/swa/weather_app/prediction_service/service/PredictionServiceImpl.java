package swa.weather_app.prediction_service.service;

import org.springframework.stereotype.Service;
import swa.weather_app.prediction_service.entity.HourPrediction;
import swa.weather_app.prediction_service.entity.WeatherMeasurement;
import swa.weather_app.prediction_service.entity.WeatherPrediction;
import swa.weather_app.prediction_service.entity.WindData;
import swa.weather_app.prediction_service.error.NotEnoughDataToPredict;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PredictionServiceImpl implements  PredictionService{
    private final long MinutesMeasurementsOffset = 120;


    @Override
    public WeatherPrediction computePrediction(LocalDateTime curTime, String city, List<WeatherMeasurement> weatherMeasurementList) throws NotEnoughDataToPredict {

        //        weatherMeasurementList.sort(Comparator.comparing(WeatherMeasurement::getTime));
//        HourPrediction hourPrediction1 = new HourPrediction(2F, 1015, 64, new WindData(0.62F, 349, 1.18F));
//        HourPrediction hourPrediction2 = new HourPrediction(3F, 1014, 66, new WindData(0.5F, 322, 1.11F));
        List<HourPrediction> computedPredictions = new ArrayList<>();
        for(int time_offset = 0; time_offset  < 24; time_offset ++){
            List<WeatherMeasurement> curTimeRelatedMeasurements = new ArrayList<>();
            for(WeatherMeasurement weatherMeasurement: weatherMeasurementList){
                if(Math.abs(ChronoUnit.MINUTES.between(weatherMeasurement.getTime(),
                        curTime.minusHours(24 - time_offset))) < MinutesMeasurementsOffset){
                    curTimeRelatedMeasurements.add(weatherMeasurement);
                }
            }
            if(curTimeRelatedMeasurements.isEmpty()) throw new NotEnoughDataToPredict(
                    String.format("Not enough weather measurements for city %s. Total found = %d", city,
                            weatherMeasurementList.size()));
            computedPredictions.add(AveragePrediction(curTimeRelatedMeasurements));
        }

        // add some logic
        return new WeatherPrediction(curTime, city, computedPredictions);

    }

    @Override
    public HourPrediction AveragePrediction(List<WeatherMeasurement> receivedMeasurements){
        HourPrediction curPrediction = new HourPrediction(0, 0, 0, new WindData());
        // There might be exception

        for(WeatherMeasurement curHourMeasurement: receivedMeasurements){
            curPrediction.setPressure(curPrediction.getPressure() + curHourMeasurement.getPressure());
            curPrediction.setHumidity(curPrediction.getHumidity() + curHourMeasurement.getHumidity());
            curPrediction.setTemperature(curPrediction.getTemperature() + curHourMeasurement.getTemperature());
            curPrediction.getWind().setDeg(curPrediction.getWind().getDeg() + curHourMeasurement.getWind().getDeg());
            curPrediction.getWind().setGust(curPrediction.getWind().getGust() + curHourMeasurement.getWind().getGust());
            curPrediction.getWind().setSpeed(curPrediction.getWind().getSpeed() + curHourMeasurement.getWind().getSpeed());
        }
        curPrediction.setPressure(curPrediction.getPressure()/receivedMeasurements.size());
        curPrediction.setHumidity(curPrediction.getHumidity()/receivedMeasurements.size());
        curPrediction.setTemperature(curPrediction.getTemperature()/receivedMeasurements.size());
        curPrediction.getWind().setDeg(curPrediction.getWind().getDeg()/receivedMeasurements.size());
        curPrediction.getWind().setGust(curPrediction.getWind().getGust()/receivedMeasurements.size());
        curPrediction.getWind().setSpeed(curPrediction.getWind().getSpeed()/ receivedMeasurements.size());
        return curPrediction;
    }
}
