package pe.gob.vuce.zee.api.contratos.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;


@RestController
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(NotEntityFound.class)
    public ResponseEntity<ResponseDTO<?>> handleValidationExceptions(NotEntityFound ex) {
        var responseBody = new ResponseDTO<>(-1, ex.getMessage());
        return ResponseEntity.ok(responseBody);
    }

}
