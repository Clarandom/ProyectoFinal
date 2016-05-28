package gestionProducto;

/**
 * Clase que representa un producto con sus correspondientes atributos.
 *
 * @author Clara
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private int idProveedor;
    private String descripcion;
    private String tipo;

    /**
     * Constructor al que le paso todos los atributos de la clase Producto.
     *
     * @param idProducto id del producto
     * @param nombre nombre del producto
     * @param idProveedor id del proveedor que comercializa el producto
     * @param descripcion descripción del producto
     * @param tipo tipo del producto.
     */
    public Producto(int idProducto, String nombre, int idProveedor, String descripcion, String tipo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.idProveedor = idProveedor;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    /**
     * Constructor al que le paso todos los atributos de la clase Producto
     * excepto la id, ya que para el alta se la asignará automáticamente la bdd
     * gracias a una secuencia.
     *
     * @param nombre nombre del producto
     * @param idProveedor id del proveedor que comercializa el producto
     * @param descripcion descripción del producto
     * @param tipo tipo del producto.
     */
    public Producto(String nombre, int idProveedor, String descripcion, String tipo) {
        this.nombre = nombre;
        this.idProveedor = idProveedor;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    /**
     * Método que devuelve la id del producto.
     *
     * @return idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Método que devuelve el nombre del producto.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que devuelve la id del proveedor.
     *
     * @return idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * Método que devuelve la descripción del producto.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que deuvelve el tipo del producto.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método que inroduce todos los atrbutos en un array de String, y lo
     * devuelve.
     *
     * @return array con los datos del producto.
     */
    public String[] getArrayProducto() {
        String[] datosProducto = new String[5];
        datosProducto[0] = Integer.toString(idProducto);
        datosProducto[1] = nombre;
        datosProducto[2] = Integer.toString(idProveedor);
        datosProducto[3] = descripcion;
        datosProducto[4] = tipo;

        return datosProducto;

    }
}
