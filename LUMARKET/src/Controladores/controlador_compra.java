/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.historial;
import Modelo.producto;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private void guardarpago(ActionEvent event) {
        String[] n={data.nombre};
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
    
}
