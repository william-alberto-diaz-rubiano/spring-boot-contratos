package pe.gob.vuce.zee.api.maestros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.maestros.models.ContratoEntity;
import pe.gob.vuce.zee.api.maestros.repository.impl.ContratoRepositoryImpl;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, UUID>,ContratoCustomRepository {
    @Query(value = "select * from vecr_ctrt vc where vc.vecr_ctrt_cliente_fk = :codigoCliente ", nativeQuery = true)
    List<ContratoEntity> findByClientId(Integer codigoCliente);
}
