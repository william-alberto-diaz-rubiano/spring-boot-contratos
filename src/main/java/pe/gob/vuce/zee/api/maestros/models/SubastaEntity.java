package pe.gob.vuce.zee.api.maestros.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(name = "vecr_ctrt_cod_contra", nullable = false)
    private UUID numeroContrato;

    @Column(name = "vecr_subs_codg_etapa")
    private Integer codigoEtapa;

    @Column(name = "vecr_subs_codg_manza")
    private Integer codigoManzana;

    @Column(name = "vecr_subs_codg_lotes")
    private Integer codigoLote;

    @Column(name = "vecr_subs_codg_bloqu")
    private Integer codigoBloque;

    @Column(name = "vecr_subs_codg_numer")
    private Integer numeroSubasta;

    @Column(name = "vecr_subs_fecha_inic", nullable = false)
    private Timestamp fechaInicial;

    @Column(name = "vecr_subs_fecha_acta", nullable = false)
    private Timestamp fechaActa;

    @Column(name = "vecr_subs_mont_costo", nullable = false)
    private Integer monto;

    @Column(name = "vecr_subs_mont_taman", nullable = false)
    private Integer tamano;

    @Column(name = "vecr_subs_cliente_fk", nullable = false)
    private Integer codigoCliente;

    @Column(name = "vecr_subs_organiz_fk", nullable = false)
    private Integer codigoOrganizacion;

    @Column(name = "vecr_subs_cod_estado", nullable = false)
    private Integer codigoEstado;

    @Column(name = "vecr_subs_cod_active", nullable = false)
    private String codigoActivo;

    @Column(name = "vecr_subs_datecreate", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "vecr_subs_usr_create", nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_subs_dateupdate")
    private Timestamp fechaModificacion ;

    @Column(name = "vecr_subs_usr_update")
    private UUID usuarioModificacion;

}
