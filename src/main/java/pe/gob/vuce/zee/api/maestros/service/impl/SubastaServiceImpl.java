package pe.gob.vuce.zee.api.maestros.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.dto.SubastaDTO;
import pe.gob.vuce.zee.api.maestros.models.SubastaEntity;
import pe.gob.vuce.zee.api.maestros.repository.SubastaRepository;
import pe.gob.vuce.zee.api.maestros.service.SubastaService;

@Service
public class SubastaServiceImpl implements SubastaService {
    private final ModelMapper modelMapper;
    private final SubastaRepository subastaRepository;

    public SubastaServiceImpl(ModelMapper modelMapper, SubastaRepository subastaRepository) {
        this.modelMapper = modelMapper;
        this.subastaRepository = subastaRepository;
    }

    @Override
    public SubastaDTO saveSubasta(SubastaDTO subastaDTO){
        SubastaEntity subastaEntity = modelMapper.map(subastaDTO, SubastaEntity.class);
        SubastaEntity subastaResponse = subastaRepository.save(subastaEntity);
        return modelMapper.map(subastaResponse,SubastaDTO.class);
    }
}
