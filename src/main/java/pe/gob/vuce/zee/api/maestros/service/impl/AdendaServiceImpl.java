package pe.gob.vuce.zee.api.maestros.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.models.AdendaEntity;
import pe.gob.vuce.zee.api.maestros.repository.AdendaRepository;
import pe.gob.vuce.zee.api.maestros.repository.ContratoRepository;
import pe.gob.vuce.zee.api.maestros.service.AdendaService;

import java.util.List;
import java.util.UUID;

@Service
public class AdendaServiceImpl implements AdendaService {

    private final ModelMapper modelMapper;
    private final AdendaRepository adendaRepository;

    public AdendaServiceImpl(ModelMapper modelMapper, AdendaRepository adendaRepository) {
        this.modelMapper = modelMapper;
        this.adendaRepository = adendaRepository;
    }

    @Override
    public AdendaEntity findByContrato(UUID idContrato){
        return null;
    }

    @Override
    public AdendaEntity saveAdenda(AdendaEntity adendaEntity){
        return adendaRepository.save(adendaEntity);
    }

    @Override
    public AdendaEntity actualizarAdenda(AdendaEntity adendaEntity){
       return adendaRepository.save(adendaEntity);
    }

    @Override
    public List<AdendaEntity> getAllAdendas(){
        return adendaRepository.findAll();
    }

    @Override
    public AdendaEntity getAdendaById(UUID idAdenda){
       return adendaRepository.getById(idAdenda);
    }


}
