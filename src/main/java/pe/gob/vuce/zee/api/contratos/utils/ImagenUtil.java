package pe.gob.vuce.zee.api.contratos.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Base64;


@Slf4j
public class ImagenUtil {

    private ImagenUtil(){
    }

    public static String saveImage(String imgBase64, String rutaAbsoluta, String nombreImg) {

        String delims = "[,]";
        String[] parts = imgBase64.split(delims);
        String imageString = parts[1];
        byte[] imageByteArray = Base64.getDecoder().decode(imageString);

        InputStream is = new ByteArrayInputStream(imageByteArray);

        String mimeType;
        String fileExtension;
        File f;
        BufferedImage image = null;
        String uploadFile;

        try {
            mimeType = URLConnection.guessContentTypeFromStream(is); //mimeType is something like "image/jpeg"
            String delimiter = "[/]";
            String[] tokens = mimeType.split(delimiter);
            fileExtension = tokens[1];
            log.info("Datos de carga decodificados : " + imageByteArray.length);
            var rutaCompleta = Paths.get(rutaAbsoluta, nombreImg + "." + fileExtension);
            uploadFile = rutaCompleta.toString();
            log.info("Ruta guardar imagen: " + uploadFile);

            try {
                image = ImageIO.read(new ByteArrayInputStream(imageByteArray));
            } catch (IOException e) {
                log.error("Ocurrió un error al leer ByteArrayInputStream imagen");
                return "";
            }
            if (image == null) {
                log.error("Buffered Imagen es nulo");
                return "";
            }
        } catch (IOException e) {
            log.error("Ocurrió un error al guardar imagen::" + e.getMessage());
            return "";
        }

        // Guardar imagen
        try {
            f = new File(uploadFile);
            log.info("Inicio guardar imagen::"+mimeType.concat(";").concat(uploadFile));
            ImageIO.write(image, fileExtension, f);

            return mimeType.concat(";").concat(uploadFile);
        } catch (IOException e) {
            log.error("Ocurrió un error al guardar imagen::" + e.getMessage());
            return "";
        }
    }

    public static void deleteImage(String rutaImagen) {
        String delims = "[;]";
        String[] parts = rutaImagen.split(delims);
        String url = parts[1];
        // Eliminar imagen
        log.info("Parseando string a file");
        File fichero = new File(url);
        log.info("Parseo a file correcto");
        log.info("Inicio de eliminar file");
        try{
            if(!fichero.delete()){
                log.info("Ocurrió un error al eliminar imagen");
            }
            log.info("File eliminado correctamente");
        }catch (Exception ex){
            log.info("Ocurrió un error al eliminar file::"+ex.getMessage());
        }
    }

}
