package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vecr_ctrt", schema = "vuce_zee", catalog = "zee_db")
public class ContratoEntity {
    @Id
    @Column(name = "vecr_ctrt_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vecr_ctrt_cod_contra", nullable = false)
    private String numeroContrato;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_id_tipo_cn", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tipoContrato;

    @Column(name = "vecr_ctrt_ddocumento", nullable = false, length = 10)
    private String documento;

    @Column(name = "vecr_ctrt_fecha_inic", nullable = false)
    private LocalDate fechaInicial;

    @Column(name = "vecr_ctrt_fecha_venc", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "vecr_ctrt_fecha_acti", nullable = false)
    private LocalDate fechaInicioActividades;

    @Column(name = "vecr_ctrt_num_expedn", nullable = false, length = 50)
    private String numeroExpediente;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_motv_prorg", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity motivoProrroga;

    @Column(name = "vecr_ctrt_fech_prorg")
    private LocalDate fechaProrroga;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_motv_cance", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity motivoCancelacion;

    @Column(name = "vecr_ctrt_fech_cance")
    private LocalDate fechaCancelacion;

    @Column(name = "vecr_ctrt_cotr_posec")
    private Integer numeroContratoPosecion;

    @Column(name = "vecr_ctrt_docu_posec")
    private String documentoContratoPosecion;

    @Column(name = "vecr_ctrt_usrn_posec")
    private Integer usuarioContratoPosecion;

    @Column(name = "vecr_ctrt_cliente_fk", nullable = false)
    private Integer codigoCliente;

    @Column(name = "vecr_ctrt_organiz_fk", nullable = false)
    private Integer codigoOrganizacion;

    @Column(name = "vecr_ctrt_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vecr_ctrt_cod_active", nullable = false)
    private Integer activo;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_id_usuario", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity idUsuario;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_usr_create", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity usuarioCreacion;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_usr_update", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity usuarioModificacion;

    @Column(name = "vecr_ctrt_dateupdate")
    private Timestamp fechaModificacio;

    @Column(name = "vecr_ctrt_datecreate")
    private Timestamp fechaCreacion;

    @OneToMany(mappedBy="contrato")
    private List<ActividadEntity> actividad;

    @OneToMany(mappedBy="contrato")
    private List<AdendaEntity> adenda;

}
