package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdendaDTO {
    public UUID id;
    public UUID tipoAdenda;
    public Integer numeroAdenda;
    public UUID idUsuarioZee;
    public Timestamp fechaInicial;
    public Timestamp fechaVencimiento;
    public Integer idCliente;
    public Integer idOrganizacion;
    public Integer estado;
    public Integer activo;
    public Timestamp fechaCreacion;
    public UUID idUsuario;
    public Timestamp fechaActualizacion;
    public UUID UsuarioModificacion;
}