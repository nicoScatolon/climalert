package ar.utn.ba.ddsi.mailing.schedulers;

import ar.utn.ba.ddsi.mailing.services.IClimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClimaScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ClimaScheduler.class);
    private final IClimaService  climaService;

    public ClimaScheduler(IClimaService climaService) {
        this.climaService = climaService;
    }

    @Scheduled(cron = "${cron.5min}")
    public void actualizarClima() { //Es asincrono
        climaService.actualizarClimaCiudades()
            .doOnSuccess(v -> logger.info("Actualización de clima completada"))
            .doOnError(e -> logger.error("Error en la actualización de clima: {}", e.getMessage()))
            .subscribe();
    }
} 