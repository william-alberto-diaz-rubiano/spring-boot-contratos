package pe.gob.vuce.zee.api.maestros.consumer;

import pe.gob.vuce.zee.api.maestros.consumer.utils.MaestroBuscarMultipleBody;
import pe.gob.vuce.zee.api.maestros.dto.MaestroDTO;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MaestroAPI {
    @GET("v1/maestros")
    Call<ResponseDTO<MaestroDTO>> getMaestro(@Query("id") UUID uuid);

    @POST("v1/maestros/buscarIds")
    Call<ResponseDTO<Map<UUID, List<MaestroDTO>>>> buscarPorIds(@Body MaestroBuscarMultipleBody requestBody);
}
