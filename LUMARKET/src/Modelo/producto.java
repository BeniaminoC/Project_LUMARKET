/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author BENJAMIN
 */
public class producto {
    public String idp;
    public String nombre;
    public float precio;
    public String imagen;
    public int cantidad;
    public String descripcion;

    public producto(String idp, String nombre, float precio, String imagen, int cantidad) {
        this.idp = idp;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }

    public producto(String idp, String nombre, float precio, String imagen, int cantidad, String descripcion) {
        this.idp = idp;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }
    
    
    
}
