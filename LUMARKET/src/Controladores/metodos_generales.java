/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LD;
import Modelo.Nodo_LS;
import Modelo.historial;
import Modelo.producto;
import Modelo.usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import javax.swing.JOptionPane;

/**
 *
 * @author BENJAMIN
 */
public class metodos_generales {
    
    public metodos_generales(){ 
        cab_s=null; 
        cab_f=null;
        tope_c=null;
        actual=null;
    }
    
    usuario actual;
    
    public void cambioventana (String direccion, ActionEvent evento, metodos_generales modelo){
        
        try {
        Object eventSource = evento.getSource();
        Node sourceNode = (Node)eventSource;
        Scene old = sourceNode.getScene();
        Window ventana = old.getWindow();
        Stage stage = (Stage)ventana;
        stage.hide();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(direccion));
        Parent root = loader.load();
        Object controlador = loader.getController();
        
        if (controlador instanceof controlador_principal) {
            ((controlador_principal) controlador).ModeloCompartido(modelo);
        }else if (controlador instanceof controlador_login) {
            ((controlador_login) controlador).ModeloCompartido(modelo);
        }else if (controlador instanceof controlador_signup) {
            ((controlador_signup) controlador).ModeloCompartido(modelo);
        }else if (controlador instanceof controlador_usuario) {
            ((controlador_usuario) controlador).ModeloCompartido(modelo);
        }else if (controlador instanceof controlador_deseos) {
            ((controlador_deseos) controlador).ModeloCompartido(modelo);
        }else if (controlador instanceof controlador_carrito) {
            ((controlador_carrito) controlador).ModeloCompartido(modelo);
        }
        
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
            if (bloques.length == 5) {
                String correo = bloques[0];
                String contraseña = bloques[1];
                String nombre =bloques[2];
                String apellido= bloques [3];
                String id = bloques[4];
                if (correo.equals(usuario)&&contraseña.equals(pass)) {
                    usuario p=new usuario(correo,contraseña, nombre,apellido,id);
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
    
    public usuario guardarDatos(String correo, String password, String name, String lastname, String id){
        if(ConsultarArchivo("src/Archivos/usuarios.txt",correo,password)==null){
        try{
        BufferedWriter guardar = new BufferedWriter(new FileWriter("src/Archivos/usuarios.txt", true));
            guardar.write(correo+","+password+","+name+","+lastname+","+id);
            guardar.newLine();
            guardar.close();
            usuario q = new usuario(correo,password,name,lastname,id);
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
    
    public void cargarproductos(){
        try{
        BufferedReader leer = new BufferedReader(new FileReader("src/Archivos/listaproductos.txt"));
        String linea="";
        while ((linea=leer.readLine())!=null){
            String[] bloques= linea.split(",");
            if (bloques.length==5){
            String id=bloques[0];
            String nombre=bloques[1];
            float precio=Float.parseFloat(bloques[2]);
            String imagen=bloques[3];
            int cant=Integer.parseInt(bloques[4]);
            producto q=new producto(id,nombre, precio, imagen, cant);
            agregarSen(q);
            System.out.println("Productos cargados: " + tamañoListaSen());
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
        if (listaSenVacia()){
            cargarproductos();
        }
    }
    
    public List<producto> obtenerProductosPagina(int inicio, int cantidad) {
    return obtenerRango(inicio, cantidad);
        }

    public List<producto> obtenerRango(int inicio, int cantidad) {
        List<producto> resultado = new ArrayList<>();
        Nodo_LS actual = cab_s;
        int index = 0;

        while (actual != null && resultado.size() < cantidad) {
            if (index >= inicio) {
                resultado.add((producto)actual.dato);
            }
            actual = actual.sig;
            index++;
        }

        return resultado;
    }
    
    public void datosProducto(String direccion, producto prodSelected, metodos_generales modelo) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(direccion));
        Parent root = loader.load();
        controlador_infoproducto controller = loader.getController();
        controller.ModeloCompartido(modelo, prodSelected);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    public void datosCarrito(String direccion, producto prodSelected, metodos_generales modelo) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(direccion));
        Parent root = loader.load();
        controlador_infoproducto controller = loader.getController();
        controller.ModeloCompartido(modelo, prodSelected);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void agregarCarrito(producto p){
        if (BuscarId(p.idp)==null){
            Push(p);
        }
    }

    //Metodos lista sencilla
    public Nodo_LS<producto> cab_s;
     
    public boolean listaSenVacia(){ 
        return cab_s==null?true:false; 
    }
    
    public void agregarSen(producto prod) {
        Nodo_LS nuevo = new Nodo_LS(prod);
        if (cab_s == null) {
            cab_s = nuevo;
        } else {
            Nodo_LS actual = cab_s;
            while (actual.sig != null) {
                actual = actual.sig;
            }
            actual.sig  = nuevo;
        }
    }
    
        public producto buscarProductoPorID(String id) {
    Nodo_LS <producto> actual = cab_s;
    while (actual != null) {
        if (actual.dato.idp.equals(id)) {
            return actual.dato;
        }
        actual = actual.sig;
    }
    return null;
}
        
    public Nodo_LS<producto> BuscarCatalogo(String idE){
        if(listaSenVacia()) return null;
        else{
            Nodo_LS<producto> p=cab_s;
            do{
                if(p.dato.idp.equals(idE))
                    return p;
                else
                    p=p.sig;
            }while(p!=cab_s);
            return null;
        }
    }
    
    public int tamañoListaSen(){
        if(listaSenVacia()) return 0;
        else{
            Nodo_LS<producto> p=cab_s;
            int cont=0;
            while(p!=null){
                cont++;
                p=p.sig;
            }
            return cont;
        }
    }
    
    public void actualizarcantidad(int cant, String id) {
    Nodo_LS<producto> actual = cab_s;
    while (actual != null) {
        if (actual.dato.idp.equals(id)) {
            actual.dato.cantidad=actual.dato.cantidad-cant;
        }
        actual = actual.sig;
    }
}
    
    public void actualizarArchivo(String idProducto, int nuevaCantidad) {
    File archivoOriginal = new File("src/Archivos/listaproductos.txt");
    File archivoTemporal = new File("src/Archivos/temp_productos.txt");

    try (BufferedReader lector = new BufferedReader(new FileReader(archivoOriginal));
         BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoTemporal))) {

        String linea;
        boolean encontrado = false;

        while ((linea = lector.readLine()) != null) {
            String[] campos = linea.split(",");

            if (campos.length == 5 && campos[0].equals(idProducto)) {
                campos[4] = String.valueOf(nuevaCantidad);
                linea = String.join(",", campos);
                encontrado = true;
            }

            escritor.write(linea);
            escritor.newLine();
        }

        if (!encontrado) {
            System.out.println("Producto con ID " + idProducto + " no encontrado.");
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    if (archivoOriginal.delete()) {
        if (!archivoTemporal.renameTo(archivoOriginal)) {
            System.out.println("No se pudo renombrar el archivo temporal.");
        }
    } else {
        System.out.println("No se pudo eliminar el archivo original.");
    }
}

    

    
    //Metodos para IDs
    
    public Set<String> obtenerIDsExistentes(String archivo) {
    Set<String> ids = new HashSet<>();
    try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length > 0) {
                ids.add(partes[0].trim());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return ids;
}

    public String generarIDUnico(Set<String> existentes, String tipo) {
    int contador = 100;
    String pref="";
    String nuevoID;
    switch(tipo){
        case "usuario":
            pref="U";
            break;
        
        case"producto":
            pref="P";
            break;
    }
    do {
        nuevoID = pref + contador;
        contador++;
    } while (existentes.contains(nuevoID));
    return nuevoID;
}
    
    //Metodos lista doble
    
    public Nodo_LD<producto> cab_f;
    
    
    public boolean listaVaciaDob(){ 
        return cab_f==null?true:false; 
    }
    
    public Nodo_LD<producto> Ultimo(){
        if(listaVaciaDob()) return null;
        else{
            Nodo_LD<producto> p=cab_f;
            while(p.sig!=null)
                p=p.sig;
            return p;
        }
    }
    
    public boolean AñadirInicio(producto datos){
        producto temp = datos;
        if(temp!=null){
            Nodo_LD<producto> info = new Nodo_LD(temp);
            if(listaVaciaDob())
                cab_f=info;
            else{
                info.sig = cab_f;
                cab_f.ant = info;
                cab_f = info;
            }
            return true;
        }else{
            return false;
        }
    }

    
    public void cargarFavoritos(String userId) {
    try (BufferedReader br = new BufferedReader(new FileReader("src/Archivos/listafavoritos.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(":");
            if (partes.length == 2 && partes[0].equals(userId)) {
                String[] idsProductos = partes[1].split(",");
                for (String id : idsProductos) {
                    producto p = buscarProductoPorID(id.trim());
                    if (p != null) {
                        AñadirInicio(p);
                    }
                }
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
  
    public void compraunitaria(historial h){
        try{
        BufferedWriter guardar = new BufferedWriter(new FileWriter("src/Archivos/historiales.txt", true));
            guardar.write(h.id+","+h.fecha+","+h.direccion+","+h.articulos[0]+","+String.valueOf(h.total));
            guardar.newLine();
            guardar.close();
            }catch(IOException e){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setTitle("Error");
                alerta.setContentText("No se pudo modificar el archivo");
                alerta.showAndWait(); 
        } 
    }
    
    public void compramultiple(historial h){
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Archivos/historiales.txt", true))) {
            String campoArray = String.join(";", h.articulos);
            String linea = String.join(",",h.id,h.fecha,h.direccion,campoArray,String.valueOf(h.total));

            writer.write(linea);
            writer.newLine();
        
    } catch (IOException e) {
        e.printStackTrace();
        return;
        }

    }
    
    //Metodos para colas
    
    public Nodo_LS<producto> tope_c;
    
    public boolean CarritoVacio(){ 
        return tope_c==null?true:false;
    }
    
    public Nodo_LS<producto> Base(){
        if(CarritoVacio()) return null;
        else{
            Nodo_LS<producto> p=tope_c;
            while(p.sig!=tope_c)
                p=p.sig;
            return p;
        }
    }
    
    public Nodo_LS<producto> AntesNodo(Nodo_LS<producto> p){
        if(CarritoVacio()) return null;
        else{
            Nodo_LS<producto> q=tope_c;
            while(q.sig!=p)
                q=q.sig;
            return q;
        }
    }
    
    public Nodo_LS<producto> BuscarId(String idE){
        if(CarritoVacio()) return null;
        else{
            Nodo_LS<producto> p=tope_c;
            while(p!=null){
                if(p.dato.idp.equals(idE))
                    return p;
                else
                    p=p.sig;
            }
            return null;
        }
    }
    
    public boolean Push(producto p){
        if(p!=null){
            producto copia = new producto(p.idp,p.nombre,p.precio,p.imagen,0);
            Nodo_LS<producto> info = new Nodo_LS(copia);
            if(CarritoVacio()){
                tope_c=info;
            }else{
                info.sig = tope_c;
                tope_c=info;
            }
            return true;
        }else{
            return false;
        }
    }
    
    public void Pop(){
        if(CarritoVacio()){
            JOptionPane.showMessageDialog(null, 
                "La pila esta vacíá!!");
        }
        else if(tope_c.sig==tope_c){
            tope_c=null;
        }else{
            Nodo_LS<producto> base=Base(), e=tope_c;
            tope_c=tope_c.sig;
            base.sig=tope_c;
            e.sig=null;
            e=null;
            JOptionPane.showMessageDialog(null, 
                "Elemento atendido!!");
        }
    }
    
    public void eliminarProdCarrito(String idProducto) {
    if (tope_c != null){

    if (tope_c.dato.idp.equals(idProducto)) {
        tope_c = tope_c.sig;
    }

    Nodo_LS<producto> anterior = tope_c;
    Nodo_LS<producto> actual  = tope_c.sig;
    while (actual != null) {
        if (actual.dato.idp.equals(idProducto)) {
            anterior.sig = actual.sig;
        }
        anterior = actual;
        actual   = actual.sig;
    }}

}
 

    
}
