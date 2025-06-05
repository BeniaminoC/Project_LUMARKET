/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.producto;
import Modelo.usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author BENJAMIN
 */
public class metodos_generales {
    
    public ArrayList<producto> catalogo = new ArrayList();
    
    public void cambioventana (String direccion, ActionEvent evento){
        
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
    
    public usuario ConsultarArchivo(String ubicacion, String usuario, String pass) {
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
                    usuario p=new usuario(correo,contraseña, nombre,apellido);
                    return p;
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
    return null;
}
    
    public usuario guardarDatos(String correo, String password, String name, String lastname){
        if(ConsultarArchivo("src/Archivos/usuarios.txt",correo,password)==null){
        try{
        BufferedWriter guardar = new BufferedWriter(new FileWriter("src/Archivos/usuarios.txt", true));
            guardar.write(correo+","+password+","+name+","+lastname);
            guardar.newLine();
            guardar.close();
            usuario q = new usuario(correo,password,name,lastname);
            return q;
            }catch(IOException e){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setTitle("Error");
                alerta.setContentText("No se pudo modificar el archivo");
                alerta.showAndWait(); 
                return null;
        } 
    }else{
        return null;    
        }
    }
    
    public void productosacatalogo(HBox generar){
        for (producto prod:catalogo){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/vista_producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod);
            generar.getChildren().add(productoVBox);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    
    public void cargarproductos(){
        try{
        BufferedReader leer = new BufferedReader(new FileReader("src/Archivos/listaproductos.txt"));
        String linea="";
        while ((linea=leer.readLine())!=null){
            String[] bloques= linea.split(",");
            if (bloques.length==3){
            String nombre=bloques[0];
            float precio=Float.parseFloat(bloques[1]);
            String imagen=bloques[2];
            
            producto q=new producto(nombre, precio, imagen);
            catalogo.add(q);
            System.out.println("Productos cargados: " + catalogo.size());
            }
        }
        leer.close();
        }catch(IOException e){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setTitle("Error");
                alerta.setContentText("No se pudo cargar los productos");
                alerta.showAndWait(); 
        }
    }
    
    public void antiduplicados(){
        if (catalogo==null){
            cargarproductos();
        }

    }
    

public List<producto> obtenerProductosPagina(int inicio, int cantidad) {
    int fin = Math.min(inicio + cantidad, catalogo.size());
    return catalogo.subList(inicio, fin);
}

}
