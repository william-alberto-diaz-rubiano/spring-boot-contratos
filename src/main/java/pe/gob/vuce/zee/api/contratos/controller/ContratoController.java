package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ContratoFormularioPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoSegundoFormularioDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getContratos(@RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                                                       @RequestParam(name = "tipoContrato", required = false) UUID tipoContrato,
                                                       @RequestParam(name = "estado", required = false) Integer estado,
                                                       @RequestParam(name = "lote", required = false) UUID lote,
                                                       @RequestParam(name = "documento", required = false) String documento,
                                                       @RequestParam(name = "tipoDocumento", required = false) UUID tipoDocumento,
                                                       @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
                                                       @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad,
                                                       @RequestParam(name = "fechaInicial", required = false) LocalDate fechaInicial,
                                                       @RequestParam(name = "tipo") Integer tipo, // 1 -> normal, 2-> para seleccion
                                                       @RequestParam(name = "fechaFinal", required = false) LocalDate fechaFinal, Pageable pageable) {

        if(numeroContrato == ""){
            numeroContrato=null;
        }
        if(documento == ""){
            documento=null;
        }

        if((fechaInicial != null && fechaFinal == null) || (fechaFinal !=null && fechaInicial == null)){

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicial != null && fechaFinal != null){

            if(fechaFinal.compareTo(fechaInicial) < 0){
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }

        Page<?> resultado = null;

        if(tipo == 1){
            resultado = contratoService.busquedaPorFiltrosTipoUno(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        if(tipo == 2){
            resultado = contratoService.busquedaPorFiltrosTipoDos(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        ResponseDTO responseBody = new ResponseDTO(resultado,"Listado de contratos");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<ResponseDTO> guardarFormularioPrincipal(@Valid @RequestBody ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        String numeroExpedienteMayuscula = contratoFormularioPrincipalDTO.getNumeroExpediente().toUpperCase();
        contratoFormularioPrincipalDTO.setNumeroExpediente(numeroExpedienteMayuscula);

        ContratoFormularioPrincipalDTO nuevoContratoPrincipal = contratoService.guardarFormularioPrincipal(contratoFormularioPrincipalDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoContratoPrincipal,"Información principal guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/{contratoId}/lotes")
    public ResponseEntity<ResponseDTO> guardarSegundoFormulario(@Valid
                                                                @PathVariable("contratoId") UUID contratoId,
                                                                @RequestBody List<ContratoSegundoFormularioDTO> listaContratoLotes, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        List<ContratoSegundoFormularioDTO> nuevoListaLotesContrato = contratoService.guardarSegundoFormulario(contratoId, listaContratoLotes);

        ResponseDTO responseBody = new ResponseDTO(nuevoListaLotesContrato,"Lista de lotes guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/numeroContrato")
    public ResponseEntity<ResponseDTO> codigoSistema(){
        String codigo = contratoService.numeroContrato();
        ResponseDTO responseBody = new ResponseDTO(codigo,"Codigo del sistema");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }


}
