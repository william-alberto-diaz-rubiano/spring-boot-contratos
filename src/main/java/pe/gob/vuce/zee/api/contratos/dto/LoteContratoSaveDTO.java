package pe.gob.vuce.zee.api.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoteContratoSaveDTO {

    private UUID id;
    @NotNull(message = "El id del contrato no puede ser nulo")
    private UUID contratoId;
    @NotNull(message = "El id del lote no puede ser nulo")
    private UUID loteId;
    private UUID bloque;
    @NotNull(message = "El id del tipo de moneda no puede ser nulo")
    private UUID tipoMoneda;
    @NotNull(message = "El costo no puede ser nulo")
    private Integer costo;
    @NotNull(message = "El tamano no puede ser nulo")
    private Integer tamano;
    private Integer idCliente;
    private Integer idOrganizacion;
    private Integer estado;
    private Integer activo;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    private UUID usuarioCreacion;
    private UUID usuarioModificacion;
}
