package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.dto.ActividadDTO;

import java.util.List;
import java.util.UUID;

public interface ActividadService {
    List<ActividadDTO> guardarTercerformulario(UUID contratoId, List<ActividadDTO> listaObjetos);
}
