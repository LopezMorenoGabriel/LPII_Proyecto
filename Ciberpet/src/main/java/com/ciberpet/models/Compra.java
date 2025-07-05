package com.ciberpet.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_COMPRA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idBoleta")
    private Integer idBoleta;

    @Column(name = "fecha_boleta")
    private LocalDate fechaBoleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "compra")
    private List<DetCompra> detalles;

    public double getTotal() {
        if (detalles == null || detalles.isEmpty()) {
            return 0;
        }

        return detalles.stream()
                       .mapToDouble(DetCompra::getTotal)
                       .sum();
    }

}
