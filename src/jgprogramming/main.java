/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgprogramming;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejandro Correa
 */
public class main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public main() {
        initComponents();
        this.setLocationRelativeTo(null);
        AddMenuItems();
        llenarTable(tblUsers);
        popupTable(tblUsers);
        
    }
    
    public void AddMenuItems(){
    JMenuItem itemAddUsers= new JMenuItem("Usuario");
    itemAddUsers.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            AgregarUsuario au= new AgregarUsuario();
            
            au.show();
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });
    
    JMenuItem itemAddEjercicio= new JMenuItem("Ejercicios");
    itemAddEjercicio.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AgregarEjercicio ae= new AgregarEjercicio();
            ae.setVisible(true);
        }
    });
    JMenuItem itemEditEjercicio= new JMenuItem("Ejercicios");
    itemEditEjercicio.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            EditeJEjercicios ee= new EditeJEjercicios();
            ee.setVisible(true);
        }
    });
    
    
    menuAgregar.add(itemAddUsers);
    menuAgregar.add(itemAddEjercicio);
    menuEditar.add(itemEditEjercicio);
    }
    
    
    //crea el popup menu y el action listener de cada item
    public void popupTable(JTable table){
        JPopupMenu menu= new JPopupMenu();
        JMenuItem itemEditeTraining =new JMenuItem("Editar entrenamiento");
        JMenuItem itemDeleteUsuario =new JMenuItem("Eliminar Usuario");
        JMenuItem itemDesacCuenta =new JMenuItem("Desactivar Cuenta");
        
        itemEditeTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditarEntrenamiento frame= new EditarEntrenamiento();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                frame.setVisible(true);
                }
        });
        itemDeleteUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int col= table.getSelectedColumn();
                int row=table.getSelectedRow();
                JOptionPane.showMessageDialog(null, itemDeleteUsuario.getText()+" "+table.getValueAt(row, col),"Mensaje",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        itemDesacCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int col= table.getSelectedColumn();
                int row=table.getSelectedRow();
                JOptionPane.showMessageDialog(null, itemDesacCuenta.getText()+" "+table.getValueAt(row, col),"Mensaje",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        menu.add(itemEditeTraining);
         menu.add(itemDeleteUsuario);
          menu.add(itemDesacCuenta);
          table.setComponentPopupMenu(menu);
          
        
        
    }
     //el segundo llena la tabla
    public void llenarTable(JTable Tabla){
        
        DefaultTableModel model= new DefaultTableModel();
        String[] usersCross={"Alex Correa", "Xchel Carranza", "Rosa Quiñones", "Joel Gongora",
                                "Carlos Gorocica", "Peter Parker", "Ruben Minaya", "Luis Minaya",
                                  "Manuel Sabala", "Usuario Cualquiera"};
        String[] usersGym={"Andres Obrador", "Vicente Fox", "Endson Estrada", "ANdres Castillo",
                            "Pepe Aguilar"};
        
        model.addColumn("CrossFit");
        model.addColumn("Gimnasio");
        int n=0;
        if(usersCross.length>usersGym.length){
        n=usersCross.length;
        }else{
        n=usersGym.length;
        }
        for(int i=0;i<n;i++){
        String u1="";
        String u2="";
        try{
        u1=usersCross[i];
        }catch(Exception e){
        u1="";
        }
        try{
        u2=usersGym[i];
        }catch(Exception e){
        u2="";
        }
        model.addRow(new Object[]{u1,u2});
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuAgregar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JTable tblUsers;
    // End of variables declaration//GEN-END:variables
}
