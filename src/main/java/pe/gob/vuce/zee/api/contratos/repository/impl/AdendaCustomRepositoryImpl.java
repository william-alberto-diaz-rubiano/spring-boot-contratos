package pe.gob.vuce.zee.api.contratos.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.AdendaCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class AdendaCustomRepositoryImpl implements AdendaCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private Predicate[] predicados(CriteriaBuilder cb, Root<AdendaEntity> entityRoot,
                                   UUID usuarioId, UUID contratoId, Integer numeroAdenda,
                                   Integer activo) {
        var predicates = new ArrayList<Predicate>();

        if (usuarioId != null) {
            predicates.add(cb.equal(entityRoot.get("usuario").get("id"), usuarioId));
        }
        if (contratoId != null) {
            predicates.add(cb.equal(entityRoot.get("contrato").get("id"), contratoId));
        }
        if (numeroAdenda != null) {
            predicates.add(cb.equal(entityRoot.get("numeroAdenda"), numeroAdenda));
        }

        if (activo != null) {
            predicates.add(cb.equal(entityRoot.get("activo"), activo));
        }
        return predicates.toArray(new Predicate[0]);
    }

    @Override
    public Page<AdendaEntity> busquedaAvanzada(UUID usuarioId, UUID contratoId, Integer numeroAdenda, Integer activo, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        var resultList = busquedaAvanzada(usuarioId, contratoId, numeroAdenda, activo, offset, size);
        var total = count(usuarioId, contratoId, numeroAdenda, activo);
        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public List<AdendaEntity> busquedaAvanzada(UUID usuarioId, UUID contratoId, Integer numeroAdenda, Integer activo, int offset, int size) {
        log.info("Ingresando a busquedaAvanzada(usuarioId={},contratoId={},numeroAdenda={},offset={},size={})", usuarioId, contratoId, numeroAdenda, offset, size);
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(AdendaEntity.class);
        var entityRoot = cq.from(AdendaEntity.class);
        var predicates = this.predicados(cb, entityRoot, usuarioId, contratoId, numeroAdenda, activo);
        if(predicates.length > 0){
            cq.where(predicates);
        }
        cq.select(entityRoot);
        var result = em.createQuery(cq);

        if (offset != -1) {
            result = result.setFirstResult(offset);
        }
        if (size != -1) {
            result = result.setMaxResults(size);
        }
        var entities = result.getResultList();
        log.info("Saliendo de busquedaAvanzada - entities = {}", entities.size());
        return entities;
    }

    private Long count(UUID usuarioId, UUID contratoId, Integer numeroAdenda, Integer activo){
        log.info("Ingresando a contar(usuarioId={},contratoId={},numeroAdenda={})", usuarioId, contratoId, numeroAdenda);
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var entityRoot = cq.from(AdendaEntity.class);
        var predicates = this.predicados(cb, entityRoot, usuarioId, contratoId, numeroAdenda, activo);
        if(predicates.length > 0){
            cq.where(predicates);
        }
        cq.select(cb.count(entityRoot));
        var result = em.createQuery(cq);
        var count = result.getSingleResult();
        log.info("Saliendo de contar - resultado = {}", count);
        return count;
    }
}
