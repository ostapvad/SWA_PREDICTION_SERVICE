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
public class WeatherMeasurement {
    @JsonProperty("time")
    private LocalDateTime time;
    @JsonProperty("city")
    private String city;
    @JsonProperty("temperature")
    private Float temperature;
    @JsonProperty("humidity")
    private Integer humidity;
    @JsonProperty("pressure")
    private Integer pressure;
    @JsonProperty("wind")
    private WindData wind;



}

