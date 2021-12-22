package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    @Column(name = "vecr_ctrt_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vecr_ctrt_cod_contra")
    private String numeroContrato;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_id_tipo_cn", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tipoContrato;

    @Column(name = "vecr_ctrt_ddocumento", nullable = false, length = 10)
    private String documento;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_id_usuario", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity usuario;

    @Column(name = "vecr_ctrt_fecha_inic", nullable = false)
    private LocalDateTime fechaInicial;

    @Column(name = "vecr_ctrt_fecha_venc", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(name = "vecr_ctrt_fecha_acti", nullable = false)
    private LocalDateTime fechaInicioActividades;

    @Column(name = "vecr_ctrt_num_expedn", nullable = false, length = 50)
    private String numeroExpediente;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_motv_prorg", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity motivoProrroga;

    @Column(name = "vecr_ctrt_fech_prorg")
    private LocalDateTime fechaProrroga;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_motv_cance", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity motivoCancelacion;

    @Column(name = "vecr_ctrt_fech_cance")
    private LocalDateTime fechaCancelacion;

    @Column(name = "vecr_ctrt_cotr_posec")
    private Integer numeroContratoPosecion;

    @Column(name = "vecr_ctrt_docu_posec")
    private String documentoContratoPosecion;

    @Column(name = "vecr_ctrt_cod_estado", nullable = false)
    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "vecr_ctrt_usrn_posec", referencedColumnName = "vepr_pers_idllave_pk")
    private PersonaEntity usuarioContratoPosecion;

    @Column(name = "vecr_ctrt_cliente_fk", nullable = false)
    private Integer codigoCliente;

    @Column(name = "vecr_ctrt_organiz_fk", nullable = false)
    private Integer codigoOrganizacion;

    @Column(name = "vecr_ctrt_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vecr_ctrt_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_ctrt_usr_update", nullable = false)
    private UUID usuarioModificacion;

    @Column(name = "vecr_ctrt_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vecr_ctrt_datecreate")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy="contrato")
    @ToString.Exclude
    private List<ActividadEntity> actividad;

    @OneToMany(mappedBy="contrato")
    @ToString.Exclude
    private List<AdendaEntity> adenda;

}
