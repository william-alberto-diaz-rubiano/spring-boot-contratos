package pe.gob.vuce.zee.api.maestros.consumer;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pe.gob.vuce.zee.api.maestros.config.ServicesConfig;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
@RequiredArgsConstructor
public class ConsumerBeans {
    private final ServicesConfig servicesConfig;

    @Bean
    public LoteAPI getMaestroApi(){
        var retrofit = new Retrofit.Builder()
                .baseUrl("https://landing-desa.vuce.gob.pe/vuce-zee-api-lote/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(LoteAPI.class);
    }
}
