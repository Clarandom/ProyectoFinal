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
 * Clase que nos permite realizar una conexión con la base de datos, y realizar
 * diferentes consultas sobre esta.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class DataBase {

    private Connection conexion;
    private String servidorOracle = "jdbc:oracle:thin:@localhost:1521"; //servidor + host + port 
    private String dataBase;
    private String user;
    private String password;

    /**
     * Constructor para determinar un nombre, usuario y contraseña de la base de
     * datos sobre la que nos conectaremos. También se registra el driver de
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para recorrer la tabla Productos de la Base de Datos. Cada fila
     * creará un objeto Producto que irá almacenando en un ArrayList.
     *
     * @return ArrayList de productos
     */
    public ArrayList<Producto> listadoProductos() {
        abrirConexion();
        String sentencia = "SELECT * from productos order by nombre";
        ResultSet rs;
        PreparedStatement st;
        ArrayList<Producto> productos = new ArrayList<>();
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

        cerrarConexion();
        return productos;
    }

    /**
     * Método listadoProductos() con un filtro por nombre de Proveedor(Razón
     * social), por lo que requiere hcer uso también de la tabla Proveedores.
     *
     * @param nombre nombre del Proveedor que buscará.
     * @return ArrayList de productos
     */
    public ArrayList<Producto> listadoProductos(String nombre) {
        abrirConexion();
        String sentencia = "SELECT p.id_prod, p.nombre, p.id_proveedor, p.descripcion, p.tipo "
                + "FROM productos p JOIN proveedores pv ON (p.id_proveedor = pv.id_proveedor) "
                + "where pv.razon_social = ?";
        ResultSet rs;
        PreparedStatement st;
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            st = conexion.prepareStatement(sentencia);
            st.setString(1, nombre);
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
        cerrarConexion();
        return productos;
    }

    /**
     * Método listadoProductos() con un filtro por número de pedido firme, por
     * lo que requiere hcer uso también de la tabla Linea_pedido_firme.
     *
     * @param idPedidoFirme id del pedido firme que buscará.
     * @return ArrayList de productos
     */
    public ArrayList<Producto> listadoProductos(int idPedidoFirme) {
        abrirConexion();
        String sentencia = "SELECT p.id_prod, p.nombre, p.id_proveedor, p.descripcion, p.tipo "
                + "FROM productos p JOIN linea_pedido_firme l ON (p.id_prod = l.id_producto) "
                + "where l.id_ped_firme = ?";
        ResultSet rs;
        PreparedStatement st;
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            st = conexion.prepareStatement(sentencia);
            st.setInt(1, idPedidoFirme);
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
        cerrarConexion();
        return productos;
    }

    /**
     * Método que recibe un producto y lo da de alta en la base de datos.
     *
     * @param producto nuevo producto a insertar en la base de datos.
     * @return true si no ha capturado ninguna excepción.
     */
    public boolean altaProducto(Producto producto) {
        abrirConexion();
        boolean altaCorrecta = false;
        //la id del producto se calculará gracias a la secuencia sec_producto.
        String sentencia = "insert into productos (id_prod, nombre, id_proveedor, "
                + "descripcion, tipo) values (sec_producto.nextval,?,?,?,?)";
        PreparedStatement st;
        try {
            st = conexion.prepareStatement(sentencia);
            st.setString(1, producto.getNombre());
            st.setInt(2, producto.getIdProveedor());
            st.setString(3, producto.getDescripcion());
            st.setString(4, producto.getTipo());
            st.executeUpdate();
            st.close();
            altaCorrecta = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarConexion();
        return altaCorrecta;
    }

    /**
     * Método que recibe un producto y lo da de baja en la base de datos.
     *
     * @param idProducto id del producto que se desea borrar.
     * @return true si no captura ninguna excepción.
     */
    public boolean bajaProducto(int idProducto) {
        abrirConexion();
        boolean bajaCorrecta = false;
        String sentencia = "delete from productos where id_prod = ?";
        PreparedStatement st;
        try {
            st = conexion.prepareStatement(sentencia);
            st.setInt(1, idProducto);
            st.executeUpdate();
            st.close();
            bajaCorrecta = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarConexion();
        return bajaCorrecta;
    }

    /**
     * Método que modifica un producto en la base de datos. Le pasaremos como
     * parámetros todos los atributos para modificar, excepto la id que se
     * utilizará para buscar el registro en la tabla productos.
     *
     * @param idProducto id del producto a buscar.
     * @param nombre nuevo nombre del producto.
     * @param idProveedor nueva id del producto.
     * @param descripcion nueva descripción del producto.
     * @param tipo nuevo tipo del producto.
     * @return true si se realiza correctamente.
     */
    public boolean modificaProducto(int idProducto, String nombre, int idProveedor, String descripcion, String tipo) {
        abrirConexion();
        boolean modificacionCorrecta = false;
        String sentencia = "update productos set nombre = ?, id_proveedor = ?, descripcion = ?, tipo = ? where id_prod = ?";
        PreparedStatement st;
        try {
            st = conexion.prepareStatement(sentencia);
            st.setString(1, nombre);
            st.setInt(2, idProveedor);
            st.setString(3, descripcion);
            st.setString(4, tipo);
            st.setInt(5, idProducto);
            st.executeUpdate();
            st.close();
            modificacionCorrecta = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarConexion();
        return modificacionCorrecta;
    }

    /**
     * Método para buscar un producto en la tabla productos. Devolverá un nuevo
     * producto con los datos del producto encontrado.
     *
     * @param idBuscar id del producto que va a buscar.
     * @return Producto con los atributos del producto encontrado. Null si no
     * encuentra la id del producto.
     */
    public Producto buscaProducto(int idBuscar) {
        abrirConexion();
        Producto producto = null;
        ResultSet rs;
        PreparedStatement st;
        // Sustituimos la variable por un ?
        String sentencia = "SELECT * from PRODUCTOS where id_prod = ?";

        try {
            st = conexion.prepareStatement(sentencia);
            //Pasamos los valores a cada uno de los interrogantes
            //comenzamos numerando por el 1
            st.setInt(1, idBuscar);
            // En este caso te pediría que fuera un entero ---> st.setInt(2,20);
            rs = st.executeQuery();
            if (rs.next()) { //si el puntero no apunta a nada, pues no entra al if, no quiero mostrar la ventana.

                producto = new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5));
            } else {
                producto = null;
            }
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error con la base de datos: " + ex.getMessage());
        }
        cerrarConexion();
        return producto;
    }

    public boolean buscaProveedor(int idBuscar) {
        abrirConexion();
        boolean existe = false;
        ResultSet rs;
        PreparedStatement st;
        String sentencia = "SELECT * from PROVEEDORES where id_proveedor= ?";
        try {
            st = conexion.prepareStatement(sentencia);
            //Pasamos los valores a cada uno de los interrogantes
            //comenzamos numerando por el 1
            st.setInt(1, idBuscar);
            // En este caso te pediría que fuera un entero ---> st.setInt(2,20);
            rs = st.executeQuery();
            existe = rs.next();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error con la base de datos: " + ex.getMessage());
        }
        cerrarConexion();
        return existe;
    }

}
