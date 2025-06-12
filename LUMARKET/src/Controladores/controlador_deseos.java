/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LD;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_deseos implements Initializable {

    @FXML
    private VBox favlist;
    private metodos_generales modelo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private void cargarFavs() {
    favlist.getChildren().clear();
    modelo.cargarFavoritos(modelo.actual.idu);
    Nodo_LD <producto> aux = modelo.cab_d;
    
    while (aux!=null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/favorito.fxml"));
            HBox productoVBox = loader.load();
            controlador_favorito controller = loader.getController();
            controller.agregarfavorito(aux.dato);
            favlist.getChildren().add(productoVBox);
            aux=aux.sig;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    modelo.antiduplicados();
    cargarFavs();
}
    
}
