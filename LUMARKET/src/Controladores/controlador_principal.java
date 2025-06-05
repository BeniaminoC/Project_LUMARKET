/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_principal implements Initializable {

    
    metodos_generales cambio = new metodos_generales();
    @FXML
    private HBox homecatalogo;
    
    private int inicio = 0;
    private final int ELEMENTOS_POR_PAGINA = 5;
    @FXML
    private Button existente;
    @FXML
    private Button nuevo;
    @FXML
    private Button derecha;
    @FXML
    private Button izquierda;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cambio.cargarproductos(); 
        cargarPagina(); 
        
    }    
    @FXML
    private void entrar(ActionEvent event){
        cambio.cambioventana("/Vistas/vista_login.fxml", event);
    }
    @FXML
    private void crear(ActionEvent event){
        cambio.cambioventana("/Vistas/vista_signup.fxml", event);
    }

    @FXML
    private void siguiente(ActionEvent event) {
        if (inicio + ELEMENTOS_POR_PAGINA < cambio.catalogo.size()) {
        inicio += ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Avanzando...");
    }
    }

    @FXML
    private void anterior(ActionEvent event) {
       if (inicio >= ELEMENTOS_POR_PAGINA) {
        inicio -= ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Retrocediendo...");
    }
    }
    
    private void cargarPagina() {
    homecatalogo.getChildren().clear();
    List<producto> productosPagina = cambio.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/vista_producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            homecatalogo.getChildren().add(productoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
}
