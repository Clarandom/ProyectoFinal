/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Clara
 */
public class VentanaPrincipal extends JFrame implements ActionListener, WindowListener {

    JLabel titulo;
    JPanel contenedor;
    JButton botones[];
    DataBase db;

    public VentanaPrincipal(DataBase db) {
        this.db = db;
        this.setTitle("Gestión Productos");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }

    public void initComponents() {
        titulo = new JLabel("CENTRAL DE COMPRAS", SwingConstants.CENTER);
        String textoBotones[] = {"Listado", "Alta Produco", "Baja Producto", "Modificar Producto", "Crear XML", "Cerrar"};
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

        this.addWindowListener(this);
    }

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
            case "5":
                DocumentoXML.escriboArrayList("productos", db.listadoProductos());

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
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
