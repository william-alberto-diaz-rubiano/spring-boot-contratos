package pe.gob.vuce.zee.api.contratos.exceptions;

public class NotEntityFound extends RuntimeException {
    private static final String DESCRIPTION = "No existe el registro solicitado";

    public NotEntityFound(String message) {
        super(String.format("%s. %s", DESCRIPTION, message));
    }
}
