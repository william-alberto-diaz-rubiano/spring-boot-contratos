package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubastaDTO {
    private UUID id;
    private UUID idContrato;
    private Integer codigoEtapa;
    private Integer codigoManzana;
    private Integer codigoLote;
    private Integer codigoBloque;
    private Integer numeroSubasta;
    private Timestamp fechaInicial;
    private Timestamp fechaActa;
    private Integer monto;
    private Integer tamano;
    private Integer codigoCliente;
    private Integer codigoOrganizacion;
    private Integer codigoEstado;
    private Integer codigoActivacion;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    private UUID usuarioCreacion;
    private UUID usuarioEdicion;

}
