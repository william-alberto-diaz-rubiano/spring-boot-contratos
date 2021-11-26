package pe.gob.vuce.zee.api.maestros.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vems_comp", schema = "vuce_zee", catalog = "zee_db")
public class ContratoEntity {
    @Id
    @Column(name = "vems_comp_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vems_comp_cod_tipocb", nullable = false)
    private Integer tipoContrato;

    @Column(name = "vems_comp_codi_serie", nullable = false)
    private String codigoSerie;

    @Column(name = "vems_comp_correlativ", nullable = false, length = 10)
    private String correlativo;

    @Column(name = "vems_comp_correl_act", nullable = false, length = 10)
    private String actividad;

    @Column(name = "vems_comp_cliente_fk", nullable = false)
    private Integer clienteId;

    @Column(name = "vems_comp_organiz_fk", nullable = false)
    private String adenda;

    @Column(name = "vems_comp_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vems_comp_datecreate", nullable = false, length = 50)
    private Timestamp fechaCreacion;

    @Column(name = "vems_comp_usr_create", nullable = false, length = 50)
    private UUID usuarioCreacion;

    @Column(name = "vems_comp_usr_update", nullable = false, length = 50)
    private UUID usuarioUpdate;

    @Column(name = "vems_comp_dateupdate", nullable = false, length = 50)
    private Timestamp fechaActualizacion;

}
