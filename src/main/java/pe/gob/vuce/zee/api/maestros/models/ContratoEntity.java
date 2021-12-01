package pe.gob.vuce.zee.api.maestros.models;

import lombok.*;
import org.bouncycastle.util.Times;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
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

    @Column(name = "vecr_ctrt_id_tipo_cn", nullable = false)
    private Integer tipoContrato;

    @Column(name = "vecr_ctrt_ddocumento", nullable = false, length = 10)
    private String documento;

    @Column(name = "vems_comp_correl_act", nullable = false, length = 10)
    private String actividad;

    @Column(name = "vecr_ctrt_id_usuario", nullable = false)
    private Integer usuarioZee;

    @Column(name = "vecr_ctrt_fecha_inic", nullable = false)
    private Timestamp fechaInicial;

    @Column(name = "vecr_ctrt_fecha_venc", nullable = false)
    private Timestamp fechaVencimiento;

    @Column(name = "vecr_ctrt_fecha_acti", nullable = false)
    private Timestamp fechaInicioActividades;

    @Column(name = "vecr_ctrt_num_expedn", nullable = false, length = 50)
    private String numeroExpediente;

    @Column(name = "vecr_ctrt_motv_prorg", length = 1)
    private Integer motivoProrroga;

    @Column(name = "vecr_ctrt_fech_prorg")
    private Timestamp fechaProrroga;

    @Column(name = "vecr_ctrt_motv_cance")
    private Integer motivoCancelacion;

    @Column(name = "vecr_ctrt_fech_cance")
    private Timestamp fechaCancelacion;

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

    @Column(name = "vecr_ctrt_usr_create", nullable = false)
    private UUID codigoUsuario;

    @Column(name = "vecr_ctrt_dateupdate")
    private Timestamp fechaModificacio;

    @Column(name = "vecr_ctrt_datecreate")
    private Timestamp fechaCreacion;

    @Column(name = "vecr_ctrt_usr_update")
    private Timestamp usuarioModificacion;

}
