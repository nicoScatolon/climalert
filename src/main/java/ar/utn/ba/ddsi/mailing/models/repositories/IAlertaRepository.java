package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;

import java.util.List;

public interface IAlertaRepository {
    Alerta save(Alerta alerta);
    Boolean delete(Long id);
    Alerta FindById(Long id);
    List<Alerta> findAll();
    public List<Clima> findClimasPorAlertar(List<Clima> climas);
    List<Clima> findClimasByAlerta(Long id, List<Clima> climasDisponibles);
}
