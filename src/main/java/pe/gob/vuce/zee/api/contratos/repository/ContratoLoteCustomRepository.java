package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandeja2DTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;

import java.util.List;
import java.util.UUID;

public interface ContratoLoteCustomRepository {
    Page<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID contratoId, UUID usuarioId, Integer numeroAdenda,
                                                   String numeroLote, UUID tipoActividad, UUID actividadEconomica,
                                                   Pageable pageable);

    Page<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, Pageable pageable);

    List<LoteContratoEntity> busquedaAvanzadaMapa(String numeroContrato, UUID usuarioId, Integer numeroAdenda,
                                                  String numeroLote, UUID tipoActividadId, UUID actividadEconomicaId);

    List<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID contratoId, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, int offset, int size);
    List<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, int offset, int size);
}
