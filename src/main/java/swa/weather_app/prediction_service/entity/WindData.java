package swa.weather_app.prediction_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WindData {
    @JsonProperty("speed")
    private float speed;
    @JsonProperty("deg")
    private int deg;
    @JsonProperty("gust")
    private float gust;
}
