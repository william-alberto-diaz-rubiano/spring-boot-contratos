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
@Table(name = "vecr_subs", schema = "vuce_zee", catalog = "zee_db")
public class SubastaEntity {
    @Id
    @Column(name = "vecr_subs_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_subs_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @ManyToOne
    @JoinColumn(name = "vecr_subs_codg_lotes", referencedColumnName = "velt_clot_idllave_pk")
    private LoteEntity lote;

    @Column(name = "vecr_subs_codg_bloqu")
    private UUID codigoBloque;

    @Column(name = "vecr_subs_codg_numer")
    private String numeroSubasta;

    @Column(name = "vecr_subs_fecha_inic", nullable = false)
    private LocalDateTime fechaInicial;

    @Column(name = "vecr_subs_fecha_acta", nullable = false)
    private LocalDateTime fechaActa;

    @Column(name = "vecr_subs_mont_costo", nullable = false)
    private Integer monto;

    @Column(name = "vecr_subs_mont_taman", nullable = false)
    private Integer tamano;

    @Column(name = "vecr_subs_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_subs_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_subs_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vecr_subs_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vecr_subs_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_subs_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_subs_dateupdate")
    private LocalDateTime fechaModificacion ;

    @Column(name = "vecr_subs_usr_update")
    private UUID usuarioModificacion;

}
