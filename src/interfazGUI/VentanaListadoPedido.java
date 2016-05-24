/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazGUI;

import conexionBaseDeDatos.DataBase;
import gestionProducto.Producto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
 *
 * @author Clara
 */
public class VentanaListadoPedido extends JFrame implements ActionListener, WindowListener {

    //  JLabel etiquetaBuscar;
    JTextField buscar;
    JButton bBuscar;

    JPanel contenedor, contenedorBotones, contenedorTabla;
    JTable table;
    DefaultTableModel modelo;
    ArrayList<Producto> productos = new ArrayList<>();
    DataBase db;

    public VentanaListadoPedido(DataBase db) {
        this.db = db;
        this.setTitle("Listado");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(400, 200);
    }

    public void initComponents() {
        contenedor = (JPanel) this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        contenedorTabla = new JPanel();
        contenedorBotones = new JPanel();
        contenedorBotones.setLayout(new GridLayout(1, 2, 5, 5));
        //    etiquetaBuscar = new JLabel("Buscar: ");
        buscar = new JTextField();
        bBuscar = new JButton("Buscar");
        bBuscar.setActionCommand("buscar");
        bBuscar.addActionListener(this);
        // contenedorTabla.setLayout(new FlowLayout());
        //    contenedorBotones.add(etiquetaBuscar);
        contenedorBotones.add(buscar);
        contenedorBotones.add(bBuscar);
        contenedor.add(contenedorBotones, BorderLayout.NORTH);
        creoTabla();
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);
        //Agregamos el JScrollPane al contenedor
        contenedor.add(scrollPane, BorderLayout.CENTER);
        // muestraFilas();
    }

    public void creoTabla() {
        modelo = new DefaultTableModel();
        // se crea la Tabla con el modelo DefaultTableModel

        // insertamos los nombres de las celdas cabecera.
        table = new JTable(modelo);
        modelo.addColumn("Nombre Proveedor");
        modelo.addColumn("IdProducto");
        modelo.addColumn("Nombre Prducto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Tipo");
    }

    public void muestraFilas() {
        String datos[];
        for (Producto pro : productos) {
            datos = pro.getArrayProducto();
            modelo.addRow(datos);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "buscar":
                productos = db.listadoProductos(Integer.parseInt(buscar.getText()));
                modelo.setRowCount(0);
                muestraFilas();
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
