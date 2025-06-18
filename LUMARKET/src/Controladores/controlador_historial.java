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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextInputDialog;
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
    @FXML
    private Button Options;
    @FXML
    private ContextMenu H;

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

    @FXML
    private void abrircarrito(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_carrito.fxml", event,this.modelo);
    }

    @FXML
    private void mostraropciones(ActionEvent event) {
        H.show(Options, Side.BOTTOM,0,0);
    }

    @FXML
    private void crearproducto(ActionEvent event) {
    TextInputDialog dialogo = new TextInputDialog();
    dialogo.setTitle("Código de Acceso");
    dialogo.setHeaderText("Ingrese el código para continuar");
    dialogo.setContentText("Código:");

    Optional<String> resultado = dialogo.showAndWait();

    if (resultado.isPresent()) {
        String codigoIngresado = resultado.get();
        String codigoCorrecto = "1234";

        if (codigoIngresado.equals(codigoCorrecto)) {
            modelo.cambioventana("/Vistas/vista_admin.fxml", event,this.modelo);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de acceso");
            alerta.setHeaderText(null);
            alerta.setContentText("Código incorrecto. Intente nuevamente.");
            alerta.showAndWait();
        }
    }
    }

    @FXML
    private void salir(ActionEvent event) {
        modelo.cerrarsesion();
        modelo.cambioventana("/Vistas/vista_principal.fxml", event,this.modelo);
    }

    @FXML
    private void abrir(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_deseos.fxml", event,this.modelo);
    }

    @FXML
    private void inicio(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event, this.modelo);
    }
    
}
