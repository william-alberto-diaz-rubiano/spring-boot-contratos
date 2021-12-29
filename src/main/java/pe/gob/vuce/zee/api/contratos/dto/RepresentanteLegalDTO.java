package pe.gob.vuce.zee.api.contratos.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepresentanteLegalDTO {

    private UUID id;

    private UUID contratoId;

    @NotNull(message = "El id de la actividad no puede ser nulo")
    private UUID tipoDocumento;

    @NotNull(message = "El id de la actividad no puede ser nulo")
    private String numeroDocumento;

    @NotNull(message = "El id de la actividad no puede ser nulo")
    private String nombres;

    @NotNull(message = "El id de la actividad no puede ser nulo")
    private String apellidoPaterno;

    private String apellidoMaterno;

    @NotNull(message = "El id de la actividad no puede ser nulo")
    private UUID codigoCargo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaInicial;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFinal;

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
