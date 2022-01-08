package pe.gob.vuce.zee.api.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO {

    private UUID id;

    private ContratoEntity contrato;

    private String nombreDocumento;

    private String rutaArchivo;

    private Integer idCliente;

    private Integer idOrganizacion;

    private Integer estado;

    private Integer activo;

    private LocalDateTime fechaCreacion;

    private UUID usuarioCreacion;

    private LocalDateTime fechaModificacion;

    private UUID usuarioModificacion;

    private String documento;
}
