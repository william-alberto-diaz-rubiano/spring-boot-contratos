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
@Table(name = "vepr_relg", schema = "vuce_zee", catalog = "zee_db")
public class RepresentanteLegalEntity {

    @Id
    @Column(name = "vepr_relg_idllave_pk", nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepr_relg_id_pers_fk", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity persona;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepr_relg_cod_tdocum", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tipoDocumento;

    @Column(name = "vepr_relg_ddocumento", nullable = false, length = 11)
    private String numeroDocumento;

    @Column(name = "vepr_relg_desnombres", nullable = false, length = 50)
    private String nombre;

    @Column(name = "vepr_relg_apellido_p", nullable = false, length = 50)
    private String apellidoP;

    @Column(name = "vepr_relg_apellido_m", nullable = false, length = 50)
    private String apellidoM;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepr_relg_cod_cargos", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity cargos;

    @Column(name = "vepr_relg_fecha_inic", nullable = false)
    private LocalDateTime fechaInicial;

    @Column(name = "vepr_relg_fecha_fina")
    private LocalDateTime fechaFinal;

    @Column(name = "vepr_relg_ruta_foto", length = 250)
    private String rutaFoto;

    @Column(name = "vepr_relg_cliente_fk", nullable = false)
    private Integer idCliente = 1;

    @Column(name = "vepr_relg_organiz_fk", nullable = false)
    private Integer idOrganizacion = 1;

    @Column(name = "vepr_relg_cod_estado", nullable = false)
    private Integer estado = 0;

    @Column(name = "vepr_relg_cod_active", nullable = false)
    private Integer activo = 0;

    @Column(name = "vepr_relg_datecreate", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "vepr_relg_usr_create", nullable = false)
    private Integer idUsuarioCreacion = 0;

    @Column(name = "vepr_relg_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vepr_relg_usr_update")
    private Integer idUsuarioModificacion = 0;

}
