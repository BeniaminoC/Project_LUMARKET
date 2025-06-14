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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_usuario implements Initializable {

    
    private int inicio = 0;
    private final int ELEMENTOS_POR_PAGINA = 5;
    private metodos_generales modelo;
    @FXML
    private HBox usercatalogo;
    @FXML
    private Button atras;
    @FXML
    private Button adelante;
    @FXML
    private Button fav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void retroceder(ActionEvent event) {
        if (inicio >= ELEMENTOS_POR_PAGINA) {
        inicio -= ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Retrocediendo...");
    }
    }

    @FXML
    private void avanzar(ActionEvent event) {
        if (inicio + ELEMENTOS_POR_PAGINA < modelo.tamañoListaSen()) {
        inicio += ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Avanzando...");
    }
    }
    
    private void cargarPagina() {
    usercatalogo.getChildren().clear();
    List<producto> productosPagina = modelo.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/vista_producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            productoVBox.setOnMouseClicked(e -> {
                modelo.datosProducto("/Vistas/vista_infoproducto.fxml", prod,modelo);
                Stage ventanaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ventanaActual.close();
            });
            usercatalogo.getChildren().add(productoVBox);
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

    @FXML
    private void abrir(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_deseos.fxml", event,this.modelo);
    }
    
}
