package pe.gob.vuce.zee.api.contratos.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ContratoLoteCustomRepositoryImpl implements ContratoLoteCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Page<ContratoLoteBandejaDTO> busquedaAvanzada(String numeroContrato, UUID usuarioId, String numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, Pageable pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        List<ContratoLoteBandejaDTO> resultList = busquedaAvanzada(numeroContrato, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica, offset, size);
        var total = contar(numeroContrato, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica);
        return new PageImpl<>(resultList, pageable, total);
    }

    private List<ContratoLoteBandejaDTO> busquedaAvanzada(String numeroContrato, UUID usuarioId, String numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, int offset, int size) {
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
                "             INNER JOIN vuce_zee.vecr_lote contrato_lote2 ON contrato_lote2.vecr_lote_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             INNER JOIN vuce_zee.vecr_actv actividad2 ON actividad2.vecr_actv_id_cont_fk = contrato2.vecr_ctrt_idllave_pk" +
                "             INNER JOIN vuce_zee.velt_clot lote ON contrato_lote2.vecr_lote_codg_lotes = lote.velt_clot_idllave_pk " +
                "             LEFT JOIN vuce_zee.vead_aden adenda2 ON adenda2.vead_aden_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vepr_pers usuario2 ON contrato2.vecr_ctrt_id_usuario = usuario2.vepr_pers_idllave_pk " +
                "    %s" +
                ")";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicados.add(" contrato2.vecr_ctrt_cod_contra LIKE CONCAT('%s',:numeroContrato,'%s')");
            parametros.put("numeroContrato", numeroContrato);
        }
        if (numeroLote != null && !numeroLote.isEmpty()) {
            predicados.add(" lote.velt_clot_nombre_lot LIKE CONCAT('%s',:numeroLote,'%s')");
            parametros.put("numeroLote", numeroLote);
        }
        if (usuarioId != null) {
            predicados.add(" contrato2.vecr_ctrt_id_usuario = :usuarioId");
            parametros.put("usuarioid", usuarioId);
        }
        if (tipoActividad != null) {
            predicados.add(" actividad2.vecr_actv_idllave_pk = :tipoActividad");
            parametros.put("tipoActividad", tipoActividad);
        }
        if (actividadEconomica != null) {
            predicados.add(" actividad2.vecr_actv_id_acti_fk = :actividadEconomica");
            parametros.put("actividadEconomica", actividadEconomica);
        }
        if (numeroAdenda != null && !numeroAdenda.isEmpty()) {
            predicados.add(" adenda2.vead_aden_numeroaden LIKE CONCAT('%s',:numeroAdenda,'%s')");
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


    private Integer contar(String numeroContrato, UUID usuarioId, String numeroAdenda,
                           String numeroLote, UUID tipoActividad, UUID actividadEconomica) {
        var sqlTemplate = "SELECT COUNT(DISTINCT(contrato2.vecr_ctrt_idllave_pk)) " +
                "    FROM vuce_zee.vecr_ctrt contrato2 " +
                "             INNER JOIN vuce_zee.vecr_lote contrato_lote2 ON contrato_lote2.vecr_lote_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             INNER JOIN vuce_zee.vecr_actv actividad2 ON actividad2.vecr_actv_id_cont_fk = contrato2.vecr_ctrt_idllave_pk" +
                "             INNER JOIN vuce_zee.velt_clot lote ON contrato_lote2.vecr_lote_codg_lotes = lote.velt_clot_idllave_pk " +
                "             LEFT JOIN vuce_zee.vead_aden adenda2 ON adenda2.vead_aden_id_cont_fk = contrato2.vecr_ctrt_idllave_pk " +
                "             LEFT JOIN vuce_zee.vepr_pers usuario2 ON contrato2.vecr_ctrt_id_usuario = usuario2.vepr_pers_idllave_pk " +
                "    %s";
        var predicados = new ArrayList<String>();
        var parametros = new HashMap<String, Object>();
        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            predicados.add(" contrato2.vecr_ctrt_cod_contra LIKE CONCAT('%s',:numeroContrato,'%s')");
            parametros.put("numeroContrato", numeroContrato);
        }
        if (numeroLote != null && !numeroLote.isEmpty()) {
            predicados.add(" lote.velt_clot_nombre_lot LIKE CONCAT('%s',:numeroLote,'%s')");
            parametros.put("numeroLote", numeroLote);
        }
        if (usuarioId != null) {
            predicados.add(" contrato2.vecr_ctrt_id_usuario = :usuarioId");
            parametros.put("usuarioid", usuarioId);
        }
        if (tipoActividad != null) {
            predicados.add(" actividad2.vecr_actv_idllave_pk = :tipoActividad");
            parametros.put("tipoActividad", tipoActividad);
        }
        if (actividadEconomica != null) {
            predicados.add(" actividad2.vecr_actv_id_acti_fk = :actividadEconomica");
            parametros.put("actividadEconomica", actividadEconomica);
        }
        if (numeroAdenda != null && !numeroAdenda.isEmpty()) {
            predicados.add(" adenda2.vead_aden_numeroaden LIKE CONCAT('%s',:numeroAdenda,'%s')");
            parametros.put("numeroAdenda", numeroAdenda);
        }


        var where = "";
        if (!predicados.isEmpty()) {
            where = String.format(" WHERE %s", String.join(" AND ", predicados));
        }
        var sql = String.format(sqlTemplate, where);
        var nativeQuery = entityManager.createNativeQuery(sql);

        parametros.forEach(nativeQuery::setParameter);

        return nativeQuery.getFirstResult();
    }
}
