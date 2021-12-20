package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.AdendaMinimalDTO;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface AdendaService {
    AdendaEntity findByContrato(UUID idContrato);

    AdendaEntity saveAdenda(AdendaEntity adendaEntity);

    AdendaEntity actualizarAdenda(AdendaEntity adendaEntity);

    List<AdendaEntity> getAllAdendas();

    AdendaEntity getAdendaById(UUID idAdenda);

    Page<AdendaMinimalDTO> busquedaAvanzadaMinimal(@NotNull UUID contratoId, UUID usuarioId, Integer numeroAdenda, Pageable pageable);
}
