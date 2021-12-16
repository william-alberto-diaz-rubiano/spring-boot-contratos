package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;

public interface ContratoLoteService {
    LoteContratoDTO findByContrato(UUID idContrato) throws IOException;
    LoteContratoDTO findbyId(UUID idContratoLote) throws IOException;

    LoteContratoDTO deleteLoteContrato(LoteContratoDTO loteContratoDTO);

    LoteContratoDTO crearLoteContrato(LoteContratoDTO loteContratoDTO) throws IOException;

    Page<LoteContratoDTO> findAll(org.springframework.data.domain.Pageable pageable);

    Page<ContratoLoteBandejaDTO> busquedaAvanzada(String numeroContrato, UUID usuarioId, String numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, Pageable pageable);

    Page<ContratoLoteBandeja2DTO> busquedaAvanzada2(@NotNull UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, Pageable pageable);

    Page<LoteContratoDetalleDTO> detalleByContrato(UUID contratoId, Pageable pageable);

    Page<ContratoLoteMapaDTO> buscarLotesPorContrato(UUID contratoID, Pageable pageable);
}
