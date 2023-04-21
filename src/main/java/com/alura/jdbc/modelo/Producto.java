package com.alura.jdbc.modelo;

public class Producto {
    private int id;
    final private String nombre;
    final private String descripcion;
    final private int cantidad;

    public Producto(String nombre, String descripcion, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, nombre: %s, descripcion: %s, cantidad: %d}", this.id, this.nombre, this.descripcion, this.cantidad);
    }
}
