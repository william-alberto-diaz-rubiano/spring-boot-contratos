package pe.gob.vuce.zee.api.maestros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.maestros.models.AdendaEntity;

import java.util.UUID;

@Repository
public interface AdendaRepository extends JpaRepository<AdendaEntity, UUID> {
}
