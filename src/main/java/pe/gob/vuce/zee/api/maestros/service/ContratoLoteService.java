package pe.gob.vuce.zee.api.maestros.service;

import pe.gob.vuce.zee.api.maestros.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.maestros.dto.LoteDTO;

import java.io.IOException;
import java.util.UUID;

public interface ContratoLoteService {
    LoteContratoDTO findByContrato(UUID idContrato) throws IOException;
    LoteContratoDTO findbyId(UUID idContratoLote) throws IOException;

    LoteContratoDTO deleteLoteContrato(LoteContratoDTO loteContratoDTO);

    LoteContratoDTO crearLoteContrato(LoteContratoDTO loteContratoDTO) throws IOException;
}
