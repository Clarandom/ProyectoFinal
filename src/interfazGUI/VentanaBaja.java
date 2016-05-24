/*
 * Cambia tipo de moneda
 */
package interfazGUI;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaBaja extends JFrame implements ActionListener {
    
    JPanel contenedor;
    JButton botonBuscar, botonBaja, botonBorrar;
    JTextField buscar, idProducto, nombre, proveedor, descripcion, tipo;
    JLabel etiquetaIdProducto, etiquetaNombre, etiquetaProveedor, etiquetaDescripcion, etiquetaTipo;
    DataBase db;
    Producto producto;
    
    public VentanaBaja(DataBase db) {
        
        this.db = db;
        this.setTitle("Baja Producto");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }
    
    private void initComponents() {
        //Utilizo todo el fondo del JFrame
        contenedor = (JPanel) this.getContentPane();
        //Inicializo un layout
        contenedor.setLayout(new GridLayout(7, 2, 5, 5));
        //Inicializo los objetos

        buscar = new JTextField();
        
        etiquetaIdProducto = new JLabel("Producto: ");
        idProducto = new JTextField();
        
        etiquetaNombre = new JLabel("Nombre: ");
        nombre = new JTextField();
        
        etiquetaProveedor = new JLabel("Proveedor: ");
        proveedor = new JTextField();
        
        etiquetaTipo = new JLabel("Tipo: ");
        tipo = new JTextField();
        
        etiquetaDescripcion = new JLabel("Descripcion: ");
        descripcion = new JTextField();
        
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(this);
        botonBuscar.setActionCommand("buscar");
        botonBaja = new JButton("Baja");
        botonBaja.addActionListener(this);
        botonBaja.setActionCommand("baja");
        botonBorrar = new JButton("Borrar");
        botonBorrar.addActionListener(this);
        botonBorrar.setActionCommand("borrar");
        //los pongo en el contendor
        contenedor.add(buscar);
        contenedor.add(botonBuscar);
        contenedor.add(etiquetaIdProducto);
        contenedor.add(idProducto);
        contenedor.add(etiquetaProveedor);
        contenedor.add(proveedor);
        contenedor.add(etiquetaNombre);
        contenedor.add(nombre);
        contenedor.add(etiquetaTipo);
        contenedor.add(tipo);
        contenedor.add(etiquetaDescripcion);
        contenedor.add(descripcion);
        
        contenedor.add(botonBaja);
        contenedor.add(botonBorrar);
    }
    
    private void limpiaPantalla() {
        nombre.setText(null);
        
    }
    
    private void ventanaError(String cadena) {
        JOptionPane.showMessageDialog(
                this, cadena,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void busca() {
        db.abrirConexion();
        
        producto = db.buscaProducto(Integer.parseInt(buscar.getText()));
        
        if (producto == null) {
            ventanaError("Registro no encontrado");
        } else {
            idProducto.setText(producto.getIdProducto() + "");
            nombre.setText(producto.getNombre());
            proveedor.setText(Integer.toString(producto.getIdProveedor()));
            descripcion.setText(producto.getDescripcion());
            tipo.setText(producto.getTipo());
            //le pasamos al método de la base de datos el nombre a buscar
        }
        db.cerrarConexion();
    }

//    private void alta() {
//        if (compruebaCadena20(nombre.getText())) {
//
//            db.alta(new Alumno(nombre.getText()));
//            ventanaError("Añadido Correctamente ;D");
//
//        } else {
//            ventanaError("");
//        }
//    }
    private boolean compruebaCadena20(String cadena) {
        return cadena.length() > 0 && cadena.length() <= 20;
    }
    
    private void fin() {
        //importante, cerrar al bd
        db.cerrarConexion();
        System.exit(0);
    }
    
    private void baja() {
        db.bajaProducto(Integer.parseInt(buscar.getText()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "buscar":
                busca();
                break;
            case "baja":
                baja();
              //  this.dispose();
                break;
            default:
                limpiaPantalla();
        }
    }
    
}
