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

public class VentanaAlta extends JFrame implements ActionListener {

    JPanel contenedor;
    JButton botonAlta, botonBorrar;
    JTextField nombre, proveedor, descripcion, tipo;
    JLabel etiquetaNombre, etiquetaProveedor, etiquetaDescripcion, etiquetaTipo;
    DataBase db;

    public VentanaAlta(DataBase db) {

        this.db = db;
        this.setTitle("Alta Producto");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }

    private void initComponents() {
        //Utilizo todo el fondo del JFrame
        contenedor = (JPanel) this.getContentPane();
        //Inicializo un layout
        contenedor.setLayout(new GridLayout(5, 2, 5, 5));
        //Inicializo los objetos
        etiquetaNombre = new JLabel("Nombre: ");
        nombre = new JTextField();

        etiquetaProveedor = new JLabel("Proveedor: ");
        proveedor = new JTextField();

        etiquetaTipo = new JLabel("Tipo: ");
        tipo = new JTextField();

        etiquetaDescripcion = new JLabel("Descripcion: ");
        descripcion = new JTextField();

        botonAlta = new JButton("Alta");
        botonAlta.addActionListener(this);
        botonAlta.setActionCommand("alta");
        botonBorrar = new JButton("Borrar");
        botonBorrar.addActionListener(this);
        botonBorrar.setActionCommand("borrar");
        //los pongo en el contendor
        contenedor.add(etiquetaProveedor);
        contenedor.add(proveedor);
        contenedor.add(etiquetaNombre);
        contenedor.add(nombre);
        contenedor.add(etiquetaTipo);
        contenedor.add(tipo);
        contenedor.add(etiquetaDescripcion);
        contenedor.add(descripcion);
        contenedor.add(botonAlta);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "alta":
                Producto producto = new Producto(nombre.getText(), Integer.parseInt(proveedor.getText()),
                        descripcion.getText(), tipo.getText());
                db.altaProducto(producto);
                this.dispose();
                break;
            default:
                limpiaPantalla();
        }
    }

}
