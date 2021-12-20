package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pe.gob.vuce.zee.api.contratos.models.ActividadEntity;
import pe.gob.vuce.zee.api.contratos.models.AdendaEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoPrincipalDTO {

    private UUID id;

    @Size(max = 50,message = "El numero de contrato soporta maximo 50 caracteres")
    @NotNull(message = "El numero del contrato no puede ser nulo")
    private String numeroContrato;

    @NotNull(message = "El tipo del contrato no puede ser nulo")
    private UUID tipoContratoId;

    @Size(max = 20,message = "El documento soporta maximo 20 caracteres")
    @NotNull(message = "El documento no puede ser nulo")
    private String documento;

    @NotNull(message = "El UUID del usuario no puede ser nulo")
    private UUID usuarioId;

    @NotNull(message = "La fecha inicial no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicial;

    @NotNull(message = "La fecha de vencimiento no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    @NotNull(message = "La fecha de inicio de actividades no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioActividades;

    @Size(max = 10,message = "El numero de expediente soporta maximo 10 caracteres")
    @NotNull(message = "El numero de expediente no puede ser nulo")
    private String numeroExpediente;

    private UUID motivoProrrogaId;

    private LocalDate fechaProrroga;

    private UUID motivoCancelacionId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCancelacion;

    private Integer numeroContratoPosecion;

    @Size(max = 10,message = "El numero de expediente soporta maximo 10 caracteres")
    private String documentoContratoPosecion;
    private UUID usuarioContratoPosecionId;

    private Integer codigoCliente;
    private Integer codigoOrganizacion;

    private UUID estadoId;

    private Integer activo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;
    private UUID usuarioCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaModificacion;
    private UUID usuarioModificacion;

}
