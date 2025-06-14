package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Clima {
    private Long id;
    private String ciudad;
    private String region;
    private String pais;
    private Double temperaturaCelsius;
    private Double temperaturaFahrenheit;
    private String condicion;
    private Double velocidadVientoKmh;
    private Integer humedad;
    private LocalDateTime fechaActualizacion;
    private boolean procesado;
}