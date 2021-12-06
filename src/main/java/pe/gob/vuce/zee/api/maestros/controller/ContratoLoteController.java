package pe.gob.vuce.zee.api.maestros.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.maestros.dto.LoteDTO;
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
    public LoteContratoDTO getContrato(@PathVariable UUID idContrato) throws IOException {
        return contratoLoteService.findByContrato(idContrato);
    }

    @GetMapping("/{idContratoLote}")
    public LoteContratoDTO getById(@PathVariable UUID idContratoLote) throws IOException {
        var loteContratoDTO = contratoLoteService.findbyId(idContratoLote);
        return loteContratoDTO;
    }

    @DeleteMapping
    public LoteContratoDTO deleteContratoLote(@RequestParam(name = "contratoLote")  LoteContratoDTO loteContratoDTO){
       return contratoLoteService.deleteLoteContrato(loteContratoDTO);
    }

    @PutMapping
    public LoteContratoDTO saveLoteContrato(@RequestParam(name = "contratoLote")  LoteContratoDTO loteContratoDTO) throws IOException {
        return contratoLoteService.crearLoteContrato(loteContratoDTO);
    }

}
