package com.levelupgamer.reviews.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResenaCrearDTO {

    @NotNull(message = "La calificación no puede ser nula")
    @Min(value = 1, message = "La calificación debe ser como mínimo 1")
    @Max(value = 5, message = "La calificación debe ser como máximo 5")
    private Integer calificacion;

    @Size(max = 1000, message = "El comentario no puede exceder los 1000 caracteres")
    private String comentario;
}