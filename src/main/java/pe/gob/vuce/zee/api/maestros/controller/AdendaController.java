package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.models.AdendaEntity;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.service.AdendaService;
import pe.gob.vuce.zee.api.maestros.service.ContratoService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/adenda")
public class AdendaController {

    private final AdendaService adendaService;

    public AdendaController(AdendaService adendaService) {
        this.adendaService = adendaService;
    }

    @GetMapping("/findByContrato/{idContrato}")
    public AdendaEntity getAdendaByContrato(@PathVariable("idContrato") UUID idContrato) {
        return adendaService.findByContrato(idContrato);
    }

    @GetMapping()
    public List<AdendaEntity> getAllAdendas() {
        return adendaService.getAllAdendas();
    }

    @PutMapping()
    public AdendaEntity saveAdenda(@RequestBody @Valid AdendaEntity adendaEntity){
        return adendaService.saveAdenda(adendaEntity);
    }

    @PostMapping("/actualizaAdenda")
    public AdendaEntity actualizarAdenda(@RequestBody @Valid AdendaEntity adendaEntity){
        return adendaService.actualizarAdenda(adendaEntity);
    }

    @GetMapping("/findById/{idAdenda}")
    public AdendaEntity buscarAdendaById(@PathVariable("idAdenda") UUID idAdenda){
        return adendaService.getAdendaById(idAdenda);
    }

}
