package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.models.ActividadEntity;

import java.util.UUID;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadEntity, UUID> {
    ActividadEntity findByTipoActividadEconomicaId(UUID tipoActividad);
}
