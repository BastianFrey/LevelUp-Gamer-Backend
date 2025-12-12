package com.levelupgamer.config; // Asegúrate que el paquete sea correcto

import com.levelupgamer.users.RolUsuario; // Asegúrate de importar tu Enum
import com.levelupgamer.users.Usuario;
import com.levelupgamer.users.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe el admin para no duplicarlo
        if (!usuarioRepository.existsByCorreo("admin@levelup.cl")) {

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellidos("Sistema");
            admin.setCorreo("admin@levelup.cl");
            admin.setRun("1-9"); // Un RUN ficticio
            admin.setContrasena(passwordEncoder.encode("admin123")); // Contraseña: admin123
            admin.setRol(RolUsuario.ADMINISTRADOR); // <--- AQUÍ LA CLAVE
            admin.setActivo(true);
            admin.setPuntosLevelUp(9999);

            usuarioRepository.save(admin);

            System.out.println("✅ USUARIO ADMIN CREADO: admin@levelup.cl / admin123");
        } else {
            System.out.println("ℹ️ El usuario admin ya existe.");
        }
    }
}