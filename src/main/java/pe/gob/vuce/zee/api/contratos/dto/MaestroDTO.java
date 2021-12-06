package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.MaestroEntity;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class MaestroDTO {
    private UUID id;
    private Integer prefijo;
    private Integer correlativo;
    private String descripcion;
    private String abreviatura;
    private Integer estado;
    private Integer activo;

    public MaestroDTO(MaestroEntity maestros) {
        if (maestros != null) {
            this.id = maestros.getId();
            this.prefijo = maestros.getPrefijo();
            this.correlativo = maestros.getCorrelativo();
            this.descripcion = maestros.getDescripcion();
            this.abreviatura = maestros.getAbreviatura();
            this.estado = maestros.getEstado();
            this.activo = maestros.getActivo();
        }
    }

    public MaestroEntity toEntity() {
        var entity = new MaestroEntity();
        entity.setId(this.id);
        return entity;
    }
}

