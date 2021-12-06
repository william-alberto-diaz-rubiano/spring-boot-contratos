package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
