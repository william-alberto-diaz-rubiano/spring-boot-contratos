package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.service.ContratoService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("v1/contrato")
public class ContratoController {
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public Page<ContratoEntity> getContratos(@RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                                             @RequestParam(name = "tipoContrato", required = false) Integer tipoContrato,
                                             @RequestParam(name = "estado", required = false) Integer estado,
                                             @RequestParam(name = "fechaInicial", required = false) Timestamp fechaInicial,
                                             @RequestParam(name = "fechaFinal", required = false) Timestamp fechaFinal, Pageable pageable) {
        return contratoService.finByCorrelativo(numeroContrato,tipoContrato, estado,
                fechaInicial,fechaFinal,pageable);
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
