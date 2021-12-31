package pe.gob.vuce.zee.api.contratos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.consumer.LoteAPI;
import pe.gob.vuce.zee.api.contratos.dto.*;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteRepository;
import pe.gob.vuce.zee.api.contratos.repository.LoteRepository;
import pe.gob.vuce.zee.api.contratos.service.ContratoLoteService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContratoLoteServiceImpl implements ContratoLoteService {

    @Autowired
    private ModelMapper modelMapper;

    private final LoteAPI loteAPI;

    @Autowired
    private ContratoLoteRepository contratoLoteRepository;

    private final LoteRepository loteRepository;


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
        if (listContrato.size() != 0) {
            LoteContratoEntity loteContratoEntity = listContrato.get(0);
            loteContratoDTO.setContrato(
                    modelMapper.map(loteContratoEntity.getContrato(),
                            ContratoDTO.class));
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
//            for (LoteContratoEntity loteobj:listContrato) {
//               LoteDTO loteDTO = buscarPorId(loteobj.getIdLote());
//               lotes.add(loteDTO);
//            }
            loteContratoDTO.setLotes(lotes);
            return loteContratoDTO;
        } else {
            throw new EntityNotFoundException("No existe el contrato buscado");
        }

    }

    @Override
    public LoteContratoDTO findbyId(UUID idContratoLote) throws IOException {
        LoteContratoDTO loteContratoDTO = new LoteContratoDTO();
        List<LoteDTO> lotes = new ArrayList<>();
        LoteContratoEntity loteContratoEntity = new LoteContratoEntity();
        loteContratoEntity = contratoLoteRepository.getById(idContratoLote);
        if (loteContratoEntity != null) {
            loteContratoDTO.setContrato(modelMapper.map(loteContratoEntity.getContrato(),
                    ContratoDTO.class));
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
//            LoteDTO loteDTO = buscarPorId(loteContratoEntity.getIdLote());
            loteContratoDTO.setLotes(lotes);
            return loteContratoDTO;
        } else {
            throw new EntityNotFoundException("No existe la asociacion buscada buscado");
        }

    }

    @Override
    public LoteContratoDTO deleteLoteContrato(LoteContratoDTO loteContratoDTO) {
        LoteContratoEntity loteContratoEntity = contratoLoteRepository.getById(loteContratoDTO.getIdContratoLote());
        contratoLoteRepository.delete(loteContratoEntity);
        return loteContratoDTO;
    }

    @Override
    public LoteContratoDTO crearLoteContrato(LoteContratoDTO loteContratoDTO) throws IOException {
        List<LoteDTO> loteDTOS = loteContratoDTO.getLotes();
        LoteContratoEntity loteContratoEntity = new LoteContratoEntity();
        List<LoteContratoEntity> loteContratoSave = new ArrayList<>();
        for (LoteDTO loteobj : loteDTOS
        ) {
            loteContratoEntity.setContrato(modelMapper.map(loteContratoDTO.getContrato(),
                    ContratoEntity.class));
//            loteContratoEntity.setIdLote(loteobj.getId());
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
        return findByContrato(loteContratoDTO.getContrato().getId());
    }

    @Override
    public Page<LoteContratoDTO> findAll(Pageable pageable) {
        List<LoteContratoEntity> listado = contratoLoteRepository.findAll();
        List<LoteContratoDTO> dtos = listado
                .stream()
                .map(contrato -> modelMapper.map(contrato, LoteContratoDTO.class))
                .collect(Collectors.toList());
        List<LoteContratoDTO> contratosDTO = new ArrayList<>();
        for (LoteContratoDTO loteContratoDTO : dtos
        ) {
            try {
                loteContratoDTO.setLote(buscarPorId(loteContratoDTO.getIdLote()));
                contratosDTO.add(loteContratoDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new PageImpl<>(contratosDTO, pageable, listado.size());
    }

    @Override
    public Page<ContratoLoteBandejaDTO> busquedaAvanzada(String numeroContrato, UUID loteId, UUID contratoId, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, Pageable pageable) {
        return contratoLoteRepository.busquedaAvanzada1(numeroContrato, loteId, contratoId, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica, pageable);
    }

    @Override
    public Page<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, Pageable pageable) {
        return contratoLoteRepository.busquedaAvanzada2(usuarioId, contratoId, adendaId, loteId, pageable);
    }

    @Override
    public Page<LoteContratoDetalleDTO> detalleByContrato(UUID contratoId, Pageable pageable) {
        var resultEntity = contratoLoteRepository.findByContratoAndActivo(contratoId, Constantes.HABILITADO, pageable);
        List<LoteContratoDetalleDTO> resultDto = new ArrayList<>();
        for (var loteContratoEntity : resultEntity) {
            var dto = modelMapper.map(loteContratoEntity, LoteContratoDetalleDTO.class);
            for (var actividadEntity : loteContratoEntity.getContrato().getActividad()) {
                dto.setActividadId(actividadEntity.getId());
                dto.setActividadDescripcion(actividadEntity.getActividad().getDescripcion());
                dto.setTipoActividadId(actividadEntity.getTipoActividadEconomica().getId());
                dto.setTipoActividadDescripcion(actividadEntity.getTipoActividadEconomica().getDescripcion());
                dto.setAlmacenId(actividadEntity.getAlmacen());
                //dto.setAlmacenCodigo(actividadEntity.getAlmacen());
                dto.setFechaInicio(loteContratoEntity.getContrato().getFechaInicial());
                dto.setFechaFin(loteContratoEntity.getContrato().getFechaVencimiento());
                dto.setFechaInicioPv(actividadEntity.getFechaInicial());
                dto.setFechaFinPv(actividadEntity.getFechaInicial());
            }
            resultDto.add(dto);
        }
        return new PageImpl<>(resultDto, pageable, resultEntity.getTotalElements());
    }

    @Override
    public Page<ContratoLoteMapaDTO> buscarLotesPorContrato(UUID contratoID, Pageable pageable) {
        var resultEntity = contratoLoteRepository.findByContratoAndActivo(contratoID, Constantes.HABILITADO, pageable);
        List<ContratoLoteMapaDTO> resultDto = new ArrayList<>();
        for (var entity : resultEntity) {
            var dto = modelMapper.map(entity.getLote(), ContratoLoteMapaDTO.class);
            dto.setContratoId(entity.getContrato().getId());
            dto.setContratoCodigo(entity.getContrato().getNumeroContrato());
            var usuario = entity.getContrato().getUsuario();
            var usuarioNombre = Optional.ofNullable(usuario.getNombre()).orElse("");
            var usuarioApellidoPaterno = Optional.ofNullable(usuario.getApellidoP()).orElse("");
            var usuarioApellidoMaterno = Optional.ofNullable(usuario.getApellidoM()).orElse("");
            var usuarioNombreCompleto = String.join(" ", usuarioApellidoPaterno, usuarioApellidoMaterno, usuarioNombre);
            dto.setUsuarioId(usuario.getId());
            dto.setUsuarioNombre(usuarioNombreCompleto.trim());
            var adenda = entity.getContrato().getAdenda().stream().sorted((x, y) -> Integer.compare(y.getNumeroAdenda(), x.getNumeroAdenda())).findFirst();
            adenda.ifPresent(x -> {
                dto.setAdendaId(x.getId());
                dto.setAdendaNumero(x.getNumeroAdenda());
            });
            resultDto.add(dto);
        }
        return new PageImpl<>(resultDto, pageable, resultEntity.getTotalElements());
    }

    @Override
    public List<ContratoLoteMapaDTO> busquedaAvanzadaMapa(UUID contratoId, String numeroContrato, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividadId, UUID actividadEconomicaId) {
        var resultEntity = contratoLoteRepository.busquedaAvanzadaMapa(contratoId, numeroContrato, usuarioId, numeroAdenda,
                numeroLote, tipoActividadId, actividadEconomicaId);
        List<ContratoLoteMapaDTO> resultDto = new ArrayList<>();
        for (var entity : resultEntity) {
            var dto = modelMapper.map(entity.getLote(), ContratoLoteMapaDTO.class);
            dto.setContratoId(entity.getContrato().getId());
            dto.setContratoCodigo(entity.getContrato().getNumeroContrato());
            var usuario = entity.getContrato().getUsuario();
            var usuarioNombre = Optional.ofNullable(usuario.getNombre()).orElse("");
            var usuarioApellidoPaterno = Optional.ofNullable(usuario.getApellidoP()).orElse("");
            var usuarioApellidoMaterno = Optional.ofNullable(usuario.getApellidoM()).orElse("");
            var usuarioNombreCompleto = String.join(" ", usuarioApellidoPaterno, usuarioApellidoMaterno, usuarioNombre);
            dto.setUsuarioId(usuario.getId());
            dto.setUsuarioNombre(usuarioNombreCompleto.trim());
            var adenda = entity.getContrato().getAdenda().stream().sorted((x, y) -> Integer.compare(y.getNumeroAdenda(), x.getNumeroAdenda())).findFirst();
            adenda.ifPresent(x -> {
                dto.setAdendaId(x.getId());
                dto.setAdendaNumero(x.getNumeroAdenda());
            });
            resultDto.add(dto);
        }
        return resultDto;
    }

    @Override
    public List<ContratoLoteBandejaDTO> busquedaAvanzada1(String numeroContrato, UUID loteId, UUID contratoId, UUID usuarioId, Integer numeroAdenda, String numeroLote, UUID tipoActividad, UUID actividadEconomica, int offset, int size) {
        return contratoLoteRepository.busquedaAvanzada1(numeroContrato, loteId, contratoId, usuarioId, numeroAdenda, numeroLote, tipoActividad, actividadEconomica, offset, size);
    }

    @Override
    public List<ContratoLoteBandeja2DTO> busquedaAvanzada2(UUID usuarioId, UUID contratoId, UUID adendaId, UUID loteId, int offset, int size) {
        return contratoLoteRepository.busquedaAvanzada2(usuarioId, contratoId, adendaId, loteId, offset, size);
    }
}
