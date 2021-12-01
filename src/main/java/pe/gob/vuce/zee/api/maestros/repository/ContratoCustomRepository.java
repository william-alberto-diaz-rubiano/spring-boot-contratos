package pe.gob.vuce.zee.api.maestros.repository;

import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ContratoCustomRepository {
    List<ContratoEntity> busqueda(String numeroContrato, Integer tipoContrato, Integer estado,
                                  Timestamp fechaInicio, Timestamp fechaFinal, int offset, int size, String sort, String filter);
}
