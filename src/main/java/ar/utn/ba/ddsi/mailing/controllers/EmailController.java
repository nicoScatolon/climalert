package ar.utn.ba.ddsi.mailing.controllers;

import ar.utn.ba.ddsi.mailing.models.DTO.Input.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.DTO.Output.EmailOutputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public EmailOutputDTO crearEmail(@RequestBody EmailInputDTO email) {
        return emailService.crearEmail(email);
    }

    @GetMapping
    public List<EmailOutputDTO> obtenerEmails(@RequestParam(required = false) Boolean pendiente) {
        return emailService.obtenerEmails(pendiente);
    }
} 