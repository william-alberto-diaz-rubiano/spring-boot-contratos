package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vecr_carn", schema = "vuce_zee", catalog = "zee_db")
public class CarnetEntity {

    @Id
    @Column(name = "vecr_carn_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_carn_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @Column(name = "vecr_carn_num_carnet")
    private String numeroCarnet;

    @Column(name = "vecr_carn_cod_mot_fk")
    private UUID codigoMotivo;

    @Column(name = "vecr_carn_id_tipo_cr")
    private UUID tipoCarnet;

    @Column(name = "vecr_carn_fecha_entr", nullable = false)
    private LocalDateTime fechaEntregaCarnet;

    @ManyToOne
    @JoinColumn(name = "vecr_carn_id_repr_fk", referencedColumnName = "vecr_repl_idllave_pk")
    private RepresentanteLegalContratoEntity representanteLegal;

    @Column(name = "vecr_carn_observacio", nullable = false)
    private String observacion;

    @Column(name = "vecr_carn_ruta_fotos", nullable = false)
    private String rutaFoto;

    @Column(name = "vecr_carn_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_carn_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_carn_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vecr_carn_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vecr_carn_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_carn_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_carn_dateupdate")
    private LocalDateTime fechaModificacion ;

    @Column(name = "vecr_carn_usr_update")
    private UUID usuarioModificacion;
}
