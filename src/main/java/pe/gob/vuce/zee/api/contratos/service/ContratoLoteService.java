package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoDTO;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ContratoLoteService {
    LoteContratoDTO findByContrato(UUID idContrato) throws IOException;
    LoteContratoDTO findbyId(UUID idContratoLote) throws IOException;

    LoteContratoDTO deleteLoteContrato(LoteContratoDTO loteContratoDTO);

    LoteContratoDTO crearLoteContrato(LoteContratoDTO loteContratoDTO) throws IOException;

    Page<LoteContratoDTO> findAll(org.springframework.data.domain.Pageable pageable);
}
