package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteDTO
{
    private UUID id;
    private EtapaManzanaBloqueDTO etapa;
    private EtapaManzanaBloqueDTO manzana;
    private String nombre;
    private String referencia;
    private BigDecimal area;
    private BigDecimal precio;
    private String polylines;
    private Integer clienteId;
    private BigDecimal zoomMapa;
    private Integer organizacionId;
    private BigDecimal estado;
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

    public LoteDTO(LoteEntity loteEntity)
    {
        this.id = loteEntity.getId();
        this.nombre = loteEntity.getNombre();
        this.referencia = loteEntity.getReferencia();
        this.area = loteEntity.getArea();
        this.precio = loteEntity.getPrecio();
        this.polylines = loteEntity.getPolylines();
        this.zoomMapa = loteEntity.getZoomMapa();
        this.estado = loteEntity.getEstado();
        this.longitud = loteEntity.getLongitud().toString();
        this.latitud = loteEntity.getLatitud().toString();
        this.manzanaNombre = loteEntity.getManzana().getNombre();
        this.etapaNombre = loteEntity.getEtapa().getNombre();

        this.etapa = new EtapaManzanaBloqueDTO(loteEntity.getEtapa());
        this.manzana = new EtapaManzanaBloqueDTO(loteEntity.getManzana());

        // FIXME: De donde saco esta informacion
        //this.organizacionId = ;
        //this.clienteId = ;
        //this.activo = ;
        //this.timestampCreate = ;
        //this.uidCreate = ;
        //this.timestampUpdate = ;
        //this.uidUpdate = ;
        //this.bloques = ;
        //this.bloqueName = ;
    }

    static public List<LoteDTO> loteEntity2LoteDTO(List<LoteEntity> loteEntityList)
    {
        List<LoteDTO> loteDTOList = new ArrayList<>();
        for (LoteEntity loteEntity : loteEntityList)
        {
            loteDTOList.add(new LoteDTO(loteEntity));
        }
        return loteDTOList;
    }
}
