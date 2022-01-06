package pe.gob.vuce.zee.api.contratos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.base.Constantes;
import pe.gob.vuce.zee.api.contratos.consumer.LoteAPI;
import pe.gob.vuce.zee.api.contratos.dto.*;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.repository.ContratoLoteRepository;
import pe.gob.vuce.zee.api.contratos.repository.LoteRepository;
import pe.gob.vuce.zee.api.contratos.repository.LoteSpecification;
import pe.gob.vuce.zee.api.contratos.repository.impl.LoteCustomRepositoryImpl;
import pe.gob.vuce.zee.api.contratos.service.ContratoLoteService;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service("loteServiceImpl")
@RequiredArgsConstructor
public class LoteServiceImpl
{
    @Autowired
    @Qualifier("loteCustomRepositoryImpl")
    private LoteCustomRepositoryImpl loteCustomRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static Sort getSortOrder(@NotNull String order)
    {
        if (order == null || order.isEmpty())
        {
            return null;
        }

        Sort.Direction direction = Sort.DEFAULT_DIRECTION;
        String fieldname = order;

        if (order.charAt(0) == '-')
        {
            direction = Sort.Direction.DESC;
            fieldname = order.substring(1);
        }
        else
        {
            if (order.charAt(0) == '+')
            {
                //direction = Sort.Direction.ASC;
                fieldname = order.substring(1);
            }
        }

        return Sort.by(direction, fieldname);
    }

    public List<LoteEntity> findAll(List<String> filterList, String order)
    {
        try
        {
            List<LoteEntity> loteEntities = new ArrayList<>();

            if (filterList != null && !filterList.isEmpty())
            {
                Specification specification = new LoteSpecification().getSpecificationByAndOperator(filterList);
                if (order == null || order.isEmpty())
                {
                    loteEntities = loteCustomRepository.findAll(specification);
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    loteEntities = loteCustomRepository.findAll(specification, sortOrder);
                }
            }
            else
            {
                if (order == null || order.isEmpty())
                {
                    loteEntities = loteCustomRepository.findAll();
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    loteEntities = loteCustomRepository.findAll(sortOrder);
                }
            }

//            List<LoteDTO> dtos = new ArrayList<>();
//            for (LoteEntity loteEntity : loteEntities)
//            {
//                dtos.add(modelMapper.map(loteEntity, LoteDTO.class));
//            }

//            List<LoteDTO> dtos = loteEntity
//                        .stream()
//                        .map(contrato -> modelMapper.map(contrato, LoteDTO.class))
//                        .collect(Collectors.toList()));

//            var resultDTO = loteEntities.stream().map(x -> modelMapper.map(x, LoteDTO.class)).collect(Collectors.toList());

            return loteEntities;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }

    public Page<LoteEntity> findAll(List<String> filterList, String order, Integer pageNumber, Integer itemsPerPage)
    {
        try
        {
            Page<LoteEntity> lotePage = null;

            if (filterList != null && !filterList.isEmpty())
            {
                Specification specification = new LoteSpecification().getSpecificationByAndOperator(filterList);
                if (order == null || order.isEmpty())
                {
                    lotePage = loteCustomRepository.findAll(specification, PageRequest.of(pageNumber, itemsPerPage));
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lotePage = loteCustomRepository.findAll(specification, PageRequest.of(pageNumber, itemsPerPage, sortOrder));
                }
            }
            else
            {
                if (order == null || order.isEmpty())
                {
                    lotePage = loteCustomRepository.findAll(PageRequest.of(pageNumber, itemsPerPage));
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lotePage = loteCustomRepository.findAll(PageRequest.of(pageNumber, itemsPerPage, sortOrder));
                }
            }

            return lotePage;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }

    public LoteEntity findById(UUID id)
    {
        try
        {
            Optional<LoteEntity> entityOptional = loteCustomRepository.findById(id);
            return (entityOptional.isPresent())? entityOptional.get() : null;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }












}
