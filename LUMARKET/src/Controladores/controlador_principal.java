/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_principal implements Initializable {

    @FXML
    private Button existente;
    @FXML
    private Button nuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void entrar(ActionEvent event){
        cambioventana("/Vistas/vista_login.fxml", event);
    }

    @FXML
    private void crear(ActionEvent event){
        cambioventana("/Vistas/vista_signup.fxml", event);
    }
    
    private void cambioventana (String direccion, Event evento){
        
        try {
        Object eventSource = evento.getSource();
        Node sourceNode = (Node)eventSource;
        Scene old = sourceNode.getScene();
        Window ventana = old.getWindow();
        Stage stage = (Stage)ventana;
        stage.hide();
        
        Parent root= FXMLLoader.load(getClass().getResource(direccion));
        Scene scene = new Scene(root);
        Stage nueva = new Stage();
        nueva.setScene(scene);
        nueva.show();
        } catch (IOException ex) {
            Logger.getLogger(controlador_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
