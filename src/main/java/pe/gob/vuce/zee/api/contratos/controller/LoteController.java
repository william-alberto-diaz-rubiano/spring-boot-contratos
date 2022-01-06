package pe.gob.vuce.zee.api.contratos.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.consumer.utils.PageWrapper;
import pe.gob.vuce.zee.api.contratos.dto.ContratoFormularioPrincipalDTO;
import pe.gob.vuce.zee.api.contratos.dto.ContratoSegundoFormularioDTO;
import pe.gob.vuce.zee.api.contratos.dto.LoteDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import pe.gob.vuce.zee.api.contratos.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.contratos.models.EtapaManzanaBloqueEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.service.ContratoService;
import pe.gob.vuce.zee.api.contratos.service.impl.LoteServiceImpl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/lotes")
public class LoteController
{
    private static final Log LOGGER = LogFactory.getLog(LoteController.class);

    @Autowired
    @Qualifier("loteServiceImpl")
    private LoteServiceImpl loteService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity find(@RequestParam(name = "nombre", required = false) String nombre,
                               @RequestParam(name = "referencia", required = false) String referencia,
                               @RequestParam(name = "area", required = false) BigDecimal area,
                               @RequestParam(name = "precio", required = false) BigDecimal precio,
                               @RequestParam(name = "latitud", required = false) BigDecimal latitud,
                               @RequestParam(name = "longitud", required = false) BigDecimal longitud,
                               @RequestParam(name = "zoomMapa", required = false) BigDecimal zoomMapa,
                               @RequestParam(name = "estado", required = false) BigDecimal estado,
                               @RequestParam(name = "polylines", required = false) String polylines,
                               @RequestParam(name = "order", required = false) String order,
                               @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                               @RequestParam(name = "itemsPerPage", required = false) Integer itemsPerPage)
    {
        try
        {
            List<String> filterList = new ArrayList<String>();
            if (nombre != null && !nombre.isEmpty())
            {
                filterList.add("nombre=" + nombre);
            }
            if (referencia != null && !referencia.isEmpty())
            {
                filterList.add("referencia=" + referencia);
            }
            if (area != null)
            {
                filterList.add("area=" + area);
            }
            if (precio != null)
            {
                filterList.add("precio=" + precio);
            }
            if (latitud != null)
            {
                filterList.add("latitud=" + latitud);
            }
            if (longitud != null)
            {
                filterList.add("longitud=" + longitud);
            }
            if (zoomMapa != null)
            {
                filterList.add("zoomMapa=" + zoomMapa);
            }
            if (estado != null)
            {
                filterList.add("estado=" + estado);
            }
            if (polylines != null && !polylines.isEmpty())
            {
                filterList.add("polylines=" + polylines);
            }

            if (pageNumber == null && itemsPerPage == null)
            {
                List<LoteEntity> entityList = loteService.findAll(filterList, order);
                if (entityList == null || entityList.isEmpty())
                {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(LoteDTO.loteEntity2LoteDTO(entityList));
            }

            pageNumber = (pageNumber == null) ? 0 : pageNumber; // valores por defecto
            itemsPerPage = (itemsPerPage == null) ? 5 : itemsPerPage; // valores por defecto
            if (pageNumber < 0 || itemsPerPage <= 0)
            {
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Not paginated been worth.");
            }
            Page<LoteEntity> entityPage = loteService.findAll(filterList, order, pageNumber, itemsPerPage);

            List<LoteDTO> pageContent = LoteDTO.loteEntity2LoteDTO(entityPage.getContent());
            //Page<LoteDTO> dtoPage = new PageImpl<LoteDTO>(pageContent, PageRequest.of(entityPage.getNumber(), entityPage.getSize(), entityPage.getSort()), entityPage.getSize());

            PageWrapper<LoteDTO> dtoPage = new PageWrapper(pageContent, entityPage);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(dtoPage);
        }
        catch (Throwable error)
        {
            LOGGER.error(error.getMessage());
            throw error;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") UUID id)
    {
        try
        {
            LoteEntity entity = loteService.findById(id);
            if (entity == null)
            {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("Not found Device with id " + id);
            }
            return ResponseEntity.ok(new LoteDTO(entity));
        }
        catch (Throwable error)
        {
            LOGGER.error(error.getMessage());
            throw error;
        }
    }

}
