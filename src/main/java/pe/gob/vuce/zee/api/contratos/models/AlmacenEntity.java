package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vems_alma", schema = "vuce_zee")
public class AlmacenEntity {
    @Id
    @Column(name = "vems_alma_idllave_pk", nullable = false)
    private UUID id;

    @Column(name = "vems_alma_id_czee_fk", nullable = false)
    private UUID zeeId;

    @Column(name = "vems_alma_id_creg_fk", nullable = false)
    private UUID regimenId;

    @Column(name = "vems_alma_cliente_fk", nullable = false)
    private Integer clienteId;

    @Column(name = "vems_alma_organiz_fk", nullable = false)
    private Integer organizacionId;

    @Column(name = "vems_alma_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vems_alma_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vems_alma_datecreate", nullable = false)
    private Instant creadoEn;

    @Column(name = "vems_alma_usr_create", nullable = false)
    private UUID creadoPor;

    @Column(name = "vems_alma_dateupdate")
    private Instant actualizadoEn;

    @Column(name = "vems_alma_usr_update")
    private UUID actualizadoPor;

    @Column(name = "vems_alma_codigo_ref", nullable = false, length = 20)
    private String codigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlmacenEntity that = (AlmacenEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}