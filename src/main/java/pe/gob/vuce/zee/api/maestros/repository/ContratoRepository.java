package pe.gob.vuce.zee.api.maestros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, UUID> {
    @Query(value = "select e.* from vems_comp e where e.vems_comp_client_fk = :clienteId ", nativeQuery = true)
    List<ContratoEntity> findByClientId(Integer clienteId);
}
