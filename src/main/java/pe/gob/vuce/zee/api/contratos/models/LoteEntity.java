package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "velt_clot", schema = "vuce_zee", catalog = "zee_db")
public class LoteEntity {
    @Id
    @Column(name = "velt_clot_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    @Basic
    @Column(name = "velt_clot_nombre_lot")
    private String nombre;
    @Basic
    @Column(name = "velt_clot_referencia")
    private String referencia;
    @Basic
    @Column(name = "velt_clot_tamanio_m2")
    private BigDecimal area;
    @Basic
    @Column(name = "velt_clot_precio_lot")
    private BigDecimal precio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LoteEntity that = (LoteEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
