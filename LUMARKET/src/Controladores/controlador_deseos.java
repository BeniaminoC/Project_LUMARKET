/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LD;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
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
    
    public metodos_generales modelo;
    
    @FXML
    private ContextMenu H;
    @FXML
    private Button Options;
    @FXML
    private ScrollPane contenedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void cargarFavs() {
    favlist.getChildren().clear();
    Nodo_LD <producto> aux = modelo.cab_f;
    if (aux!=null){
        while (aux!=null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/favorito.fxml"));
                HBox productoVBox = loader.load();
                controlador_favorito controller = loader.getController();
                controller.agregarfavorito(aux.dato, this);
                favlist.getChildren().add(productoVBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
            aux=aux.sig;
            }
        }else{
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/favorito.fxml"));
                HBox productoHBox = loader.load();
                controlador_favorito controller = loader.getController();
                controller.defecto();
                favlist.getChildren().add(productoHBox);
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

    @FXML
    private void abrirhistorial(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_historial.fxml", event,this.modelo);
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
    private void abrircarrito(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_carrito.fxml", event,this.modelo);
    }

    @FXML
    private void inicio(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event, this.modelo);
    }
    
}
