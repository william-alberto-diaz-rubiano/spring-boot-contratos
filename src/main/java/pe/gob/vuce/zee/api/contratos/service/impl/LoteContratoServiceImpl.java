package pe.gob.vuce.zee.api.contratos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.contratos.dto.LoteContratoFilterDTO;
import pe.gob.vuce.zee.api.contratos.models.LoteContratoEntity;
import pe.gob.vuce.zee.api.contratos.models.LoteEntity;
import pe.gob.vuce.zee.api.contratos.repository.impl.LoteContratoCustomRepositoryImpl;
import pe.gob.vuce.zee.api.contratos.repository.impl.LoteCustomRepositoryImpl;
import pe.gob.vuce.zee.api.contratos.repository.specification.LoteSpecification;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service("loteContratoServiceImpl")
@RequiredArgsConstructor
public class LoteContratoServiceImpl
{
    @Autowired
    @Qualifier("loteContratoCustomRepositoryImpl")
    private LoteContratoCustomRepositoryImpl loteContratoCRepo;

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

    public List<LoteContratoEntity> findAll(List<String> filterList, String order)
    {
        try
        {
            List<LoteContratoEntity> lcEntities = new ArrayList<>();

            if (filterList != null && !filterList.isEmpty())
            {
                Specification specification = new LoteSpecification().getSpecificationByAndOperator(filterList);
                if (order == null || order.isEmpty())
                {
                    lcEntities = loteContratoCRepo.findAll(specification);
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lcEntities = loteContratoCRepo.findAll(specification, sortOrder);
                }
            }
            else
            {
                if (order == null || order.isEmpty())
                {
                    lcEntities = loteContratoCRepo.findAll();
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lcEntities = loteContratoCRepo.findAll(sortOrder);
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

            return lcEntities;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }

    public Page<LoteContratoEntity> findAll(List<String> filterList, String order, Integer pageNumber, Integer itemsPerPage)
    {
        try
        {
            Page<LoteContratoEntity> lcPage = null;

            if (filterList != null && !filterList.isEmpty())
            {
                Specification specification = new LoteSpecification().getSpecificationByAndOperator(filterList);
                if (order == null || order.isEmpty())
                {
                    lcPage = loteContratoCRepo.findAll(specification, PageRequest.of(pageNumber, itemsPerPage));
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lcPage = loteContratoCRepo.findAll(specification, PageRequest.of(pageNumber, itemsPerPage, sortOrder));
                }
            }
            else
            {
                if (order == null || order.isEmpty())
                {
                    lcPage = loteContratoCRepo.findAll(PageRequest.of(pageNumber, itemsPerPage));
                }
                else
                {
                    Sort sortOrder = this.getSortOrder(order);
                    lcPage = loteContratoCRepo.findAll(PageRequest.of(pageNumber, itemsPerPage, sortOrder));
                }
            }

            return lcPage;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }

    public LoteContratoEntity findById(UUID id)
    {
        try
        {
            Optional<LoteContratoEntity> entityOptional = loteContratoCRepo.findById(id);
            return (entityOptional.isPresent())? entityOptional.get() : null;
        }
        catch (Throwable error)
        {
            throw error;
        }
    }

    public List<LoteContratoFilterDTO> loteContratoFilter()
    {
        try
        {
            return loteContratoCRepo.getLoteContratoFilterDTO();
        }
        catch (Throwable error)
        {
            throw error;
        }
    }










}
