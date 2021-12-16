package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteMapaDTO {
    private UUID id;
    private String nombre;
    private String referencia;

    private UUID etapaId;
    private String etapaNombre;
    private String etapaReferencia;
    private BigDecimal etapaArea;

    private UUID manzanaId;
    private String manzanaNombre;
    private String manzanaReferencia;
    private BigDecimal manzanaArea;

    private String polylines;
    private Integer zoomMapa;
}
