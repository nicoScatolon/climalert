package ar.utn.ba.ddsi.mailing.schedulers;

import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private final IEmailService emailService;

    public EmailScheduler(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "${cron.1min}")
    public void procesarEmailsPendientes() {
        emailService.procesarPendientes();
    }

    @Scheduled(cron = "${cron.1min}")
    public void loguearEmailsPendientes() {
        emailService.loguearEmailsPendientes();
    }
} 