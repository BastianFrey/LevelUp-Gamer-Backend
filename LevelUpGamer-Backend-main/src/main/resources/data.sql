-- Insertar Roles de Usuario (Enum: CLIENTE, VENDEDOR, ADMINISTRADOR)
-- (Tu código Java maneja esto como un Enum, así que nos enfocamos en los datos)

-- Insertar Categorías de Producto (Enum: CONSOLA, VIDEOJUEGO, ACCESORIO, PC, SILLA, ROPA, AUDIFONO, MESA)
-- (Tu código Java maneja esto como un Enum)

-- Insertar Productos (Usamos los nombres de columna de tu Producto.java)
-- Asegúrate de que los nombres de las tablas (ej. producto) coincidan con el nombre de tu clase (ej. Producto.java)
INSERT INTO producto (id, codigo, nombre, descripcion, precio, stock, stock_critico, categoria, imagen_url, activo, created_at, updated_at) VALUES
                                                                                                                                                (1, 'P-001', 'Catan', 'Juego de estrategia y gestión de recursos', 29990.00, 50, 5, 'MESA', 'url_imagen_catan', true, NOW(), NOW()),
                                                                                                                                                (2, 'P-002', 'Controlador Inalámbrico Xbox Series X', 'Controlador de última generación', 59990.00, 30, 5, 'ACCESORIO', 'url_imagen_xbox', true, NOW(), NOW()),
                                                                                                                                                (3, 'P-003', 'PlayStation 5', 'Consola de videojuegos Sony', 549990.00, 10, 2, 'CONSOLA', 'url_imagen_ps5', true, NOW(), NOW()),
                                                                                                                                                (4, 'P-004', 'Silla Gamer Secretlab Titan', 'Silla ergonómica para gaming', 349990.00, 15, 3, 'SILLA', 'url_imagen_silla', true, NOW(), NOW());

-- Insertar Usuarios (con contraseñas encriptadas)
-- La contraseña para todos es "12345" (encriptada con BCrypt)
-- Asegúrate de que los nombres de las tablas (ej. usuario) coincidan con el nombre de tu clase (ej. Usuario.java)
INSERT INTO usuario (id, run, nombre, apellidos, correo, contrasena, fecha_nacimiento, rol, puntos_level_up, activo, created_at, updated_at, is_duoc_user) VALUES
                                                                                                                                                               (1, '11111111-1', 'Admin', 'Nistrador', 'admin@duoc.cl', '$2a$10$E.qfH5Pz1/N.kHMHe.0h.O0Cq0iZJ1q9E.B5qN5S0LzJYvYvGfOUu', '1990-01-01', 'ADMINISTRADOR', 0, true, NOW(), NOW(), true),
                                                                                                                                                               (2, '22222222-2', 'Cliente', 'Prueba', 'cliente@gmail.com', '$2a$10$E.qfH5Pz1/N.kHMHe.0h.O0Cq0iZJ1q9E.B5qN5S0LzJYvYvGfOUu', '1995-05-15', 'CLIENTE', 100, true, NOW(), NOW(), false);

-- (El resto de las tablas como pedido, pedido_item se crearán pero estarán vacías)

-- Asegura que las secuencias de ID estén actualizadas después de insertar manualmente
ALTER SEQUENCE producto_id_seq RESTART WITH 5;
ALTER SEQUENCE usuario_id_seq RESTART WITH 3;