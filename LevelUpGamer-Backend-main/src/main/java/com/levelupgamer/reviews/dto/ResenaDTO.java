package com.levelupgamer.reviews.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResenaDTO {
    private Long id;
    private int calificacion;
    private String comentario;
    private String nombreUsuario;
    private LocalDate fecha;
}