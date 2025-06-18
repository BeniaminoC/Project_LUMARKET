/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.producto;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_admin implements Initializable {

    @FXML
    private Button logo;
    @FXML
    private Pane zona;
    @FXML
    private ImageView preview;
    @FXML
    private TextField ruta;
    @FXML
    private Button aggproducto;
    @FXML
    private TextField nombre;
    @FXML
    private TextArea desc;
    @FXML
    private TextField cash;
    @FXML
    private TextField stock;
    
    private metodos_generales modelo;
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

    @FXML
    private void over(DragEvent event) {
        if (event.getGestureSource() != zona && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    private void arrastrar(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            File archivo = db.getFiles().get(0); 
            String path = archivo.getAbsolutePath();

            if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                try{
                String name =archivo.getName();
                Path dest = Paths.get("src/Imagenes/Productos",name);
                Files.copy(archivo.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                String rutaRelativa = "/Imagenes/Productos/" + name;
                ruta.setText(rutaRelativa);
                preview.setImage(new Image(archivo.toURI().toString()));
                success = true;
                }catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setTitle("Error");
                alerta.setContentText("Archivo no valido");
                alerta.showAndWait();
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void crear(ActionEvent event) {
        String id = modelo.generarIDUnico(modelo.obtenerIDsExistentes("src/Archivos/listaproductos.txt"), "producto");
        producto nuevo= new producto(id, nombre.getText(),Float.parseFloat(cash.getText()),ruta.getText(),Integer.parseInt(stock.getText()),desc.getText());
        modelo.agregarSen(nuevo);
        modelo.guardarProducto(nuevo);
        exito();
    }
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
}
    
    public void exito(){
        ruta.setText("");
        desc.setText("");
        stock.setText("");
        nombre.setText("");
        cash.setText("");
        String defecto="C:/Users/BENJAMIN/Documents/Trabajos/Programacion II/Repositorio LUMARKET/LUMARKET/src/Imagenes/Productos/imagen.jpg";
        preview.setImage(new Image("file:"+defecto));
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Exito");
            alerta.setContentText("Producto agregado de manera exitosa");
            alerta.showAndWait();
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

    @FXML
    private void abrircarrito(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_carrito.fxml", event,this.modelo);
    }
    
}
