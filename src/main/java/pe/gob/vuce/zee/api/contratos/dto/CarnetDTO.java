package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarnetDTO {

    private UUID id;

    private UUID contratoId;

    //@NotNull(message = "El numero del carnet no puede ser nulo")
    private String numeroCarnet;

    //@NotNull(message = "El codigo del motico no puede ser nulo")
    private UUID motivoCarnetId;

    //@NotNull(message = "El tipo de carnet no puede ser nulo")
    private UUID tipoCarnetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull(message = "El fecha de entrega del carnet no puede ser nulo")
    private LocalDateTime fechaEntregaCarnet;

    //@NotNull(message = "El id del representante legal no puede ser nulo puede ser nulo")
    private UUID representanteLegalId;

    private String observacion;

    private String rutaFoto;

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

    private String imagen;
}
