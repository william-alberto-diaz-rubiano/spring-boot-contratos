package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.ContratoFormularioPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoSegundoFormularioDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import javax.validation.Valid;
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
/*
    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getContratos( @RequestParam(name = "tipoContrato", required = false) UUID tipoContrato,
                                                        @RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                                                        @RequestParam(name = "tipoDocumento", required = false) UUID tipoDocumento,
                                                        @RequestParam(name = "documento", required = false) String documento,
                                                        @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
                                                        @RequestParam(name = "estado", required = false) Integer estado,
                                                        @RequestParam(name = "lote", required = false) UUID lote,
                                                        @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad,
                                                        @RequestParam(name = "fechaInicial", required = false) Timestamp fechaInicial,
                                                        @RequestParam(name = "fechaFinal", required = false) Timestamp fechaFinal,
                                                        Pageable pageable) {

        return null;
    }
 */

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
