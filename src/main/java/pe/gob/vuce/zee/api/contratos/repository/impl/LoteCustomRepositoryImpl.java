package pe.gob.vuce.zee.api.contratos.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandeja2DTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository("loteCustomRepositoryImpl")
public interface LoteCustomRepositoryImpl extends CrudRepository<LoteEntity, UUID>,
                                                    JpaRepository<LoteEntity, UUID>,
                                                    PagingAndSortingRepository<LoteEntity, UUID>,
                                                    JpaSpecificationExecutor<LoteEntity>
{

}
