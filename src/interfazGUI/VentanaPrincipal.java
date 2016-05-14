package interfazGUI;

import conexionBaseDeDatos.DataBase;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Interfaz del menú principal del programa.
 *
 * @author Clara
 */
public class VentanaPrincipal extends JFrame implements ActionListener, WindowListener {

    JPanel contenedor;
    JButton botones[];
    DataBase db;

    /**
     * Constructor que recibe como parámetro la base de datos completa.
     *
     * @param db
     */
    public VentanaPrincipal(DataBase db) {

        this.db = db;
        this.setTitle("Menú principal");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }

    /**
     * Método para instanciar los elementos de la interfaz.
     */
    private void initComponents() {
        String textoBotones[] = {"Gestionar Productos", "Consutar información", "Fin"};
        botones = new JButton[textoBotones.length];
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new GridLayout(textoBotones.length + 5, 1, 5, 5));

        for (int i = 0; i < textoBotones.length; i++) {
            botones[i] = new JButton();
            botones[i].setText(textoBotones[i]);
            botones[i].setActionCommand(Integer.toString(i));
            botones[i].addActionListener(this);
            contenedor.add(botones[i]);
        }
        this.addWindowListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "0":
                System.out.println("hola");
                break;
            case "1":
                break;
            case "2":
                fin();
                break;
            default:
                System.out.println("Opcion no Valida.");
                break;
        }
    }

    private void fin() {

        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        fin();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
