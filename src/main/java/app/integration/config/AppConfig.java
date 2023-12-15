package app.integration.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
        info = @Info(
                title = "Project",
                description = "Project for test task", version = "1.0.0",
                contact = @Contact(
                        name = "IITU student",
                        email = "iitu.edu@kz",
                        url = "https://iitu.kz"
                )
        )
)
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
