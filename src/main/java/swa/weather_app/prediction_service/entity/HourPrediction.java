package swa.weather_app.prediction_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HourPrediction{
    @JsonProperty("temperature")
    private float temperature;
    @JsonProperty("humidity")
    private int humidity;
    @JsonProperty("pressure")
    private int pressure;
    @JsonProperty("wind")
    private WindData wind;

}
