package com.orbitguard.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Expõe um bean {@link RestTemplate} para realizar requisições HTTP externas.
 * Usar um bean central permite configurar e instrumentar chamadas (ex.:
 * tempos limite, interceptadores) em um único lugar.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
