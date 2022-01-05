package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_actv_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_id_econ_fk", referencedColumnName = "vems_gcon_idllave_pk") // vecr_actv_id_acti_fk
    private MaestroEntity  actividad;

    @ManyToOne
    @JoinColumn(name = "vecr_actv_id_acti_fk", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tipoActividadEconomica;

    @ManyToOne(optional = true)
    @JoinColumn(name = "vecr_actv_id_alma_fk", referencedColumnName = "vems_alma_idllave_pk", nullable = true)
    // @Column(name = "vecr_actv_id_alma_fk")
    private AlmacenEntity almacen;

    @Column(name = "vecr_actv_cod_corre")
    private String codigoAlmacen;

    @Column(name = "vecr_actv_fecha_inic", nullable = false)
    private LocalDateTime fechaInicial;

    @Column(name = "vecr_actv_fecha_fina", nullable = false)
    private LocalDateTime fechaFinal;

    @Column(name = "vecr_actv_fch_ini_pv", nullable = false)
    private LocalDateTime fechaInicialPV;

    @Column(name = "vecr_actv_fch_fin_pv", nullable = false)
    private LocalDateTime fechaFinalPV;

    @Column(name = "vecr_actv_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_actv_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_actv_cod_estado", nullable = false, length = 1)
    private Integer estado;

    @Column(name = "vecr_actv_cod_active", nullable = false, length = 1)
    private Integer activo;

    @Column(name = "vecr_actv_datecreate",nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_actv_usr_create",nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_actv_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vecr_actv_usr_update")
    private UUID usuarioModificacion;
}
