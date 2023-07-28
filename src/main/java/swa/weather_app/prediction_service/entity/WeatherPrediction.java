package swa.weather_app.prediction_service.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WeatherPrediction{
    @JsonProperty("start")
    LocalDateTime start;
    @JsonProperty("city")
    String city;
    @JsonProperty("prediction")
    List<HourPrediction> predictions;
}

