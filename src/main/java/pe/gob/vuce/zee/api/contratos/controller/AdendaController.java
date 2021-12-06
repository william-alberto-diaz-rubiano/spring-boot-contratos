package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping()
    public List<AdendaEntity> getAllAdendas() {
        return adendaService.getAllAdendas();
    }

    @PutMapping()
    public AdendaEntity saveAdenda(@RequestBody @Valid AdendaEntity adendaEntity){
        return adendaService.saveAdenda(adendaEntity);
    }


}
