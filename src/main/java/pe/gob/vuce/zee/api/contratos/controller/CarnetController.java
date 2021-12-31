package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.CarnetDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.CarnetService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/carnet")
public class CarnetController {

    @Autowired
    private CarnetService carnetService;

    @PostMapping
    public ResponseEntity<ResponseDTO> guardarTercerFormulario(@Valid
                                                               @RequestBody List<CarnetDTO> listaCarnet, BindingResult result) {

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }

        List<CarnetDTO> nuevoListaCarnet = carnetService.guardarFormularioCarnet(listaCarnet);

        ResponseDTO responseBody = new ResponseDTO(nuevoListaCarnet, "Lista de carnet guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }


    @GetMapping("/numeroCarnet")
    public ResponseEntity<ResponseDTO> numeroCarnet(){
        String codigo = carnetService.numeroCarnet();
        ResponseDTO responseBody = new ResponseDTO(codigo,"numero carnet");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }
}
