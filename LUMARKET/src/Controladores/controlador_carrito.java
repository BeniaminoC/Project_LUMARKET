/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_carrito implements Initializable {

    @FXML
    private VBox llenarcarrito;
    @FXML
    private Button hacerpago;
    
    public metodos_generales modelo;
    @FXML
    private Label total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void abrirformulario(ActionEvent event) throws IOException {
        Nodo_LS <producto>recorrido = modelo.tope_c;
        while(recorrido!=null){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/formulario_compra.fxml"));
        Parent root = loader.load();
        controlador_compra controller = loader.getController();
        Nodo_LS <producto>cat = modelo.BuscarCatalogo(recorrido.dato.idp);
        modelo.actualizarcantidad(recorrido.dato.cantidad, recorrido.dato.idp);
        int c=cat.dato.cantidad-recorrido.dato.cantidad;
        modelo.actualizarArchivo(cat.dato.idp, c);
        controller.ModeloCompartido(modelo);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    }
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    cargarcarrito();
    actualizarTotal();
}
    
    public void cargarcarrito(){
    llenarcarrito.getChildren().clear();
    Nodo_LS<producto> actual = modelo.tope_c;
    while (actual != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/carrito_prod.fxml"));
            HBox productoHBox = loader.load();
            controlador_carrito_prod controller = loader.getController();
            controller.agregarencarrito(actual.dato, this);
            llenarcarrito.getChildren().add(productoHBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        actual = actual.sig;
    }
}
    
    public void actualizarTotal() {
    double suma = 0;
    Nodo_LS <producto>actual = modelo.tope_c;  
    while (actual != null) {
        producto p = actual.dato;
        int qty = p.cantidad;
        suma = suma+(p.precio * qty);
        actual = actual.sig;
    }
    total.setText(String.valueOf(suma));
}

    
}
