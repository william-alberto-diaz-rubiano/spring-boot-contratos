package pe.gob.vuce.zee.api.contratos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.dto.CarnetDTO;
import pe.gob.vuce.zee.api.contratos.models.CarnetEntity;
import pe.gob.vuce.zee.api.contratos.repository.CarnetRepository;
import pe.gob.vuce.zee.api.contratos.service.CarnetService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CarnetServiceImpl implements CarnetService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarnetRepository carnetRepository;

    @Override
    public CarnetDTO guardarFormularioCarnet(CarnetDTO carnetDTO) {

            carnetDTO.setEstado(1);
            carnetDTO.setActivo(Constantes.HABILITADO);
            carnetDTO.setIdCliente(1);
            carnetDTO.setIdOrganizacion(1);
            carnetDTO.setFechaCreacion(LocalDateTime.now());
            carnetDTO.setFechaModificacion(LocalDateTime.now());
            carnetDTO.setUsuarioCreacion(Constantes.UID_TEST);
            carnetDTO.setUsuarioModificacion(Constantes.UID_TEST);

            CarnetEntity carnetEntity= modelMapper.map(carnetDTO, CarnetEntity.class);



       var nuevoCarnet=carnetRepository.save(carnetEntity);

        return modelMapper.map(nuevoCarnet,CarnetDTO.class);
    }

    @Override
    public String numeroCarnet() {

        Integer codigoMayor;
        Integer codigoNumero;
        List<CarnetEntity> listaContratos= carnetRepository.findAll();


        List<Integer> listadoCodigos = new ArrayList<>();

        for(CarnetEntity carnetEntity : listaContratos){
            String cod = carnetEntity.getNumeroCarnet();
            String cadenaNumerica = cod.substring(5);
            Integer codigoInteger = Integer.parseInt(cadenaNumerica);
            listadoCodigos.add(codigoInteger);
        }

        if(listadoCodigos.isEmpty()){
            codigoMayor = 1;
            codigoNumero = codigoMayor;
        }else{
            codigoMayor= Collections.max(listadoCodigos);
            codigoNumero = codigoMayor + 1;
        }

        String codigoFormateado=String.format("%05d", codigoNumero);

        return "CARN" +"-" + codigoFormateado;
    }
}
