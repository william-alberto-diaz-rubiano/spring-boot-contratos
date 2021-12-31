package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.RepresentanteLegalContratoDTO;
import pe.gob.vuce.zee.api.contratos.models.RepresentanteLegalContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.RepresentanteLegalContratoRepository;
import pe.gob.vuce.zee.api.contratos.service.RepresentanteLegalContratoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RepresentanteLegalContratoServiceImpl implements RepresentanteLegalContratoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RepresentanteLegalContratoRepository representanteLegalContratoRepository;

    @Override
    public List<RepresentanteLegalContratoDTO> guardarFormularioRepresentante(List<RepresentanteLegalContratoDTO> listaObjetos) {

        List<RepresentanteLegalContratoEntity> listaobjetosEntity = new ArrayList<>();

        for(RepresentanteLegalContratoDTO representanteLegalContratoDTO : listaObjetos){

            representanteLegalContratoDTO.setEstado(1);
            representanteLegalContratoDTO.setActivo(Constantes.HABILITADO);
            representanteLegalContratoDTO.setIdCliente(1);
            representanteLegalContratoDTO.setIdOrganizacion(1);
            representanteLegalContratoDTO.setFechaCreacion(LocalDateTime.now());
            representanteLegalContratoDTO.setFechaModificacion(LocalDateTime.now());
            representanteLegalContratoDTO.setUsuarioCreacion(Constantes.UID_TEST);
            representanteLegalContratoDTO.setUsuarioModificacion(Constantes.UID_TEST);

            RepresentanteLegalContratoEntity representanteLegalContratoEntity = modelMapper.map(representanteLegalContratoDTO, RepresentanteLegalContratoEntity.class);

            listaobjetosEntity.add(representanteLegalContratoEntity);
        }

        listaobjetosEntity = representanteLegalContratoRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, RepresentanteLegalContratoDTO.class)).collect(Collectors.toList());
    }
}
