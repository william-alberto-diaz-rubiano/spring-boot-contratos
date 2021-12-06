package pe.gob.vuce.zee.api.maestros.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.consumer.LoteAPI;
import pe.gob.vuce.zee.api.maestros.dto.LoteContratoDTO;
import pe.gob.vuce.zee.api.maestros.dto.LoteDTO;
import pe.gob.vuce.zee.api.maestros.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.maestros.repository.ContratoLoteRepository;
import pe.gob.vuce.zee.api.maestros.service.ContratoLoteService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContratoLoteServiceImpl implements ContratoLoteService {

    private final LoteAPI loteAPI;
    private final ContratoLoteRepository contratoLoteRepository;

    public LoteDTO buscarPorId(UUID uuid) throws IOException {
        var response = loteAPI.getLote(uuid).execute();
        var errorMsg = "";
        if (response.code() == HttpStatus.OK.value()) {
            var body = response.body();
            if (body != null) {
                if (body.getCodigo() == 0) {
                    return body.getData();
                } else {
                    errorMsg = String.format("Código de error: %d mensaje: %s", body.getCodigo(), body.getMensaje());
                }
            } else {
                errorMsg = String.format("El servicio no ha retornado ningún resultado para id: %s", uuid);
            }
        } else {
            errorMsg = String.format("ResponseCode: %d", response.code());
            log.error(errorMsg);
        }

        throw new EntityNotFoundException(errorMsg);
    }

    @Override
    public LoteContratoDTO findByContrato(UUID idContrato) throws IOException {
        List<LoteContratoEntity> listContrato = contratoLoteRepository.finByContrato(idContrato);
        List<LoteDTO> lotes = new ArrayList<>();
        LoteContratoDTO loteContratoDTO = new LoteContratoDTO();
        if(listContrato.size() != 0){
            LoteContratoEntity loteContratoEntity = listContrato.get(0);
            loteContratoDTO.setContrato(loteContratoEntity.getIdContrato());
            loteContratoDTO.setCosto(loteContratoEntity.getCosto());
            loteContratoDTO.setActivo(loteContratoEntity.getActivo());
            loteContratoDTO.setEstado(loteContratoEntity.getEstado());
            loteContratoDTO.setFechaModificacion(loteContratoEntity.getFechaModificacion());
            loteContratoDTO.setFechaCreacion(loteContratoEntity.getFechaCreacion());
            loteContratoDTO.setIdOrganizacion(loteContratoEntity.getIdOrganizacion());
            loteContratoDTO.setMonto(loteContratoEntity.getMontoPenal());
            loteContratoDTO.setIdCliente(loteContratoEntity.getIdCliente());
            loteContratoDTO.setUsuarioId(loteContratoEntity.getUsuarioCreacion());
            loteContratoDTO.setUsuarioNombre("DEMO");
            for (LoteContratoEntity loteobj:listContrato) {
               LoteDTO loteDTO = buscarPorId(loteobj.getIdLote());
               lotes.add(loteDTO);
            }
            loteContratoDTO.setLotes(lotes);
            return loteContratoDTO;
        }else {
            throw new EntityNotFoundException("No existe el contrato buscado");
        }

    }

    @Override
    public LoteContratoDTO findbyId(UUID idContratoLote) throws IOException {
        LoteContratoDTO loteContratoDTO = new LoteContratoDTO();
        List<LoteDTO> lotes = new ArrayList<>();
        LoteContratoEntity loteContratoEntity = new LoteContratoEntity();
        loteContratoEntity=contratoLoteRepository.getById(idContratoLote);
        if(loteContratoEntity != null){
            loteContratoDTO.setContrato(loteContratoEntity.getIdContrato());
            loteContratoDTO.setCosto(loteContratoEntity.getCosto());
            loteContratoDTO.setActivo(loteContratoEntity.getActivo());
            loteContratoDTO.setEstado(loteContratoEntity.getEstado());
            loteContratoDTO.setFechaModificacion(loteContratoEntity.getFechaModificacion());
            loteContratoDTO.setFechaCreacion(loteContratoEntity.getFechaCreacion());
            loteContratoDTO.setIdOrganizacion(loteContratoEntity.getIdOrganizacion());
            loteContratoDTO.setMonto(loteContratoEntity.getMontoPenal());
            loteContratoDTO.setIdCliente(loteContratoEntity.getIdCliente());
            loteContratoDTO.setUsuarioId(loteContratoEntity.getUsuarioCreacion());
            loteContratoDTO.setUsuarioNombre("DEMO");
            LoteDTO loteDTO = buscarPorId(loteContratoEntity.getIdLote());
            loteContratoDTO.setLotes(lotes);
            return loteContratoDTO;
        }else {
            throw new EntityNotFoundException("No existe la asociacion buscada buscado");
        }

    }

    @Override
    public LoteContratoDTO deleteLoteContrato(LoteContratoDTO loteContratoDTO){
        LoteContratoEntity loteContratoEntity = contratoLoteRepository.getById(loteContratoDTO.getIdContratoLote());
        contratoLoteRepository.delete(loteContratoEntity);
        return loteContratoDTO;
    }

    @Override
    public LoteContratoDTO crearLoteContrato(LoteContratoDTO loteContratoDTO) throws IOException {
        List<LoteDTO> loteDTOS = loteContratoDTO.getLotes();
        LoteContratoEntity loteContratoEntity = new LoteContratoEntity();
        List<LoteContratoEntity> loteContratoSave= new ArrayList<>();
        for (LoteDTO loteobj:loteDTOS
             ) {
            loteContratoEntity.setIdContrato(loteContratoDTO.getIdContratoLote());
            loteContratoEntity.setIdLote(loteobj.getId());
            loteContratoEntity.setCosto(loteContratoDTO.getCosto());
            loteContratoEntity.setActivo(1);
            loteContratoEntity.setEstado(1);
            loteContratoEntity.setTamano(loteContratoDTO.getTamano());
            loteContratoEntity.setIdCliente(loteContratoDTO.getIdCliente());
            loteContratoEntity.setMontoPenal(loteContratoDTO.getMonto());
            loteContratoEntity.setIdOrganizacion(loteContratoDTO.getIdOrganizacion());
            loteContratoEntity.setUsuarioCreacion(loteContratoDTO.getUsuarioId());
            loteContratoEntity.setUsuarioModificacion(loteContratoDTO.getUsuarioId());
            loteContratoSave.add(loteContratoEntity);
        }
        contratoLoteRepository.saveAll(loteContratoSave);
        return findByContrato(loteContratoDTO.getContrato());
    }
}
