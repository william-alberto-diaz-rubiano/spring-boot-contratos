package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteContratoDetalleDTO {
    private UUID id;
    private UUID loteId;
    private String loteNombre;
    private UUID tipoActividadId;
    private String tipoActividadDescripcion;
    private UUID actividadId;
    private String actividadDescripcion;
    private UUID almacenId;
    private String almacenCodigo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioPv;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinPv;
}
