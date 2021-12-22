package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContratoSegundoFormularioDTO {

    private UUID id;

    private UUID contratoId;

    private UUID loteId;
    private UUID bloque;
    @NotNull(message = "El id del tipo de moneda no puede ser nulo")
    private UUID tipoMoneda;
    @NotNull(message = "El costo no puede ser nulo")
    private Integer costo;
    @NotNull(message = "El tamano no puede ser nulo")
    private Integer tamano;
    private Integer montoPenal;
    private Integer idCliente;
    private Integer idOrganizacion;
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    private UUID usuarioCreacion;
    private UUID usuarioModificacion;
}
