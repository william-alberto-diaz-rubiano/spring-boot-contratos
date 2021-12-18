package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoMinimalDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoRepository;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContratoServiceImpl implements ContratoService {
    private final ModelMapper modelMapper;
    private final ContratoRepository contratoRepository;

    public ContratoServiceImpl(ModelMapper modelMapper, ContratoRepository contratoRepository) {
        this.modelMapper = modelMapper;
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<ContratoEntity> findAll(){
        return contratoRepository.findAll();
    }

    @Override
    public  ContratoDTO createContrato(ContratoDTO contrato){
        ContratoEntity contratoEntity = modelMapper.map(contrato, ContratoEntity.class);
        contratoRepository.save(contratoEntity);
        return contrato;
    }

    @Override
    public Page<ContratoDTO> finByCorrelativo(String numeroContrato,
                                              UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, Timestamp fechaInicial, Timestamp fechaFinal, Pageable pageable){
        Page<ContratoEntity> contratoList = contratoRepository.busquedaPageable(numeroContrato,tipoContrato, estado,lote,documento,tipoDocumento,usuario,tipoActividad,
                fechaInicial,fechaFinal,pageable);
        List<ContratoDTO> dtos = contratoList
                .stream()
                .map(contrato -> modelMapper.map(contrato, ContratoDTO.class))
                .collect(Collectors.toList());
       return new PageImpl<>(dtos, pageable, contratoList.getTotalElements());
    }

    @Override
    public Page<ContratoMinimalDTO> busquedaAvanzadaSeleccion(String numeroContrato,
                                                              UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, Timestamp fechaInicial, Timestamp fechaFinal, Pageable pageable){
        Page<ContratoEntity> contratoList = contratoRepository.busquedaPageable(numeroContrato,tipoContrato, estado,lote,documento,tipoDocumento,usuario,tipoActividad,
                fechaInicial,fechaFinal,pageable);
        List<ContratoMinimalDTO> dtos = contratoList
                .stream()
                .map(contrato -> modelMapper.map(contrato, ContratoMinimalDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, contratoList.getTotalElements());
    }

    @Override
    public Page<ContratoDTO> finByUsuario(UUID usuarioId, Pageable pageable) {
        return null;
    }
}
