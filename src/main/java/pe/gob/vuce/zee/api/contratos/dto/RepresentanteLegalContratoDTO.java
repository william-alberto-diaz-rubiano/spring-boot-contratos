package pe.gob.vuce.zee.api.contratos.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RepresentanteLegalContratoDTO {

    private UUID id;

    @NotNull(message = "El id del contrato no puede ser nulo")
    private UUID contratoId;

    @NotNull(message = "El id del representante legal no puede ser nulo")
    private UUID representanteLegalId;

    @NotNull(message = "La fecha de firma de contrato no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFirma;

    private Boolean firmaContrato;

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
