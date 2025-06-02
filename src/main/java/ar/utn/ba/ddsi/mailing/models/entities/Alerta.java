package ar.utn.ba.ddsi.mailing.models.entities;

import ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.ICriterio;
import ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.impl.CriterioHumedad;
import ar.utn.ba.ddsi.mailing.models.entities.CriteriosAlerta.impl.CriterioTempC;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Alerta {
    private  List<ICriterio> criterios = new ArrayList<>();
    private Long id;

    public Set<Clima> filtrarClimas(List<Clima> climas) {
        if (this.criterios.isEmpty() || climas.isEmpty()) {
            return new HashSet<>();
        }
        return climas.stream()
                .filter(this::cumpleCondiciones)
                .collect(Collectors.toSet());
    }

    public void agregarCriterio(ICriterio criterio) {
        this.criterios.add(criterio);
    }

    public void agregarCriterios(List<ICriterio> criterios) {
        this.criterios.addAll(criterios);
    }

    public void eliminarCriterio(ICriterio criterio) {
        this.criterios.remove(criterio);
    }

    public Boolean cumpleCondiciones(Clima clima){
        return criterios.stream()
                .allMatch(criterio -> criterio.seCumple(clima));
    }

    private void agregarCriteriosBasicos(){
        ICriterio criterio = new CriterioHumedad();
        ICriterio criterio2 = new CriterioTempC();
        this.agregarCriterios(List.of(criterio, criterio2));
    }
}
