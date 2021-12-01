package pe.gob.vuce.zee.api.maestros.repository.impl;

import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.repository.ContratoCustomRepository;
import pe.gob.vuce.zee.api.maestros.repository.ContratoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ContratoRepositoryImpl implements ContratoCustomRepository {

    @PersistenceContext
    private EntityManager em;

    private Predicate[] predicados(CriteriaBuilder cb, Root<ContratoEntity> entityRoot,String numeroContrato, Integer tipoContrato, Integer estado,
                                   Timestamp fechaInicio, Timestamp fechaFinal) {
        var predicates = new ArrayList<Predicate>();

        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicates.add(cb.like(cb.upper(entityRoot.get("numeroContrato")), "%" + numeroContrato.toUpperCase() + "%"));
        }
        if (tipoContrato != null) {
            predicates.add(cb.equal(entityRoot.get("tipoContrato"),tipoContrato));
        }
        if (estado != null) {
            predicates.add(cb.equal(entityRoot.get("estado"), estado));
        }
        if (fechaInicio != null && fechaFinal == null) {
            predicates.add(cb.equal(entityRoot.get("fechaInicial"), fechaInicio));
        }
        if (fechaFinal != null && fechaInicio == null) {
            predicates.add(cb.equal(entityRoot.get("fechaFinal"), fechaFinal));
        }
        if (fechaFinal != null && fechaInicio != null) {
            predicates.add(cb.greaterThanOrEqualTo(entityRoot.get("fechaInicial"), fechaInicio));
            predicates.add(cb.lessThanOrEqualTo(entityRoot.get("fechaFinal"), fechaFinal));
        }
        return predicates.toArray(new Predicate[0]);
    }

    @Override
    public List<ContratoEntity> busqueda(String numeroContrato, Integer tipoContrato, Integer estado,
                                         Timestamp fechaInicio, Timestamp fechaFinal, int offset, int size, String sort, String filter) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ContratoEntity.class);
        var entityRoot = cq.from(ContratoEntity.class);

        var predicates = predicados(cb, entityRoot, numeroContrato, tipoContrato, estado,
                fechaInicio, fechaFinal);
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
}
