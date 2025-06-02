package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.DTO.Input.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.DTO.Output.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final IEmailRepository emailRepository;

    public EmailService(IEmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public EmailOutputDTO crearEmail(EmailInputDTO email) {
        return this.convertirEmailOutputDTO(emailRepository.save(convertirEmail(email)));
    }

    @Override
    public List<EmailOutputDTO> obtenerEmails(Boolean pendiente) {
        if (pendiente != null) {
            return this.convertirListaEmailOutputDTO(emailRepository.findByEnviado(!pendiente));
        }
        return this.convertirListaEmailOutputDTO(emailRepository.findAll());
    }

    @Override
    public void procesarPendientes() {
        List<Email> pendientes = emailRepository.findByEnviado(false);
        for (Email email : pendientes) {
            email.enviar();
            email.setEnviado(true); //ToDo si no se envia igual se marcara como enviado
            emailRepository.save(email);
        }
    }

    @Override
    public void loguearEmailsPendientes() {
        List<EmailOutputDTO> pendientes = obtenerEmails(true);
        logger.info("Emails pendientes de envío: {}", pendientes.size());
        pendientes.forEach(email -> 
            logger.info("Email pendiente - ID: {}, Destinatario: {}, Asunto: {}", 
                email.getId(),
                email.getDestinatario(), 
                email.getAsunto())
        );
    }

    private Email convertirEmail(EmailInputDTO email) {
        return Email.builder()
                .destinatario(email.getDestinatario())
                .asunto(email.getAsunto())
                .contenido(email.getContenido())
                .remitente(email.getRemitente())
                .build();
    }

    private List<EmailOutputDTO> convertirListaEmailOutputDTO(List<Email> emails) {
        return emails.stream()
                .map(this::convertirEmailOutputDTO)
                .collect(Collectors.toList());
    }

    private EmailOutputDTO convertirEmailOutputDTO(Email email) {
        return EmailOutputDTO.builder()
                .destinatario(email.getDestinatario())
                .asunto(email.getAsunto())
                .contenido(email.getContenido())
                .remitente(email.getRemitente())
                .build();
    }
} 