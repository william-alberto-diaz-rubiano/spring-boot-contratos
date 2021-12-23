package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteContratoDTO {

    private UUID idContratoLote;
    private ContratoDTO contrato;
    private List<LoteDTO> lotes;
    private Integer costo;
    private Integer tamano;
    private Integer idCliente;
    private Integer idOrganizacion;
    private Integer monto;
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    private UUID usuarioId;
    private String usuarioNombre;
    private LoteDTO lote;
    private UUID idLote;
}
