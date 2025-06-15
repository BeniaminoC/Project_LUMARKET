/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.historial;
import Modelo.producto;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_compra implements Initializable {

    @FXML
    private TextField address;
    @FXML
    private Button pago;
    
    private metodos_generales modelo;
    
    private producto data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardarpagounitario(ActionEvent event) {
        String[] n={data.idp};
        historial credenciales = new historial(modelo.actual.idu,fecha(),address.getText(),n,(data.precio*data.cantidad));
        System.out.println(data.precio*data.cantidad);
        System.out.println(data.cantidad);
        System.out.println(data.precio);
        modelo.compraunitaria(credenciales);
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Compra exitosa");
        alerta.setContentText("Los productos llegaran a su destino algun dia...");
        alerta.showAndWait();
        Object eventSource = event.getSource();
        Node sourceNode = (Node)eventSource;
        Scene old = sourceNode.getScene();
        Window ventana = old.getWindow();
        Stage stage = (Stage)ventana;
        stage.hide();
    }
    
    private void guardarpagocarrito(ActionEvent event) {
        List<String> acumulador = new ArrayList();
        Nodo_LS <producto> nombres = modelo.tope_c;
        while(nombres!=null){
            acumulador.add(nombres.dato.idp);
            nombres=nombres.sig;
        }
        String[] n = acumulador.toArray(new String [0]);
        historial credenciales = new historial(modelo.actual.idu,fecha(),address.getText(),n,(data.precio*data.cantidad));
        System.out.println(data.precio*data.cantidad);
        System.out.println(data.cantidad);
        System.out.println(data.precio);
        modelo.compramultiple(credenciales);
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Compra exitosa");
        alerta.setContentText("Los productos llegaran a su destino algun dia...");
        alerta.showAndWait();
        Object eventSource = event.getSource();
        Node sourceNode = (Node)eventSource;
        Scene old = sourceNode.getScene();
        Window ventana = old.getWindow();
        Stage stage = (Stage)ventana;
        stage.hide();
    }
    
    private String fecha(){
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = hoy.format(fmt);
        return fecha;
    }
    
    public void ModeloCompartido(metodos_generales modelo, producto p) {
    this.modelo = modelo;
    this.data=p;
}
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
}
    
}
