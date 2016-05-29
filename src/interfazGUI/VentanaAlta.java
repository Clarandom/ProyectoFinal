package interfazGUI;

import accesodatos.DataBase;
import gestionproductos.Producto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana que permite añadir un producto.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class VentanaAlta extends JFrame implements ActionListener {

    JPanel contenedor;
    JButton botonAlta, botonBorrar;
    JTextField nombre, proveedor, descripcion, tipo;
    JLabel etiquetaNombre, etiquetaProveedor, etiquetaDescripcion, etiquetaTipo;
    DataBase db;

    /**
     * Constructor que recibe la base de datos como parámetro y se encarga de
     * hacer visible la ventana, así como inicializar todos los componentes
     * necesarios para ésta, gracias a la llamada al método initComponenents().
     *
     * @param db nueva DataBase
     */
    public VentanaAlta(DataBase db) {

        this.db = db;
        this.setTitle("Alta Producto");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }

    /**
     * Método que inicializa todos los componentes de la ventana y los añade a
     * un contenedor.
     *
     */
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
        botonBorrar = new JButton("Borrar datos");
        botonBorrar.addActionListener(this);
        botonBorrar.setActionCommand("borrar");
        //los añado al contendor
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

    /**
     * Método que da de alta un producto en la base de datos. Muestra una
     * ventana emergente indicando si ha sido posible o no según el campo que
     * esté comprobando.
     */
    private void alta() {
        int idProveedor = proveedorCorrecto(proveedor.getText());
        if (idProveedor <= 0 || !db.buscaProveedor(idProveedor)) {
            ventanaError("Debe introducir una id de proveedor válida.");
        } else if (!compruebaCadena100(nombre.getText())) {
            ventanaError("Nombre incorrecto.");
        } else if (!compruebaCadena4(tipo.getText())) {
            ventanaError("Tipo incorrecto.");
        } else {
            if (compruebaCadena100(descripcion.getText())) {
                Producto producto = new Producto(nombre.getText(), idProveedor,
                        descripcion.getText(), tipo.getText());
                if (db.altaProducto(producto)) {
                    ventanaAviso("Alta correcta.");
                } else {
                    ventanaAviso("El producto ya existe.");
                }
            } else {
                ventanaError("Descripción incorrecta.");
            }
        }
    }

    /**
     * Método que convierte a int una id de proveedor, y la devuelve. Si no es
     * posible realizar la conversión o no tiene el número de carácteres
     * correcto devuelve 0.
     *
     * @param cadena id de proveedor en string.
     * @return id del proveedor en int.
     */
    public int proveedorCorrecto(String cadena) {
        int idProveedor = 0;
        try {
            idProveedor = Integer.valueOf(cadena);
            if (cadena.length() < 0 || cadena.length() > 4) {
                idProveedor = 0;
            }
        } catch (NumberFormatException e) {
        }
        return idProveedor;
    }

    /**
     * Comprueba que un String está comprendido entre 0 y 100 carácteres.
     *
     * @param cadena a comprobar
     * @return true si la cadena cumple las condiciones.
     */
    private boolean compruebaCadena100(String cadena) {
        return cadena.length() > 0 && cadena.length() <= 100;
    }

    /**
     * Comprueba que un String está comprendido entre 0 y 4 carácteres.
     *
     * @param cadena a comprobar
     * @return true si la cadena cumple las condiciones.
     */
    private boolean compruebaCadena4(String cadena) {
        return cadena.length() > 0 && cadena.length() <= 4;
    }

    /**
     * Crea una ventana emergente cuya finalidad es mostrar un error.
     *
     * @param cadena mensaje que mostrará la ventana
     */
    private void ventanaError(String cadena) {
        JOptionPane.showMessageDialog(
                this, cadena,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Crea una ventana emergente cuya finalidad es mostrar un aviso.
     *
     * @param cadena mensaje que mostrará la ventana
     */
    private void ventanaAviso(String cadena) {
        JOptionPane.showMessageDialog(
                this, cadena,
                "", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método para borrar todos los campos del producto encontrado.
     *
     */
    public void borra() {
        nombre.setText("");
        tipo.setText("");
        proveedor.setText("");
        descripcion.setText("");
    }

    /**
     * Método que controla el botón que ha sido seleccionado para realizar una
     * acción determinada.
     *
     * @param e acción realizada.
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "alta":
                alta();
                break;
            case "borrar":
                borra();
                break;
            default:

        }
    }

}
