package pe.gob.vuce.zee.api.maestros.service;

import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;

import java.util.List;

public interface ContratoService {
    List<ContratoEntity> findAll();
    ContratoEntity createContrato(ContratoEntity contrato);

    List<ContratoEntity> finByClienteId(Integer clientId);

    List<ContratoEntity> finByCorrelativo();

    List<ContratoEntity> finByUsuario();
}
