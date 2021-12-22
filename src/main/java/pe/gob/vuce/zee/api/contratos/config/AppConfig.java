package pe.gob.vuce.zee.api.contratos.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Getter
@Setter
@Slf4j
public class AppConfig {
    private Map<Integer, String> contratoEtapas;

    @PostConstruct
    public void init(){
        log.info("Etapas de un contrato: {}", contratoEtapas);
    }
}
