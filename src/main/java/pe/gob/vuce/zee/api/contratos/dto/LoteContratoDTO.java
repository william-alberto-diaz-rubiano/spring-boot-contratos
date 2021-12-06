package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteContratoDTO {
    private UUID idContratoLote;
    private UUID contrato;
    private List<LoteDTO> lotes;
    private Integer costo;
    private Integer tamano;
    private Integer idCliente;
    private Integer idOrganizacion;
    private Integer monto;
    private Integer estado;
    private Integer activo;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    private UUID usuarioId;
    private String usuarioNombre;
    private String loteObj;
}
