package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.MaestroEntity;
import pe.gob.vuce.zee.api.contratos.models.PersonaEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActividadDTO {
    public UUID id;
    public MaestroDTO idActividad;
    public Timestamp fechaInicial;
    public Timestamp fechaFinl;
    public Integer idCliente;
    public Integer idOrganizacion;
    public Integer estado;
    public Integer activo;
    public Timestamp fechaCreacionActividad;
    public PersonaDTO idUsuarioActividad;
    public Timestamp fechaActualizacionActividad;
    public PersonaDTO UsuarioModificacionActividad;
    public MaestroDTO tipoActividadEconomica;
}
