package ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.ICriterio;

public class CriterioHumedad implements ICriterio {
    private static final Integer HUMEDAD_ALERTA = 60;

    @Override
    public Boolean seCumple(Clima clima) {
        return clima.getHumedad() > HUMEDAD_ALERTA;
    }
}
