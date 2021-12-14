package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteBandejaDTO;

import java.util.List;
import java.util.UUID;

public interface ContratoLoteCustomRepository {
    Page<ContratoLoteBandejaDTO> busquedaAvanzada(String numeroContrato, UUID usuarioId, String numeroAdenda,
                                                  String numeroLote, UUID tipoActividad, UUID actividadEconomica,
                                                  Pageable pageable);
}
