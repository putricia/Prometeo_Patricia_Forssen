package org.example.logitronapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Articulo {

    private int id;
    private String nombre;
    private int udsStock;
    private double precioCompra;
    private double precioVenta;
    private boolean porPeso;

    public Articulo(int id, String nombre, int udsStock,
                    double precioCompra, double precioVenta, boolean porPeso) {
        this.id = id;
        this.nombre = nombre;
        this.udsStock = udsStock;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.porPeso = porPeso;
    }

    @Override
    public String toString() {
        return nombre;
    }

}