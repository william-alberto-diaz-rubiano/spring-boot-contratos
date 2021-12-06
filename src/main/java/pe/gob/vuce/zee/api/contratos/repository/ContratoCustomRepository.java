package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ContratoCustomRepository {

    Page<ContratoEntity> busquedaPageable(String numeroContrato,
                                          UUID tipoContrato, Integer estado,
                                          UUID lote, String documento,
                                          UUID tipoDocumento, UUID usuario,
                                          UUID tipoActividad, Timestamp fechaInicial,
                                          Timestamp fechaFinal, Pageable pageable);

    List<ContratoEntity> busqueda(String numeroContrato,
                                  UUID tipoContrato, Integer estado,
                                  UUID lote, String documento, UUID tipoDocumento,
                                  UUID usuario, UUID tipoActividad, Timestamp fechaInicial,
                                  Timestamp fechaFinal, int
                                          offset, int size, String sort, String filter);

    Long contar(String numeroContrato,
                UUID tipoContrato, Integer estado,
                UUID lote, String documento, UUID tipoDocumento,
                UUID usuario, UUID tipoActividad, Timestamp fechaInicial,
                Timestamp fechaFinal);
}
