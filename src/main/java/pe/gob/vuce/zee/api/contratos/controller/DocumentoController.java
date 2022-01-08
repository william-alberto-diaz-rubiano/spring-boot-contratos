package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.dto.CarnetDTO;
import pe.gob.vuce.zee.api.contratos.dto.DocumentoDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;
import pe.gob.vuce.zee.api.contratos.repository.DocumentoRepository;
import pe.gob.vuce.zee.api.contratos.service.DocumentoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private DocumentoRepository documentoRepository;

    @PostMapping
    public ResponseEntity<ResponseDTO> guardarDocumento(@Valid
                                                            @RequestBody DocumentoDTO documentoDTO, BindingResult result) {

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        var nuevoDocumento = documentoService.guardar(documentoDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoDocumento, "Lista de carnet guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("documento/{documentoId}")
    public void getPhotoByUuid(@PathVariable(name = "documentoId") UUID id, HttpServletResponse response) throws IOException {
        var archivo = documentoRepository.findById(id).orElseThrow(() -> new NotEntityFound("Archivo no encontrado para este id :: " + id));
        var archivoDB = archivo.getRutaArchivo();
        var archivoValues = archivoDB.split(";");
        var contentType = archivoValues[0];
        var archivoUrl = Paths.get(archivoValues[1]);

        var separador= Pattern.quote("\\");
        String[] parts = archivo.getRutaArchivo().split(separador);
        String nombreExtension=parts[5];

        response.setHeader("Content-Disposition", "attachment; filename=" + nombreExtension);
        //response.setHeader("Content-Disposition", "inline");
        response.setContentType(contentType);
        var os = response.getOutputStream();
        var archivoData = Files.readAllBytes(archivoUrl);
        os.write(archivoData);
        os.close();
    }
}