/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author BENJAMIN
 */
public class Nodo_LS<T> {
    public T dato;
    public Nodo_LS <T> sig;

    public Nodo_LS(T dato) {
        this.dato = dato;
        sig = null;
    }
}
