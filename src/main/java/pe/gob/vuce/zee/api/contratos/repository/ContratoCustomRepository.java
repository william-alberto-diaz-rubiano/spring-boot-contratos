package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ContratoCustomRepository {

    List<ContratoEntity> busqueda(UUID id,String numeroContrato,
                                     UUID tipoContrato, Integer estado,
                                     UUID lote, String documento,
                                     UUID tipoDocumento,String nombreUsuario, UUID usuario,
                                     UUID tipoActividad, LocalDateTime fechaInicial,
                                  LocalDateTime fechaFinal);

    List<ContratoEntity> busqueda(UUID id,String numeroContrato,
                                     UUID tipoContrato, Integer estado,
                                     UUID lote, String documento,
                                     UUID tipoDocumento,String nombreUsuario, UUID usuario,
                                     UUID tipoActividad, LocalDateTime fechaInicial,
                                  LocalDateTime fechaFinal,int offset, int size);

    Page<ContratoEntity> busquedaPageable(UUID id,String numeroContrato,
                                             UUID tipoContrato, Integer estado,
                                             UUID lote, String documento,
                                             UUID tipoDocumento,String nombreUsuario, UUID usuario,
                                             UUID tipoActividad, LocalDateTime fechaInicial,
                                          LocalDateTime fechaFinal, Pageable pageable);

    Long contar(UUID id,String numeroContrato,
                UUID tipoContrato, Integer estado,
                UUID lote, String documento,
                UUID tipoDocumento,String nombreUsuario, UUID usuario,
                UUID tipoActividad, LocalDateTime fechaInicial,
                LocalDateTime fechaFinal);


}
