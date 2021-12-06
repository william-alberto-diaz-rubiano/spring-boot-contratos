package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;

import java.util.List;
import java.util.UUID;

public interface ContratoLoteRepository extends JpaRepository<LoteContratoEntity, UUID> {

    @Query(value = "select vl.* from vecr_lote vl \n" +
            "where vl.vecr_lote_id_cont_fk = :idContrato ", nativeQuery = true)
    List<LoteContratoEntity> finByContrato(@Param("idContrato") UUID idContrato);
}
