package pe.gob.vuce.zee.api.contratos.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.*;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;
import pe.gob.vuce.zee.api.contratos.utils.ExportarUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getContratos(@RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                                                       @RequestParam(name = "tipoContrato", required = false) UUID tipoContrato,
                                                       @RequestParam(name = "estado", required = false) Integer estado,
                                                       @RequestParam(name = "lote", required = false) UUID lote,
                                                       @RequestParam(name = "documento", required = false) String documento,
                                                       @RequestParam(name = "tipoDocumento", required = false) UUID tipoDocumento,
                                                       @RequestParam(name= "nombreUsuario",required = false) String nombreUsuario,
                                                       @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
                                                       @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad,
                                                       @RequestParam(name = "fechaInicial", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicial,
                                                       @RequestParam(name = "tipo") Integer tipo, // 1 -> normal, 2-> para seleccion
                                                       @RequestParam(name = "fechaFinal", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFinal, Pageable pageable) {

        if(numeroContrato == ""){
            numeroContrato=null;
        }
        if(documento == ""){
            documento=null;
        }
        if(nombreUsuario == ""){
            nombreUsuario=null;
        }

        if((fechaInicial != null && fechaFinal == null) || (fechaFinal !=null && fechaInicial == null)){

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicial != null && fechaFinal != null){

            if(fechaFinal.compareTo(fechaInicial) < 0){
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }

        Page<?> resultado = null;

        if(tipo == 1){
            resultado = contratoService.busquedaPorFiltrosTipoUno(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento,nombreUsuario, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        if(tipo == 2){
            resultado = contratoService.busquedaPorFiltrosTipoDos(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento,nombreUsuario, usuario, tipoActividad, fechaInicial, fechaFinal, pageable);
        }

        ResponseDTO responseBody = new ResponseDTO(resultado,"Listado de contratos");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<ResponseDTO> guardarFormularioPrincipal(@Valid @RequestBody ContratoFormularioPrincipalDTO contratoFormularioPrincipalDTO, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        String numeroExpedienteMayuscula = contratoFormularioPrincipalDTO.getNumeroExpediente().toUpperCase();
        contratoFormularioPrincipalDTO.setNumeroExpediente(numeroExpedienteMayuscula);

        ContratoFormularioPrincipalDTO nuevoContratoPrincipal = contratoService.guardarFormularioPrincipal(contratoFormularioPrincipalDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoContratoPrincipal,"Información principal guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/{contratoId}/lotes")
    public ResponseEntity<ResponseDTO> guardarSegundoFormulario(@Valid
                                                                @PathVariable("contratoId") UUID contratoId,
                                                                @RequestBody List<ContratoSegundoFormularioDTO> listaContratoLotes, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        List<ContratoSegundoFormularioDTO> nuevoListaLotesContrato = contratoService.guardarSegundoFormulario(contratoId, listaContratoLotes);

        ResponseDTO responseBody = new ResponseDTO(nuevoListaLotesContrato,"Lista de lotes guardada");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/numeroContrato")
    public ResponseEntity<ResponseDTO> codigoSistema(){
        String codigo = contratoService.numeroContrato();
        ResponseDTO responseBody = new ResponseDTO(codigo,"Codigo del sistema");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/fechasContrato/{idContrato}")
    public  ResponseEntity<ResponseDTO> fechasActividades(@PathVariable("idContrato") UUID idContrato){

     var result=contratoService.fechasContrato(idContrato);

        ResponseDTO responseBody = new ResponseDTO(result,"Fechas inicio y vencimiento del contrato");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

    @GetMapping("exportar")
    public void export(@RequestParam(name = "numeroContrato", required = false) String numeroContrato,
                        @RequestParam(name = "tipoContrato", required = false) UUID tipoContrato,
                        @RequestParam(name = "estado", required = false) Integer estado,
                        @RequestParam(name = "lote", required = false) UUID lote,
                        @RequestParam(name = "documento", required = false) String documento,
                        @RequestParam(name = "tipoDocumento", required = false) UUID tipoDocumento,
                        @RequestParam(name= "nombreUsuario",required = false) String nombreUsuario,
                        @RequestParam(name = "usuarioZEE", required = false) UUID usuario,
                        @RequestParam(name = "tipoActividad", required = false) UUID tipoActividad,
                        @RequestParam(name = "fechaInicial", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicial,
                        @RequestParam(name = "tipo") Integer tipo, // 1 -> normal, 2-> para seleccion
                        @RequestParam(name = "fechaFinal", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFinal,
                        @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
                        HttpServletResponse response) throws IOException
    {
        if (!formato.equalsIgnoreCase("xls") && !formato.equalsIgnoreCase("xlsx") && !formato.equalsIgnoreCase("csv"))
        {
            throw new BadRequestException("FAILED", HttpStatus.NOT_ACCEPTABLE, "Formato '" + formato + "' no admitido'");
        }

        if(numeroContrato == "")
        {
            numeroContrato = null;
        }
        if(documento == "")
        {
            documento=null;
        }
        if(nombreUsuario == "")
        {
            nombreUsuario=null;
        }

        if((fechaInicial != null && fechaFinal == null) || (fechaFinal !=null && fechaInicial == null))
        {

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicial != null && fechaFinal != null)
        {
            if(fechaFinal.compareTo(fechaInicial) < 0)
            {
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }

        String[] columnas = null;
        List<String[]> data = null;

        if(tipo == 1)
        {
            List<ContratoBandejaDTO> resultado = contratoService.busquedaPorFiltrosTipoUno(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento,nombreUsuario, usuario, tipoActividad, fechaInicial, fechaFinal);
            columnas = new String[]{"USUARIO", "NO. CONTRATO", "TIPO DE CONTRATO", "ESTADO", "CANT. LOTES", " CANT. ACTIVIDADES"};
            data = resultado.stream().map(x -> new String[]{x.getUsuarioNombre(),
                                                            x.getNumeroContrato(),
                                                            x.getTipoContratoDescripcion(),
                                                            x.getEstadoDescripcion(),
                                                            x.getCantidadLotes().toString(),
                                                            x.getCantidadActividades().toString()}
                                        ).collect(Collectors.toList());
        }

        if(tipo == 2)
        {
            List<ContratoMinimalDTO> resultado = contratoService.busquedaPorFiltrosTipoDos(null,numeroContrato, tipoContrato, estado, lote, documento, tipoDocumento,nombreUsuario, usuario, tipoActividad, fechaInicial, fechaFinal);
            columnas = new String[]{"NO. CONTRATO"};
            data = resultado.stream().map(x -> new String[]{x.getNumeroContrato()}).collect(Collectors.toList());
        }

        if (columnas != null && data != null)
        {
            var contentDispositionTmpl = "attachment; filename=%s";
            if (formato.equalsIgnoreCase("csv"))
            {
                response.setContentType(Constantes.CONTENT_TYPE_CSV);
                var contentDisposition = String.format(contentDispositionTmpl, "contratos.csv");
                response.setHeader("Content-Disposition", contentDisposition);
                ExportarUtil.crearCSV(data, columnas, response.getWriter());
            }
            else if (formato.equalsIgnoreCase("xls") || formato.equalsIgnoreCase("xlsx"))
            {
                response.setContentType(Constantes.CONTENT_TYPE_XLSX);
                var contentDisposition = String.format(contentDispositionTmpl, "contratos.xlsx");
                response.setHeader("Content-Disposition", contentDisposition);
                ExportarUtil.crearExcel(data, "Contratos", "Contratos", columnas, response.getOutputStream());
            }
        }
        else
        {
            throw new BadRequestException("FAILED", HttpStatus.NO_CONTENT, "Nada que retornar");
        }
    }


}
