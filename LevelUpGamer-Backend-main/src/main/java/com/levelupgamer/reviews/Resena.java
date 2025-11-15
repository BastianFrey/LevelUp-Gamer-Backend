package com.levelupgamer.reviews;

import com.levelupgamer.products.Producto;
import com.levelupgamer.users.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity 
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int calificacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private String nombreUsuario;

    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    public Resena(int calificacion, String comentario, String nombreUsuario, Producto producto, Usuario usuario) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.nombreUsuario = nombreUsuario;
        this.producto = producto;
        this.usuario = usuario;
        this.fecha = LocalDate.now();
    }
}