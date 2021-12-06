package pe.gob.vuce.zee.api.contratos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.service.ContratoLoteService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("v1/contratoLotes")
@RequiredArgsConstructor
public class ContratoLoteController {

    private final ContratoLoteService contratoLoteService;

    @GetMapping("{idContrato}/lotes")
    public ResponseEntity<ResponseDTO<?>> getContrato(@PathVariable UUID idContrato) throws IOException {
        ResponseDTO<?> response;
         LoteContratoDTO loteContratoDTO = contratoLoteService.findByContrato(idContrato);
        response = new ResponseDTO<>(Constantes.NO_ERROR, loteContratoDTO);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{idContratoLote}")
    public ResponseEntity<ResponseDTO<?>> getById(@PathVariable UUID idContratoLote) throws IOException {
        ResponseDTO<?> response;
        var loteContratoDTO = contratoLoteService.findbyId(idContratoLote);
        response = new ResponseDTO<>(Constantes.NO_ERROR, loteContratoDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<?>> deleteContratoLote(@RequestParam(name = "contratoLote")  LoteContratoDTO loteContratoDTO){
        ResponseDTO<?> response;
        LoteContratoDTO loteContratoDTO1 = contratoLoteService.deleteLoteContrato(loteContratoDTO);
        response = new ResponseDTO<>(Constantes.NO_ERROR, loteContratoDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<?>> saveLoteContrato(@RequestParam(name = "contratoLote")  LoteContratoDTO loteContratoDTO) throws IOException {
        ResponseDTO<?> response;
        LoteContratoDTO loteContratoDTO1 = contratoLoteService.crearLoteContrato(loteContratoDTO);
        response = new ResponseDTO<>(Constantes.NO_ERROR, loteContratoDTO);
        return ResponseEntity.ok(response);
    }

}
