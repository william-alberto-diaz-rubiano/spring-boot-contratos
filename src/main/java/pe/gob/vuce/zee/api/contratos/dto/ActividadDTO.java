package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDTO {

    public UUID id;
    @NotNull(message = "El id del contrato no puede ser nulo")
    private UUID contratoId;
    @NotNull(message = "El id de la actividad no puede ser nulo")
    public UUID actividadId;
    @NotNull(message = "El id del tipo de actividad no puede ser nulo")
    public UUID tipoActividadEconomicaId;
    @NotNull(message = "El id del almacen no puede ser nulo")
    public UUID almacenId;

    @NotNull(message = "La fecha inicial no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaInicial;

    @NotNull(message = "La fecha final no puede ser nulo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFinal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaInicialPV;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFinlPV;

    public Integer idCliente;
    public Integer idOrganizacion;
    public Integer estado;
    public Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    public UUID usuarioCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    public UUID UsuarioModificacion;
}
