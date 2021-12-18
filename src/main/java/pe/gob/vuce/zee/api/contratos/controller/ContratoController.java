package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/contratos")
public class ContratoController {
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getContratos(@RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                                                       @RequestParam(name = "tipoContrato", required = false) UUID tipoContrato,
                                                       @RequestParam(name = "estado", required = false) Integer estado,
                                                       @RequestParam(name = "lote", required = false) UUID lote,
                                                       @RequestParam(name = "documento", required = false) String documento,
                                                       @RequestParam(name = "tipoDocumento", required = false) UUID tipoDocumento,
                                                       @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
                                                       @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad,
                                                       @RequestParam(name = "fechaInicial", required = false) Timestamp fechaInicial,
                                                       @RequestParam(name = "tipo") Integer tipo, // 1 -> normal, 2-> para seleccion
                                                       @RequestParam(name = "fechaFinal", required = false) Timestamp fechaFinal, Pageable pageable) {
        ResponseDTO<?> response;
        Page<?> resultado = null;
        if(tipo == 1){
            resultado = contratoService.finByCorrelativo(numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        if(tipo == 2){
            resultado = contratoService.busquedaAvanzadaSeleccion(numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        response = new ResponseDTO<>(Constantes.NO_ERROR, resultado);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ContratoDTO createContratos(@RequestBody @Validated ContratoDTO contrato) {
        return contratoService.createContrato(contrato);
    }


}
