package ar.utn.ba.ddsi.mailing.models.DTO.Input.Weatherapi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Location {
    private String name;
    private String region;
    private String country;
} 