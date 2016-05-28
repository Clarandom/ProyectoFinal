package interfazGUI;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class VentanaListado extends JFrame implements ActionListener, WindowListener {

    JTextField buscarPe, buscarPro;
    JButton bBuscarPe, bBuscarPro, botones[];
    JPanel contenedor, contenedorBuscar, contenedorTabla, contenedorOpciones, contenedorIzquierda;
    JTable table;
    DefaultTableModel modelo;
    ArrayList<Producto> productos = new ArrayList<>();
    DataBase db;
    JLabel textoProveedor, textoPedido;

    /**
     * Constructor al que le paso la DataBase completa.
     */
    public VentanaListado(DataBase db) {
        this.db = db;
        this.setTitle("Listado");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(800, 200);
    }

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
        String textoBotones[] = {"Listado Completo", "Limpiar Tabla", "Fin"};
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
     * Métdo para añadir las filas con los datos de Producto.
     */
    public void muestraFilas() {
        String datos[];
        for (Producto pro : productos) {
            datos = pro.getArrayProducto();
            modelo.addRow(datos);
        }
    }

    /**
     * Método que controla el botón que ha sido seleccionado.
     *
     * @param ae acción realizada.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "buscarPe":
                productos = db.listadoProductos(Integer.parseInt(buscarPe.getText()));
                modelo.setRowCount(0);
                muestraFilas();
                break;
            case "buscarPro":
                productos = db.listadoProductos(buscarPro.getText());
                modelo.setRowCount(0);
                muestraFilas();
                break;
            case "Listado Completo":
                productos = db.listadoProductos();
                modelo.setRowCount(0);
                muestraFilas();
                break;
            case "Limpiar Tabla":
                modelo.setRowCount(0);
                break;
            case "Fin":
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
