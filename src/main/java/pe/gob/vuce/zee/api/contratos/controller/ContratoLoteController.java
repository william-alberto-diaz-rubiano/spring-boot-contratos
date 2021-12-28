package pe.gob.vuce.zee.api.contratos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteMapaDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.service.ContratoLoteService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("v1/contrato-lotes")
@RequiredArgsConstructor
public class ContratoLoteController {

    private final ContratoLoteService contratoLoteService;

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getBandejaContratoLote(
            @RequestParam(name="tipo") Integer tipoBandeja,
            @RequestParam(name="contrato", required=false) String numeroContrato,
            @RequestParam(name="contratoId", required=false) UUID contratoId,
            @RequestParam(name="adendaId", required=false) UUID adendaId,
            @RequestParam(name="loteId", required=false) UUID loteId,
            @RequestParam(name="usuario", required=false) UUID usuarioId,
            @RequestParam(name="adenda", required=false) Integer numeroAdenda,
            @RequestParam(name="lote", required=false) String numeroLote,
            @RequestParam(name="tipo-actividad", required=false) UUID tipoActividadId,
            @RequestParam(name="actividad-economica", required=false) UUID actividadEconomicaId,
            Pageable pageable
    ) {
        Object result = null;
        if (tipoBandeja == 1) {
            result = contratoLoteService.busquedaAvanzada(numeroContrato, usuarioId, numeroAdenda,
                    numeroLote, tipoActividadId, actividadEconomicaId, pageable);
        }
        if (tipoBandeja == 2) {
            result = contratoLoteService.busquedaAvanzada2(usuarioId, contratoId, adendaId, loteId, pageable);
        }

        if(tipoBandeja == 3){
            result = this.contratoLoteService.busquedaAvanzadaMapa(numeroContrato, usuarioId, numeroAdenda, numeroLote, tipoActividadId, actividadEconomicaId);
        }
        var body = new ResponseDTO<>(Constantes.NO_ERROR, result);
        return ResponseEntity.ok(body);
    }

    //TODO el exportar
//    @GetMapping("exportar")
//    public ResponseEntity<ResponseDTO<?>> getExportar(
//            @RequestParam(name="contrato", required=false) String numeroContrato,
//            @RequestParam(name="contratoId", required=false) UUID contratoId,
//            @RequestParam(name="adendaId", required=false) UUID adendaId,
//            @RequestParam(name="loteId", required=false) UUID loteId,
//            @RequestParam(name="usuario", required=false) UUID usuarioId,
//            @RequestParam(name="adenda", required=false) Integer numeroAdenda,
//            @RequestParam(name="lote", required=false) String numeroLote,
//            @RequestParam(name="tipo-actividad", required=false) UUID tipoActividadId,
//            @RequestParam(name="actividad-economica", required=false) UUID actividadEconomicaId,
//            HttpServletResponse response
//    ) {
//        var pageable = PageRequest.of(-1, -1);
//        var result = contratoLoteService.busquedaAvanzada2(usuarioId, contratoId, adendaId, loteId, pageable);
//        return null;
//    }

    @GetMapping("{idContrato}/lotes")
    public ResponseEntity<ResponseDTO<Page<ContratoLoteMapaDTO>>> getLotes(@PathVariable UUID idContrato, Pageable pageable) {
        var result = contratoLoteService.buscarLotesPorContrato(idContrato, pageable);
        var body = new ResponseDTO<>(Constantes.NO_ERROR, result);
        return ResponseEntity.ok(body);

    }

//    @GetMapping
//    public ResponseEntity<ResponseDTO<?>> getContratosByLotes(
//            @RequestParam(name = "actividadEconomica", required = false) String actividadEconomica,
//            @RequestParam(name = "lote", required = false) UUID lote,
//            @RequestParam(name = "contrato", required = false) UUID contrato,
//            @RequestParam(name = "numeroAdenda", required = false) UUID adenda,
//            @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
//            @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad, Pageable pageable) throws IOException {
//        ResponseDTO<?> response;
//        Page<LoteContratoDTO> loteContratoDTO = contratoLoteService.findAll(pageable);
//        response = new ResponseDTO<>(Constantes.NO_ERROR, loteContratoDTO);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/{contratoId}")
    public ResponseEntity<ResponseDTO<?>> getById(@PathVariable UUID contratoId, Pageable pageable) throws IOException {
        ResponseDTO<?> response;
        var resultDto = contratoLoteService.detalleByContrato(contratoId, pageable);
        response = new ResponseDTO<>(Constantes.NO_ERROR, resultDto);
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
