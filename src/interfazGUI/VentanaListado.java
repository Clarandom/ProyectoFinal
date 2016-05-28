package interfazGUI;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que permite la visualización de listados, permitiéndome añadir varios
 * filtros: por proveedor y por pedido firme.
 *
 * @author Clara Subirón
 *
 */
public class VentanaListado extends JFrame implements ActionListener {

    JTextField buscarPe, buscarPro;
    JButton bBuscarPe, bBuscarPro, botones[];
    JPanel contenedor, contenedorBuscar, contenedorTabla, contenedorOpciones, contenedorIzquierda;
    JTable table;
    DefaultTableModel modelo;
    ArrayList<Producto> productos = new ArrayList<>();
    DataBase db;
    JLabel textoProveedor, textoPedido;

    /**
     * Constructor que recibe la base de datos como parámetro y se encarga de
     * hacer visible la ventana, así como inicializar todos los componentes
     * necesarios para ésta, gracias a la llamada al método initComponenents().
     *
     * @param db nueva DataBase
     */
    public VentanaListado(DataBase db) {
        this.db = db;
        this.setTitle("Listado");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(800, 200);
    }

    /**
     * Método que inicializa todos los componentes de la ventana y los añade a
     * un contenedor.
     *
     */
    public void initComponents() {
        //inicializo "contenedor" (será el que me permita dividir la ventana 
        //final en 2: contenedorIzquierda y tabla)
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new GridLayout(1, 2, 2, 2));
        //inicializo "contenedorIzquierda" que contiene todas las opciones de mi listado.
        contenedorIzquierda = new JPanel();
        contenedorIzquierda.setLayout(new GridLayout(3, 1));
        //añado las opciones.
        rellenarContenedorIzquierda();
        //contenedorTabla contendrá el listado.
        contenedorTabla = new JPanel();
        creoTabla();
        JScrollPane scrollPane = new JScrollPane(table);
        contenedor.add(contenedorIzquierda);
        contenedor.add(scrollPane);
    }

    /**
     * Método para agregar opciones a la izquierda del listado
     * (contenedorIzquierda). Lo divide en en otros dos contenedores
     * (contenedorOpciones y contenedorBuscar) únicamente por poder controlar el
     * tamaño de mis botones.
     */
    public void rellenarContenedorIzquierda() {
        contenedorBuscar = new JPanel();
        contenedorBuscar.setLayout(new GridLayout(2, 3, 5, 5));
        //elementos para búsqueda por Pedido
        textoPedido = new JLabel();
        textoPedido.setText("POR PEDIDO");
        buscarPe = new JTextField();
        bBuscarPe = new JButton("Buscar");
        bBuscarPe.setActionCommand("buscarPe");
        bBuscarPe.addActionListener(this);
        //elementos para búsqueda por Proveedor
        textoProveedor = new JLabel();
        textoProveedor.setText("POR PROVEEDOR");
        buscarPro = new JTextField();
        bBuscarPro = new JButton("Buscar");
        bBuscarPro.setActionCommand("buscarPro");
        bBuscarPro.addActionListener(this);
        //los añado a contenedor Buscar
        contenedorBuscar.add(textoPedido);
        contenedorBuscar.add(buscarPe);
        contenedorBuscar.add(bBuscarPe);
        contenedorBuscar.add(textoProveedor);
        contenedorBuscar.add(buscarPro);
        contenedorBuscar.add(bBuscarPro);
        //paso a diseñar contenedor Opciones.
        contenedorOpciones = new JPanel();
        String textoBotones[] = {"Completo", "Limpiar Tabla", "Fin"};
        contenedorOpciones.setLayout(new GridLayout(1, textoBotones.length));
        botones = new JButton[textoBotones.length];
        for (int i = 0; i < textoBotones.length; i++) {
            botones[i] = new JButton();
            botones[i].setText(textoBotones[i]);
            botones[i].setActionCommand(textoBotones[i]);
            botones[i].addActionListener(this);
            contenedorOpciones.add(botones[i]);
        }
        contenedorIzquierda.add(contenedorBuscar);
        contenedorIzquierda.add(new JPanel());
        contenedorIzquierda.add(contenedorOpciones);

    }

    /**
     * Método para inicializar el modelo de mi tabla y añadirle los nombres de
     * las columnas.
     */
    public void creoTabla() {
        modelo = new DefaultTableModel();
        table = new JTable(modelo);
        modelo.addColumn("IdProveedor");
        modelo.addColumn("IdProducto");
        modelo.addColumn("Nombre Prducto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Tipo");
    }

    /**
     * Métdo para añadir filas a la tabla con los datos de Producto.
     */
    public void muestraFilas() {
        modelo.setRowCount(0);
        String datos[];
        for (Producto pro : productos) {
            datos = pro.getArrayProducto();
            modelo.addRow(datos);
        }
    }

    /**
     * Método que busca productos por la id de pedido que se haya escrito en el
     * cuadro de búsqueda correspondiente. Si introducimos una id válida muestra
     * los productos encontrados en la tabla; de lo contrario nos mostrará un
     * error.
     */
    private void buscaPedido() {
        try {
            productos = db.listadoProductos(Integer.parseInt(buscarPe.getText()));
            if (productos.isEmpty()) {
                ventanaError("Registro no encontrado");
            } else {
                muestraFilas();
            }
        } catch (NumberFormatException e) {
            ventanaError("La ID del pedido debe ser un número");

        }
    }

    /**
     * Método que busca productos por el nombre de proveedor que se haya escrito
     * en el cuadro de búsqueda correspondiente. Si introducimos proveedor
     * existente nos muestra los productos encontrados en la tabla; de lo
     * contrario nos mostrará un aviso. Se permite buscar por cualquier carácter
     * debido a que son permitidos en los nombres.
     *
     */
    private void buscaProveedor() {
       
        productos = db.listadoProductos(buscarPro.getText());
        if (productos.isEmpty()) {
            ventanaError("Registro no encontrado");
        } else {
            muestraFilas();
        }
    }

    private void buscaProductos() {
        productos = db.listadoProductos();
        if (productos.isEmpty()) {
            ventanaError("No hay productos.");
        } else {
            muestraFilas();
        }
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
     * Método que controla el botón que ha sido seleccionado para realizar una
     * acción determinada.
     *
     * @param ae acción realizada.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "buscarPe":
                buscaPedido();
                break;
            case "buscarPro":
                buscaProveedor();
                break;
            case "Completo":
                buscaProductos();
                break;
            case "Limpiar Tabla":
                modelo.setRowCount(0);
                break;
            case "Fin":
                this.dispose();
                break;
        }
    }
}
