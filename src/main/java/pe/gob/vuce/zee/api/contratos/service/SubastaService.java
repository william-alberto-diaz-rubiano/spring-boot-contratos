package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.dto.SubastaDTO;

import java.util.List;
import java.util.UUID;

public interface SubastaService {
    List<SubastaDTO> guardarFormularioSubasta(UUID contratoId, List<SubastaDTO> listaObjetos);
}
