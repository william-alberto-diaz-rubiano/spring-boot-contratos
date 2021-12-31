package pe.gob.vuce.zee.api.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.vuce.zee.api.contratos.models.CarnetEntity;
import java.util.UUID;

@Repository
public interface CarnetRepository extends JpaRepository<CarnetEntity, UUID> {
}
