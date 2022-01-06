package pe.gob.vuce.zee.api.contratos.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoFilterDTO;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;

import java.util.List;
import java.util.UUID;

@Repository("loteContratoCustomRepositoryImpl")
public interface LoteContratoCustomRepositoryImpl extends CrudRepository<LoteContratoEntity, UUID>,
                                                    JpaRepository<LoteContratoEntity, UUID>,
                                                    PagingAndSortingRepository<LoteContratoEntity, UUID>,
                                                    JpaSpecificationExecutor<LoteContratoEntity>
{
    @Query("SELECT new pe.gob.vuce.zee.api.contratos.dto.LoteContratoFilterDTO(lc.id, c.id, l.id, l.nombre) " +
            "FROM LoteContratoEntity lc JOIN ContratoEntity c ON lc.contrato.id = c.id " +
            "JOIN LoteEntity l ON lc.lote.id = l.id")
    List<LoteContratoFilterDTO> getLoteContratoFilterDTO();
}
