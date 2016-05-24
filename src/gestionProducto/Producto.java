/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionProducto;

/**
 * Clase que representa un producto con sus correspondientes atributos.
 *
 * @author Clara
 */
public class Producto implements Comparable<Producto> {

    private int idProducto;
    private String nombre;
    private int idProveedor;
    private String descripcion;
    private String tipo;

    /**
     * Constructor al que le paso todos los atributos de la clase Producto.
     *
     * @param idProducto
     * @param nombre
     * @param idProveedor
     * @param descripcion
     * @param tipo
     */
    public Producto(int idProducto, String nombre, int idProveedor, String descripcion, String tipo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.idProveedor = idProveedor;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Producto(String nombre, int idProveedor, String descripcion, String tipo) {
        this.nombre = nombre;
        this.idProveedor = idProveedor;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the idProveedor
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return idProducto + " " + nombre + " " + idProveedor + " " + descripcion + " " + tipo;
    }

    @Override
    public int compareTo(Producto p) {
        return this.nombre.compareToIgnoreCase(p.nombre);
    }

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
