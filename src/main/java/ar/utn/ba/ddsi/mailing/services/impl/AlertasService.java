package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.DTO.Input.EmailInputDTO;
import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertasService implements IAlertasService {
    private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);

    private final IAlertaRepository alertaRepository;
    private final IClimaRepository climaRepository;
    private final IEmailService emailService;
    private final String remitente;
    private final List<String> destinatarios;

    public AlertasService(
            IClimaRepository climaRepository,
            IEmailService emailService,
            IAlertaRepository alertaRepository,
            @Value("${email.alertas.remitente}") String  remitente,
            @Value("${email.alertas.destinatarios}") String destinatarios) {
        this.climaRepository = climaRepository;
        this.emailService = emailService;
        this.remitente = remitente;
        this.alertaRepository = alertaRepository;
        this.destinatarios = Arrays.asList(destinatarios.split(","));
    }

    @Override
    public Mono<Void> generarAlertasYAvisar() {
        return Mono.fromCallable(() -> climaRepository.findByProcesado(false))
            .flatMap(climas -> {
                logger.info("Procesando {} registros de clima no procesados", climas.size());
                return Mono.just(climas);
            })
            .flatMap(climas -> {
                climas.stream()
                    .filter(this::cumpleCondicionesAlerta)
                    .forEach(this::generarYEnviarEmail);
                
                // Marcar todos como procesados
                climas.forEach(clima -> {
                    clima.setProcesado(true);
                    climaRepository.save(clima);
                });
                return Mono.empty();
            })
            .onErrorResume(e -> {
                logger.error("Error al procesar alertas: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }

    private boolean cumpleCondicionesAlerta(Clima clima) {
        if (clima==null){
            return false;
        };
        return climasPorAlertar().contains(clima);
    }

    private List<Clima> climasPorAlertar() {
        return alertaRepository.findClimasPorAlertar(climaRepository.findAll());
    }

    private void generarYEnviarEmail(Clima clima) {
        String asunto = "Alerta de Clima - Condiciones Extremas";
        String mensaje = String.format(
                """
                        ALERTA: Condiciones climáticas extremas detectadas en %s
                        
                        Temperatura: %.1f°C
                        Humedad: %d%%
                        Condición: %s
                        Velocidad del viento: %.1f km/h
                        
                        Se recomienda tomar precauciones.""",
            clima.getCiudad(),
            clima.getTemperaturaCelsius(),
            clima.getHumedad(),
            clima.getCondicion(),
            clima.getVelocidadVientoKmh()
        );

        for (String destinatario : destinatarios) {
            Email email = new Email(destinatario, remitente, asunto, mensaje);
            emailService.crearEmail(this.convertirEmail(email));
        }
        
        logger.info("Email de alerta generado para {} - Enviado a {} destinatarios", 
            clima.getCiudad(), destinatarios.size());
    }

    private EmailInputDTO convertirEmail(Email email) {
        return EmailInputDTO.builder()
                .destinatario(email.getDestinatario())
                .asunto(email.getAsunto())
                .contenido(email.getContenido())
                .remitente(email.getRemitente())
                .build();
    }

} 