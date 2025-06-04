package com.br.order.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI enqueuerService() {
    return new OpenAPI().info(
        (new Info()).title("Pedidos")
            .description("serviço responsável por processar os pedidos")
            .version("v0.0.1")
    );
  }
}
