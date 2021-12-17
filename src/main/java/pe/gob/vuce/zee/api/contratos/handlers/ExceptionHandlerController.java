package pe.gob.vuce.zee.api.contratos.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;


@RestController
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleValidationExceptions(NotEntityFound ex) {
        log.error("Error encontrado: ", ex);
        var responseBody = new ResponseDTO<>(-1, ex.getMessage());
        return ResponseEntity.ok(responseBody);
    }

}
