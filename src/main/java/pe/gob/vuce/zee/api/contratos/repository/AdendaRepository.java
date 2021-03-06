package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;

import java.util.UUID;

@Repository
public interface AdendaRepository extends JpaRepository<AdendaEntity, UUID>, AdendaCustomRepository {
}
