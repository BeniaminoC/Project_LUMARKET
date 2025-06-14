/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_infoproducto implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label price;
    @FXML
    private Label description;
    @FXML
    private Label stock;
    
    private metodos_generales modelo;
    
    private producto data;
    @FXML
    private Button compra;
    @FXML
    private TextField cant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void ModeloCompartido(metodos_generales modelo, producto p) {
    this.modelo = modelo;
    this.data=p;
    cargarinfo();
}
    
    private void cargarinfo(){
        Image img = new Image(getClass().getResource(data.imagen).toExternalForm());
        image.setImage(img);
        title.setText(data.nombre);
        price.setText(String.valueOf(data.precio)+"$");
        description.setText("");
        stock.setText(String.valueOf(data.cantidad));
        if(data.cantidad==0){
            cant.setText("No stock");
            cant.setDisable(true);
        }
    }

    @FXML
    private void abrirformulario(ActionEvent event) throws IOException {
        if(data.cantidad>Integer.parseInt(cant.getText())){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/formulario_compra.fxml"));
        Parent root = loader.load();
        controlador_compra controller = loader.getController();
        producto Udata= new producto(data.idp,data.nombre,data.precio,data.imagen,(Integer.parseInt(cant.getText())));
        modelo.actualizarcantidad(Udata.cantidad, data.idp);
        int c=data.cantidad-Udata.cantidad;
        modelo.actualizarArchivo(data.idp, c);
        controller.ModeloCompartido(modelo, Udata);
        System.out.println(data.cantidad);
        System.out.println(Udata.cantidad);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Stock insuficiente");
            alerta.setContentText("No hay productos suficientes para su pedido");
            alerta.showAndWait();
        }
    }
    
}
