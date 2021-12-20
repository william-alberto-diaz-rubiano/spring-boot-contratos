package pe.gob.vuce.zee.api.contratos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class MaestrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaestrosApplication.class, args);
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

}
