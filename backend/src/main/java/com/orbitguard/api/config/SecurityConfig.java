package com.orbitguard.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração básica de segurança. Esta classe desativa CSRF para simplificar
 * e permite acesso sem autenticação a alguns pontos de acesso públicos. Todos os
 * outros pontos de acesso exigem autenticação. Um bean {@link PasswordEncoder} é
 * exposto para gerar hashes das senhas dos usuários.
 *
 * Observação: uma autenticação JWT completa exige um filtro de autenticação e
 * um serviço de detalhes do usuário adicionais, que estão fora do escopo deste
 * esqueleto. Consulte a documentação do Spring Security para detalhes sobre a
 * implementação de filtros JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/actuator/health"
                ).permitAll()
                .anyRequest().authenticated()
            )
            // Por enquanto, permitimos autenticação básica HTTP para simplificar os testes;
            // substitua por um filtro JWT em uma implementação de produção
            .httpBasic(config -> {});
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
