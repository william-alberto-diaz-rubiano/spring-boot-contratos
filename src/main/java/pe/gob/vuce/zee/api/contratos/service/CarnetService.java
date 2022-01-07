package pe.gob.vuce.zee.api.contratos.service;

import pe.gob.vuce.zee.api.contratos.dto.CarnetDTO;

import java.util.List;
import java.util.UUID;

public interface CarnetService {
    CarnetDTO guardarFormularioCarnet(CarnetDTO carnetDTO);
    String numeroCarnet();
}
