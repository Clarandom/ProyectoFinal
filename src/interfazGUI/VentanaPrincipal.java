package interfazGUI;

import conexionBaseDeDatos.DataBase;
import conexionBaseDeDatos.DocumentoXML;
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
import javax.swing.SwingConstants;

/**
 * Ventana principal que se ejecutará cuando el usuario inicie el programa.
 * Contiene todos los casos de uso del programa.
 *
 * @author Clara Subirón
 * @version 24/05/2016
 */
public class VentanaPrincipal extends JFrame implements ActionListener, WindowListener {

    JLabel titulo;
    JPanel contenedor;
    JButton botones[];
    DataBase db;

    /**
     * Constructor que recibe la base de datos como parámetro y se encarga de
     * hacer visible la ventana, así como inicializar todos los componentes
     * necesarios para ésta, gracias a la llamada al método initComponenents().
     *
     * @param db nueva DataBase
     */
    public VentanaPrincipal(DataBase db) {
        this.db = db;
        this.setTitle("Central de compras");
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
    public void initComponents() {
        titulo = new JLabel("CENTRAL DE COMPRAS", SwingConstants.CENTER);
        String textoBotones[] = {"Listado", "Alta Producto", "Baja Producto",
            "Modificar Producto", "Crear XML", "Cerrar"};
        botones = new JButton[textoBotones.length];
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new GridLayout(textoBotones.length + 1, 1, 5, 5));
        contenedor.add(titulo);
        for (int i = 0; i < textoBotones.length; i++) {
            botones[i] = new JButton();
            botones[i].setText(textoBotones[i]);
            botones[i].setActionCommand(Integer.toString(i));
            botones[i].addActionListener(this);
            contenedor.add(botones[i]);
        }
    }

    /**
     * Crea una ventana emergente cuya finalidad es mostrar un aviso.
     *
     * @param mensaje mensaje que mostrará la ventana
     * @param titulo título qute contendrá la ventana
     */
    private void ventanaMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(
                this, mensaje,
                titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método que controla el botón que ha sido seleccionado para realizar una
     * acción determinada.
     *
     * @param e acción realizada.
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "0":
                VentanaListado vP = new VentanaListado(db);
                break;
            case "1":
                VentanaAlta vA = new VentanaAlta(db);
                break;
            case "2":
                VentanaBaja vB = new VentanaBaja(db);
                break;
            case "3":
                VentanaModificacion vM = new VentanaModificacion(db);
                break;
            case "4":
                if (DocumentoXML.escriboArrayList("productos", db.listadoProductos())) {
                    ventanaMensaje("Documento creado correctanente", "Felicidades");
                } else {
                    ventanaMensaje("No se pudo crear el documento", "Error");
                }
                ;
                break;
            case "5":
                System.exit(0);
            default:
                System.out.println("Opcion no Valida.");
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
