package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;

import java.util.UUID;

public interface LoteRepository extends JpaRepository<LoteEntity, UUID> {
    @Query("SELECT DISTINCT(lc.lote) FROM LoteContratoEntity lc WHERE lc.contrato.id = ?1 AND lc.activo = ?2")
    Page<LoteEntity> findLoteByContratoAndActivo(UUID contratoId, Integer activo, Pageable pageable);
}
