package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.SubastaDTO;
import pe.gob.vuce.zee.api.contratos.models.SubastaEntity;
import pe.gob.vuce.zee.api.contratos.repository.MaestroRepository;
import pe.gob.vuce.zee.api.contratos.repository.SubastaRepository;
import pe.gob.vuce.zee.api.contratos.service.SubastaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubastaServiceImpl implements SubastaService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private MaestroRepository maestroRepository;


    @Override
    public List<SubastaDTO> guardarFormularioSubasta(UUID contratoId, List<SubastaDTO> listaObjetos) {

        List<SubastaEntity> listaobjetosEntity = new ArrayList<>();

        for(SubastaDTO subastaDTO : listaObjetos){

            subastaDTO.setContratoId(contratoId);
            subastaDTO.setEstado(1);
            subastaDTO.setActivo(Constantes.HABILITADO);
            subastaDTO.setIdCliente(1);
            subastaDTO.setIdOrganizacion(1);
            subastaDTO.setFechaCreacion(LocalDateTime.now());
            subastaDTO.setFechaModificacion(LocalDateTime.now());
            subastaDTO.setUsuarioCreacion(Constantes.UID_TEST);
            subastaDTO.setUsuarioModificacion(Constantes.UID_TEST);

            SubastaEntity subastaEntity= modelMapper.map(subastaDTO, SubastaEntity.class);

            listaobjetosEntity.add(subastaEntity);
        }

        listaobjetosEntity = subastaRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, SubastaDTO.class)).collect(Collectors.toList());
    }
}
