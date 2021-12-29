package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.dto.RepresentanteLegalDTO;

import java.util.List;
import java.util.UUID;

public interface RepresentanteLegalService {
    List<RepresentanteLegalDTO> guardarFormularioRepresentante(UUID contratoId, List<RepresentanteLegalDTO> listaObjetos);
}
