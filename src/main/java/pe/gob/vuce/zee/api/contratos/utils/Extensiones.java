package pe.gob.vuce.zee.api.contratos.utils;

import java.util.Arrays;
import java.util.Optional;

public enum Extensiones {

    DOC("data:application/msword;base64","doc","application/msword"),
    DOCX("data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64","docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PDF("data:application/pdf;base64","pdf","application/pdf");

    private String preBase;
    private String extencion;
    private String application;

    Extensiones(String preBase, String extencion, String application) {
        this.preBase = preBase;
        this.extencion = extencion;
        this.application = application;
    }

    public String getPreBase() {
        return preBase;
    }

    public String getExtencion() {
        return extencion;
    }

    public String getApplication() {
        return application;
    }

    public static Optional<Extensiones> getExtensionByPreBase(String preBase){
        return Arrays.stream(Extensiones.values()).
                filter(ext->ext.getPreBase().equals(preBase)).
                findFirst();
    }

}
