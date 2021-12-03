package pe.gob.vuce.zee.api.maestros.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ContratoCustomRepository {
    Page<ContratoEntity> busquedaPageable(String numeroContrato, Integer tipoContrato, Integer estado,
                                          Timestamp fechaInicio, Timestamp fechaFinal, Pageable pageable);

    List<ContratoEntity> busqueda(String numeroContrato, Integer tipoContrato, Integer estado,
                                  Timestamp fechaInicio, Timestamp fechaFinal, int offset, int size, String sort, String filter);

    Long contar(String numeroContrato, Integer tipoContrato, Integer estado,
                Timestamp fechaInicio, Timestamp fechaFinal);
}
