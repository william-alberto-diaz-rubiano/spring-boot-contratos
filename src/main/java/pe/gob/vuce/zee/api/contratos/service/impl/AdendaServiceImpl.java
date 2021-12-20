package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.AdendaMinimalDTO;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;
import pe.gob.vuce.zee.api.contratos.repository.AdendaRepository;
import pe.gob.vuce.zee.api.contratos.service.AdendaService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdendaServiceImpl implements AdendaService {

    private final ModelMapper modelMapper;
    private final AdendaRepository adendaRepository;

    public AdendaServiceImpl(ModelMapper modelMapper, AdendaRepository adendaRepository) {
        this.modelMapper = modelMapper;
        this.adendaRepository = adendaRepository;
    }

    @Override
    public AdendaEntity findByContrato(UUID idContrato) {
        return null;
    }

    @Override
    public AdendaEntity saveAdenda(AdendaEntity adendaEntity) {
        return adendaRepository.save(adendaEntity);
    }

    @Override
    public AdendaEntity actualizarAdenda(AdendaEntity adendaEntity) {
        return adendaRepository.save(adendaEntity);
    }

    @Override
    public List<AdendaEntity> getAllAdendas() {
        return adendaRepository.findAll();
    }

    @Override
    public AdendaEntity getAdendaById(UUID idAdenda) {
        return adendaRepository.getById(idAdenda);
    }

    @Override
    public Page<AdendaMinimalDTO> busquedaAvanzadaMinimal(@NotNull UUID contratoId, UUID usuarioId, Integer numeroAdenda, Pageable pageable) {
        var entities = adendaRepository.busquedaAvanzada(usuarioId, contratoId, numeroAdenda, Constantes.HABILITADO, pageable);
        var dtos = entities
                .stream()
                .map(x -> modelMapper.map(x, AdendaMinimalDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
