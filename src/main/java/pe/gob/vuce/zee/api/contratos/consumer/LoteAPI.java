package pe.gob.vuce.zee.api.contratos.consumer;

import pe.gob.vuce.zee.api.contratos.dto.LoteDTO;
import pe.gob.vuce.zee.api.contratos.dto.ResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.UUID;

public interface LoteAPI {
    @GET("v1/lotes/{uuid}")
    Call<ResponseDTO<LoteDTO>> getLote(@Path("uuid") UUID uuid);
}
