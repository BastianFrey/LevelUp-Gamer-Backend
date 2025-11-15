package com.levelupgamer.reviews;

import com.levelupgamer.exception.ResourceNotFoundException;
import com.levelupgamer.products.Producto;
import com.levelupgamer.products.ProductoRepository;
import com.levelupgamer.reviews.dto.ResenaCrearDTO;
import com.levelupgamer.reviews.dto.ResenaDTO;
import com.levelupgamer.reviews.dto.ResenaStatsDTO;
import com.levelupgamer.users.Usuario;
import com.levelupgamer.users.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public ResenaStatsDTO getResenasPorProducto(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + productoId);
        }

        List<Resena> resenas = resenaRepository.findByProductoId(productoId);
        List<ResenaDTO> resenaDTOs = ResenaMapper.toDTOList(resenas);
        double promedio = resenaRepository.getAverageRatingForProducto(productoId).orElse(0.0);

        return new ResenaStatsDTO(promedio, resenas.size(), resenaDTOs);
    }

    public ResenaDTO crearResena(Long productoId, ResenaCrearDTO crearDTO, String usuarioEmail) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId));

        Usuario usuario = usuarioRepository.findByCorreo(usuarioEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + usuarioEmail));

        resenaRepository.findByProductoIdAndUsuarioId(productoId, usuario.getId())
                .ifPresent(resena -> {
                    throw new IllegalStateException("El usuario ya ha dejado una reseña para este producto");
                });

        Resena nuevaResena = new Resena(
                crearDTO.getCalificacion(),
                crearDTO.getComentario(),
                usuario.getNombre(),
                producto,
                usuario
        );

        Resena resenaGuardada = resenaRepository.save(nuevaResena);

        return ResenaMapper.toDTO(resenaGuardada);
    }
}