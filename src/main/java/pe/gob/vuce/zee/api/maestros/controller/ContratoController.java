package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.service.ContratoService;
import pe.gob.vuce.zee.api.maestros.service.MaestroService;

import java.util.List;

@RestController
@RequestMapping("v1/contrato")
public class ContratoController {
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public List<ContratoEntity> getContratos() {
        return contratoService.findAll();
    }

    @PostMapping
    public ContratoEntity createContratos(@RequestBody @Validated ContratoEntity contrato) {
        return contratoService.createContrato(contrato);
    }

    @GetMapping("/findByClientId/{clientId}")
    public List<ContratoEntity> getContratosByClientId(@PathVariable("clientId") Integer clientId) {
        return contratoService.finByClienteId(clientId);
    }
}
