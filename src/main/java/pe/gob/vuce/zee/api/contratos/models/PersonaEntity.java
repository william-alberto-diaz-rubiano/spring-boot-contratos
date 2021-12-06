package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Table(name = "vepr_pers", schema = "vuce_zee")
@Entity
@Data
@ToString
public class PersonaEntity {

    @Id
    @Column(name = "vepr_pers_idllave_pk", nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepr_pers_cod_tipous")
    private MaestroEntity tipoUsuarioCreacion;

    @ManyToOne
    @JoinColumn(name = "vepr_pers_cod_catego")
    private MaestroEntity tipoCategoria;

    @ManyToOne
    @JoinColumn(name = "vepr_pers_cod_person")
    private MaestroEntity tipoPersona;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepr_pers_cod_tdocum")
    private MaestroEntity tipoDocumento;

    @Column(name = "vepr_pers_ddocumento", length = 20)
    private String numeroDocumento;

    @Column(name = "vepr_pers_desnombres", length = 50)
    private String nombre;

    @Column(name = "vepr_pers_apellido_p", length = 20)
    private String apellidoP;

    @Column(name = "vepr_pers_apellido_m", length = 20)
    private String apellidoM;

    @Column(name = "vepr_pers_cod_persna")
    private Integer codigoPersonal;

    @ManyToOne
    @JoinColumn(name = "vepr_pers_id_usuario")
    private MaestroEntity tipoUsuarioRegistro;

    @Column(name = "vepr_pers_cliente_fk", nullable = false)
    private Integer idCliente = 1;

    @Column(name = "vepr_pers_organiz_fk", nullable = false)
    private Integer idOrganizacion = 1;

    @Column(name = "vepr_pers_cod_estado", nullable = false)
    private Integer estado = 1;

    @Column(name = "vepr_pers_cod_active", nullable = false)
    private Integer activo = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "vepr_pers_datecreate", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "vepr_pers_usr_create", nullable = false)
    private Integer idUsuarioCreacion = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "vepr_pers_dateupdate")
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @Column(name = "vepr_pers_usr_update")
    private Integer idUsuarioModificacion = 0;


}

