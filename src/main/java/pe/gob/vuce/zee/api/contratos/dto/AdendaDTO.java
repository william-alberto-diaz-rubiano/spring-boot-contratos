package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdendaDTO {
    public UUID id;
    public UUID tipoAdenda;
    public Integer numeroAdenda;
    public UUID idUsuarioZee;
    public LocalDate fechaInicial;
    public LocalDate fechaVencimiento;
    public Integer idCliente;
    public Integer idOrganizacion;
    public Integer estado;
    public Integer activo;
    public LocalDate fechaCreacion;
    public UUID idUsuario;
    public LocalDate fechaActualizacion;
    public UUID UsuarioModificacion;
}
