/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgprogramming;

import Clases.AccesoriosYMetcon;
import Clases.Box;
import Clases.CrossCompleto;
import Clases.CrossMetcon;
import Clases.EjerciciosInBox;
import Clases.Stage;
import Clases.TecnicoAccesorios;
import Clases.UserStage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.Frequency;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Alejandro Correa
 */
public class EditarEntrenamiento extends javax.swing.JFrame {

    /**
     * Creates new form EditarEntrenamiento
     */
    UserStage[][] usersStages;
    Stage[][] stages;
    int filaSelected;
    int columnaSelected;
    List<Box> boxes;
    List<EjerciciosInBox> ejerciciosInBoxs;
    public EditarEntrenamiento() {
        initComponents();
       
        boxes=new ArrayList<>();
        ejerciciosInBoxs=new ArrayList<>();
        spnEjercicios.setValue(1);
        addFormats();
        listeners();
        //JTabbed pane
        copyPane();
        copyPane();
        copyPane();

    }

    public void jtabbedPaneConfig() {
        try {
            spnEjercicios.commitEdit();
        } catch (java.text.ParseException e) {
        }
        int valueSpn = (Integer) spnEjercicios.getValue();
        for (int i = 0; i < valueSpn; i++) {
            if (i >tbpTraining.getTabCount()-1) {
                tbpTraining.addTab("Grupo " + (i+1), tbpTraining.getTabComponentAt(0));
                
            }
            

        }
        if(tbpTraining.getTabCount()>valueSpn){
        for(int i=tbpTraining.getTabCount();i>0;i--){
        if(valueSpn<i){
        tbpTraining.remove(i-1);
        }
        }
        }
        
    }
    
    public void listeners(){
        
    tblTraining.getSelectionModel().addListSelectionListener(listSelection());
    tblTraining.getColumnModel().getSelectionModel().addListSelectionListener(listSelection());
    
    }
    
    public void addFormats() {
        String[] format = {"ACCESORIOS Y METCON", "CROSSFIT COMPLETO",
            "CROSSFIT SOLO METCON", "TECNICO, ACCESORIOS"};

        JMenuItem item = new JMenuItem("ACCESORIOS Y METCON");
        JMenuItem item1 = new JMenuItem("CROSSFIT COMPLETO");
        JMenuItem item2 = new JMenuItem("CROSSFIT SOLO METCON");
        JMenuItem item3 = new JMenuItem("TECNICO, ACCESORIOS");
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("");
        model.addColumn("Lunes");
        model.addColumn("Martes");
        model.addColumn("Miercoles");
        model.addColumn("Jueves");
        model.addColumn("Viernes");
        model.addColumn("Sabado");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable(model);
                AccesoriosYMetcon am = new AccesoriosYMetcon();
                for (int i = 0; i < am.getCampos().length; i++) {
                    model.addRow(new Object[]{am.getCampos()[i], "", "", "", "", "", ""});
                    tblTraining.setModel(model);
                }
                crearUsersStage(6 ,am.getCampos().length);
               
            }
        });
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable(model);
                CrossCompleto cc = new CrossCompleto();
                for (int i = 0; i < cc.getCampos().length; i++) {
                    model.addRow(new Object[]{cc.getCampos()[i], "", "", "", "", "", ""});
                    tblTraining.setModel(model);
                }
                crearUsersStage(6, cc.getCampos().length);
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable(model);
                CrossMetcon cm = new CrossMetcon();
                for (int i = 0; i < cm.getCampos().length; i++) {
                    model.addRow(new Object[]{cm.getCampos()[i], "", "", "", "", "", ""});
                    tblTraining.setModel(model);
                }
                crearUsersStage(6, cm.getCampos().length);
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clearTable(model);
                TecnicoAccesorios ta = new TecnicoAccesorios();
                for (int i = 0; i < ta.getCampos().length; i++) {
                    model.addRow(new Object[]{ta.getCampos()[i], "", "", "", "", "", ""});
                    tblTraining.setModel(model);
                }
                crearUsersStage(6, ta.getCampos().length);
            }
        });
        mnuFormato.add(item);
        mnuFormato.add(item1);
        mnuFormato.add(item2);
        mnuFormato.add(item3);

//    for(int i=0; i<format.length;i++){
//        JMenuItem item =new JMenuItem(format[i]);
//        
//    }
    }
    
    public void crearUsersStage(int dia, int parametro){
    
    usersStages= new UserStage[dia][parametro];
    stages= new Stage[dia][parametro];
    int count=0;
    for(int i=0;i<dia;i++){
        for(int j=0;j<parametro;j++){
        stages[i][j]= new Stage(count, "");
        usersStages[i][j]= new UserStage(0/*user_id*/, count/*stage_id*/, 0/*index_in_parameter*/
                                , 0/*parameter_id*/, 0/*week_day*/);
        
        count++;
        }
        
    }
    
    }

    public void clearTable(DefaultTableModel model) {
        int n = model.getRowCount();
        if (n > 0) {
            for (int i = n; i > 0; i--) {
                model.removeRow(i - 1);

            }
        }

    }
    
    private ListSelectionListener listSelection(){
       ListSelectionListener handler = new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               
             filaSelected= tblTraining.getSelectedRow();  
             columnaSelected= tblTraining.getSelectedColumn() - 1;
             llenarElementos(filaSelected, columnaSelected);
             
           }
       };
        
        return handler;
    }
    
    public void llenarElementos(int row, int col){
    Stage stage= stages[col][row];
    
    txtName.setText(stage.getName());
    int numBox=0;
        for(Box b:boxes){
            if(b.getStage_id()==stage.getId()){
            
            if(numBox==0){
            
            }
            else{
            
            
            }
                
            numBox++;
            }
        }
    spnEjercicios.setValue(numBox);
    }
    
    public void UpdateStage(){
    
    }
    
    public void copyPane(){
        
        JPanel pnl= new JPanel();
        pnl.setBounds(pnlGrupo1.getBounds());
        pnl.setBackground(pnlGrupo1.getBackground());   
        pnl.setSize(pnlGrupo1.getSize());
        pnl.setAlignmentX(pnlGrupo1.getAlignmentX());
        pnl.setAlignmentY(pnlGrupo1.getAlignmentY());
        pnl.setLayout(null);
        
        JLabel lblRondas= new JLabel();
        lblRondas.setBounds(lblRondas1.getBounds());
        lblRondas.setAlignmentX(lblRondas1.getAlignmentX());
        lblRondas.setAlignmentY(lblRondas1.getAlignmentY());
        lblRondas.setText(lblRondas1.getText());
        pnl.add(lblRondas);
        
        JComboBox cmbRondas= new JComboBox();
        cmbRondas.setBounds(cmbRondas1.getBounds());
        cmbRondas.setAlignmentX(cmbRondas1.getAlignmentX());
        cmbRondas.setAlignmentY(cmbRondas1.getAlignmentY());
        pnl.add(cmbRondas);
        
        JButton btnAddEjercicio = new JButton();
        btnAddEjercicio.setBounds(btnAddEjercicio1.getBounds());
        btnAddEjercicio.setAlignmentX(btnAddEjercicio1.getAlignmentX());
        btnAddEjercicio.setAlignmentY(btnAddEjercicio1.getAlignmentY());
        btnAddEjercicio.setText("Agregar Ejercicio");
        btnAddEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            }
        });
        pnl.add(btnAddEjercicio);
        
        JLabel lblReps= new JLabel();
        lblReps.setBounds(lblReps1.getBounds());
        lblReps.setAlignmentX(lblReps1.getAlignmentX());
        lblReps.setAlignmentY(lblReps1.getAlignmentY());
        lblReps.setText(lblReps1.getText());
        pnl.add(lblReps);
        
        JTextField txtReps= new JTextField();
        txtReps.setBounds(txtReps1.getBounds());
        txtReps.setSize(txtReps1.getSize());
        txtReps.setAlignmentX(txtReps1.getAlignmentX());
        txtReps.setAlignmentY(txtReps1.getAlignmentY());
        pnl.add(txtReps);
        
        JLabel lblEjercicios= new JLabel();
        lblEjercicios.setBounds(lblEjercicios1.getBounds());
        lblEjercicios.setAlignmentX(lblEjercicios1.getAlignmentX());
        lblEjercicios.setAlignmentY(lblEjercicios1.getAlignmentY());
        lblEjercicios.setText(lblEjercicios1.getText());
        pnl.add(lblEjercicios);
        
        JComboBox cmbEjercicios= new JComboBox();
        cmbEjercicios.setBounds(cmbEjercicios1.getBounds());
        cmbEjercicios.setAlignmentX(cmbEjercicios1.getAlignmentX());
        cmbEjercicios.setAlignmentY(cmbEjercicios1.getAlignmentY());
        pnl.add(cmbEjercicios);
        
        JLabel lblComentarios= new JLabel();
        lblComentarios.setBounds(lblComentarios1.getBounds());
        lblComentarios.setAlignmentX(lblComentarios1.getAlignmentX());
        lblComentarios.setAlignmentY(lblComentarios1.getAlignmentY());
        lblComentarios.setText(lblComentarios1.getText());
        pnl.add(lblComentarios);
        
        JTextField txtComentarios= new JTextField();
        txtComentarios.setBounds(txtComentarios1.getBounds());
        txtComentarios.setSize(txtComentarios1.getSize());
        txtComentarios.setAlignmentX(txtComentarios1.getAlignmentX());
        txtComentarios.setAlignmentY(txtComentarios1.getAlignmentY());
        pnl.add(txtComentarios);
        
        JLabel lblListEjercicios= new JLabel();
        lblListEjercicios.setBounds(lblListEjercicios1.getBounds());
        lblListEjercicios.setAlignmentX(lblListEjercicios1.getAlignmentX());
        lblListEjercicios.setAlignmentY(lblListEjercicios1.getAlignmentY());
        lblListEjercicios.setText(lblListEjercicios1.getText());
        pnl.add(lblListEjercicios);
        
        JTable tblEjercicios = new JTable();
        tblEjercicios.setBounds(tblEjercicios1.getBounds());
        tblEjercicios.setAlignmentX(tblEjercicios1.getAlignmentX());
        tblEjercicios.setAlignmentY(tblEjercicios1.getAlignmentY());
         pnl.add(tblEjercicios);
        
          
         
        tbpTraining.addTab("Grupo2", pnl);
        btnAddEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            txtReps.setText("Prueba a ber que pedo");
            }
        });
        
    
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
        tblTraining = new javax.swing.JTable();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spnEjercicios = new javax.swing.JSpinner();
        tbpTraining = new javax.swing.JTabbedPane();
        pnlGrupo1 = new javax.swing.JPanel();
        lblRondas1 = new javax.swing.JLabel();
        cmbRondas1 = new javax.swing.JComboBox<>();
        lblListEjercicios1 = new javax.swing.JLabel();
        lblEjercicios1 = new javax.swing.JLabel();
        cmbEjercicios1 = new javax.swing.JComboBox<>();
        lblReps1 = new javax.swing.JLabel();
        txtReps1 = new javax.swing.JTextField();
        lblComentarios1 = new javax.swing.JLabel();
        txtComentarios1 = new javax.swing.JTextField();
        btnAddEjercicio1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEjercicios1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFormato = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblTraining.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parameter", "Lunes", "Martes", "Miercoles", "Jueves", "Viernesl", "Sabado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTraining.setCellSelectionEnabled(true);
        tblTraining.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTraining.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTraining.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblTraining);
        tblTraining.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblName.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        lblName.setText("jLabel1");

        jLabel1.setText("Name:");

        jLabel2.setText("Número de grupos de ejercicios:");

        spnEjercicios.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnEjerciciosStateChanged(evt);
            }
        });

        tbpTraining.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbpTraining.setAutoscrolls(true);

        pnlGrupo1.setAutoscrolls(true);

        lblRondas1.setText("Rondas:");

        lblListEjercicios1.setText("Ejercicios");

        lblEjercicios1.setText("Ejercicio:");

        lblReps1.setText("Repeticiones:");

        lblComentarios1.setText("Comentario:");

        btnAddEjercicio1.setText("Agregar Ejercicio");
        btnAddEjercicio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEjercicio1ActionPerformed(evt);
            }
        });

        tblEjercicios1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ejercicios"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        javax.swing.GroupLayout pnlGrupo1Layout = new javax.swing.GroupLayout(pnlGrupo1);
        pnlGrupo1.setLayout(pnlGrupo1Layout);
        pnlGrupo1Layout.setHorizontalGroup(
            pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(lblRondas1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbRondas1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)
                        .addComponent(lblListEjercicios1))
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblEjercicios1)
                            .addComponent(lblReps1)
                            .addComponent(lblComentarios1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtReps1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtComentarios1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbEjercicios1, 0, 123, Short.MAX_VALUE))
                            .addComponent(btnAddEjercicio1))
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(tblEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGrupo1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGrupo1Layout.setVerticalGroup(
            pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblRondas1)
                            .addComponent(cmbRondas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblListEjercicios1)))
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblEjercicios1)
                                    .addComponent(cmbEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(txtReps1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblReps1))
                                .addGap(21, 21, 21)
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblComentarios1)
                                    .addComponent(txtComentarios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(tblEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)
                        .addComponent(btnAddEjercicio1))
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(95, 95, 95))
        );

        tbpTraining.addTab("Grupo 1", pnlGrupo1);

        jButton2.setText("Aceptar");

        mnuFormato.setText("Formato");
        jMenuBar1.add(mnuFormato);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 262, Short.MAX_VALUE)
                .addComponent(tbpTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(255, 255, 255)
                            .addComponent(lblName))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(245, 245, 245)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tbpTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void spnEjerciciosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnEjerciciosStateChanged
        try {
            spnEjercicios.commitEdit();
        } catch (java.text.ParseException e) {
        }
        int valueSpn = (Integer) spnEjercicios.getValue();
        if (valueSpn < 1) {
            spnEjercicios.setValue(1);
        }
        jtabbedPaneConfig();
    }//GEN-LAST:event_spnEjerciciosStateChanged

    private void btnAddEjercicio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEjercicio1ActionPerformed
        txtReps1.setText(btnAddEjercicio1.getText());
    }//GEN-LAST:event_btnAddEjercicio1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditarEntrenamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarEntrenamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarEntrenamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarEntrenamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarEntrenamiento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEjercicio1;
    private javax.swing.JComboBox<String> cmbEjercicios1;
    private javax.swing.JComboBox<String> cmbRondas1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblComentarios1;
    private javax.swing.JLabel lblEjercicios1;
    private javax.swing.JLabel lblListEjercicios1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblReps1;
    private javax.swing.JLabel lblRondas1;
    private javax.swing.JMenu mnuFormato;
    private javax.swing.JPanel pnlGrupo1;
    private javax.swing.JSpinner spnEjercicios;
    private javax.swing.JTable tblEjercicios1;
    private javax.swing.JTable tblTraining;
    private javax.swing.JTabbedPane tbpTraining;
    private javax.swing.JTextField txtComentarios1;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtReps1;
    // End of variables declaration//GEN-END:variables
}
