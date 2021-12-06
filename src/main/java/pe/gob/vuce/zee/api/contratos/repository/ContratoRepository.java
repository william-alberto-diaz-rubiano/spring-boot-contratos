package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.models.ContratoEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, UUID>,ContratoCustomRepository {

}
