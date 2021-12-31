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
@Table(name = "vecr_repl", schema = "vuce_zee", catalog = "zee_db")
public class RepresentanteLegalContratoEntity {

    @Id
    @Column(name = "vecr_repl_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_repl_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_repl_id_relg_fk", referencedColumnName = "vepr_relg_idllave_pk")
    private RepresentanteLegalEntity representanteLegal;

    @Column(name = "vecr_repl_fecha_firm", nullable = false)
    private LocalDateTime fechaFirma;

    @Column(name = "vecr_repl_check_firm")
    private Boolean firmaContrato;

    @Column(name = "vecr_repl_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_repl_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_repl_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vecr_repl_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vecr_repl_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_repl_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_repl_dateupdate")
    private LocalDateTime fechaModificacion ;

    @Column(name = "vecr_repl_usr_update")
    private UUID usuarioModificacion;

}
