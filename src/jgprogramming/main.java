/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgprogramming;

import Clases.URLs;
import Clases.Usuario;
import Clases.modelNoEditable;
import HTTP.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static jgprogramming.EditarEntrenamiento.usuario;

/**
 *
 * @author Alejandro Correa
 *
 *
 */
public class main extends javax.swing.JFrame {
    Usuario selected;

    /**
     * Creates new form main
     */
    static List<Usuario> usuariosList;

    public main() {
        initComponents();
        String directorio = System.getProperty("user.dir") + "\\src\\imagenes\\logo_unicolor.png";
        setIconImage(new ImageIcon(directorio).getImage());
        this.setLocationRelativeTo(null);
        usuariosList = new ArrayList<>();
        AddMenuItems();
        llenarTable(tblUsers);
        popupTable(tblUsers);
        

    }

    public void AddMenuItems() {
        JMenuItem itemAddUsers = new JMenuItem("Usuario");
        itemAddUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AgregarUsuario au = new AgregarUsuario();
                au.setUsers(usuariosList);
                au.setVisible(true);

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        JMenuItem itemAddEjercicio = new JMenuItem("Ejercicios");
        itemAddEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarEjercicio ae = new AgregarEjercicio();
                ae.setVisible(true);
            }
        });
        JMenuItem itemEditEjercicio = new JMenuItem("Ejercicio");
        itemEditEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //EditeJEjercicios ee = new EditeJEjercicios();
                EditEjercicios2 ee = new EditEjercicios2();
                ee.setVisible(true);
            }
        });
        JMenuItem itemEditLinks = new JMenuItem("Actualizar links(Recovery y warm up)");
        itemEditLinks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EditLinks eL = new EditLinks();
                eL.setVisible(true);
            }
        });
        

        menuAgregar.add(itemAddUsers);
        menuAgregar.add(itemAddEjercicio);
        menuEditar.add(itemEditEjercicio);
        menuEditar.add(itemEditLinks);
    }

    //crea el popup menu y el action listener de cada item
    public void popupTable(JTable table) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem itemEditeTraining = new JMenuItem("Editar o agregar entrenamiento");
        JMenuItem itemEditeUser = new JMenuItem("Editar usuario");
        JMenuItem itemDeleteUsuario = new JMenuItem("Eliminar usuario");
        JMenuItem itemDesacCuenta = new JMenuItem("Activar/Desactivar Cuenta");

        itemEditeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario userSelect = null;
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                for (Usuario u : usuariosList) {
                    String name = u.getFirst_name() + " " + u.getLast_name();
                    if (name.equals(table.getValueAt(row, col))&& u.getTraining_type() == col) {
                        userSelect = u;
                    }
                }
                EditUser.setUser(userSelect);
                EditUser us = new EditUser();
                us.setVisible(true);
                
            }
        });
        
        itemEditeTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Usuario userSelect = null;
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                for (Usuario u : usuariosList) {
                    String name = u.getFirst_name() + " " + u.getLast_name();
                    if (name.equals(table.getValueAt(row, col))&& u.getTraining_type() == col) {
                        userSelect = u;
                    }
                }
                EditarEntrenamiento.setUsuario(userSelect);
                Proceso1 proceso1=new Proceso1();
                Proceso2 proceso2=new Proceso2();
                proceso1.start();
                proceso2.start();
            }
        });
        itemDeleteUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario userSelect = null;
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                for (Usuario u : usuariosList) {
                    String name = u.getFirst_name() + " " + u.getLast_name();
                    if (name.equals(table.getValueAt(row, col))&& u.getTraining_type() == col) {
                        userSelect = u;
                    }
                }
                
                
                try{
                
                    deleteUser(userSelect);
                }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.toString() ,"ERROR" , JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        itemDesacCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 Usuario userSelect = null;
                int col = table.getSelectedColumn();
                int row = table.getSelectedRow();
                for (Usuario u : usuariosList) {
                    String name = u.getFirst_name() + " " + u.getLast_name();
                    if (name.equals(table.getValueAt(row, col)) && u.getTraining_type() == col) {
                        userSelect = u;
                    }
                }
                Http http=new Http();
                String resp=http.GET(URLs.UpdateStateByUser(userSelect));
               if(resp.equals("Activo")){
                JOptionPane.showMessageDialog(null,"Usuario activado" ,"Bien" , JOptionPane.INFORMATION_MESSAGE);
               }
               else if(resp.equals("Desactivado")){
                JOptionPane.showMessageDialog(null, "Usuario desactivado", "Bien", JOptionPane.INFORMATION_MESSAGE);
               }else{
               JOptionPane.showMessageDialog(null, "ERROR", resp, JOptionPane.ABORT);
               }
               llenarTable(table);
            }
        });

        menu.add(itemEditeTraining);
        menu.add(itemEditeUser);
        menu.add(itemDesacCuenta);
        menu.addSeparator();
        menu.add(itemDeleteUsuario);
        table.setComponentPopupMenu(menu);
        
    }
    //el segundo llena la tabla
       private void deleteUser(Usuario us) {
        Http http = new Http();

        
        String result5 = http.GET(URLs.deleteUser(us.getId()));
        
        if(result5.equals("bien")){
        JOptionPane.showMessageDialog(null, "Usuario eliminado", "Bien", JOptionPane.INFORMATION_MESSAGE);
           llenarTable(tblUsers);
        }
        else{
        JOptionPane.showMessageDialog(null, result5, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void llenarTable(JTable Tabla) {

        Http peticion = new Http();
        String us = peticion.GET(URLs.GetUsers);
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaUsuarios = new TypeToken<List<Usuario>>() {
        }.getType();
        usuariosList = gson.fromJson(us, tipoListaUsuarios);

        modelNoEditable model = new modelNoEditable();
        int uc = 0, ug = 0;
        for (Usuario u : usuariosList) {
            if (u.getTraining_type() == 1 && u.getUser_type() != 0) {
                uc++;
            } else if (u.getTraining_type() == 0 && u.getUser_type() != 0) {
                ug++;
            }
        }
        String[] usersCross = new String[uc];
        String[] usersGym = new String[ug];
        int count_cross = 0, count_gym = 0;
        for (Usuario u : usuariosList) {
            if (u.getTraining_type() == 1 && u.getUser_type() != 0) {
                usersCross[count_cross] = u.getFirst_name() + " " + u.getLast_name();
                count_cross++;
            } else if (u.getTraining_type() == 0 && u.getUser_type() != 0) {
                usersGym[count_gym] = u.getFirst_name() + " " + u.getLast_name();
                count_gym++;
            }
        }

        model.addColumn("Gimnasio");
        model.addColumn("CrossFit");
        int n = 0;
        if (usersCross.length > usersGym.length) {
            n = usersCross.length;
        } else {
            n = usersGym.length;
        }
        for (int i = 0; i < n; i++) {
            String u1 = "";
            String u2 = "";
            try {
                u1 = usersCross[i];
            } catch (Exception e) {
                u1 = "";
            }
            try {
                u2 = usersGym[i];
            } catch (Exception e) {
                u2 = "";
            }

            model.addRow(new Object[]{u2, u1});

        }
        
        Tabla.setModel(model);

    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        menuAgregar = new javax.swing.JMenu();
        menuEditar = new javax.swing.JMenu();
        menuActualizar = new javax.swing.JMenu();
        itemUpdate = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CrossFit", "Gimnasio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsers.setColumnSelectionAllowed(true);
        tblUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblUsers);
        tblUsers.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        menuAgregar.setText("Agregar");
        menuBar.add(menuAgregar);

        menuEditar.setText("Editar");
        menuBar.add(menuEditar);

        menuActualizar.setText("Actualizar");
        menuActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActualizarActionPerformed(evt);
            }
        });

        itemUpdate.setText("Actualizar Tabla");
        itemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUpdateActionPerformed(evt);
            }
        });
        menuActualizar.add(itemUpdate);

        menuBar.add(menuActualizar);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActualizarActionPerformed
        
    }//GEN-LAST:event_menuActualizarActionPerformed

    private void itemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUpdateActionPerformed
        llenarTable(tblUsers);
        //Http http=new Http();
        //JOptionPane.showMessageDialog(null, http.PostStages(null), "MAL", JOptionPane.ABORT);
        
        
    }//GEN-LAST:event_itemUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }
    
    public class Proceso1 extends Thread {

        @Override
        public void run() {
            Progress p = new Progress();
            p.setVisible(true);
        }

    }

    public class Proceso2 extends Thread {

        @Override
        public void run() {
            
                EditarEntrenamiento frame = new EditarEntrenamiento();

                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemUpdate;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuActualizar;
    private javax.swing.JMenu menuAgregar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEditar;
    public static javax.swing.JTable tblUsers;
    // End of variables declaration//GEN-END:variables
}
