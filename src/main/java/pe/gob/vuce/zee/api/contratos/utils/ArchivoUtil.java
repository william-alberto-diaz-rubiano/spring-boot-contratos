package pe.gob.vuce.zee.api.contratos.utils;

import lombok.extern.slf4j.Slf4j;
import pe.gob.vuce.zee.api.contratos.exceptions.ConflictException;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ArchivoUtil {

    private ArchivoUtil() {
    }

    public static String saveArchivo(String imgBase64, String rutaAbsoluta, String nombreImg) {

        String delims = "[,]";
        String[] parts = imgBase64.split(delims);
        String preBase = parts[0];
        String base64String = parts[1];
        var extension = Extensiones.getExtensionByPreBase(preBase);

        AtomicReference<String> locacion = new AtomicReference<>("");
        extension.map(extensiones -> {
            byte[] data = DatatypeConverter.parseBase64Binary(base64String);
            String fileExtension = extensiones.getExtencion();
            var rutaCompleta = Paths.get(rutaAbsoluta, nombreImg);
            File file = new File(rutaCompleta.toString());
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                outputStream.write(data);
                locacion.set(extensiones.getApplication().concat(";").concat(rutaCompleta.toString()));
            } catch (IOException e) {
                log.error("Ocurrió un error al guardar documento::" + e.getMessage());
            }
            return extensiones;
        }).orElseThrow(() -> new ConflictException("Extension de archivo no permitida"));

        return locacion.get();
    }

}
