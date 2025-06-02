package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.DTO.Input.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.DTO.Output.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import java.util.List;

public interface IEmailService {
    EmailOutputDTO crearEmail(EmailInputDTO email);
    List<EmailOutputDTO> obtenerEmails(Boolean pendiente);
    void procesarPendientes();
    void loguearEmailsPendientes();
} 