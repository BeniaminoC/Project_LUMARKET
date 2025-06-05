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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_usuario implements Initializable {

    @FXML
    private Button adelante;
    @FXML
    private Button atras;
    
    private int inicio = 0;
    private final int ELEMENTOS_POR_PAGINA = 5;
    metodos_generales cambio = new metodos_generales();
    @FXML
    private HBox usercatalogo;

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
    private void retroceder(ActionEvent event) {
        if (inicio >= ELEMENTOS_POR_PAGINA) {
        inicio -= ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Retrocediendo...");
    }
    }

    @FXML
    private void avanzar(ActionEvent event) {
        if (inicio + ELEMENTOS_POR_PAGINA < cambio.catalogo.size()) {
        inicio += ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Avanzando...");
    }
    }
    
    private void cargarPagina() {
    usercatalogo.getChildren().clear();
    List<producto> productosPagina = cambio.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/vista_producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            usercatalogo.getChildren().add(productoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
}
