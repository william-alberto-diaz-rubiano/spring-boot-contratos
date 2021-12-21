package pe.gob.vuce.zee.api.contratos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    private int codigo;
    private String mensaje;
    private T data;
    private List<?> list;
    private String status;
    private UUID id;
    private Object object;

    public ResponseDTO(int codigo, T data) {
        this.codigo = codigo;
        this.data = data;
    }

    public ResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ResponseDTO(Object object, String mensaje, String status, UUID id) {
        this.object = object;
        this.mensaje = mensaje;
        this.status = status;
        this.id = id;
    }

    public ResponseDTO(T data, String mensaje) {
        this.data = data;
        this.mensaje = mensaje;
    }
}
