package ar.utn.ba.ddsi.mailing.models.DTO.Output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailOutputDTO {
    private Long id;
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
}
