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
        // Limpiamos espacios
        String correoLimpio = loginRequest.getCorreo().trim();
        String passwordPlana = loginRequest.getContrasena().trim();

        // Buscamos usuario
        Usuario usuario = usuarioRepository.findByCorreo(correoLimpio)
                .orElseThrow(() -> new org.springframework.security.core.AuthenticationException("Usuario no encontrado: " + correoLimpio) {});

        // --- ZONA DE DEBUG (MIRA LA CONSOLA AL PROBAR) ---
        System.out.println("================= DEBUG LOGIN =================");
        System.out.println("1. Correo: " + correoLimpio);
        System.out.println("2. Pass Ingresada (Plana): " + passwordPlana);
        System.out.println("3. Hash en Base de Datos : " + usuario.getContrasena());
        System.out.println("4. Largo del Hash en BD  : " + usuario.getContrasena().length());

        boolean coinciden = passwordEncoder.matches(passwordPlana, usuario.getContrasena());
        System.out.println("5. ¿COINCIDEN?           : " + coinciden);
        System.out.println("===============================================");
        // ----------------------------------------------------

        if (!coinciden) {
            throw new org.springframework.security.core.AuthenticationException("Contraseña incorrecta") {};
        }

        String token = jwtProvider.generateToken(usuario);
        return LoginResponse.builder()
                .token(token)
                .rol(usuario.getRol())
                .usuarioId(usuario.getId())
                .build();
    }
}