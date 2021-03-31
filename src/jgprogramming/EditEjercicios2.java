/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgprogramming;

import Clases.Ejercicio;
import Clases.ModelExersisesNotEditables;
import Clases.URLs;
import HTTP.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejandro Correa
 */
public class EditEjercicios2 extends javax.swing.JFrame {

    /**
     * Creates new form EditEjercicios2
     */
    ModelExersisesNotEditables model;
    private List<Ejercicio> ejercicios;

    public EditEjercicios2() {
        initComponents();
        String directorio = System.getProperty("user.dir") + "\\src\\imagenes\\logo_unicolor.png";
        setIconImage(new ImageIcon(directorio).getImage());

        

        getExersices();
        llenarTable();
        popupMenu(tblEjercicios);

    }

    private void getExersices() {
        Http http = new Http();
        String body = http.GET(URLs.GetEjercicios);
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaEjercicio = new TypeToken<List<Ejercicio>>() {
        }.getType();
        ejercicios = gson.fromJson(body, tipoListaEjercicio);
    }

    private void llenarTable() {
        model = new ModelExersisesNotEditables();
        model.addColumn("Ejercicios");
        model.addColumn("Link");

        for (Ejercicio e : ejercicios) {
            model.addRow(new Object[]{e.getName(), e.getLink()});

        }
        tblEjercicios.setModel(model);

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    String textEdited = tblEjercicios.getValueAt(tblEjercicios.getSelectedRow(), 1).toString();
                    if (tblEjercicios.getSelectedRow() > -1 ) {
                        Ejercicio ejercicioSelected = ejercicios.get(tblEjercicios.getSelectedRow());

                        String resp=EditLink(ejercicioSelected, getV(textEdited));
                        if(resp.equals("1")){
                        String str = "Se editó el link del ejercicio: " + ejercicios.get(tblEjercicios.getSelectedRow()).getName();
                        javax.swing.JOptionPane.showMessageDialog(EditEjercicios2.this, str, "Exito", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                        javax.swing.JOptionPane.showMessageDialog(EditEjercicios2.this, resp, "Exito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (Exception ex) {

                }
            }

        });
        
    }

    private String getV(String linknew) { //Obtener el identificador del video
        String link = "";
        String str = linknew;
        
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {

            if (String.valueOf(str.charAt(i)).equals("&") || String.valueOf(str.charAt(i)).equals("?")) {
                flag = false;

            }
            if (flag) {
                link = link + String.valueOf(str.charAt(i));
            }

            if (i != 0) {
                String param = String.valueOf(str.charAt(i - 1)) + String.valueOf(str.charAt(i));
                if (param.equals("v=")) {
                    flag = true;
                }

            }

        }
        return link;
    }

    private String EditLink(Ejercicio ejercicio, String newLink) {
        Http http = new Http();
        String resp = http.GET(URLs.updateExercise(new Ejercicio(ejercicio.getId(), ejercicio.getName().toUpperCase(), newLink)));
        // Devuelve 1 si editó bien
        return resp;
    }
    
    private void deleteEjercicio(Ejercicio ejercicio){
    int sel = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar " + ejercicio.getName() + "?",
                "¿?", JOptionPane.YES_NO_OPTION);

        if (sel == 0) {
            //borrar
            try {
                Http http = new Http();

                String r = http.GET(URLs.deleteExercise(ejercicio.getId()));
                if (r.equals("bien")) {
                    JOptionPane.showMessageDialog(null, "Ejercicio " + ejercicio.getName()+" eliminado", "Bien", JOptionPane.INFORMATION_MESSAGE);
                    ejercicios.remove(ejercicio);
                    llenarTable();
                } else {
                    JOptionPane.showMessageDialog(null, r, "error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void popupMenu(JTable table){
        
    JPopupMenu menu = new JPopupMenu();
    JMenuItem itemRemove = new JMenuItem("Eliminar este ejercicio");
    itemRemove.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (tblEjercicios.getSelectedRow() > -1 ) {
                        Ejercicio ejercicioSelected = ejercicios.get(tblEjercicios.getSelectedRow());
                        deleteEjercicio(ejercicioSelected);
            }
            
        }
    });
    menu.add(itemRemove);
    table.setComponentPopupMenu(menu);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblEjercicios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblEjercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ejercicio", "Link"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEjercicios);
        if (tblEjercicios.getColumnModel().getColumnCount() > 0) {
            tblEjercicios.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditEjercicios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditEjercicios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditEjercicios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditEjercicios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditEjercicios2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEjercicios;
    // End of variables declaration//GEN-END:variables

}
