/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazGUI;

import gestionProducto.Producto;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Clara
 */
public class VentanaListado extends JFrame {

    JPanel contenedor;
    JTable table;
    DefaultTableModel modelo;
    ArrayList<Producto> productos;

    public VentanaListado(ArrayList<Producto> productos) {
        this.productos = productos;
        this.setTitle("Listado");
        this.setVisible(true);
        initComponents();
        this.pack();
      //  this.setSize(300, 300);
    }
    
     public void initComponents() {
        contenedor = (JPanel) this.getContentPane();
        modelo = new DefaultTableModel();
        // se crea la Tabla con el modelo DefaultTableModel
        table = new JTable(modelo);
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);
        //Agregamos el JScrollPane al contenedor
        contenedor.add(scrollPane, BorderLayout.CENTER);
        // insertamos los nombres de las celdas cabecera.
        modelo.addColumn("IdProducto");
        modelo.addColumn("Nombre");
        modelo.addColumn("IdProveedor");
        modelo.addColumn("Descripción");
        modelo.addColumn("Tipo");
        muestraFilas();
    }

    public void muestraFilas() {
        String datos[];
        for (Producto pro : productos) {
            datos = pro.getArrayProducto();
            modelo.addRow(datos);
        }

    }

}
