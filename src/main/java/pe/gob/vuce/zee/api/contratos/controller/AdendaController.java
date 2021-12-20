package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;
import pe.gob.vuce.zee.api.contratos.service.AdendaService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/adendas")
public class AdendaController {

    private final AdendaService adendaService;

    public AdendaController(AdendaService adendaService) {
        this.adendaService = adendaService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<?>>> getAllAdendas(
            @RequestParam(name = "contratoId", required = false) UUID contratoId,
            @RequestParam(name = "usuarioId", required = false) UUID usuarioId,
            @RequestParam(name = "numeroAdenda", required = false) Integer numeroAdenda,
            @RequestParam(name = "tipo") Integer tipo,
            Pageable pageable
    ) {
        Page<?> result = null;
        if (tipo == 2) {
            result = adendaService.busquedaAvanzadaMinimal(contratoId, usuarioId, numeroAdenda, pageable);
        }
        var body = new ResponseDTO<Page<?>>(Constantes.NO_ERROR, result);
        return ResponseEntity.ok(body);
    }

    @PutMapping()
    public AdendaEntity saveAdenda(@RequestBody @Valid AdendaEntity adendaEntity) {
        return adendaService.saveAdenda(adendaEntity);
    }


}
