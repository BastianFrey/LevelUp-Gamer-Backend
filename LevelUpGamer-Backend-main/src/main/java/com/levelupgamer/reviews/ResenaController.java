package com.levelupgamer.reviews;

import com.levelupgamer.reviews.dto.ResenaCrearDTO;
import com.levelupgamer.reviews.dto.ResenaDTO;
import com.levelupgamer.reviews.dto.ResenaStatsDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/products/{productoId}/reviews")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @GetMapping
    public ResponseEntity<ResenaStatsDTO> getResenasPorProducto(@PathVariable Long productoId) {
        ResenaStatsDTO stats = resenaService.getResenasPorProducto(productoId);
        return ResponseEntity.ok(stats);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResenaDTO> crearResena(
            @PathVariable Long productoId,
            @Valid @RequestBody ResenaCrearDTO crearDTO,
            Principal principal
    ) {
        String userEmail = principal.getName();

        ResenaDTO nuevaResena = resenaService.crearResena(productoId, crearDTO, userEmail);
        return new ResponseEntity<>(nuevaResena, HttpStatus.CREATED);
    }
}