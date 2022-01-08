package pe.gob.vuce.zee.api.contratos.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.config.AppConfig;
import pe.gob.vuce.zee.api.contratos.dto.*;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.AdendaRepository;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteRepository;
import pe.gob.vuce.zee.api.contratos.repository.ContratoRepository;
import pe.gob.vuce.zee.api.contratos.repository.MaestroRepository;
import pe.gob.vuce.zee.api.contratos.service.AdendaService;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final MaestroRepository maestroRepository;
    private final AdendaRepository adendaRepository;


    @Override
    public ContratoFormularioPrincipalDTO guardarFormularioPrincipal(ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO) {

        contratoFormularioPrincipalDTO.setActivo(Constantes.HABILITADO);
        contratoFormularioPrincipalDTO.setEstado(3);
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
                contratoSegundoFormularioDTO.setEstado(1);
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
    public Page<ContratoBandejaDTO> busquedaPorFiltrosTipoUno(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal, Pageable paginador)
    {
        if(fechaInicial != null && fechaFinal != null)
        {
            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicial.toLocalDate();
            LocalDate fechaFinDate = fechaFinal.toLocalDate();

            fechaInicial = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFinal = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result =contratoRepository.busquedaPageable(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal,paginador);

        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoBandejaDTO.class)).collect(Collectors.toList());

        for(ContratoBandejaDTO contratoBandejaDTO : resultDTO)
        {

            var estadoString= maestroRepository.findByPrefijoAndCorrelativo(70,contratoBandejaDTO.getEstado()).getDescripcion();
            contratoBandejaDTO.setEstadoDescripcion(estadoString);

            var listaAdendaActiva = adendaRepository.busquedaAvanzada(null,contratoBandejaDTO.getId(),null,null,-1,-1).stream().filter(adenda -> adenda.getEstado().equals(1)).collect(Collectors.toList());

            if(!listaAdendaActiva.isEmpty()){

                contratoBandejaDTO.setInicioAdenda(listaAdendaActiva.get(0).getFechaInicial());
                contratoBandejaDTO.setVencimientoAdenda(listaAdendaActiva.get(0).getFechaVencimiento());

                var estadoDescripcion= maestroRepository.findByPrefijoAndCorrelativo(71,listaAdendaActiva.get(0).getEstado()).getDescripcion();

                contratoBandejaDTO.setEstadoAdendaDescripcion(estadoDescripcion);
            }

            contratoBandejaDTO.setCantidadActividades(contratoRepository.countActividad(contratoBandejaDTO.getId()));
            contratoBandejaDTO.setCantidadLotes(contratoRepository.countLoteContratos(contratoBandejaDTO.getId()));

        }

        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ContratoBandejaDTO> busquedaPorFiltrosTipoUno(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal)
    {
        if(fechaInicial != null && fechaFinal != null)
        {
            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicial.toLocalDate();
            LocalDate fechaFinDate = fechaFinal.toLocalDate();

            fechaInicial = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFinal = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result =contratoRepository.busqueda(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal);

        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoBandejaDTO.class)).collect(Collectors.toList());

        for(ContratoBandejaDTO contratoBandejaDTO : resultDTO)
        {

            var estadoString= maestroRepository.findByPrefijoAndCorrelativo(70,contratoBandejaDTO.getEstado()).getDescripcion();
            contratoBandejaDTO.setEstadoDescripcion(estadoString);

            var listaAdendaActiva = adendaRepository.busquedaAvanzada(null,contratoBandejaDTO.getId(),null,null,-1,-1).stream().filter(adenda -> adenda.getEstado().equals(1)).collect(Collectors.toList());

            if(!listaAdendaActiva.isEmpty()){

                contratoBandejaDTO.setInicioAdenda(listaAdendaActiva.get(0).getFechaInicial());
                contratoBandejaDTO.setVencimientoAdenda(listaAdendaActiva.get(0).getFechaVencimiento());

                var estadoDescripcion= maestroRepository.findByPrefijoAndCorrelativo(71,listaAdendaActiva.get(0).getEstado()).getDescripcion();

                contratoBandejaDTO.setEstadoAdendaDescripcion(estadoDescripcion);
            }

            contratoBandejaDTO.setCantidadActividades(contratoRepository.countActividad(contratoBandejaDTO.getId()));
            contratoBandejaDTO.setCantidadLotes(contratoRepository.countLoteContratos(contratoBandejaDTO.getId()));

        }

        return resultDTO;
    }


    @Override
    public Page<ContratoMinimalDTO> busquedaPorFiltrosTipoDos(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad,LocalDateTime fechaInicial, LocalDateTime fechaFinal, Pageable paginador)
    {
        if(fechaInicial != null && fechaFinal != null)
        {
            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicial.toLocalDate();
            LocalDate fechaFinDate = fechaFinal.toLocalDate();

            fechaInicial = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFinal = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result =contratoRepository.busquedaPageable(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal,paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoMinimalDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ContratoMinimalDTO> busquedaPorFiltrosTipoDos(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad,LocalDateTime fechaInicial, LocalDateTime fechaFinal)
    {
        if(fechaInicial != null && fechaFinal != null)
        {
            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicial.toLocalDate();
            LocalDate fechaFinDate = fechaFinal.toLocalDate();

            fechaInicial = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFinal = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result = contratoRepository.busqueda(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, ContratoMinimalDTO.class)).collect(Collectors.toList());
        return resultDTO;
    }

    @Override
    public List<ContratoBandejaDTO> busquedaPorFiltros(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal) {

        if(fechaInicial != null && fechaFinal != null){

            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicial.toLocalDate();
            LocalDate fechaFinDate = fechaFinal.toLocalDate();

            fechaInicial = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFinal = LocalDateTime.of(fechaFinDate,horaFin);
        }
        var result =contratoRepository.busqueda(id,numeroContrato,tipoContrato,estado,lote,documento,tipoDocumento,nombreUsuario, usuario,tipoActividad,fechaInicial,fechaFinal);
        return result.stream().map(x -> modelMapper.map(x, ContratoBandejaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FechasContratoDTO fechasContrato(UUID idContrato) {

        var contrato= busquedaPorFiltros(idContrato,null,null,null,null,null,null,null,null,null,null,null).get(0);

        FechasContratoDTO fechasContratoDTO=modelMapper.map(contrato,FechasContratoDTO.class);
        return fechasContratoDTO;
    }

}
