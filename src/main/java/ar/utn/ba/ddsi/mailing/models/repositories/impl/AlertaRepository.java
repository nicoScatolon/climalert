package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AlertaRepository implements IAlertaRepository {
    private final Map<Long, Alerta> alertas = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Alerta> findAll() {
        return new ArrayList<>(this.alertas.values());
    }

    @Override
    public Alerta save(Alerta alerta) {
        if (alerta.getId() == null) {
            return this.create(alerta);
        } else {
            return this.update(alerta);
        }
    }

    @Override
    public Boolean delete(Long id) {
        if (alertas.containsKey(id)) {
            this.alertas.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Alerta FindById(Long id) {
        return this.alertas.get(id);
    }

    @Override
    public List<Clima> findClimasByAlerta(Long id, List<Clima> climasDisponibles) {
        Alerta alerta = this.FindById(id);
        if (alerta == null) {
            return List.of();
        }
        return new ArrayList<>(alerta.filtrarClimas(climasDisponibles));
    }

    @Override
    public List<Clima> findClimasPorAlertar(List<Clima> climas){
        if (alertas.isEmpty()) {return List.of();}
        List<Clima> climasPorAlertar = new ArrayList<>();
        for (Alerta c : this.findAll()) {
            climasPorAlertar.addAll(findClimasByAlerta(c.getId(), climas));
        }
        return climasPorAlertar;
    }

    private Alerta create(Alerta alerta){
        Long id = idGenerator.getAndIncrement();
        alerta.setId(id);
        alertas.put(id, alerta);

        return alerta;
    }

    private Alerta update(Alerta alerta) {
        alertas.put(alerta.getId(), alerta);
        return alerta;
    }
}
