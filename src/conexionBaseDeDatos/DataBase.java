/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionBaseDeDatos;

import gestionProducto.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.driver.OracleDriver;

/**
 *
 * @author Clara
 */
public class DataBase {

    private Connection conexion = null;
    private String servidorOracle = "jdbc:oracle:thin:@localhost:1521";
    private String dataBase = null;
    private String user = null;
    private String password = null;

    /**
     * Constructor para determinar un nombre de base de datos, usuario y
     * contraseña para el servidor oracle. También se registra el driver de
     * Oracle.
     *
     * @param dataBase nombre de la base de datos
     * @param user usuario
     * @param password contraseña
     */
    public DataBase(String dataBase, String user, String password) {
        this.dataBase = dataBase;
        this.user = user;
        this.password = password;
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Método que abre una conexión con los datos de la clase.
     *
     * @return true si la conexión se realiza correctamente.
     */
    public boolean abrirConexion() {
        boolean estado = false;
        try {
            conexion = DriverManager.getConnection(servidorOracle
                    + ":" + dataBase, user, password);
            System.out.println(" Parece ser que nos hemos conectado");
            //    conexion.setAutoCommit(false);
            estado = true;
        } catch (SQLException e) {
            System.out.println("SQL Exception:\n" + e.toString());
        } catch (Exception e) {
            System.out.println("Exception:\n" + e.toString());
        }
        return estado;
    }

    /**
     * Metodo que cierra la conexion con la base de datos.
     */
    public void cerrarConexion() {

        try {
            conexion.close();
            System.out.println("Se cerró :D");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int ejecutaUpdate(String statement) {

        int n = 0;
        try {

            Statement st = conexion.createStatement();
            System.out.println("La sentencia es: " + statement);

            n = st.executeUpdate(statement);
        } catch (SQLException ex) {
            System.out.println("SQL Exception:\n" + ex.getMessage());
        }
        return n;
    }

    public ResultSet ejecutaConsulta(String consulta) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conexion.createStatement();
            rs = st.executeQuery(consulta);
        } catch (SQLException ex) {
            System.out.println("Error sql: " + ex.getMessage());
        }
//        try {
//            st.close();
//        } catch (SQLException ex) {
//            System.out.println("Error sql: " + ex.getMessage());
//        }
        return rs;
    }

//    public boolean buscaRegistro(String nombreBuscar) {
//        ResultSet rs;
//        PreparedStatement st;
//        // Sustituimos la variable por un ?
//        String sentencia = "SELECT * from PRODUCTOS where nombre= ?";
//        System.out.println(sentencia);
//        System.out.println(sentencia);
//        try {
//            st = conexion.prepareStatement(sentencia);
//            //Pasamos los valores a cada uno de los interrogantes
//            //comenzamos numerando por el 1
//            st.setString(1, nombreBuscar);
//            // En este caso te pediría que fuera un entero ---> st.setInt(2,20);
//            rs = st.executeQuery();
//            if (rs.next()) { //si el puntero no apunta a nada, pues no entra al if, no quiero mostrar la ventana.
//
//                Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3),
//                        rs.getString(4), rs.getString(5));
//            } else {
//                return false;
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error con la base de datos: " + ex.getMessage());
//        }
//        return true; //aunque puede ser que se haya producido la excepción.  contamos conn el mensaje
//    }
    public Producto buscaRegistro2(String nombreBuscar) {
        Producto producto = null;
        ResultSet rs;
        PreparedStatement st;
        // Sustituimos la variable por un ?
        String sentencia = "SELECT * from PRODUCTOS where nombre= ?";
        System.out.println(sentencia);

        try {
            st = conexion.prepareStatement(sentencia);
            //Pasamos los valores a cada uno de los interrogantes
            //comenzamos numerando por el 1
            st.setString(1, nombreBuscar);
            // En este caso te pediría que fuera un entero ---> st.setInt(2,20);
            rs = st.executeQuery();
            if (rs.next()) { //si el puntero no apunta a nada, pues no entra al if, no quiero mostrar la ventana.

                producto = new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Error con la base de datos: " + ex.getMessage());
        }
        return producto;
    }

    public void cierraResultSet(ResultSet rs) {
        try {
            //cerramos el rs. porque garbage no puede eliminar el heap
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error con la base de datos: " + ex.getMessage());
        }
    }

    public void recorreResultado(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2)
                        + "\t" + rs.getString(3) + "\t" + rs.getString(4));
            }
        } catch (SQLException ex) {
            System.out.println("Error sql: " + ex.getMessage());
        }
    }
//
//    public void alta(Alumno al) {
//        abrirConexion();
//        int n = al.getNota()[0];
//
//        String cadena = "INSERT INTO alumnos (nombre, nota1, nota2, nota3) VALUES ('"
//                + al.getNombre() + "',  '" + al.getNota(0) + "',  '" + al.getNota(1) + "',  '" + al.getNota(2) + "')";
//
//        ejecutaUpdate(cadena);
//        cerrarConexion();
//    }
    // Abre la conexión , realiza la consulta, pasa todos los alumnos a un ArrayList, cierra las conexiones y devuelve el ArrayList

    /**
     * Método para recorrer
     *
     * @return
     */
    public ArrayList<Producto> listado() {
        abrirConexion();
        String sentencia = "SELECT * from productos";
        ResultSet rs;
        PreparedStatement st;
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try {
            st = conexion.prepareStatement(sentencia);
            rs = st.executeQuery();
            while (rs.next()) {
                productos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5)));
            }

            st.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(productos);
        cerrarConexion();
        return productos;
    }

    public void ordenarListado(ArrayList<Producto> productos) {
        Collections.sort(productos);
    }
}
