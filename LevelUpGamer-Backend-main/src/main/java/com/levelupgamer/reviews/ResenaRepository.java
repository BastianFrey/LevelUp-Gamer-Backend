package com.levelupgamer.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProductoId(Long productoId);

    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.producto.id = :productoId")
    Optional<Double> getAverageRatingForProducto(@Param("productoId") Long productoId);

    Optional<Resena> findByProductoIdAndUsuarioId(Long productoId, Long usuarioId);

}