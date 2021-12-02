package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.gob.vuce.zee.api.maestros.dto.SubastaDTO;
import pe.gob.vuce.zee.api.maestros.service.SubastaService;

import javax.validation.Valid;

public class SubastaController {

    private final SubastaService subastaService;

    public SubastaController(SubastaService subastaService) {
        this.subastaService = subastaService;
    }

    @PutMapping()
    public SubastaDTO saveSubasta(@RequestBody @Valid SubastaDTO subastaDTO){
        return subastaService.saveSubasta(subastaDTO);
    }
}
