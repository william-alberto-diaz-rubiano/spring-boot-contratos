package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.ActividadDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ActividadService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @PostMapping("/{contratoId}/actividad")
    public ResponseEntity<ResponseDTO> guardarSegundoFormulario(@Valid
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




}
