package pe.gob.vuce.zee.api.contratos.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.contratos.dto.ErrorDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;

import javax.servlet.http.HttpServletRequest;


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

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDTO> badRequestExceptionHandler(HttpServletRequest request, BadRequestException ex) {
        ErrorDTO error = ErrorDTO.builder().code(ex.getCode()).path(request.getRequestURI()).statusValue(ex.getStatus().value()).errors(ex.getErrors()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

}
