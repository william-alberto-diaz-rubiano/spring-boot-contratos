package pe.gob.vuce.zee.api.contratos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoMinimalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoFormularioPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoSegundoFormularioDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ContratoService {

    ContratoFormularioPrincipalDTO guardarFormularioPrincipal(ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO);

    List<ContratoSegundoFormularioDTO> guardarSegundoFormulario(UUID contratoId,List<ContratoSegundoFormularioDTO> listaObjetos);

    String numeroContrato();

    Page<ContratoFormularioPrincipalDTO> busquedaPorFiltrosTipoUno(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal, Pageable paginador);

    Page<ContratoMinimalDTO> busquedaPorFiltrosTipoDos(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal, Pageable paginador);

    List<ContratoFormularioPrincipalDTO> busquedaPorFiltros(UUID id, String numeroContrato, UUID tipoContrato, Integer estado, UUID lote, String documento, UUID tipoDocumento, UUID usuario, UUID tipoActividad, LocalDate fechaInicial, LocalDate fechaFinal);






}
