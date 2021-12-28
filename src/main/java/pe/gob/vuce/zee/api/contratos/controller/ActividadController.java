package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.ActividadDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoBandejaDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ActividadService;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private ContratoService contratoService;

    @PostMapping("/{contratoId}/actividad")
    public ResponseEntity<ResponseDTO> guardarTercerFormulario(@Valid
                                                                @PathVariable("contratoId") UUID contratoId,
                                                                @RequestBody List<ActividadDTO> listaActividades, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        List<ActividadDTO> nuevoListaActividades = actividadService.guardarTercerformulario(contratoId,listaActividades);

        ResponseDTO responseBody = new ResponseDTO(nuevoListaActividades,"Lista de actividades guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/numeroCorrelativo/{actividadEconomica}")
    public ResponseEntity<ResponseDTO> numeroCorrelativo(@PathVariable("actividadEconomica") UUID actividadEconomica){
        String codigo = actividadService.correlativoAlmacen(actividadEconomica);
        ResponseDTO responseBody = new ResponseDTO(codigo,"Codigo correlativo");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }


}
