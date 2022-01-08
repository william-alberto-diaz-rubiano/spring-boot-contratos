package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarnetDTO {

    private UUID id;

    private UUID contratoId;

    private String numeroCarnet;

    private UUID motivoCarnetId;

    private UUID tipoCarnetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEntregaCarnet;

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
