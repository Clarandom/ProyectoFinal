/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import conexionBaseDeDatos.DataBase;
import interfazGUI.VentanaPrincipal;

/**
 * Clase principal que pone en ejecución el programa.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Paso como parámetro nombre de la base de datos, usuario y contraseña.
        DataBase db = new DataBase("xe", "clalocal", "case");
        VentanaPrincipal vP = new VentanaPrincipal(db);
    }

}
