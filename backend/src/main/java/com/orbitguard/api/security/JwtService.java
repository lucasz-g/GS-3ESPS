package com.orbitguard.api.security;

import com.orbitguard.api.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Classe utilitária que encapsula criação e leitura de JWT. Esta implementação
 * é simplificada para fins de demonstração. Para uso em produção, considere
 * rotação de chaves, revogação de tokens e declarações adicionais.
 */
@Service
public class JwtService {

    // Chave secreta usada para assinar e verificar tokens JWT. Em uma aplicação
    // real, armazene este valor com segurança fora do controle de versão.
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Gera um token JWT contendo o e-mail e o papel do usuário. O token expira
     * após um dia.
     *
     * @param user usuário para quem o token é criado
     * @return um JWT assinado em formato de string
     */
    public String generateToken(User user) {
        long expirationMillis = 24 * 60 * 60 * 1000L; // 1 dia
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("role", user.getRole().name())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
            .signWith(secretKey)
            .compact();
    }

    /**
     * Extrai o subject (e-mail) do JWT. Retorna null se o token não puder ser
     * lido.
     */
    public String extractEmail(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Valida se o token foi assinado corretamente e pertence ao usuário
     * informado. Retorna false para tokens expirados ou inválidos.
     */
    public boolean isValid(String token, User user) {
        try {
            String email = extractEmail(token);
            return email != null && email.equals(user.getEmail());
        } catch (Exception e) {
            return false;
        }
    }
}
