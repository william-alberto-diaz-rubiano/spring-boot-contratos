package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vead_aden", schema = "vuce_zee", catalog = "zee_db")

public class AdendaEntity {
    @Id
    @Column(name = "vead_aden_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vead_aden_id_tipo_fk", nullable = false)
    private UUID tipoAdenda;

    @Column(name = "vead_aden_numeroaden", nullable = false)
    private Integer numeroAdenda;

    @ManyToOne
    @JoinColumn(name = "vead_aden_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @Column(name = "vead_aden_id_usrn_fk", nullable = false)
    private UUID idUsuarioZee;

    @Column(name = "vead_aden_fecha_inic", nullable = false)
    private LocalDate fechaInicial;

    @Column(name = "vead_aden_fecha_venc", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "vead_aden_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vead_aden_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vead_aden_cod_estado", nullable = false, length = 1)
    private Integer estado;

    @Column(name = "vead_aden_cod_active", nullable = false, length = 1)
    private Integer activo;

    @Column(name = "vead_aden_datecreate",nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "vead_aden_usr_create",nullable = false)
    private UUID idUsuario;

    @Column(name = "vead_aden_dateupdate")
    private LocalDate fechaActualizacion;

    @Column(name = "vead_aden_usr_update")
    private UUID UsuarioModificacion;


}
