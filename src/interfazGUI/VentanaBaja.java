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

/**
 * Ventana que permite eliminar un producto buscándolo por su id.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class VentanaBaja extends JFrame implements ActionListener {

    JPanel contenedor;
    JButton botonBuscar, botonBaja, botonCerrar;
    JTextField buscar, idProducto, nombre, proveedor, descripcion, tipo;
    JLabel etiquetaIdProducto, etiquetaNombre, etiquetaProveedor, etiquetaDescripcion, etiquetaTipo;
    DataBase db;
    Producto producto;

    /**
     * Constructor que recibe la base de datos como parámetro y se encarga de
     * hacer visible la ventana, así como inicializar todos los componentes
     * necesarios para ésta, gracias a la llamada al método initComponenents().
     *
     * @param db nueva DataBase
     */
    public VentanaBaja(DataBase db) {
        this.db = db;
        this.setTitle("Baja Producto");
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
        botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(this);
        botonCerrar.setActionCommand("cerrar");
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
        contenedor.add(botonCerrar);
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
     * Método que busca un producto por la id que se haya escrito en el cuadro
     * de búsqueda. Si introducimos una id válida almacena el producto
     * encontrado en el atributo Producto además de mostrar sus datos; de lo
     * contrario nos mostrará un error.
     */
    private void busca() {
        producto = db.buscaProducto(Integer.parseInt(buscar.getText()));
        if (producto == null) {
            ventanaError("Registro no encontrado");
        } else {
            idProducto.setText(producto.getIdProducto() + "");
            nombre.setText(producto.getNombre());
            proveedor.setText(Integer.toString(producto.getIdProveedor()));
            descripcion.setText(producto.getDescripcion());
            tipo.setText(producto.getTipo());

        }
    }

    /**
     * Método que da de baja un producto en la base de datos. Muestra una
     * ventana emergente indicando si ha sido posible o no.
     */
    private void baja() {
        if (db.bajaProducto(producto.getIdProducto())) {
            ventanaAviso("Producto eliminado correctamente");
        } else {
            ventanaError("No ha sido posible eliminar el producto.");
        }
    }

    /**
     * Método que controla el botón que ha sido seleccionado para realizar una
     * acción determinada.
     *
     * @param e acción realizada.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "buscar":
                busca();
                break;
            case "baja":
                baja();
                break;
            case "cerrar":
                this.dispose();
            default:
        }
    }
}
