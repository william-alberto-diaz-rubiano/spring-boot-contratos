package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;

import java.util.List;
import java.util.UUID;

public interface AdendaService {
    AdendaEntity findByContrato(UUID idContrato);

    AdendaEntity saveAdenda(AdendaEntity adendaEntity);

    AdendaEntity actualizarAdenda(AdendaEntity adendaEntity);

    List<AdendaEntity> getAllAdendas();

    AdendaEntity getAdendaById(UUID idAdenda);
}
