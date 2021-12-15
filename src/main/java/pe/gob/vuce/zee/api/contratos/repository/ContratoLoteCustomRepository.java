package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandeja2DTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;

import java.util.List;
import java.util.UUID;

public interface ContratoLoteCustomRepository {
    Page<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID usuarioId, String numeroAdenda,
                                                   String numeroLote, UUID tipoActividad, UUID actividadEconomica,
                                                   Pageable pageable);
    Page<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, Pageable pageable);
}
