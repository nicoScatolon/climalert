package ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.ICriterio;

public class CriterioTempC implements ICriterio {
    private static final Double TEMPERATURA_ALERTA = 35.0;
    @Override
    public Boolean seCumple(Clima clima) {
        return clima.getTemperaturaCelsius() > TEMPERATURA_ALERTA;
    }
}
