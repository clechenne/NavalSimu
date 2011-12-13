/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OrdresTableDialog.java
 *
 * Created on 11 déc. 2011, 19:36:45
 */
package com.naval.gui;

import com.naval.modele.Navire;
import com.naval.ordres.Ordre;
import com.naval.tables.TableHorizonVisuel;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author christophe
 */
public class VisibleDialog extends javax.swing.JDialog {
    
    /** Creates new form OrdresTableDialog */
    public VisibleDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Saisie des ordres");

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                OrdresTableDialog dialog = new OrdresTableDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    void setDonnees(List<Navire> navires, int visibilite) {
        jTable1.setModel(new VisibleModel(navires, visibilite));
    }
}

class VisibleModel extends AbstractTableModel {

    private String[] columnNames = {"Navire", "Navire detecte",};
    private Object[][] data;
    
    VisibleModel(List<Navire> navires, int visibilite) {
        Object[][] detection = new Object[navires.size()][2];
        
        int id = 0 ;
        for (Navire n : navires) {
            for (Navire p : navires) {
                if (TableHorizonVisuel.visible(visibilite, n, p) && ! n.nom.equals(p.nom)) {
                    detection[id][0] = n.nom;
                    detection[id][1] = p.nom;
                    id++;
                }
            }
        }
        
        data = new Object[id][2];
        
        for (int i=0; i < id; i++) {
            data[i][0] = detection[i][0];
            data[i][1] = detection[i][1];
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return String.class;
    }
}