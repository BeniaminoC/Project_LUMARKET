/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    List<producto> productosPagina = modelo.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/favorito.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            favlist.getChildren().add(productoVBox);
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
