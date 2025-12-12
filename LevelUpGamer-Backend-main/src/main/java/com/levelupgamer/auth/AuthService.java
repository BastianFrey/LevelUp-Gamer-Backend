package com.levelupgamer.auth;

import com.levelupgamer.users.Usuario;
import com.levelupgamer.users.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String correoLimpio = loginRequest.getCorreo().trim();

        Usuario usuario = usuarioRepository.findByCorreo(correoLimpio)
                .orElseThrow(() -> new org.springframework.security.core.AuthenticationException("Usuario o contraseña inválidos") {});

        if (!passwordEncoder.matches(loginRequest.getContrasena().trim(), usuario.getContrasena())) {
            throw new org.springframework.security.core.AuthenticationException("Usuario o contraseña inválidos") {};
        }

        String token = jwtProvider.generateToken(usuario);
        return LoginResponse.builder()
                .token(token)
                .rol(usuario.getRol())
                .usuarioId(usuario.getId())
                .build();
    }
}