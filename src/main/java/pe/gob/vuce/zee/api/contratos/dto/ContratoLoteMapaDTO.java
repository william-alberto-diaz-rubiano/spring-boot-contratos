package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContratoLoteMapaDTO {
    private UUID loteId;
    private UUID contratoId;
    private String contratoCodigo;
    private UUID adendaId;
    private Integer adendaNumero;

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
    private Integer estado;
    private BigDecimal latitud;
    private BigDecimal longitud;

}
