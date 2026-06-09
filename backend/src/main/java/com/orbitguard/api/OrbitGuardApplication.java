package com.orbitguard.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada principal da aplicação backend OrbitGuard. Esta classe
 * inicializa o contexto do Spring e inicia o contêiner de servlets embarcado.
 */
@SpringBootApplication
public class OrbitGuardApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrbitGuardApplication.class, args);
    }
}
