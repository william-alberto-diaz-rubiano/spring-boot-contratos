package pe.gob.vuce.zee.api.maestros.consumer;

import pe.gob.vuce.zee.api.maestros.dto.LoteDTO;
import pe.gob.vuce.zee.api.maestros.dto.MaestroDTO;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.UUID;

public interface LoteAPI {
    @GET("v1/lotes/{uuid}")
    Call<ResponseDTO<LoteDTO>> getLote(@Path("uuid") UUID uuid);
}
