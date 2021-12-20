package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoMinimalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoRepository;
import pe.gob.vuce.zee.api.contratos.repository.MaestroRepository;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private MaestroRepository maestroRepository;

    @Override
    public ContratoPrincipalDTO guardarPrincipal(ContratoPrincipalDTO contratoPrincipalDTO) {

        UUID estadoActivo=maestroRepository.findByPrefijoAndCorrelativo(70,1).getId();

        contratoPrincipalDTO.setActivo(Constantes.HABILITADO);
        contratoPrincipalDTO.setContratostateId(estadoActivo);
        contratoPrincipalDTO.setCodigoCliente(1);
        contratoPrincipalDTO.setCodigoOrganizacion(1);
        contratoPrincipalDTO.setUsuarioCreacion(UUID.randomUUID());
        contratoPrincipalDTO.setFechaCreacion(LocalDate.now());
        contratoPrincipalDTO.setFechaModificacion(LocalDate.now());
        contratoPrincipalDTO.setUsuarioModificacion(UUID.randomUUID());

        ContratoEntity contratoEntity = modelMapper.map(contratoPrincipalDTO, ContratoEntity.class);

        contratoEntity = contratoRepository.save(contratoEntity);

        return modelMapper.map(contratoEntity, ContratoPrincipalDTO.class);
    }

    @Override
    public String numeroContrato() {

        Integer codigoMayor;
        Integer codigoNumero;
        List<ContratoEntity> listaContratos= contratoRepository.findAll();


        List<Integer> listadoCodigos = new ArrayList<>();

        for(ContratoEntity contratoEntity : listaContratos){
            String cod = contratoEntity.getNumeroContrato();
            String cadenaNumerica = cod.substring(5);
            Integer codigoInteger = Integer.parseInt(cadenaNumerica);
            listadoCodigos.add(codigoInteger);
        }

        if(listadoCodigos.isEmpty()){
            codigoMayor = 1;
            codigoNumero = codigoMayor;
        }else{
            codigoMayor= Collections.max(listadoCodigos);
            codigoNumero = codigoMayor + 1;
        }

        String codigoFormateado=String.format("%05d", codigoNumero);

        return "CTRT" +"-" + codigoFormateado;
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
