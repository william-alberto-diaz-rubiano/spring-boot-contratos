package pe.gob.vuce.zee.api.contratos.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "velt_remb", schema = "vuce_zee", catalog = "zee_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EtapaManzanaBloqueEntity {
    @Id
    @Column(name = "velt_remb_idllave_pk")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    @Basic
    @Column(name = "velt_remb_cod_tipor")
    private Integer tipo;
    @Basic
    @Column(name = "velt_remb_des_nombre")
    private String nombre;
    @Basic
    @Column(name = "velt_remb_referencia")
    private String referencia;
    @Basic
    @Column(name = "velt_remb_tamanio_m2")
    private BigDecimal area;
    @Basic
    @Column(name = "velt_remb_cliente_fk")
    private Integer clienteId;
    @Basic
    @Column(name = "velt_remb_organiz_fk")
    private Integer organizacionId;
    @Basic
    @Column(name = "velt_remb_cod_estado")
    private Integer estado;
    @Basic
    @Column(name = "velt_remb_cod_active")
    private Integer activo;
    @Basic
    @Column(name = "velt_remb_datecreate")
    private LocalDateTime timestampCreate;
    @Basic
    @Column(name = "velt_remb_usr_create")
    private Integer uidCreate;
    @Basic
    @Column(name = "velt_remb_dateupdate")
    private LocalDateTime timestampUpdate;
    @Basic
    @Column(name = "velt_remb_usr_update")
    private Integer uidUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EtapaManzanaBloqueEntity that = (EtapaManzanaBloqueEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
