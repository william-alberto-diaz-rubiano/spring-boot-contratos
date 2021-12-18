package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContratoDTO {
    private UUID id;
    private String numeroContrato;
    private MaestroDTO tipoContrato;
    private String documento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioActividades;
    private String numeroExpediente;
    private MaestroDTO motivoProrroga;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaProrroga;
    private MaestroDTO motivoCancelacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCancelacion;
    private Integer numeroContratoPosecion;
    private String documentoContratoPosecion;
    private Integer usuarioContratoPosecion;
    private Integer codigoCliente;
    private Integer codigoOrganizacion;
    private Integer estado;
    private Integer activo;
    private PersonaDTO idUsuario;
    private PersonaDTO usuarioCreacion;
    private PersonaDTO usuarioModificacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaModificacio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;
    private Integer flag;
    private List<ActividadDTO> actividad;
    private List<AdendaDTO> adenda;
}
