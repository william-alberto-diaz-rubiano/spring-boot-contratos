package pe.gob.vuce.zee.api.contratos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.dto.SubastaDTO;
import pe.gob.vuce.zee.api.contratos.service.SubastaService;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/subastas")
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
