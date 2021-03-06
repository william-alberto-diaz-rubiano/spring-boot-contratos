package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vecr_lote", schema = "vuce_zee", catalog = "zee_db")
public class LoteContratoEntity {
    @Id
    @Column(name = "vecr_lote_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_lote_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @ManyToOne
    @JoinColumn(name = "vecr_lote_codg_lotes", referencedColumnName = "velt_clot_idllave_pk")
    private LoteEntity lote;

    @Column(name = "vecr_lote_blq_sel_fk", nullable = false)
    private UUID bloque;

    @Column(name = "vecr_lote_tip_mnd_fk", nullable = false)
    private UUID tipoMoneda;

    @Column(name = "vecr_lote_mont_costo", nullable = false)
    private Integer costo;

    @Column(name = "vecr_lote_mont_taman", nullable = false)
    private Integer tamano;

    @Column(name = "vecr_lote_mont_penal", nullable = false)
    private Integer montoPenal;

    @Column(name = "vecr_lote_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_lote_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_lote_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vecr_lote_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vecr_lote_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_lote_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_lote_dateupdate", nullable = false)
    private LocalDateTime fechaModificacion;

    @Column(name = "vecr_lote_usr_update", nullable = false)
    private UUID usuarioModificacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoteContratoEntity that = (LoteContratoEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
