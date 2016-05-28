package interfazGUI;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana que permite realizar la modificación de un producto buscándolo por su
 * id.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class VentanaModificacion extends JFrame implements ActionListener{

    JPanel contenedor;
    JButton botonBuscar, botonBorrar, botonModificar;
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
    public VentanaModificacion(DataBase db) {

        this.db = db;
        this.setTitle("Modificar Producto");
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
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new GridLayout(7, 2, 5, 5));

        buscar = new JTextField();
        etiquetaIdProducto = new JLabel("Producto: ");
        idProducto = new JTextField();
        idProducto.setEnabled(false);
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
        botonBorrar = new JButton("Borrar");
        botonBorrar.addActionListener(this);
        botonBorrar.setActionCommand("borrar");
        botonModificar = new JButton("Modificar");
        botonModificar.addActionListener(this);
        botonModificar.setActionCommand("modificar");
        //añado los elementos en el contenedor.
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
        contenedor.add(botonBorrar);
        contenedor.add(botonModificar);
  
    }

    /**
     * Método que busca un producto por la id que se haya escrito en el cuadro
     * de búsqueda. Si introducimos una id válida almacena el producto
     * encontrado en el atributo Producto además de mostrar sus datos; de lo
     * contrario nos mostrará un error.
     */
    private void busca() {
        try {
            int idProd = Integer.parseInt(buscar.getText());
            producto = db.buscaProducto(idProd);
            if (producto == null) {
                ventanaError("Registro no encontrado");
            } else {
                idProducto.setText(idProd + "");
                nombre.setText(producto.getNombre());
                proveedor.setText(Integer.toString(producto.getIdProveedor()));
                descripcion.setText(producto.getDescripcion());
                tipo.setText(producto.getTipo());
            }
        } catch (NumberFormatException e) {
            ventanaError("Debe introducir una id de producto válida.");
        }

    }

    /**
     * Método que modifica un producto de la base de datos, en caso de que los
     * datos introducidos en los cuadros de texto sean válidos (incluyendo que
     * la id de proveedor exista).
     */
    private void modifica() {
        if (!compruebaCadena100(nombre.getText())) {
            ventanaError("Nombre incorrecto.");
        } else if (!compruebaCadena5(tipo.getText())) {
            ventanaError("Tipo incorrecto.");
        } else if (!compruebaCadena100(descripcion.getText())) {
            ventanaError("Descripción incorrecta.");
        } else {
            int idProveedor = proveedorCorrecto(proveedor.getText());
            if (idProveedor > 0) {
                if (db.buscaProveedor(idProveedor)) {
                    db.modificaProducto(Integer.parseInt(idProducto.getText()),
                            nombre.getText(), idProveedor, descripcion.getText(),
                            tipo.getText());
                    ventanaAviso("Modificación correcta.");
                } else {
                    ventanaError("El proveedor no existe.");
                }
            } else {
                ventanaError("Debe introducir una id de proveedor válida.");
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
     * Comprueba que un String está comprendido entre 0 y 5 carácteres.
     *
     * @param cadena a comprobar
     * @return true si la cadena cumple las condiciones.
     */
    private boolean compruebaCadena5(String cadena) {
        return cadena.length() > 0 && cadena.length() <= 100;
    }

    /**
     * Método para borrar todos los campos del producto encontrado, excepto la
     * id.
     *
     */
    public void borra() {
        nombre.setText("");
        tipo.setText("");
        proveedor.setText("");
        descripcion.setText("");
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
     * Método que controla el botón que ha sido seleccionado para realizar una
     * acción determinada.
     *
     * @param e acción realizada.
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "buscar":
                busca();
                break;
            case "modificar":
                modifica();
                break;
            case "borrar":
                borra();
                break;
            default:
        }
    }
}
