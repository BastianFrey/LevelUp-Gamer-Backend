package com.levelupgamer.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaStatsDTO {
    private double promedioCalificacion;
    private int totalResenas;
    private List<ResenaDTO> resenas;
}