package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;

import java.util.List;
import java.util.UUID;

public interface AdendaCustomRepository {
    Page<AdendaEntity> busquedaAvanzada(UUID usuarioId, UUID contratoId, Integer numeroAdenda, Integer activo, Pageable pageable);

    List<AdendaEntity> busquedaAvanzada(UUID usuarioId, UUID contratoId, Integer numeroAdenda, Integer activo, int offset, int size);
}
