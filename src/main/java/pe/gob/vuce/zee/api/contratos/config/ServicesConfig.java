package pe.gob.vuce.zee.api.contratos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "services", ignoreInvalidFields = true)
@Getter
@Setter
public class ServicesConfig {
    private String maestroUrl;
}
