package pe.gob.vuce.zee.api.contratos.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ContratoCustomRepositoryImpl implements ContratoCustomRepository {


    @PersistenceContext
    private EntityManager em;


    @Override
    public List<ContratoEntity> busqueda(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, String nombreUsuario, UUID usuario, UUID tipoActividad,LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return busqueda(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario,usuario,tipoActividad,fechaInicial,fechaFinal,-1,-1);
    }

    @Override
    public List<ContratoEntity> busqueda(UUID id,String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal, int offset, int size) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ContratoEntity.class);
        var root = cq.from(ContratoEntity.class);
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if(numeroContrato != null){
            Expression<String> numeroContratoExp = root.get("numeroContrato");
            numeroContratoExp = cb.upper(numeroContratoExp);
            predicates.add(cb.like(numeroContratoExp, String.join("", "%", numeroContrato.toUpperCase(), "%") ));
        }
        if(tipoContrato != null){
            predicates.add(cb.equal(root.get("tipoContrato").get("id"), tipoContrato));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"),estado));
        }
        if(lote != null){

        }
        if (documento != null) {
            Expression<String> numeroContratoExp = cb.upper(root.get("documento"));
            predicates.add(cb.like(numeroContratoExp, String.join("", "%", documento.toUpperCase(), "%")));
        }
        if (tipoDocumento != null) {
            predicates.add(cb.equal(root.get("usuario").get("tipoDocumento").get("id"), tipoDocumento));
        }

        if(nombreUsuario != null){
            Expression<String> usuarioNombreExpr = cb.coalesce(root.get("usuario").get("nombre"), "");
            Expression<String> usuarioApellidoPaternoExpr = cb.coalesce(root.get("usuario").get("apellidoP"), "");
            Expression<String> usuarioApellidoMaternoExpr = cb.coalesce(root.get("usuario").get("apellidoM"), "");

            Expression<String>
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreExpr, " ");
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, usuarioApellidoPaternoExpr);
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, " ");
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, usuarioApellidoMaternoExpr);
            usuarioNombreCompletoExpr = cb.upper(usuarioNombreCompletoExpr);

            predicates.add(cb.like(usuarioNombreCompletoExpr, "%" + nombreUsuario.toUpperCase() + "%"));

            //predicates.add(cb.equal(root.get("usuario").get("nombre"), nombreUsuario));
        }
        if(usuario != null){
            predicates.add(cb.equal(root.get("usuario").get("id"), usuario));
        }
        if(tipoActividad != null){


        }
        if (!((fechaInicial == null) && (fechaFinal == null))
        ) {
            predicates.add(cb.between(root.get("fechaInicial"), fechaInicial, fechaFinal));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }
        cq.orderBy(cb.desc(root.get("fechaCreacion")));

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
    public Page<ContratoEntity> busquedaPageable(UUID id,String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var resultList =busqueda(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario,usuario,tipoActividad,fechaInicial,fechaFinal,offset,pageable.getPageSize());
        var count =contar(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal);
        return new PageImpl<>(resultList, pageable, count);

    }

    @Override
    public Long contar(UUID id,String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(ContratoEntity.class);

        cq.select(cb.count(root));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();
        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if(numeroContrato != null){
            Expression<String> numeroContratoExp = root.get("numeroContrato");
            numeroContratoExp = cb.upper(numeroContratoExp);
            predicates.add(cb.like(numeroContratoExp, String.join("", "%", numeroContrato.toUpperCase(), "%") ));
        }
        if(tipoContrato != null){
            predicates.add(cb.equal(root.get("tipoContrato").get("id"), tipoContrato));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"),estado));
        }
        if(lote != null){

        }
        if (documento != null) {
            Expression<String> numeroContratoExp = cb.upper(root.get("documento"));
            predicates.add(cb.like(numeroContratoExp, String.join("", "%", documento.toUpperCase(), "%")));
        }
        if (tipoDocumento != null) {
            predicates.add(cb.equal(root.get("usuario").get("tipoDocumento").get("id"), tipoDocumento));
        }
        if(usuario != null){
            predicates.add(cb.equal(root.get("usuario").get("id"), usuario));
        }
        if(nombreUsuario != null){
            Expression<String> usuarioNombreExpr = cb.coalesce(root.get("usuario").get("nombre"), "");
            Expression<String> usuarioApellidoPaternoExpr = cb.coalesce(root.get("usuario").get("apellidoP"), "");
            Expression<String> usuarioApellidoMaternoExpr = cb.coalesce(root.get("usuario").get("apellidoM"), "");

            Expression<String>
                    usuarioNombreCompletoExpr = cb.concat(usuarioNombreExpr, " ");
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, usuarioApellidoPaternoExpr);
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, " ");
            usuarioNombreCompletoExpr = cb.concat(usuarioNombreCompletoExpr, usuarioApellidoMaternoExpr);
            usuarioNombreCompletoExpr = cb.upper(usuarioNombreCompletoExpr);

            predicates.add(cb.like(usuarioNombreCompletoExpr, "%" + nombreUsuario.toUpperCase() + "%"));
        }
        if(tipoActividad != null){

        }
        if (!((fechaInicial == null) && (fechaFinal == null))
        ) {
            predicates.add(cb.between(root.get("fechaInicial"), fechaInicial, fechaFinal));
        }

        predicatesArray = predicates.toArray(new Predicate[0]);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }

        var query = em.createQuery(cq);
        return query.getSingleResult();
    }
}
