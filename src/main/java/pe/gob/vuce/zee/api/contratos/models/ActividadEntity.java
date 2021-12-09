package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vecr_actv", schema = "vuce_zee", catalog = "zee_db")
public class ActividadEntity {
    @Id
    @Column(name = "vecr_actv_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_id_acti_fk", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity idActividad;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @Column(name = "vecr_actv_fecha_inic", nullable = false)
    private Timestamp fechaInicial;

    @Column(name = "vecr_actv_fecha_fina", nullable = false)
    private Timestamp fechaFinl;

    @Column(name = "vecr_actv_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_actv_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_actv_cod_estado", nullable = false, length = 1)
    private Integer estado;

    @Column(name = "vecr_actv_cod_active", nullable = false, length = 1)
    private Integer activo;

    @Column(name = "vecr_actv_datecreate",nullable = false)
    private Timestamp fechaCreacionActividad;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_usr_create", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity idUsuarioActividad;

    @Column(name = "vecr_actv_dateupdate")
    private Timestamp fechaActualizacionActividad;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_usr_update", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity UsuarioModificacionActividad;
}
