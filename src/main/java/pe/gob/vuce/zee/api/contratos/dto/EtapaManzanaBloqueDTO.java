package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.EtapaManzanaBloqueEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EtapaManzanaBloqueDTO
{
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

    public EtapaManzanaBloqueDTO(EtapaManzanaBloqueEntity etapaManzanaBloqueEntity)
    {
        this.id = etapaManzanaBloqueEntity.getId();
        this.tipo = etapaManzanaBloqueEntity.getTipo();
        this.nombre = etapaManzanaBloqueEntity.getNombre();
        this.referencia = etapaManzanaBloqueEntity.getReferencia();
        this.area = etapaManzanaBloqueEntity.getArea();
        this.clienteId = etapaManzanaBloqueEntity.getClienteId();
        this.organizacionId = etapaManzanaBloqueEntity.getOrganizacionId();
        this.estado = etapaManzanaBloqueEntity.getEstado();
        this.activo = etapaManzanaBloqueEntity.getActivo();
        this.uidCreate = etapaManzanaBloqueEntity.getUidCreate();
        this.uidUpdate = etapaManzanaBloqueEntity.getUidUpdate();

        // FIXME: De donde saco esta informacion
        //this.timestampUpdate = ;
        //this.timestampCreate = ;
    }
}
