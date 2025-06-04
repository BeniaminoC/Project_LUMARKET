/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.BufferedReader;
import java.io.FileReader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_login implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button ingresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void iniciar(ActionEvent event) {
        String em = email.getText();
        String pas = password.getText();
        if (ConsultarArchivo("src/Archivos/usuarios.txt",em,pas)){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Exito");
        alerta.setContentText("iniciando sesion");
        alerta.showAndWait();
        cambioventana("/Vistas/vista_usuario.fxml", event);
        }else{
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText("Usuario/Contraseña incorrecta");
        alerta.showAndWait();
        }
    }
    
    public boolean ConsultarArchivo(String ubicacion, String usuario, String pass) {
    try (BufferedReader reader = new BufferedReader(new FileReader(ubicacion))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] bloques = linea.split(",");
            if (bloques.length == 4) {
                String correo = bloques[0];
                String contraseña = bloques[1];
                String nombre =bloques[2];
                String apellido= bloques [3];
                if (correo.equals(usuario)&&contraseña.equals(pass)) {
                    return true;
                }
            }
        }
    } catch (IOException e) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText("No se pudo leer el archivo");
        alerta.showAndWait();
    }
    return false;
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
