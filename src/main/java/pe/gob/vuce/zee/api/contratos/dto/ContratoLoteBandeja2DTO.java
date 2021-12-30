package pe.gob.vuce.zee.api.contratos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoLoteBandeja2DTO {
    private UUID id;
    private String contratoTipo;
    private String contratoNumero;
    private Integer cantidadAdendas;
    private Integer loteCantidad;
    private BigDecimal costo;
    private BigDecimal tamanio;
}
