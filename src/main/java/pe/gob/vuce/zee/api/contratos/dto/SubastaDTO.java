package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubastaDTO {

    private UUID id;

    private UUID contratoId;

    @NotNull(message = "El id del lote no puede ser nulo")
    private UUID loteId;

    @NotNull(message = "El id del bloque no puede ser nulo")
    private UUID codigoBloque;

    @NotNull(message = "El numero de subasta no puede ser nulo")
    private String numeroSubasta;

    @NotNull(message = "La fecha inicial no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaInicial;

    @NotNull(message = "La fecha de acta no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActa;

    @NotNull(message = "El monto no puede ser nulo")
    private Integer monto;

    @NotNull(message = "El tamaño no puede ser nulo")
    private Integer tamano;

    private Integer idCliente;

    private Integer idOrganizacion;

    private Integer estado;

    private Integer activo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;

    private UUID usuarioCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion ;

    private UUID usuarioModificacion;

}
