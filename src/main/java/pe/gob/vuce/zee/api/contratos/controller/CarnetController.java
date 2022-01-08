package pe.gob.vuce.zee.api.contratos.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.CarnetDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;
import pe.gob.vuce.zee.api.contratos.repository.CarnetRepository;
import pe.gob.vuce.zee.api.contratos.service.CarnetService;
import pe.gob.vuce.zee.api.contratos.utils.ImagenUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("v1/carnet")
public class CarnetController {

    @Autowired
    private CarnetService carnetService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarnetRepository carnetRepository;

    @PostMapping
    public ResponseEntity<ResponseDTO> guardarTercerFormulario(@Valid
                                                               @RequestBody CarnetDTO carnetDTO, BindingResult result) {

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        var nuevoCarnet = carnetService.guardarFormularioCarnet(carnetDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoCarnet, "Lista de carnet guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }


    @Value("${spring.properties.url-file-path.contrato.carnet}")
    private String urlPath;


    @PutMapping("/carnetImagen/{carnetId}")
    public ResponseEntity<ResponseDTO> agregarFoto(@PathVariable(name = "carnetId") UUID carnetId, @Valid @RequestBody CarnetDTO carnetDTO) {

        var carnet = carnetRepository.findById(carnetId).orElseThrow(() -> new NotEntityFound("Carnet no encontrado para el id :: " + carnetId));

        // Guardar archivo
        Path dirImg = Paths.get(urlPath);
        Path pathAbsoluta = dirImg.toAbsolutePath();
        if (!Files.exists(pathAbsoluta)) {
            if(!new File(pathAbsoluta.toString()).mkdirs()){
                throw new BadRequestException("Error al leer la imagen");
            }
        }
        String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

        // Guardar imagen
        var ruta = ImagenUtil.saveImage(carnetDTO.getImagen(), rutaAbsoluta, carnetId.toString());
        carnet.setRutaFoto(ruta);

        var result = carnetRepository.save(carnet);
        var resultDTO=modelMapper.map(result,CarnetDTO.class);

        ResponseDTO responseBody = new ResponseDTO(resultDTO,"Imagen guardada");

        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

    @GetMapping("descargarImagen/{carnet-id}")
    public void descargarImagen(@PathVariable(name = "carnet-id") UUID id, HttpServletResponse response) throws IOException {
        var carnet = carnetRepository.findById(id).orElseThrow(() -> new NotEntityFound("Carnet no encontrado para el id :: " + id));
        var dirImagen = carnet.getRutaFoto();

        if (dirImagen != null && !StringUtils.isBlank(dirImagen)) {
            var photoDB = String.format(dirImagen, id);
            var photoValues = photoDB.split(";");
            var contentType = photoValues[0];
            var photoUrl = Paths.get(photoValues[1]);

            var separador= Pattern.quote("\\");
            String[] parts = carnet.getRutaFoto().split(separador);
            String nombreExtension=parts[5];

            response.setHeader("Content-Disposition", "attachment; filename=" + nombreExtension);
            response.setContentType(contentType);
            var os = response.getOutputStream();
            var photoData = Files.readAllBytes(photoUrl);
            os.write(photoData);
            os.close();
        }
    }

    @GetMapping("verImagen/{carnet-id}")
    public void verImagen(@PathVariable(name = "carnet-id") UUID id, HttpServletResponse response) throws IOException {
        var carnet = carnetRepository.findById(id).orElseThrow(() -> new NotEntityFound("Carnet no encontrado para el id :: " + id));
        var dirImagen = carnet.getRutaFoto();

        if (dirImagen != null && !StringUtils.isBlank(dirImagen)) {
            var photoDB = String.format(dirImagen, id);
            var photoValues = photoDB.split(";");
            var contentType = photoValues[0];
            var photoUrl = Paths.get(photoValues[1]);

            var separador= Pattern.quote("\\");
            String[] parts = carnet.getRutaFoto().split(separador);
            String nombreExtension=parts[5];

            response.setHeader("Content-Disposition", "inline");
            response.setContentType(contentType);
            var os = response.getOutputStream();
            var photoData = Files.readAllBytes(photoUrl);
            os.write(photoData);
            os.close();
        }
    }

    @DeleteMapping("eliminarImagen/{carnet-id}")
    public ResponseEntity<ResponseDTO> eliminarImagen(@PathVariable(name = "carnetId") UUID id){
        var imagen=carnetRepository.findById(id).orElseThrow(() -> new NotEntityFound("Imagen no encontrada para este ID ::" + id));
        var imgRutaAbsoluta=imagen.getRutaFoto();

        ResponseDTO responseBody;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        if (!imgRutaAbsoluta.equalsIgnoreCase("")) {
            try {
                ImagenUtil.deleteImage(imgRutaAbsoluta);
                imagen.setFechaModificacion(LocalDateTime.now());

                var resultDTO= carnetRepository.save(imagen);

                responseBody = new ResponseDTO(resultDTO,"Imagen eliminada");
                httpStatus=HttpStatus.OK;

            } catch (Exception ex) {
                log.info("Ocurrió un error al eliminar file::" + ex.getMessage());
                responseBody = new ResponseDTO("ERROR","Imagen eliminada");
            }
        } else {
            responseBody = new ResponseDTO("ERROR","No cuenta con archivo");
        }

        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/numeroCarnet")
    public ResponseEntity<ResponseDTO> numeroCarnet(){
        String codigo = carnetService.numeroCarnet();
        ResponseDTO responseBody = new ResponseDTO(codigo,"numero carnet");

        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }
}
