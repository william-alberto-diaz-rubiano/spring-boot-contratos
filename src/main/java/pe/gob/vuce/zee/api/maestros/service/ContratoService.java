package pe.gob.vuce.zee.api.maestros.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ContratoService {
    List<ContratoEntity> findAll();
    ContratoEntity createContrato(ContratoEntity contrato);

    List<ContratoEntity> finByClienteId(Integer clientId);

    Page<ContratoEntity> finByCorrelativo(String numeroContrato, Integer tipoContrato, Integer estado,
                                          Timestamp fechaInicio, Timestamp fechaFinal, Pageable pageable);

    List<ContratoEntity> finByUsuario();
}
