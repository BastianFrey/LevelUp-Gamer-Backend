package com.levelupgamer.reviews;

import com.levelupgamer.reviews.dto.ResenaDTO;
import java.util.List;
import java.util.stream.Collectors;

public class ResenaMapper {
    public static ResenaDTO toDTO(Resena resena) {
        if (resena == null) {
            return null;
        }

        ResenaDTO dto = new ResenaDTO();
        dto.setId(resena.getId());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setNombreUsuario(resena.getNombreUsuario());
        dto.setFecha(resena.getFecha());

        return dto;
    }

    public static List<ResenaDTO> toDTOList(List<Resena> resenas) {
        return resenas.stream()
                .map(ResenaMapper::toDTO)
                .collect(Collectors.toList());
    }
}