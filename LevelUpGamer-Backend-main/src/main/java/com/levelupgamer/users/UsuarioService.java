package com.levelupgamer.users;

import com.levelupgamer.exception.UserAlreadyExistsException;
import com.levelupgamer.users.dto.UsuarioRegistroDTO;
import com.levelupgamer.users.dto.UsuarioRespuestaDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {
    private static final int REFERRAL_POINTS = 100;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto) {
        // 1. Validaciones previas
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new UserAlreadyExistsException("Correo ya registrado");
        }
        if (usuarioRepository.existsByRun(dto.getRun())) {
            throw new UserAlreadyExistsException("RUN ya registrado");
        }

        // 2. Mapeo inicial
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));

        // 3. Lógica de Roles según el dominio del correo
        String correoLower = dto.getCorreo().toLowerCase(); // Convertimos a minúsculas para comparar seguro

        if (correoLower.endsWith("@admin.cl")) {
            usuario.setRol(RolUsuario.ADMINISTRADOR);
        } else if (correoLower.endsWith("@gmail.com")) {
            usuario.setRol(RolUsuario.VENDEDOR);
        } else if (correoLower.endsWith("@duocuc.cl")) {
            usuario.setRol(RolUsuario.CLIENTE);
        } else {
            // Por defecto para cualquier otro correo
            usuario.setRol(RolUsuario.CLIENTE);
        }

        // 4. Configuración adicional
        usuario.setPuntosLevelUp(0);
        usuario.setActivo(true);

        // Mantengo tu lógica de DuocUser por si la usas para descuentos u otra cosa
        if (correoLower.endsWith("@admin.cl") || correoLower.endsWith("@profesor.admin.cl")) {
            usuario.setIsDuocUser(true);
        }

        // 5. Lógica de Referidos
        if (dto.getCodigoReferido() != null && !dto.getCodigoReferido().isEmpty()) {
            usuarioRepository.findByCodigoReferido(dto.getCodigoReferido())
                    .ifPresent(referrer -> {
                        referrer.setPuntosLevelUp(referrer.getPuntosLevelUp() + REFERRAL_POINTS);
                        usuarioRepository.save(referrer);
                    });
        }

        usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioRespuestaDTO actualizarUsuario(Long id, UsuarioRegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setRegion(dto.getRegion());
        usuario.setComuna(dto.getComuna());
        usuario.setDireccion(dto.getDireccion());
        usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }
}