package pe.gob.vuce.zee.api.contratos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoLoteMapaDTO;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoSaveDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ContratoLoteService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody List<LoteContratoSaveDTO> listaContratoLotes, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        List<LoteContratoSaveDTO> nuevoListaLotesContrato = contratoLoteService.guardarAllLoteContrato(listaContratoLotes);

        ResponseDTO responseBody = new ResponseDTO(nuevoListaLotesContrato,"Lista de lotes guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }




}
