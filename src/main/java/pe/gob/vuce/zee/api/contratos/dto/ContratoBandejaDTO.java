package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoBandejaDTO {

    private UUID id;
    private String tipoContratoDescripcion;
    private String numeroContrato;
    private String usuarioNombre;
    private Integer cantidadLotes;
    private Integer cantidadActividades;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaInicioActividades;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaInicial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaVencimiento;
    private UUID motivoProrrogaDescripcion;
    private Integer estado;
    private String estadoDescripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicioAdenda;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimientoAdenda;
    private String estadoAdendaDescripcion;
}
