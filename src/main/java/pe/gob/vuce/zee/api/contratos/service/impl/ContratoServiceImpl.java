package pe.gob.vuce.zee.api.contratos.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.config.AppConfig;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoMinimalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoFormularioPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoSegundoFormularioDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteRepository;
import pe.gob.vuce.zee.api.contratos.repository.ContratoRepository;
import pe.gob.vuce.zee.api.contratos.repository.MaestroRepository;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContratoServiceImpl implements ContratoService {

    private final ModelMapper modelMapper;
    private final ContratoRepository contratoRepository;
    private final AppConfig appConfig;
    private final ContratoLoteRepository contratoLoteRepository;


    @Autowired
    private MaestroRepository maestroRepository;

    @Override
    public ContratoFormularioPrincipalDTO guardarFormularioPrincipal(ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO) {

        contratoFormularioPrincipalDTO.setActivo(Constantes.HABILITADO);
        contratoFormularioPrincipalDTO.setEstado(1);
        contratoFormularioPrincipalDTO.setCodigoCliente(1);
        contratoFormularioPrincipalDTO.setCodigoOrganizacion(1);
        contratoFormularioPrincipalDTO.setUsuarioCreacion(Constantes.UID_TEST);
        contratoFormularioPrincipalDTO.setFechaCreacion(LocalDateTime.now());
        contratoFormularioPrincipalDTO.setFechaModificacion(LocalDateTime.now());
        contratoFormularioPrincipalDTO.setUsuarioModificacion(Constantes.UID_TEST);

        ContratoEntity contratoEntity = modelMapper.map(contratoFormularioPrincipalDTO, ContratoEntity.class);

        contratoEntity = contratoRepository.save(contratoEntity);

        return modelMapper.map(contratoEntity, ContratoFormularioPrincipalDTO.class);
    }

    @Override
    public List<ContratoSegundoFormularioDTO> guardarSegundoFormulario(UUID contratoId, List<ContratoSegundoFormularioDTO> listaObjetos) {

            List<LoteContratoEntity> listaobjetosEntity = new ArrayList<>();

            for(ContratoSegundoFormularioDTO contratoSegundoFormularioDTO : listaObjetos){

                contratoSegundoFormularioDTO.setContratoId(contratoId);
                contratoSegundoFormularioDTO.setEstado(2);
                contratoSegundoFormularioDTO.setActivo(Constantes.HABILITADO);
                contratoSegundoFormularioDTO.setIdCliente(1);
                contratoSegundoFormularioDTO.setIdOrganizacion(1);
                contratoSegundoFormularioDTO.setFechaCreacion(LocalDateTime.now());
                contratoSegundoFormularioDTO.setFechaModificacion(LocalDateTime.now());
                contratoSegundoFormularioDTO.setUsuarioCreacion(Constantes.UID_TEST);
                contratoSegundoFormularioDTO.setUsuarioModificacion(Constantes.UID_TEST);

                LoteContratoEntity loteContratoEntity = modelMapper.map(contratoSegundoFormularioDTO, LoteContratoEntity.class);

                listaobjetosEntity.add(loteContratoEntity);
            }

            listaobjetosEntity = contratoLoteRepository.saveAll(listaobjetosEntity);

            return listaobjetosEntity.stream().map(x -> modelMapper.map(x, ContratoSegundoFormularioDTO.class)).collect(Collectors.toList());

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
    public Page<ContratoFormularioPrincipalDTO> busquedaPorFiltrosTipoUno(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal, Pageable paginador) {

        var result =contratoRepository.busquedaPageable(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,usuario,tipoActividad,fechaInicial,fechaFinal,paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoFormularioPrincipalDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());

    }

    @Override
    public Page<ContratoMinimalDTO> busquedaPorFiltrosTipoDos(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal, Pageable paginador) {

        var result =contratoRepository.busquedaPageable(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,usuario,tipoActividad,fechaInicial,fechaFinal,paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoMinimalDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ContratoFormularioPrincipalDTO> busquedaPorFiltros(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal) {
        return null;
    }

}
