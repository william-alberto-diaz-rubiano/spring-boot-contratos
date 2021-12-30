package pe.gob.vuce.zee.api.contratos.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandeja2DTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContratoLoteCustomRepositoryImpl implements ContratoLoteCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Page<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID loteId, UUID contratoId, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        List<ContratoLoteBandejaDTO> resultList = busquedaAvanzada1(numeroContrato, loteId, contratoId, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica, offset, size);
        var total = contar1(numeroContrato, loteId, contratoId, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica);
        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public Page<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        List<ContratoLoteBandeja2DTO> resultList = busquedaAvanzada2(usuarioId, contratoId, adendaId, loteId, offset, size);
        var total = contar2(usuarioId, contratoId, adendaId, loteId);
        return new PageImpl<>(resultList, pageable, total);
    }

    private Predicate[] predicadosMapaAvanzado1(CriteriaBuilder cb, Root<ContratoEntity> entityRoot, String numeroContrato,
                                                UUID tipoContrato, Integer estado,
                                                UUID lote, String documento, UUID tipoDocumento,
                                                UUID usuario, UUID tipoActividad, Timestamp fechaInicial,
                                                Timestamp fechaFinal) {
        var predicates = new ArrayList<Predicate>();

        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicates.add(cb.like(cb.upper(entityRoot.get("numeroContrato")), "%" + numeroContrato.toUpperCase() + "%"));
        }
        if (tipoContrato != null) {
            predicates.add(cb.like(entityRoot.get("tipoContrato").get("descripcion"), "%" + tipoContrato + "%"));
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
    public List<LoteContratoEntity> busquedaAvanzadaMapa(String numeroContrato, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividadId, UUID actividadEconomicaId) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(LoteContratoEntity.class);
        var loteContratoFrom = cq.from(LoteContratoEntity.class);
        var contratoJoin = loteContratoFrom.join("contrato", JoinType.INNER);
        var actividadJoin = contratoJoin.join("actividad", JoinType.INNER);
        var adendaJoin = contratoJoin.join("adenda", JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();

        if(numeroContrato != null && !numeroContrato.isEmpty()){
            predicates.add(cb.like(cb.upper(contratoJoin.get("numeroContrato")), "%" + numeroContrato.toUpperCase() + "%"));
        }

        if(usuarioId != null){
            predicates.add(cb.equal(contratoJoin.get("usuario").get("id"), usuarioId));
        }

        if(numeroAdenda != null){
            predicates.add(cb.equal(adendaJoin.get("numeroAdenda"), numeroAdenda));
        }

        if(numeroLote != null && !numeroLote.isEmpty()){
            predicates.add(cb.like(cb.upper(loteContratoFrom.get("lote").get("nombre")), "%" + numeroLote.toUpperCase() + "%"));
        }

        if(tipoActividadId != null){
            predicates.add(cb.equal(actividadJoin.get("tipoActividadEconomica").get("id"), tipoActividadId));
        }

        if(actividadEconomicaId != null){
            predicates.add(cb.equal(actividadJoin.get("actividad").get("id"), actividadEconomicaId));
        }

        var predicatesArray = predicates.toArray(new Predicate[0]);

        cq.select(loteContratoFrom).distinct(true);
        if (!predicates.isEmpty()) {
            cq.where(predicatesArray);
        }

        var query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, int offset, int size) {
        var sqlTemplate = "SELECT " +
                "CAST(contrato.vecr_ctrt_idllave_pk AS VARCHAR) id, " +
                "MIN(tipo_contrato.vems_gcon_descripcin) AS contrato_tipo, " +
                "MIN(contrato.vecr_ctrt_cod_contra) AS contrato_numero, " +
                "(SELECT COUNT(adenda.vead_aden_idllave_pk) FROM vuce_zee.vead_aden adenda WHERE adenda.vead_aden_id_cont_fk = contrato.vecr_ctrt_idllave_pk) adendas_cantidad, " +
                "COUNT(lote_contrato.vecr_lote_codg_lotes) AS lote_cantidad, " +
                "SUM(lote_contrato.vecr_lote_mont_costo) AS lote_costo, " +
                "SUM(lote_contrato.vecr_lote_mont_taman) AS lote_tamanio " +
                "FROM vuce_zee.vecr_ctrt contrato " +
                "INNER JOIN vuce_zee.vems_gcon tipo_contrato ON contrato.vecr_ctrt_id_tipo_cn = tipo_contrato.vems_gcon_idllave_pk " +
                "INNER JOIN vuce_zee.vepr_pers usuario ON contrato.vecr_ctrt_id_usuario = usuario.vepr_pers_idllave_pk " +
                "LEFT JOIN vuce_zee.vecr_lote lote_contrato ON lote_contrato.vecr_lote_id_cont_fk = contrato.vecr_ctrt_idllave_pk " +
                "LEFT JOIN vuce_zee.velt_clot lote ON lote.velt_clot_idllave_pk = lote_contrato.vecr_lote_codg_lotes " +
                "WHERE " +
                "contrato.vecr_ctrt_cod_active != 9 AND lote_contrato.vecr_lote_cod_active != 9 %s " +
                "GROUP BY contrato.vecr_ctrt_idllave_pk ";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        predicados.add("CAST(contrato.vecr_ctrt_id_usuario AS VARCHAR) = :usuarioId");
        parametros.put("usuarioId", usuarioId.toString());
        if (contratoId != null) {
            predicados.add("CAST(contrato.vecr_ctrt_idllave_pk AS VARCHAR) = :contratoId");
            parametros.put("contratoId", contratoId.toString());
        }
        if (adendaId != null) {
            predicados.add("contrato.vecr_ctrt_idllave_pk IN (SELECT DISTINCT(adenda2.vead_aden_id_cont_fk) FROM vuce_zee.vead_aden adenda2 WHERE CAST(adenda2.vead_aden_idllave_pk AS VARCHAR) = :adendaId)");
            parametros.put("adendaId", adendaId.toString());
        }
        if (loteId != null) {
            predicados.add("CAST(lote.velt_clot_idllave_pk AS VARCHAR) = :loteId");
            parametros.put("loteId", loteId.toString());
        }

        var where = "";
        if (!predicados.isEmpty()) {
            where = "AND " + String.join(" AND ", predicados);
        }

        var sql = String.format(sqlTemplate, where);

        var nativeQuery = entityManager.createNativeQuery(sql);

        if (offset != -1) {
            nativeQuery.setFirstResult(offset);
        }

        if (size != -1) {
            nativeQuery.setMaxResults(size);
        }

        parametros.forEach(nativeQuery::setParameter);

        List<Object[]> result = nativeQuery.getResultList();

        return result.stream()
                .map(x ->
                        ContratoLoteBandeja2DTO.builder()
                                .id(UUID.fromString(x[0].toString()))
                                .contratoTipo(x[1].toString())
                                .contratoNumero(x[2].toString())
                                .cantidadAdendas(Integer.parseInt(x[3].toString()))
                                .loteCantidad(Integer.parseInt(x[4].toString()))
                                .costo(Optional.ofNullable(x[5]).map(Objects::toString).map(BigDecimal::new).orElse(null))
                                .tamanio(Optional.ofNullable(x[6]).map(Objects::toString).map(BigDecimal::new).orElse(null))
                                .build())
                .collect(Collectors.toList());
    }


    @Override
    public List<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID loteId, UUID contratoId, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, int offset, int size) {
        var sqlTemplate = "SELECT " +
                "       CAST(contrato.vecr_ctrt_idllave_pk AS VARCHAR) as id, " +
                "       contrato.vecr_ctrt_cod_contra as contrato_numero, " +
                "       CAST(contrato.vecr_ctrt_id_usuario AS VARCHAR) as usuario_id, " +
                "       TRIM(CONCAT(usuario.vepr_pers_apellido_p, ' ', usuario.vepr_pers_apellido_m, ' ', usuario.vepr_pers_desnombres)) usuario_nombre, " +
                "       (SELECT COUNT(adenda.vead_aden_idllave_pk) FROM vuce_zee.vead_aden adenda WHERE adenda.vead_aden_id_cont_fk = contrato.vecr_ctrt_idllave_pk) cantidad_adendas, " +
                "       (SELECT COUNT(contrato_lote.vecr_lote_idllave_pk) FROM vuce_zee.vecr_lote contrato_lote WHERE contrato_lote.vecr_lote_id_cont_fk = contrato.vecr_ctrt_idllave_pk) cantidad_lotes, " +
                "       (SELECT COUNT(actividad.vecr_actv_idllave_pk) FROM vuce_zee.vecr_actv actividad WHERE actividad.vecr_actv_id_cont_fk = contrato.vecr_ctrt_idllave_pk) cantidad_actividades_economicas, " +
                "       (SELECT COUNT(DISTINCT(actividad.vecr_actv_id_acti_fk)) FROM vuce_zee.vecr_actv actividad WHERE actividad.vecr_actv_id_cont_fk = contrato.vecr_ctrt_idllave_pk) cantidad_tipo_actividades_economicas, " +
                "       (SELECT COUNT(DISTINCT(actividad.vecr_actv_id_alma_fk)) FROM vuce_zee.vecr_actv actividad WHERE actividad.vecr_actv_id_cont_fk = contrato.vecr_ctrt_idllave_pk) cantidad_almacenes " +
                "FROM vuce_zee.vecr_ctrt contrato LEFT JOIN vuce_zee.vepr_pers usuario ON contrato.vecr_ctrt_id_usuario = usuario.vepr_pers_idllave_pk " +
                "WHERE contrato.vecr_ctrt_idllave_pk IN ( " +
                "    SELECT DISTINCT(contrato2.vecr_ctrt_idllave_pk) " +
                "    FROM vuce_zee.vecr_ctrt contrato2 " +
                "             LEFT JOIN vuce_zee.vecr_lote contrato_lote2 ON contrato_lote2.vecr_lote_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vecr_actv actividad2 ON actividad2.vecr_actv_id_cont_fk = contrato2.vecr_ctrt_idllave_pk" +
                "             LEFT JOIN vuce_zee.velt_clot lote ON contrato_lote2.vecr_lote_codg_lotes = lote.velt_clot_idllave_pk " +
                "             LEFT JOIN vuce_zee.vead_aden adenda2 ON adenda2.vead_aden_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vepr_pers usuario2 ON contrato2.vecr_ctrt_id_usuario = usuario2.vepr_pers_idllave_pk " +
                "    %s" +
                ")";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicados.add(" contrato2.vecr_ctrt_cod_contra LIKE CONCAT('%',:numeroContrato,'%')");
            parametros.put("numeroContrato", numeroContrato);
        }
        if (contratoId != null) {
            predicados.add(" contrato2.vecr_ctrt_idllave_pk = :contratoId");
            parametros.put("contratoId", contratoId);
        }
        if (loteId != null) {
            predicados.add(" lote.velt_clot_idllave_pk = :loteId");
            parametros.put("loteId", loteId);
        }
        if (numeroLote != null && !numeroLote.isEmpty()) {
            predicados.add(" lote.velt_clot_nombre_lot LIKE CONCAT('%',:numeroLote,'%')");
            parametros.put("numeroLote", numeroLote);
        }
        if (usuarioId != null) {
            predicados.add(" contrato2.vecr_ctrt_id_usuario = :usuarioId");
            parametros.put("usuarioId", usuarioId);
        }
        if (tipoActividad != null) {
            predicados.add(" actividad2.vecr_actv_idllave_pk = :tipoActividad");
            parametros.put("tipoActividad", tipoActividad);
        }
        if (actividadEconomica != null) {
            predicados.add(" actividad2.vecr_actv_id_acti_fk = :actividadEconomica");
            parametros.put("actividadEconomica", actividadEconomica);
        }
        if (numeroAdenda != null) {
            predicados.add(" adenda2.vead_aden_numeroaden = :numeroAdenda");
            parametros.put("numeroAdenda", numeroAdenda);
        }

        var where = "";
        if (!predicados.isEmpty()) {
            where = String.format(" WHERE %s", String.join(" AND ", predicados));
        }
        var sql = String.format(sqlTemplate, where);

        var nativeQuery = entityManager.createNativeQuery(sql);

        if (offset != -1) {
            nativeQuery.setFirstResult(offset);
        }

        if (size != -1) {
            nativeQuery.setMaxResults(size);
        }

        parametros.forEach(nativeQuery::setParameter);

        List<Object[]> result = nativeQuery.getResultList();

        return result.stream()
                .map(x ->
                        ContratoLoteBandejaDTO.builder()
                                .id(UUID.fromString(x[0].toString()))
                                .contratoNumero(x[1].toString())
                                .usuarioId(x[2] == null ? null : UUID.fromString(x[2].toString()))
                                .usuarioNombre(x[3] == null ? null : x[3].toString())
                                .cantidadAdendas(Integer.parseInt(x[4].toString()))
                                .cantidadLotes(Integer.parseInt(x[5].toString()))
                                .cantidadActividadesEconomicas(Integer.parseInt(x[6].toString()))
                                .cantidadTipoActividadesEconomicas(Integer.parseInt(x[7].toString()))
                                .cantidadAlmacenes(Integer.parseInt(x[8].toString()))
                                .build())
                .collect(Collectors.toList());

    }


    private Integer contar1(String numeroContrato, UUID loteId, UUID contratoId, UUID usuarioId, Integer numeroAdenda,
                            String numeroLote, UUID tipoActividad, UUID actividadEconomica) {
        var sqlTemplate = "SELECT COUNT(DISTINCT(contrato2.vecr_ctrt_idllave_pk)) " +
                "    FROM vuce_zee.vecr_ctrt contrato2 " +
                "             LEFT JOIN vuce_zee.vecr_lote contrato_lote2 ON contrato_lote2.vecr_lote_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vecr_actv actividad2 ON actividad2.vecr_actv_id_cont_fk = contrato2.vecr_ctrt_idllave_pk" +
                "             LEFT JOIN vuce_zee.velt_clot lote ON contrato_lote2.vecr_lote_codg_lotes = lote.velt_clot_idllave_pk " +
                "             LEFT JOIN vuce_zee.vead_aden adenda2 ON adenda2.vead_aden_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vepr_pers usuario2 ON contrato2.vecr_ctrt_id_usuario = usuario2.vepr_pers_idllave_pk " +
                "    %s";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicados.add(" contrato2.vecr_ctrt_cod_contra LIKE CONCAT('%',:numeroContrato,'%')");
            parametros.put("numeroContrato", numeroContrato);
        }
        if (loteId != null) {
            predicados.add(" lote.velt_clot_idllave_pk = :loteId");
            parametros.put("loteId", loteId);
        }
        if (numeroLote != null && !numeroLote.isEmpty()) {
            predicados.add(" lote.velt_clot_nombre_lot LIKE CONCAT('%',:numeroLote,'%')");
            parametros.put("numeroLote", numeroLote);
        }
        if (contratoId != null) {
            predicados.add(" contrato2.vecr_ctrt_idllave_pk = :contratoId");
            parametros.put("contratoId", contratoId);
        }
        if (usuarioId != null) {
            predicados.add(" contrato2.vecr_ctrt_id_usuario = :usuarioId");
            parametros.put("usuarioId", usuarioId);
        }
        if (tipoActividad != null) {
            predicados.add(" actividad2.vecr_actv_idllave_pk = :tipoActividad");
            parametros.put("tipoActividad", tipoActividad);
        }
        if (actividadEconomica != null) {
            predicados.add(" actividad2.vecr_actv_id_acti_fk = :actividadEconomica");
            parametros.put("actividadEconomica", actividadEconomica);
        }
        if (numeroAdenda != null) {
            predicados.add(" adenda2.vead_aden_numeroaden = :numeroAdenda");
            parametros.put("numeroAdenda", numeroAdenda);
        }


        var where = "";
        if (!predicados.isEmpty()) {
            where = String.format(" WHERE %s", String.join(" AND ", predicados));
        }
        var sql = String.format(sqlTemplate, where);
        var nativeQuery = entityManager.createNativeQuery(sql);

        parametros.forEach(nativeQuery::setParameter);

        return Integer.parseInt(nativeQuery.getSingleResult().toString());
    }

    private Integer contar2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId) {
        var sqlTemplate = "SELECT COUNT(*) " +
                "FROM vuce_zee.vecr_ctrt contrato " +
                "INNER JOIN vuce_zee.vems_gcon tipo_contrato ON contrato.vecr_ctrt_id_tipo_cn = tipo_contrato.vems_gcon_idllave_pk " +
                "INNER JOIN vuce_zee.vepr_pers usuario ON contrato.vecr_ctrt_id_usuario = usuario.vepr_pers_idllave_pk " +
                "LEFT JOIN vuce_zee.vecr_lote lote_contrato ON lote_contrato.vecr_lote_id_cont_fk = contrato.vecr_ctrt_idllave_pk " +
                "LEFT JOIN vuce_zee.velt_clot lote ON lote.velt_clot_idllave_pk = lote_contrato.vecr_lote_codg_lotes " +
                "WHERE " +
                "      contrato.vecr_ctrt_cod_active != 9 %s ";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        if (usuarioId != null) {
            predicados.add("contrato.vecr_ctrt_id_usuario = :usuarioId");
            parametros.put("usuarioId", usuarioId);
        }
        if (contratoId != null) {
            predicados.add("contrato.vecr_ctrt_idllave_pk = :contratoId");
            parametros.put("contratoId", contratoId);
        }
        if (adendaId != null) {
            predicados.add("contrato.vecr_ctrt_idllave_pk IN (SELECT DISTINCT(adenda2.vead_aden_id_cont_fk) FROM vuce_zee.vead_aden adenda2 WHERE adenda2.vead_aden_idllave_pk = :adendaId)");
            parametros.put("adendaId", adendaId);
        }
        if (loteId != null) {
            predicados.add("lote.velt_clot_idllave_pk = :loteId");
            parametros.put("loteId", loteId);
        }

        var where = "";
        if (!predicados.isEmpty()) {
            where = "AND " + String.join(" AND ", predicados);
        }

        var sql = String.format(sqlTemplate, where);

        var nativeQuery = entityManager.createNativeQuery(sql);

        parametros.forEach(nativeQuery::setParameter);

        return Integer.parseInt(nativeQuery.getSingleResult().toString());
    }
}
