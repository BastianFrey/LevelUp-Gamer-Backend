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
        // 1. OBTENER DATOS Y LIMPIAR
        // Usamos trim() para espacios y toLowerCase() por si el celular puso Mayúsculas
        String correoIngresado = loginRequest.getCorreo().trim();

        // --- DEBUG VISIBLE (Ahora sí saldrá en consola) ---
        System.out.println("====== INTENTO DE LOGIN ======");
        System.out.println("Correo recibido del celular: '" + loginRequest.getCorreo() + "'");
        System.out.println("Correo que usaremos para buscar: '" + correoIngresado + "'");
        // --------------------------------------------------

        // 2. BUSCAR USUARIO
        // NOTA: Si esto falla, el error es que el correo NO existe en la BD
        // o se guardó con mayúsculas/minúsculas diferentes.
        Usuario usuario = usuarioRepository.findByCorreo(correoIngresado)
                .orElseThrow(() -> {
                    System.out.println("❌ ERROR: El correo no fue encontrado en la Base de Datos.");
                    return new org.springframework.security.core.AuthenticationException("Usuario o contraseña inválidos") {};
                });

        System.out.println("✅ Usuario encontrado: " + usuario.getCorreo());
        System.out.println("Hash en BD: " + usuario.getContrasena());

        // 3. VERIFICAR CONTRASEÑA
        if (!passwordEncoder.matches(loginRequest.getContrasena().trim(), usuario.getContrasena())) {
            System.out.println("❌ ERROR: La contraseña no coincide con el hash.");
            throw new org.springframework.security.core.AuthenticationException("Usuario o contraseña inválidos") {};
        }

        // 4. GENERAR TOKEN
        String token = jwtProvider.generateToken(usuario);
        return LoginResponse.builder()
                .token(token)
                .rol(usuario.getRol())
                .usuarioId(usuario.getId())
                .build();
    }
}