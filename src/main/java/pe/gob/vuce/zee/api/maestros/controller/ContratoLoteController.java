package pe.gob.vuce.zee.api.maestros.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.maestros.dto.LoteDTO;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.maestros.service.ContratoLoteService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("v1/contratoLote")
@RequiredArgsConstructor
public class ContratoLoteController {

    private final ContratoLoteService contratoLoteService;

    @GetMapping("/contrato/{idContrato}")
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
