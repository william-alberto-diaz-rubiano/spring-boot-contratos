package pe.gob.vuce.zee.api.contratos.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContratoRepositoryImpl implements ContratoCustomRepository {

    @PersistenceContext
    private EntityManager em;

    private Predicate[] predicados(CriteriaBuilder cb, Root<ContratoEntity> entityRoot,String numeroContrato,
                                   UUID tipoContrato, Integer estado,
                                   UUID lote,String documento,UUID tipoDocumento,
                                   UUID usuario,UUID tipoActividad, Timestamp fechaInicial,
                                   Timestamp fechaFinal) {
        var predicates = new ArrayList<Predicate>();

        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicates.add(cb.like(cb.upper(entityRoot.get("numeroContrato")), "%" + numeroContrato.toUpperCase() + "%"));
        }
        if (tipoContrato != null) {
            predicates.add(cb.like(entityRoot.get("tipoContrato").get("descripcion"),"%"+tipoContrato+"%"));
        }
        if (estado != null) {
            predicates.add(cb.equal(entityRoot.get("estado"), estado));
        }
        if (usuario != null) {
            predicates.add(cb.equal(entityRoot.get("usuarioCreacion").get("id"), usuario));
        }
        if (fechaInicial != null && fechaFinal == null) {
            predicates.add(cb.greaterThanOrEqualTo(entityRoot.get("fechaInicial"), fechaInicial));
        }
        if (fechaFinal != null && fechaInicial == null) {
            predicates.add(cb.lessThanOrEqualTo(entityRoot.get("fechaVencimiento"), fechaFinal));
        }
        if (fechaFinal != null && fechaInicial != null) {
            predicates.add(cb.greaterThanOrEqualTo(entityRoot.get("fechaInicial"), fechaInicial));
            predicates.add(cb.lessThanOrEqualTo(entityRoot.get("fechaVencimiento"), fechaFinal));
        }
        return predicates.toArray(new Predicate[0]);
    }

    @Override
    public Page<ContratoEntity> busquedaPageable(String numeroContrato,
                                                 UUID tipoContrato, Integer estado,
                                                 UUID lote, String documento,
                                                 UUID tipoDocumento, UUID usuario,
                                                 UUID tipoActividad, Timestamp fechaInicial,
                                                 Timestamp fechaFinal, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList = busqueda(numeroContrato,
                tipoContrato, estado,lote,documento,tipoDocumento,usuario,tipoActividad,
                fechaInicial,fechaFinal,offset, pageable.getPageSize(), null, null);
        var count = contar(numeroContrato,
                tipoContrato, estado,
                lote,documento,tipoDocumento,
                usuario, tipoActividad,fechaInicial,
                fechaFinal);
        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public List<ContratoEntity> busqueda(String numeroContrato,
                                         UUID tipoContrato, Integer estado,
                                         UUID lote,String documento,UUID tipoDocumento,
                                         UUID usuario,UUID tipoActividad, Timestamp fechaInicial,
                                         Timestamp fechaFinal, int
                                                     offset, int size, String sort, String filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ContratoEntity.class);
        var entityRoot = cq.from(ContratoEntity.class);

        var predicates = predicados(cb, entityRoot,numeroContrato,
                tipoContrato, estado,lote,documento,tipoDocumento,usuario,tipoActividad,
                fechaInicial,fechaFinal);
        if (predicates.length > 0) {
            cq.where(predicates);
        }
        cq.select(entityRoot).distinct(true);
//        if (sort != null) {
//            var sortValues = sort.split(" ");
//            Expression<?> sortExpression;
//            switch (sortValues[0]) {
//                case "etapa":
//                    sortExpression = entityRoot.get("etapa").get("nombre");
//                    break;
//                case "manzana":
//                    sortExpression = entityRoot.get("manzana").get("nombre");
//                    break;
//                case "bloque":
//                    sortExpression = loteBloqueJoin.get("bloque").get("nombre");
//                    break;
//                case "costo":
//                    sortExpression = entityRoot.get("precio");
//                    break;
//                case "tamanio":
//                    sortExpression = entityRoot.get("area");
//                    break;
//                case "estado":
//                    sortExpression = entityRoot.get("estado");
//                    break;
//                default:
//                    sortExpression = entityRoot.get("nombre");
//                    break;
//            }
//            if (sortValues[1].equals("desc")) {
//                cq.orderBy(cb.desc(sortExpression));
//            } else {
//                cq.orderBy(cb.asc(sortExpression));
//            }
//        }
        var result = em.createQuery(cq);

        if (offset != -1) {
            result = result.setFirstResult(offset);
        }
        if (size != -1) {
            result = result.setMaxResults(size);
        }
        return result.getResultList();
    }

    @Override
    public Long contar(String numeroContrato,
                       UUID tipoContrato, Integer estado,
                       UUID lote,String documento,UUID tipoDocumento,
                       UUID usuario,UUID tipoActividad, Timestamp fechaInicial,
                       Timestamp fechaFinal) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var entityRoot = cq.from(ContratoEntity.class);
        cq.select(cb.count(entityRoot));
        var predicates = predicados(cb, entityRoot,numeroContrato,tipoContrato, estado,lote,documento,tipoDocumento,usuario,tipoActividad,
                fechaInicial,fechaFinal);
        if (predicates.length > 0) {
            cq.where(predicates);
        }
        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
