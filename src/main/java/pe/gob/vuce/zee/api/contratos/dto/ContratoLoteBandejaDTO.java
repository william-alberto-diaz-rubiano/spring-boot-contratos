package pe.gob.vuce.zee.api.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoLoteBandejaDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNombre;
    private String contratoNumero;
    private Integer cantidadAdendas;
    private Integer cantidadLotes;
    private Integer cantidadTipoActividadesEconomicas;
    private Integer cantidadActividadesEconomicas;
    private Integer cantidadAlmacenes;
}
