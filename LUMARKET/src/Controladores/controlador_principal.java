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
import javafx.scene.image.ImageView;
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

    @FXML
    private HBox homecatalogo;
    
    private metodos_generales modelo;
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
    }    
    @FXML
    private void entrar(ActionEvent event) throws IOException{
        modelo.cambioventana("/Vistas/vista_login.fxml", event,this.modelo);
    }
    @FXML
    private void crear(ActionEvent event) throws IOException{
        modelo.cambioventana("/Vistas/vista_signup.fxml", event,this.modelo);
    }

    @FXML
    private void siguiente(ActionEvent event) {
        if (inicio + ELEMENTOS_POR_PAGINA < modelo.tamañoLista()) {
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
    List<producto> productosPagina = modelo.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            homecatalogo.getChildren().add(productoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
     modelo.antiduplicados();
     cargarPagina(); 
}
    
}
