package com.orbitguard.api.service;

import com.orbitguard.api.dto.AuthResponse;
import com.orbitguard.api.dto.LoginRequest;
import com.orbitguard.api.dto.RegisterRequest;
import com.orbitguard.api.exception.BusinessException;
import com.orbitguard.api.exception.NotFoundException;
import com.orbitguard.api.model.User;
import com.orbitguard.api.repository.UserRepository;
import com.orbitguard.api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço que encapsula a lógica de autenticação e cadastro de usuários.
 * Abstrai detalhes de persistência e geração de tokens, retornando objetos de
 * resposta de alto nível para serialização pelos controladores.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registra um novo usuário no sistema. Lança uma exceção de negócio se já
     * existir uma conta com o e-mail informado.
     *
     * @param request dados do novo usuário
     * @return um {@link AuthResponse} contendo token e dados do usuário
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        // Persiste a senha com hash em vez de texto puro
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getName(), user.getEmail());
    }

    /**
     * Autentica um usuário existente e retorna um novo token em caso de sucesso.
     * Lança {@link NotFoundException} para usuários desconhecidos e
     * {@link BusinessException} para credenciais inválidas.
     *
     * @param request credenciais de login
     * @return um {@link AuthResponse} com token JWT e informações básicas do usuário
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Credenciais inválidas");
        }
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getName(), user.getEmail());
    }
}
