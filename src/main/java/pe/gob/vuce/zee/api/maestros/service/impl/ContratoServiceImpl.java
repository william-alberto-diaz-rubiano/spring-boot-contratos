package pe.gob.vuce.zee.api.maestros.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.MaestroDTO;
import pe.gob.vuce.zee.api.maestros.exceptions.NotEntityFound;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.repository.ContratoRepository;
import pe.gob.vuce.zee.api.maestros.service.ContratoService;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ContratoServiceImpl implements ContratoService {
    private final ModelMapper modelMapper;
    private final ContratoRepository contratoRepository;

    public ContratoServiceImpl(ModelMapper modelMapper, ContratoRepository contratoRepository) {
        this.modelMapper = modelMapper;
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<ContratoEntity> findAll(){
        return contratoRepository.findAll();
    }

    @Override
    public  ContratoEntity createContrato(ContratoEntity contrato){
        return contratoRepository.save(contrato);
    }

    @Override
    public List<ContratoEntity> finByClienteId(Integer clientId){
        return contratoRepository.findByClientId(clientId);
    }

    @Override
    public Page<ContratoEntity> finByCorrelativo(String numeroContrato, Integer tipoContrato, Integer estado,
                                                 Timestamp fechaInicio, Timestamp fechaFinal, Pageable pageable){
        Page<ContratoEntity> contratoList = contratoRepository.busquedaPageable(numeroContrato,tipoContrato,estado,fechaInicio,fechaFinal,pageable);
       return contratoList;
    }

    @Override
    public List<ContratoEntity> finByUsuario(){
       //todo implementar
        return null;
    }

}
