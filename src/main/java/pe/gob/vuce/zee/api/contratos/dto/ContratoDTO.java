package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
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
    private Timestamp fechaInicial;
    private Timestamp fechaVencimiento;
    private Timestamp fechaInicioActividades;
    private String numeroExpediente;
    private MaestroDTO motivoProrroga;
    private Timestamp fechaProrroga;
    private MaestroDTO motivoCancelacion;
    private Timestamp fechaCancelacion;
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
    private Timestamp fechaModificacio;
    private Timestamp fechaCreacion;
    private Integer flag;
}
