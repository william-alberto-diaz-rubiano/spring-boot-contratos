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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime fechaInicioActividades;
    private Integer cantidadLotes;
    private Integer cantidadActividades;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime fechaInicial;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime fechaVencimiento;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime fechaProrroga;
    private UUID motivoProrrogaDescripcion;
    private Integer estado;
    private String estadoDescripcion;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate inicioAdenda;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate vencimientoAdenda;
    private String estadoAdendaDescripcion;
}
