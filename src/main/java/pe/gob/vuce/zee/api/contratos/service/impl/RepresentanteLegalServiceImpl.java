package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.RepresentanteLegalDTO;
import pe.gob.vuce.zee.api.contratos.models.RepresentanteLegalEntity;
import pe.gob.vuce.zee.api.contratos.repository.RepresentanteLegalRepository;
import pe.gob.vuce.zee.api.contratos.service.RepresentanteLegalService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RepresentanteLegalServiceImpl implements RepresentanteLegalService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RepresentanteLegalRepository representanteLegalRepository;

    @Override
    public List<RepresentanteLegalDTO> guardarFormularioRepresentante(UUID contratoId, List<RepresentanteLegalDTO> listaObjetos) {

        List<RepresentanteLegalEntity> listaobjetosEntity = new ArrayList<>();

        for(RepresentanteLegalDTO representanteLegalDTO : listaObjetos){

            representanteLegalDTO.setContratoId(contratoId);
            representanteLegalDTO.setEstado(1);
            representanteLegalDTO.setActivo(Constantes.HABILITADO);
            representanteLegalDTO.setIdCliente(1);
            representanteLegalDTO.setIdOrganizacion(1);
            representanteLegalDTO.setFechaCreacion(LocalDateTime.now());
            representanteLegalDTO.setFechaModificacion(LocalDateTime.now());
            representanteLegalDTO.setUsuarioCreacion(Constantes.UID_TEST);
            representanteLegalDTO.setUsuarioModificacion(Constantes.UID_TEST);

            RepresentanteLegalEntity representanteLegalEntity= modelMapper.map(representanteLegalDTO, RepresentanteLegalEntity.class);

            listaobjetosEntity.add(representanteLegalEntity);
        }

        listaobjetosEntity = representanteLegalRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, RepresentanteLegalDTO.class)).collect(Collectors.toList());
    }
}
