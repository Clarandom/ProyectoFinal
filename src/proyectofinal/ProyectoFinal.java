/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import interfazGUI.VentanaPrincipal;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Clara
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        DataBase db = new DataBase("xe","clalocal", "case");
        VentanaPrincipal vP = new VentanaPrincipal(db);
//       System.out.println(pruebi.abrirConexion());
//        ResultSet probando =  pruebi.ejecutaConsulta("select * from PRODUCTOS");
//        pruebi.recorreResultado(probando);
//        System.out.println(pruebi.buscaRegistro2("JUEGO"));
//        
//        ArrayList <Producto> productos = db.listadoProductos();
//        
//        for (Producto p : productos) {
//            System.out.println(p);
//        }
   
        
    }
    
}
