/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *s
 * @author Clara
 */
public class VentanaMenuListados extends JFrame implements ActionListener, WindowListener {

    JPanel contenedor;
    JButton botones[];
    DataBase db;

    public VentanaMenuListados(DataBase db) {
        this.db = db;
        this.setTitle("Listados");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);

    }

    public void initComponents() {
        String textoBotones[] = {"Completo", "Por proveedor", "Por pedido firme"};
        botones = new JButton[textoBotones.length];
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new GridLayout(textoBotones.length, 1, 5, 5));

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
               VentanaListado vL = new VentanaListado(db.listado());
                break;
            case "1":
               
                break;
            case "2":
             
                break;
            default:
                System.out.println("Opcion no Valida.");
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}