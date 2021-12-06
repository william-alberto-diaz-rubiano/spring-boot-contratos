package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EtapaManzanaBloqueDTO {
    private UUID id;
    @JsonIgnore
    private int tipo;
    private String nombre;
    private String referencia;
    private BigDecimal area;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp timestampCreate;
    private Integer uidCreate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp timestampUpdate;
    private Integer uidUpdate;

}
