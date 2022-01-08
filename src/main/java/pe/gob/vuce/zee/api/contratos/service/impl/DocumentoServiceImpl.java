package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.dto.DocumentoDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.exceptions.NotEntityFound;
import pe.gob.vuce.zee.api.contratos.models.DocumentoEntity;
import pe.gob.vuce.zee.api.contratos.repository.DocumentoRepository;
import pe.gob.vuce.zee.api.contratos.service.DocumentoService;
import pe.gob.vuce.zee.api.contratos.utils.ArchivoUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${spring.properties.url-file-path.contrato.archivos}")
    private String urlPath;

    @Override
    public DocumentoDTO guardar(DocumentoDTO documentoDTO) {
        var idContrato=documentoDTO.getContrato().getId();

        if(idContrato == null){
            throw new NotEntityFound("Debe existir un contrato registrado");
        }
         var documento=new DocumentoEntity();

        String nombreArchivo = documentoDTO.getNombreDocumento();
        var parts = documentoDTO.getNombreDocumento().split("\\.");
        var nombre = parts[0];
        var extension = parts[parts.length - 1];

        Path dirImg = Paths.get(urlPath);
        Path pathAbsoluta = dirImg.toAbsolutePath();
        if (!Files.exists(pathAbsoluta)) {
            if(!new File(pathAbsoluta.toString()).mkdirs()){
                throw new BadRequestException("Error al leer la imagen");
            }
        }
        String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

        var rutaCompleta = Paths.get(rutaAbsoluta, nombreArchivo);

        int cont = 1;
        while (Files.exists(rutaCompleta)){
            nombreArchivo = nombre.concat(" (").concat(String.valueOf(cont)).concat(")").concat(".").concat(extension);
            rutaCompleta = Paths.get(rutaAbsoluta, nombreArchivo);
            cont++;
        }
        // Guardar imagen
        var ruta = ArchivoUtil.saveArchivo(documentoDTO.getDocumento(), rutaAbsoluta, nombreArchivo);
        documento.setRutaArchivo(ruta);
        documento.setNombreDocumento(nombreArchivo);
        var documentoEntity=documentoRepository.save(documento);


        return modelMapper.map(documentoEntity,DocumentoDTO.class);
    }
}
