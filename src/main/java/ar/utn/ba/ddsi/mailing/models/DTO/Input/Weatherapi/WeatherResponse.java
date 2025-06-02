package ar.utn.ba.ddsi.mailing.models.DTO.Input.Weatherapi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class WeatherResponse {
    private Location location;
    private Current current;
} 