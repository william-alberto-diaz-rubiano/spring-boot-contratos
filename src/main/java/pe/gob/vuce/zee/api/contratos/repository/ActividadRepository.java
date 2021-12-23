package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.vuce.zee.api.contratos.models.ActividadEntity;

import java.util.UUID;

public interface ActividadRepository extends JpaRepository<ActividadEntity, UUID> {
}
