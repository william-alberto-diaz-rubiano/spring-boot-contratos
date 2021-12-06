package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteDTO {
    private UUID id;
    private EtapaManzanaBloqueDTO etapa;
    private EtapaManzanaBloqueDTO manzana;
    private String nombre;
    private String referencia;
    private BigDecimal area;
    private BigDecimal precio;
    private String polylines;
    private Integer clienteId;
    private Integer zoomMapa;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestampCreate;
    private Integer uidCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestampUpdate;
    private Integer uidUpdate;
    @JsonIgnoreProperties(value = "lote", allowSetters = true)
    private List<LoteBloqueDTO> bloques;
    private String bloqueName;
    private String longitud;
    private String latitud;
    private String manzanaNombre;
    private String etapaNombre;
}
