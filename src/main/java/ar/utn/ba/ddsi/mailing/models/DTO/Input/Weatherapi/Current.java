package ar.utn.ba.ddsi.mailing.models.DTO.Input.Weatherapi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Current {
    private double temp_c;
    private double temp_f;
    private Condition condition;
    private double wind_kph;
    private int humidity;
} 