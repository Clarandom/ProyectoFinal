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

    /**
     * Método para recorrer
     *
     * @return
     */
    public ArrayList<Producto> listadoProductos() {
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

    public void altaProducto(Producto producto) {
        abrirConexion();
        String sentencia = "insert into productos (id_prod, nombre, id_proveedor, descripcion, tipo) values (sec_producto.nextval,?,?,?,?)";
        PreparedStatement st = null;
        try {
            st = conexion.prepareStatement(sentencia);
            st.setString(1, producto.getNombre());
            st.setInt(2, producto.getIdProveedor());
            st.setString(3, producto.getDescripcion());
            st.setString(4, producto.getTipo());
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarConexion();
    }
}
