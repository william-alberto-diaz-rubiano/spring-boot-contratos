package pe.gob.vuce.zee.api.maestros.consumer.utils;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MaestroBuscarMultipleBody {
    private List<UUID> ids;
}
