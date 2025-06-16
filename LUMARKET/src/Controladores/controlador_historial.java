/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.historial;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_historial implements Initializable {

    @FXML
    private VBox histolist;
    public metodos_generales modelo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    cargarHistorial(); 
}
    
    private void cargarHistorial() {
    histolist.getChildren().clear();
    Nodo_LS <historial> historiales = modelo.cab_h;
    
    while (historiales!=null ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/contenedor_historial.fxml"));
            VBox ContenedorVBox = loader.load();
            contenedor_historial controller = loader.getController();
            controller.agregarhistorial(historiales.dato, modelo);
            histolist.getChildren().add(ContenedorVBox);
            historiales=historiales.sig;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
}
