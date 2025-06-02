package ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;

public interface ICriterio {
    Boolean seCumple(Clima clima);
}
