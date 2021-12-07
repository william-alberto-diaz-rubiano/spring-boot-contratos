package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ContratoService {
    List<ContratoEntity> findAll();

    ContratoDTO createContrato(ContratoDTO contrato);

    Page<ContratoDTO> finByCorrelativo(String numeroContrato,
                                       UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, Timestamp fechaInicial, Timestamp fechaFinal, Pageable pageable);

    List<ContratoEntity> finByUsuario();
}