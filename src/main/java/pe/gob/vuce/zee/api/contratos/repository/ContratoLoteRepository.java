package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;

import java.util.List;
import java.util.UUID;

public interface ContratoLoteRepository extends JpaRepository<LoteContratoEntity, UUID>, ContratoLoteCustomRepository {

    @Query(value = "select vl.* from vecr_lote vl \n" +
            "where vl.vecr_lote_id_cont_fk = :idContrato ", nativeQuery = true)
    List<LoteContratoEntity> finByContrato(@Param("idContrato") UUID idContrato);

    @Query("SELECT lc FROM LoteContratoEntity lc WHERE lc.contrato.id = ?1 AND lc.activo = ?2 ORDER BY lc.lote.nombre")
    Page<LoteContratoEntity> findByContratoAndActivo(UUID contratoId, Integer activo, Pageable pageable);

}
