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
@Table(name = "vecr_larc", schema = "vuce_zee", catalog = "zee_db")
public class DocumentoEntity {

    @Id
    @Column(name = "vecr_larc_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vecr_larc_id_cont_fk", referencedColumnName = "vecr_ctrt_idllave_pk")
    private ContratoEntity contrato;

    @Column(name = "vecr_larc_nombre_doc")
    private String nombreDocumento;

    @Column(name = "vecr_larc_ruta_archi")
    private String rutaArchivo;

    @Column(name = "vecr_larc_cliente_fk", nullable = false)
    private Integer idCliente;

    @Column(name = "vecr_larc_organiz_fk", nullable = false)
    private Integer idOrganizacion;

    @Column(name = "vecr_larc_cod_estado", nullable = false, length = 1)
    private Integer estado;

    @Column(name = "vecr_larc_cod_active", nullable = false, length = 1)
    private Integer activo;

    @Column(name = "vecr_larc_datecreate",nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vecr_larc_usr_create",nullable = false)
    private UUID usuarioCreacion;

    @Column(name = "vecr_larc_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vecr_larc_usr_update")
    private UUID usuarioModificacion;
}
