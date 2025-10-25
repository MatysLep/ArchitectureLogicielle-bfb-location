package imt.archi.bfb.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projet Architecture Logicielle & EcoConception")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Benjamin Zawoda & Matys Lepretre")
                                .email("benjamin.zawoda@etu.imt-nord-europe.fr; matys.lepretre@imt-nord-europe.fr"))
                        );

    }
}
