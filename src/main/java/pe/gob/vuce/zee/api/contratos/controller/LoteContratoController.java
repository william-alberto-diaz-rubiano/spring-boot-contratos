package pe.gob.vuce.zee.api.contratos.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.contratos.consumer.utils.PageWrapper;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoDetalleDTO;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoFilterDTO;
import pe.gob.vuce.zee.api.contratos.dto.LoteDTO;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.service.impl.LoteContratoServiceImpl;
import pe.gob.vuce.zee.api.contratos.service.impl.LoteServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/lotes-contratos")
public class LoteContratoController
{
    private static final Log LOGGER = LogFactory.getLog(LoteContratoController.class);

    @Autowired
    @Qualifier("loteContratoServiceImpl")
    private LoteContratoServiceImpl loteContratoService;

    @GetMapping(value = "/filter")
    public ResponseEntity filter()
    {
        try
        {
            List<LoteContratoFilterDTO> filterList = loteContratoService.loteContratoFilter();
            if (filterList == null || filterList.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(filterList);
        }
        catch (Throwable error)
        {
            LOGGER.error(error.getMessage());
            throw error;
        }
    }

}
