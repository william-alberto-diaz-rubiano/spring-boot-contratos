package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.ActividadDTO;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.UUID;

public class ActividadController {

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getActividad(@RequestParam(name = "idTipoActividad", required = false) UUID idTipoActividad,
                                                       @RequestParam(name = "idActividadEconomica", required = false) UUID idActividadEconomica,
                                                       @RequestParam(name = "idAlmacen", required = false) UUID idAlmacen,
                                                       @RequestParam(name = "fechaInicio", required = false) Timestamp fechaInicio,
                                                       @RequestParam(name = "fechaFinal", required = false) UUID fechaFinal, Pageable pageable) {
        ResponseDTO<?> response;
        response = new ResponseDTO<>(Constantes.NO_ERROR, null);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<?>> crearActividad(@RequestBody @Valid ActividadDTO actividadDTO) {
        ResponseDTO<?> response;
        response = new ResponseDTO<>(Constantes.NO_ERROR, actividadDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<?>> eliminarActividad(@RequestBody @Valid ActividadDTO actividadDTO) {
        ResponseDTO<?> response;
        response = new ResponseDTO<>(Constantes.NO_ERROR, null);
        return ResponseEntity.ok(response);
    }


}
