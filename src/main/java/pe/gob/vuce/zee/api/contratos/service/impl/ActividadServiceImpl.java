package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ActividadDTO;
import pe.gob.vuce.zee.api.contratos.models.ActividadEntity;
import pe.gob.vuce.zee.api.contratos.repository.ActividadRepository;
import pe.gob.vuce.zee.api.contratos.service.ActividadService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ActividadDTO> guardarTercerformulario(UUID contratoId, List<ActividadDTO> listaObjetos) {

        List<ActividadEntity> listaobjetosEntity = new ArrayList<>();

        for(ActividadDTO actividadDTO : listaObjetos){

            actividadDTO.setContratoId(contratoId);
            actividadDTO.setEstado(1);
            actividadDTO.setActivo(Constantes.HABILITADO);
            actividadDTO.setIdCliente(1);
            actividadDTO.setIdOrganizacion(1);
            actividadDTO.setFechaCreacion(LocalDateTime.now());
            actividadDTO.setFechaModificacion(LocalDateTime.now());
            actividadDTO.setUsuarioCreacion(Constantes.UID_TEST);
            actividadDTO.setUsuarioModificacion(Constantes.UID_TEST);

            ActividadEntity actividadEntity= modelMapper.map(actividadDTO, ActividadEntity.class);

            listaobjetosEntity.add(actividadEntity);
        }

        listaobjetosEntity = actividadRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, ActividadDTO.class)).collect(Collectors.toList());
    }
}
