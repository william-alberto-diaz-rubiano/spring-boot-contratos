package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.contratos.models.PersonaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PersonaDTO {

    private UUID id;
    private MaestroDTO tipoUsuarioCreacion;
    private MaestroDTO tipoCategoria;
    private MaestroDTO tipoPersona;
    private MaestroDTO tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private Integer codigoPersonal;
    private MaestroDTO tipoUsuarioRegistro;
    private Integer idCliente;
    private Integer idOrganizacion;
    private Integer estado;
    private Integer activo;
    @JsonIgnore
    private LocalDateTime fechaCreacion;
    @JsonIgnore
    private Integer idUsuarioCreacion;
    @JsonIgnore
    private LocalDateTime fechaModificacion;
    @JsonIgnore
    private Integer idUsuarioModificacion;

    public PersonaDTO(PersonaEntity entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.tipoUsuarioCreacion = new MaestroDTO(entity.getTipoUsuarioCreacion());
            this.tipoCategoria = new MaestroDTO(entity.getTipoCategoria());
            this.tipoPersona = new MaestroDTO(entity.getTipoPersona());
            this.tipoDocumento = new MaestroDTO(entity.getTipoDocumento());
            this.numeroDocumento = entity.getNumeroDocumento();
            this.nombre = entity.getNombre();
            this.apellidoP = entity.getApellidoP();
            this.apellidoM = entity.getApellidoM();
            this.codigoPersonal = entity.getCodigoPersonal();
            this.tipoUsuarioRegistro = new MaestroDTO(entity.getTipoUsuarioRegistro());
            this.idCliente = entity.getIdCliente();
            this.idOrganizacion = entity.getIdOrganizacion();
            this.estado = entity.getEstado();
            this.activo = entity.getActivo();
            this.fechaCreacion = entity.getFechaCreacion();
            this.idUsuarioCreacion = entity.getIdUsuarioCreacion();
            this.fechaModificacion = entity.getFechaModificacion();
            this.idUsuarioModificacion = entity.getIdUsuarioModificacion();
        }
    }

    public PersonaEntity toEntity() {
        var entity = new PersonaEntity();
        entity.setId(this.id);
        return entity;
    }
}

