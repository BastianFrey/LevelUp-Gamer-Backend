package com.levelupgamer.products;

import com.levelupgamer.reviews.Resena;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String codigo;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String nombre;

    @Column(length = 500)
    @Size(max = 500)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal precio;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer stock;

    @Min(0)
    private Integer stockCritico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CategoriaProducto categoria;

    private String imagenUrl;

    private Boolean activo = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Resena> resenas;

}