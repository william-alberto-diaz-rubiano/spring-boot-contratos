package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.dto.RepresentanteLegalContratoDTO;

import java.util.List;
import java.util.UUID;

public interface RepresentanteLegalContratoService {
    List<RepresentanteLegalContratoDTO> guardarFormularioRepresentante(List<RepresentanteLegalContratoDTO> listaObjetos);
}
