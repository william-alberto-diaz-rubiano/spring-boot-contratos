package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.dto.SubastaDTO;
import pe.gob.vuce.zee.api.maestros.service.SubastaService;

import javax.validation.Valid;

public class SubastaController {

    private final SubastaService subastaService;

    public SubastaController(SubastaService subastaService) {
        this.subastaService = subastaService;
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO<?>> saveSubasta(@RequestBody @Valid SubastaDTO subastaDTO){
        ResponseDTO<?> response;
        SubastaDTO subastaDTO1 = subastaService.saveSubasta(subastaDTO);
        response = new ResponseDTO<>(Constantes.NO_ERROR, subastaDTO1);
        return ResponseEntity.ok(response);
    }
}
