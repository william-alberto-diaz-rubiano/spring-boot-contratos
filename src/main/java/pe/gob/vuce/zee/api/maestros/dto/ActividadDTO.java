package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActividadDTO {
    public UUID idActividad;
    public UUID idContrato;
    public UUID idTipoActividad;
    public UUID idActividadEconomica;
    public UUID idAlmacen;
    public Timestamp fechaInicial;
    public Timestamp fechaFinal;
    public Integer idCliente;
    public Integer idOrganzacion;
    public Integer estado;
    public Integer activo;
    public Timestamp fechaCreacion;
    public UUID usuarioCreacion;
    public Timestamp fechaModificacion;
    public UUID usuarioModificacion;
}
