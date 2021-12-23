package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.*;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ContratoService {

    ContratoFormularioPrincipalDTO guardarFormularioPrincipal(ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO);

    List<ContratoSegundoFormularioDTO> guardarSegundoFormulario(UUID contratoId,List<ContratoSegundoFormularioDTO> listaObjetos);

    String numeroContrato();

    Page<ContratoBandejaDTO> busquedaPorFiltrosTipoUno(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal, Pageable paginador);

    Page<ContratoMinimalDTO> busquedaPorFiltrosTipoDos(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal, Pageable paginador);

    List<ContratoFormularioPrincipalDTO> busquedaPorFiltros(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento,String nombreUsuario, UUID usuario, UUID tipoActividad, LocalDateTime fechaInicial, LocalDateTime fechaFinal);






}
