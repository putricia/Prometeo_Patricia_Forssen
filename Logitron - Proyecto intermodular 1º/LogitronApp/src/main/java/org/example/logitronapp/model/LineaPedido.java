package org.example.logitronapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LineaPedido {

    private Articulo articulo;
    private int unidades;
    private int descuento;
    private int idPedido;

    public double getTotalLinea() {
        double base = articulo.getPrecioVenta() * unidades;
        double pLinea = base - (base * descuento / 100);
        return pLinea;
    }

    @Override
    public String toString() {
        return articulo.getNombre()
                + " | uds: " + unidades
                + " | dto: "+ descuento +"%"
                + " | total: "+ String.format("%.2f €", getTotalLinea());
    }

    public String getNombreArticulo() {
        return articulo.getNombre();
    }

    public double getPrecioUnitario() {
        return articulo.getPrecioVenta();
    }
}
