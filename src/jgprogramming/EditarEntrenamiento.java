/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgprogramming;

import Clases.Box;
import Clases.Ejercicio;
import Clases.EjerciciosInBox;
import Clases.Format;
import Clases.MultiLineTableCellRenderer;
import Clases.Parameter;
import Clases.Stage;
import Clases.URLs;
import Clases.UserStage;
import Clases.Usuario;
import Clases.modelNoEditable;
import HTTP.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import jdk.jfr.Frequency;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
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
    int parameterSelected;
    int daySelected;
    DefaultTableCellRenderer centerRenderer;
    public static Usuario userToLoad;

    modelNoEditable modelEjercicios;
    modelNoEditable model;

    List<Format> formatosList;
    List<Parameter> paramList;
    Boolean newPlann = false;
    //tiempo dinamico
    List<Box> boxes;
    List<Box> boxesTotales;
    List<EjerciciosInBox> ejerciciosInBoxs;
    List<EjerciciosInBox> ejerciciosInBoxsTotales;
    List<Stage> stagesList;
    List<UserStage> usersStageList;
    List<Ejercicio> ejerciciosList;

    //BASE DE DATOS
    List<Box> boxListDB;
//    List<EjerciciosInBox> ejerciciosInBoxsDB;
    List<Stage> stagesListDB;
//    List<UserStage> usersStageListDB;

    List<Box> boxListByUserDB;
    List<EjerciciosInBox> ejerciciosInBoxsByUserDB;
    List<Stage> stagesListByUserDB;
    List<UserStage> usersStageListByUserDB;

    //al guardar
    List<Box> boxListSave;
    List<EjerciciosInBox> ejerciciosInBoxsSave;
    List<Stage> stagesListSave;
    List<UserStage> usersStageListSave;

    List<JTextField> namesStages;
    List<JCheckBox> cbxSeparadores;
    Format formatSelected;
    int[] paramsId;
    int[] paramsActuales;
    int countStage = 0;
    static Usuario usuario;
    int idStageActual;
    String formatUser;
    int prog = 0;

    private static boolean haveTraining = false;

    public static void setUsuario(Usuario user) {
        usuario = user;
    }

    public EditarEntrenamiento() {
        initComponents();
        btnDeleteGroup1.setEnabled(false);
        String directorio = System.getProperty("user.dir") + "\\src\\imagenes\\logo_unicolor.png";
        setIconImage(new ImageIcon(directorio).getImage());
        //formatUser = getFormatUser(usuario);
        init();
        Progress.close();
    }

    public String getFormatUser(Usuario us) {
        Http http = new Http();
        String format_id = http.GET(URLs.getUserFormatByUserId(us.getId()));
        return format_id;
    }

    public void getBoxUser(Usuario us) {

        Http http = new Http();
        String body = http.GET(URLs.getBoxByUser(us.getId()));
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaBox = new TypeToken<List<Box>>() {
        }.getType();
        boxListByUserDB = gson.fromJson(body, tipoListaBox);
    }

    public void getBoxs() {

        Http http = new Http();
        String body = http.GET(URLs.GetBoxs);
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaBox = new TypeToken<List<Box>>() {
        }.getType();
        boxListDB = gson.fromJson(body, tipoListaBox);
    }

    public void getSategeUser(Usuario us) {
        Http http = new Http();
        String body = http.GET(URLs.getStageByUser(us.getId()));
        Gson gson = new Gson();
        java.lang.reflect.Type list = new TypeToken<List<Stage>>() {
        }.getType();
        stagesListByUserDB = gson.fromJson(body, list);
    }

    public void getStages() {
        Http http = new Http();
        String body = http.GET(URLs.GetStages);
        Gson gson = new Gson();
        java.lang.reflect.Type list = new TypeToken<List<Stage>>() {
        }.getType();
        stagesListDB = gson.fromJson(body, list);
    }

    public void getExersisesInBoxUser(Usuario us) {
        Http http = new Http();
        String body = http.GET(URLs.getExersisesInBoxByUser(us.getId()));
        Gson gson = new Gson();
        java.lang.reflect.Type list = new TypeToken<List<EjerciciosInBox>>() {
        }.getType();
        ejerciciosInBoxsByUserDB = gson.fromJson(body, list);
    }

    public void getUserStageUser(Usuario us) {
        Http http = new Http();
        String body = http.GET(URLs.getUserStageByUser(us.getId()));
        Gson gson = new Gson();
        java.lang.reflect.Type list = new TypeToken<List<UserStage>>() {
        }.getType();
        usersStageListByUserDB = gson.fromJson(body, list);
    }

    private void init() {
        btnAceptar.setVisible(false);
        prog += 2;
        updateProgress(prog);
        lblName.setText(usuario.getFirst_name() + " " + usuario.getLast_name());
        SetComponentsEnabled(false);
        jLabel1.setVisible(false);
        txtName.setVisible(false);
        cbxSeparador1.setSelected(true);
        cbxSeparador1.setEnabled(false);
        //txtNameStages1.setEnabled(true);
        //lblNameStage1.setEnabled(true);

        boxes = new ArrayList<>();
        boxesTotales = new ArrayList<>();
        ejerciciosInBoxs = new ArrayList<>();
        ejerciciosInBoxsTotales = new ArrayList<>();
        stagesList = new ArrayList<>();
        usersStageList = new ArrayList<>();
        ejerciciosList = new ArrayList<>();
        cbxSeparadores = new ArrayList<>();
        namesStages = new ArrayList<>();

        spnEjercicios.setValue(1);
        modelEjercicios = new modelNoEditable();
        modelEjercicios.addColumn("Ejercicios");
        tblEjercicios1.setModel(modelEjercicios);

        ejerciciosList = getExercises();
        boxListSave = new ArrayList<>();
        ejerciciosInBoxsSave = new ArrayList<>();
        stagesListSave = new ArrayList<>();
        usersStageListSave = new ArrayList<>();

        prog += 2;
        updateProgress(prog);
        //hilos
        getTrainning(usuario);
        //while(hilo1.isAlive()&&hilo3.isAlive()&&hilo2.isAlive()){}
        prog += 2;
        updateProgress(prog);

        if (formatUser.equals("")) {
            haveTraining = false;
        } else {
            haveTraining = true;
        }
        JPopupMenu menu = new JPopupMenu();
        JMenuItem itemDelete = new JMenuItem("Eliminar ejercicio");
        itemDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boxID = tbpTraining.getSelectedIndex();
                List<EjerciciosInBox> ejs = new ArrayList<>();
                for (EjerciciosInBox item : ejerciciosInBoxs) {
                    if (item.getBox_id() == boxID) {
                        ejs.add(item);
                    }

                }
                for (EjerciciosInBox item : ejs) {
                    if (ejs.indexOf(item) == tblEjercicios1.getSelectedRow()) {
                        ejerciciosInBoxs.remove(item);
                        modelEjercicios.removeRow(tblEjercicios1.getSelectedRow());
                    }

                }
            }
        });
        menu.add(itemDelete);
        tblEjercicios1.setComponentPopupMenu(menu);
        prog += 2;
        updateProgress(prog);
        addFormats();
        listeners();
    }

    private void getTrainning(Usuario user) {
        Proceso1 hilo1 = new Proceso1(user);
        Proceso2 hilo2 = new Proceso2(user);
        Proceso3 hilo3 = new Proceso3(user);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    private void getTrainningToLoad(Usuario user) {
        Proceso1 hilo1 = new Proceso1(user);
        Proceso2 hilo2 = new Proceso2(user);
        Proceso4 hilo3 = new Proceso4(user);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void updateProgress(int num) {
        try {
            Progress.progressBar.setValue(num);
        } catch (Exception e) {
            System.out.println("jgprogramming.EditarEntrenamiento.updateProgress()");
        }
    }

    private void SetComponentsEnabled(boolean b) {
        //txtNameStages1.setVisible(b);
        tbpTraining.setEnabled(b);
        btnAceptar.setEnabled(b);
        spnEjercicios.setEnabled(b);
        txtNameStages1.setEnabled(b);
        cmbEjercicios1.setEnabled(b);
        tblEjercicios1.setEnabled(b);
        tblEjercicios1.setVisible(b);
        txtReps1.setEnabled(b);
        txtComentarios1.setEnabled(b);
        txtPesos1.setEnabled(b);
        txtName.setEnabled(b);
        txtRondas1.setEnabled(b);
        btnAddEjercicio1.setEnabled(b);
        btnSaveGroup1.setEnabled(b);

    }

    public void jtabbedPaneConfig() {
        try {
            spnEjercicios.commitEdit();
        } catch (java.text.ParseException e) {
        }
        int valueSpn = (Integer) spnEjercicios.getValue();
        for (int i = 0; i < valueSpn; i++) {
            if (i > tbpTraining.getTabCount() - 1) {
                copyPane(i + 1);

            }

        }
        if (tbpTraining.getTabCount() > valueSpn) {
            for (int i = tbpTraining.getTabCount(); i > 0; i--) {
                if (valueSpn < i) {
                    tbpTraining.remove(i - 1);
                }
            }
        }

    }

    public void listeners() {

        for (Ejercicio e : ejerciciosList) {
            cmbEjercicios1.addItem(e.getName());

        }
        AutoCompleteDecorator.decorate(cmbEjercicios1);

        tblTraining.getSelectionModel().addListSelectionListener(listSelection());
        tblTraining.getColumnModel().getSelectionModel().addListSelectionListener(listSelection());

    }

    public void addFormats() {

        stagesList = new ArrayList<>();
        stagesListSave = new ArrayList<>();
        boxListSave = new ArrayList<>();
        boxes = new ArrayList<>();
        ejerciciosInBoxs = new ArrayList<>();
        ejerciciosInBoxsSave = new ArrayList<>();
        usersStageList = new ArrayList<>();
        usersStageListSave = new ArrayList<>();
        //JMenuItem item = new JMenuItem("ACCESORIOS Y METCON");
        model = new modelNoEditable();
        model.addColumn("");
        model.addColumn("Lunes");
        model.addColumn("Martes");
        model.addColumn("Miercoles");
        model.addColumn("Jueves");
        model.addColumn("Viernes");
        model.addColumn("Sabado");

        tblTraining.setRowHeight(50);

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        mnuFormato.removeAll();

        for (Format f : formatosList) {
            JMenuItem item = new JMenuItem(f.getFormat());

            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearTable(model);

                    stagesList = new ArrayList<>();
                    stagesListSave = new ArrayList<>();
                    boxListSave = new ArrayList<>();
                    boxes = new ArrayList<>();
                    ejerciciosInBoxs = new ArrayList<>();
                    ejerciciosInBoxsSave = new ArrayList<>();
                    usersStageList = new ArrayList<>();
                    usersStageListSave = new ArrayList<>();

                    formatSelected = f;
                    List<Parameter> list = getParams(f.getId());
                    paramsId = new int[list.size()];
                    paramsActuales = new int[15];
                    for (Parameter p : list) {
                        model.addRow(new Object[]{p.getParameter(), "", "", "", "", "", ""});
                        paramsId[list.indexOf(p)] = p.getId();
                        paramsActuales[p.getId()] = list.indexOf(p);
                        tblTraining.setModel(model);
                    }
                    crearUsersStage(6, list.size());
                    spnEjercicios.setValue(1);
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

            mnuFormato.add(item);
        }
        prog += 2;
        updateProgress(prog);
//    for(int i=0; i<format.length;i++){
//        JMenuItem item =new JMenuItem(format[i]);
//        
//    }
        //Formato de inicio
        if (!haveTraining) {
            formatSelected = formatosList.get(0);
            List<Parameter> list = getParams(formatosList.get(0).getId());
            paramsId = new int[list.size()];
            paramsActuales = new int[15];

            for (Parameter p : list) {
                model.addRow(new Object[]{p.getParameter(), "", "", "", "", "", ""});
                paramsId[list.indexOf(p)] = p.getId();
                paramsActuales[p.getId()] = list.indexOf(p);
                tblTraining.setModel(model);
            }
            crearUsersStage(6, list.size());
        } else {

            printTraining();
        }
        prog += 2;
        updateProgress(prog);
    }

    public void printTraining() {
        for (Format f : formatosList) {
            int entero = Integer.parseInt(formatUser);
            if (f.getId() == entero) {
                formatSelected = f;
                List<Parameter> list = getParams(f.getId());
                paramsId = new int[list.size()];
                paramsActuales = new int[15];
                for (Parameter p : list) {
                    model.addRow(new Object[]{p.getParameter(), "", "", "", "", "", ""});
                    paramsId[list.indexOf(p)] = p.getId();
                    paramsActuales[p.getId()] = list.indexOf(p);
                    tblTraining.setModel(model);
                }
                crearUsersStage(6, list.size());
            }
        }

        boolean flag1 = false;
        String[][] parrafos = new String[paramsId.length][7];
        int[] lastParamDay = new int[2];
        String parrafo = "";
        for (int i = 0; i < usersStageListByUserDB.size(); i++) {

            UserStage us = usersStageListByUserDB.get(i);
            int day = us.getWeek_day();
            int param = paramsActuales[us.getParameter_id()];
            if (lastParamDay[0] == param && lastParamDay[1] == day && i != 0) {
                parrafo += "---<br>";
            } else if (i != 0) {
                parrafo += "</p>";
                parrafos[lastParamDay[0]][lastParamDay[1]] = parrafo;
                parrafo = "<html>";
                parrafo = parrafo + "<p align=\"center\">";
            } else {
                parrafo = "<html>";
                parrafo = parrafo + "<p align=\"center\">";
            }

            for (Stage s : stagesListByUserDB) {

                if (s.getId() == us.getStage_id()) {

                    parrafo = parrafo + s.getName() + "<br>";
                    for (Box b : boxListByUserDB) {
                        if (b.getStage_id() == s.getId()) {
                            flag1 = true;
                            String r = b.getRondas();
                            parrafo = parrafo + r.replace("\n", "<br>") + "<br>";
                            for (EjerciciosInBox eb : ejerciciosInBoxsByUserDB) {
                                if (eb.getBox_id() == b.getId()) {
                                    for (Ejercicio ej : ejerciciosList) {
                                        if (ej.getId() == eb.getEjercicio_id()) {
                                            parrafo = parrafo + ej.getName() + " " + eb.getRepeticiones() + " "
                                                    + eb.getComentario() + "<br>";
                                            if(!eb.getPesos().equals("")){
                                                parrafo=parrafo+eb.getPesos().replace("\n", "<br>") + "<br>";
                                            }
                                        }
                                    }
                                }
                            }

                        }

                    }

                }
            }

            lastParamDay[0] = param;
            lastParamDay[1] = day;

        }
        parrafo += "</p>";
        parrafos[lastParamDay[0]][lastParamDay[1]] = parrafo;

//        int param = 0;
//            for (int i = 0; i < paramsId.length; i++) {
//                if (paramsId[i] == us.getParameter_id()) {
//                    param = i + 1;
//                }
//            }
//            model.setValueAt(parrafo, param, us.getWeek_day() + 1);
        for (UserStage us : usersStageListByUserDB) {
            int day = us.getWeek_day();
            int param = paramsActuales[us.getParameter_id()];

            model.setValueAt(parrafos[param][day], param, day + 1);
        }

        updateRowHeights();
        usersStageListSave = usersStageListByUserDB;
        stagesListSave = stagesListByUserDB;
        boxListSave = boxListByUserDB;
        ejerciciosInBoxsSave = ejerciciosInBoxsByUserDB;

    }

    public List<Parameter> getParams(int id) {
        List<Parameter> list;
        Http http = new Http();
        String body = http.GET(URLs.GetParametersByFormat(id));
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaparam = new TypeToken<List<Parameter>>() {
        }.getType();
        list = gson.fromJson(body, tipoListaparam);
        return list;
    }

    public void getFormats() {
        Http http = new Http();
        String body = http.GET(URLs.GetFormats);
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListaFormats = new TypeToken<List<Format>>() {
        }.getType();
        formatosList = gson.fromJson(body, tipoListaFormats);
    }

    public List<Ejercicio> getExercises() {
        List<Ejercicio> list;
        Http http = new Http();
        String body = http.GET(URLs.GetEjercicios);
        Gson gson = new Gson();
        java.lang.reflect.Type tipoListEjercicio = new TypeToken<List<Ejercicio>>() {
        }.getType();
        list = gson.fromJson(body, tipoListEjercicio);
        return list;
    }

    public void crearUsersStage(int dia, int parametro) {

        usersStages = new UserStage[dia][parametro];
        stages = new Stage[dia][parametro];
        int count = 0;
        for (int i = 0; i < dia; i++) {
            for (int j = 0; j < parametro; j++) {
                stages[i][j] = new Stage(count, "");
                usersStages[i][j] = new UserStage(0/*user_id*/, count/*stage_id*/, 0/*index_in_parameter*/,
                        0/*parameter_id*/, 0/*week_day*/);

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

    private ListSelectionListener listSelection() {
        ListSelectionListener handler = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                tbpTraining.setEnabled(true);
                parameterSelected = tblTraining.getSelectedRow();
                daySelected = tblTraining.getSelectedColumn() - 1;
                if (parameterSelected > -1 && daySelected > -1) {
                    SetComponentsEnabled(true);
                } else {
                    SetComponentsEnabled(false);
                }
                clearTab();
                llenarElementos(parameterSelected, daySelected);
                boxes = new ArrayList<>();
                namesStages = new ArrayList<>();
                namesStages.add(txtNameStages1);
                ejerciciosInBoxs = new ArrayList<>();
                stagesList = new ArrayList<>();
                usersStageList = new ArrayList<>();
                cbxSeparadores = new ArrayList<>();
                cbxSeparadores.add(cbxSeparador1);
                //haveTraining=true;
                if (/*haveTraining&&*/ parameterSelected > -1 && daySelected > -1 /*&& !newPlann*/) {
                    llenarElementos(parameterSelected, daySelected);
                    int a = modelEjercicios.getRowCount();
                    for (int i = a; i > 0; i--) {
                        modelEjercicios.removeRow(i - 1);
                    }

                    boolean first = false;
                    List<UserStage> userStages = new ArrayList<>();
                    int num = 1;
                    for (int i = 0; i < usersStageListSave.size(); i++) {
                        UserStage us = usersStageListSave.get(i);
                        if (us.getWeek_day() == daySelected && us.getParameter_id() == paramsId[parameterSelected]) {

                            for (Stage st : stagesListSave) {

                                if (st.getId() == us.getStage_id()) {

                                    for (Box b : boxListSave) {
                                        if (b.getStage_id() == st.getId()) {
                                            //copyPane(b, num);
                                            boxes.add(new Box(num - 1, num - 1, b.getRondas(), 0));
                                            if (num == 1) {

                                                txtNameStages1.setText(st.getName());
                                                txtRondas1.setText(b.getRondas().replace("/n", "\n"));
                                                for (EjerciciosInBox eb : ejerciciosInBoxsSave) {
                                                    if (eb.getBox_id() == b.getId()) {
                                                        ejerciciosInBoxs.add(new EjerciciosInBox(eb.getEjercicio_id(),
                                                                eb.getRepeticiones(), num - 1, eb.getComentario(),eb.getPesos(), 0));
                                                        String linea = "";
                                                        for (Ejercicio ej : ejerciciosList) {

                                                            if (ej.getId() == eb.getEjercicio_id()) {
                                                                linea += ej.getName() + " ";
                                                            }
                                                        }
                                                        linea += eb.getRepeticiones() + " " + eb.getComentario();
                                                        modelEjercicios.addRow(new Object[]{linea, "", "", "", "", "", ""});
                                                    }
                                                }
                                            } else {
                                                List<String> lineas = new ArrayList<>();

                                                for (EjerciciosInBox eb : ejerciciosInBoxsSave) {
                                                    if (eb.getBox_id() == b.getId()) {
                                                        ejerciciosInBoxs.add(new EjerciciosInBox(eb.getEjercicio_id(),
                                                                eb.getRepeticiones(), num - 1, eb.getComentario(),eb.getPesos(), 0));
                                                        String linea = "";
                                                        for (Ejercicio ej : ejerciciosList) {

                                                            if (ej.getId() == eb.getEjercicio_id()) {
                                                                linea += ej.getName() + " ";
                                                            }
                                                        }
                                                        linea += eb.getRepeticiones() + " " + eb.getComentario();
                                                        lineas.add(linea);

                                                    }
                                                }
                                                copyPane(num, st.getName(), b.getRondas().replace("/n", "\n"), lineas, first);
                                                first = false;
                                            }
                                            num++;
                                        }

                                    }
                                    first = true;
                                }
                            }
                        }
                    }
                    spnEjercicios.setValue(num - 1);
                }

            }

        };

        return handler;
    }

    public void llenarElementos(int row, int col) {
        if (col > -1 && row > -1) {
            Stage stage = stages[col][row];

            txtName.setText(stage.getName());
            int numBox = 0;
            for (Box b : boxes) {
                if (b.getStage_id() == stage.getId()) {

                    if (numBox == 0) {

                    } else {

                    }

                    numBox++;
                }
            }
            spnEjercicios.setValue(numBox);
        }
    }

    

    private void clearTab() {
        txtNameStages1.setText("");
        txtComentarios1.setText("");
        txtReps1.setText("");
        txtRondas1.setText("");
        txtPesos1.setText("");
        int a = modelEjercicios.getRowCount();
        for (int i = a; i > 0; i--) {
            modelEjercicios.removeRow(i - 1);
        }

    }

    public void copyPane(int valor) {

        modelNoEditable modelEjercicio1 = new modelNoEditable();
        modelEjercicio1.addColumn("Ejercicios");

        JPanel pnl = new JPanel();
        pnl.setBounds(pnlGrupo1.getBounds());
        pnl.setBackground(pnlGrupo1.getBackground());
        pnl.setSize(pnlGrupo1.getSize());
        pnl.setAlignmentX(pnlGrupo1.getAlignmentX());
        pnl.setAlignmentY(pnlGrupo1.getAlignmentY());
        pnl.setLayout(null);

        JTextField txtNameStage = new JTextField();
        txtNameStage.setBounds(txtNameStages1.getBounds());
        txtNameStage.setSize(txtNameStages1.getSize());
        txtNameStage.setAlignmentX(txtNameStages1.getAlignmentX());
        txtNameStage.setAlignmentY(txtNameStages1.getAlignmentY());
        txtNameStage.setEnabled(false);
        pnl.add(txtNameStage);

        JCheckBox cmxSeparador = new JCheckBox();
        cmxSeparador.setBounds(cbxSeparador1.getBounds());
        cmxSeparador.setSize(cbxSeparador1.getSize());
        cmxSeparador.setAlignmentX(cbxSeparador1.getAlignmentX());
        cmxSeparador.setAlignmentY(cbxSeparador1.getAlignmentY());
        cmxSeparador.setText(cbxSeparador1.getText());
        cmxSeparador.setEnabled(true);
        pnl.add(cmxSeparador);
        cbxSeparadores.add(cmxSeparador);

        JLabel lblNameStage = new JLabel();
        lblNameStage.setBounds(lblNameStage1.getBounds());
        lblNameStage.setAlignmentX(lblNameStage1.getAlignmentX());
        lblNameStage.setAlignmentY(lblNameStage1.getAlignmentY());
        lblNameStage.setText(lblNameStage1.getText());
        lblNameStage.setEnabled(true);
        pnl.add(lblNameStage);

        JLabel lblRondas = new JLabel();
        lblRondas.setBounds(lblRondas1.getBounds());
        lblRondas.setAlignmentX(lblRondas1.getAlignmentX());
        lblRondas.setAlignmentY(lblRondas1.getAlignmentY());
        lblRondas.setText(lblRondas1.getText());
        pnl.add(lblRondas);

        JTextArea txtRondas = new JTextArea(txtRondas1.getHeight(), txtRondas1.getWidth());
        txtRondas.setBounds(txtRondas1.getBounds());
        txtRondas.setAlignmentX(txtRondas1.getAlignmentX());
        txtRondas.setAlignmentY(txtRondas1.getAlignmentY());
        txtRondas.setSize(txtRondas1.getSize());
        pnl.add(txtRondas);
        
        JLabel lblPesos = new JLabel();
        lblPesos.setBounds(lblPesos1.getBounds());
        lblPesos.setAlignmentX(lblPesos1.getAlignmentX());
        lblPesos.setAlignmentY(lblPesos1.getAlignmentY());
        lblPesos.setText(lblPesos1.getText());
        pnl.add(lblPesos);

        JTextArea txtPesos = new JTextArea(txtPesos1.getHeight(), txtPesos1.getWidth());
        txtPesos.setBounds(txtPesos1.getBounds());
        txtPesos.setAlignmentX(txtPesos1.getAlignmentX());
        txtPesos.setAlignmentY(txtPesos1.getAlignmentY());
        txtPesos.setSize(txtPesos1.getSize());
        pnl.add(txtPesos);
        

//        JPanel panel = new JPanel();
//        panel.setBounds(pnlRondas.getBounds());
//        panel.setAlignmentX(pnlRondas.getAlignmentX());
//        panel.setAlignmentY(pnlRondas.getAlignmentY());
//        panel.setSize(pnlRondas.getSize());
//        panel.add(txtRondas);
//        panel.setBackground(pnlRondas.getBackground());
//        pnl.add(panel);
        JButton btnAddEjercicio = new JButton();
        btnAddEjercicio.setBounds(btnAddEjercicio1.getBounds());
        btnAddEjercicio.setAlignmentX(btnAddEjercicio1.getAlignmentX());
        btnAddEjercicio.setAlignmentY(btnAddEjercicio1.getAlignmentY());
        btnAddEjercicio.setText("Agregar Ejercicio");
        pnl.add(btnAddEjercicio);

        JButton btnUp = new JButton();
        btnUp.setBounds(btnUp1.getBounds());
        btnUp.setAlignmentX(btnUp1.getAlignmentX());
        btnUp.setAlignmentY(btnUp1.getAlignmentY());
        btnUp.setText(btnUp1.getText());
        pnl.add(btnUp);

        JButton btnDown = new JButton();
        btnDown.setBounds(btnDown1.getBounds());
        btnDown.setAlignmentX(btnDown1.getAlignmentX());
        btnDown.setAlignmentY(btnDown1.getAlignmentY());
        btnDown.setText(btnDown1.getText());
        pnl.add(btnDown);

        JButton btSaveGroup = new JButton();
        btSaveGroup.setBounds(btnSaveGroup1.getBounds());
        btSaveGroup.setAlignmentX(btnSaveGroup1.getAlignmentX());
        btSaveGroup.setAlignmentY(btnSaveGroup1.getAlignmentY());
        btSaveGroup.setText(btnSaveGroup1.getText());

        pnl.add(btSaveGroup);
        
        JButton btnDeleteGroup = new JButton();
        btnDeleteGroup.setBounds(btnDeleteGroup1.getBounds());
        btnDeleteGroup.setAlignmentX(btnDeleteGroup1.getAlignmentX());
        btnDeleteGroup.setAlignmentY(btnDeleteGroup1.getAlignmentY());
        btnDeleteGroup.setText(btnDeleteGroup1.getText());
        
        
        pnl.add(btnDeleteGroup);

        JLabel lblReps = new JLabel();
        lblReps.setBounds(lblReps1.getBounds());
        lblReps.setAlignmentX(lblReps1.getAlignmentX());
        lblReps.setAlignmentY(lblReps1.getAlignmentY());
        lblReps.setText(lblReps1.getText());
        pnl.add(lblReps);

        JTextField txtReps = new JTextField();
        txtReps.setBounds(txtReps1.getBounds());
        txtReps.setSize(txtReps1.getSize());
        txtReps.setAlignmentX(txtReps1.getAlignmentX());
        txtReps.setAlignmentY(txtReps1.getAlignmentY());
        pnl.add(txtReps);

        JLabel lblEjercicios = new JLabel();
        lblEjercicios.setBounds(lblEjercicios1.getBounds());
        lblEjercicios.setAlignmentX(lblEjercicios1.getAlignmentX());
        lblEjercicios.setAlignmentY(lblEjercicios1.getAlignmentY());
        lblEjercicios.setText(lblEjercicios1.getText());
        pnl.add(lblEjercicios);

        JComboBox cmbEjercicios = new JComboBox();
        cmbEjercicios.setBounds(cmbEjercicios1.getBounds());
        cmbEjercicios.setAlignmentX(cmbEjercicios1.getAlignmentX());
        cmbEjercicios.setAlignmentY(cmbEjercicios1.getAlignmentY());
        cmbEjercicios.setEditable(true);
        for (Ejercicio e : ejerciciosList) {
            cmbEjercicios.addItem(e.getName());
        }
        AutoCompleteDecorator.decorate(cmbEjercicios);
        pnl.add(cmbEjercicios);

        JLabel lblComentarios = new JLabel();
        lblComentarios.setBounds(lblComentarios1.getBounds());
        lblComentarios.setAlignmentX(lblComentarios1.getAlignmentX());
        lblComentarios.setAlignmentY(lblComentarios1.getAlignmentY());
        lblComentarios.setText(lblComentarios1.getText());
        pnl.add(lblComentarios);

        JTextField txtComentarios = new JTextField();
        txtComentarios.setBounds(txtComentarios1.getBounds());
        txtComentarios.setSize(txtComentarios1.getSize());
        txtComentarios.setAlignmentX(txtComentarios1.getAlignmentX());
        txtComentarios.setAlignmentY(txtComentarios1.getAlignmentY());
        pnl.add(txtComentarios);

        JLabel lblListEjercicios = new JLabel();
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
        tblEjercicios.setModel(modelEjercicio1);

        tbpTraining.addTab("Grupo" + valor, pnl);
        namesStages.add(txtNameStage);
        btnAddEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ejercicio ejercicioSelected = null;

                for (Ejercicio ej : ejerciciosList) {
                    if (ej.getName().equals(cmbEjercicios.getSelectedItem().toString())) {
                        ejercicioSelected = ej;
                    }
                }
                if (ejercicioSelected != null) {
                    ejerciciosInBoxs.add(new EjerciciosInBox(/*ejercicio_id*/ejercicioSelected.getId(),
                            /*tepeticiones*/ txtReps.getText(),
                            /*box_id*/ tbpTraining.getSelectedIndex(),
                            /*comentario*/ txtComentarios.getText(), txtPesos.getText(), /*index in box*/ 0));
                    String linea = "";

                    linea += ejercicioSelected.getName() + " " + txtReps.getText() + " " + txtComentarios.getText();

                    modelEjercicio1.addRow(new Object[]{linea, "", "", "", "", "", ""});
                } else {
                    javax.swing.JOptionPane.showMessageDialog(EditarEntrenamiento.this, "No existe el ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btSaveGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                if (cmxSeparador.isSelected()) {
//                    int idStage = 1;
//                    boolean flag = true;
//                    for (int i = 1; i <= stagesList.size(); i++) {
//                        if (stagesList.get(i - 1).getId() != i) {
//                            idStage = i;
//                            flag = false;
//                        }
//                    }
//                    if (flag) {
//                        idStage = stagesList.size() + 1;
//                    }
//                    idStageActual = idStage;
//                    stagesList.add(new Stage(idStage, txtNameStage.getText()));
//                    boxes.add(new Box(tbpTraining.getSelectedIndex(), tbpTraining.getSelectedIndex(),
//                            txtRondas.getText(), idStage));
//                } else {
                Box boxReplace = null;
                for (Box b : boxes) {
                    if (b.getId() == tbpTraining.getSelectedIndex()) {
                        boxReplace = b;
                    }
                }
                if (boxReplace != null) {
                    boxes.remove(boxReplace);
                }
                boxes.add(new Box(tbpTraining.getSelectedIndex(), tbpTraining.getSelectedIndex(),
                        txtRondas.getText(), 0));
//                }
                if (txtNameStage.getText().equals("") && txtRondas.getText().equals("") && modelEjercicio1.getRowCount() == 0) {
                    ACEPTAR(false);
                } else {
                    ACEPTAR(true);
                }
                updateRowHeights();
            }
        });

        btnDeleteGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxes.removeIf(n->(n.getId()==tbpTraining.getSelectedIndex()));
                ejerciciosInBoxs.removeIf(n->(n.getBox_id()==tbpTraining.getSelectedIndex()));
                ACEPTAR(true);
                tbpTraining.remove(tbpTraining.getSelectedIndex());
                int valueSpn = (Integer) spnEjercicios.getValue();
                spnEjercicios.setValue(valueSpn -1);
                updateRowHeights();
                
            }
        });
        
        cmxSeparador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtNameStage.setEnabled(cmxSeparador.isSelected());

            }
        });

        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePositionExersise(tblEjercicios, true, modelEjercicio1);
            }
        });

        btnDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePositionExersise(tblEjercicios, false, modelEjercicio1);
            }
        });

        JPopupMenu menu = new JPopupMenu();
        JMenuItem itemDelete = new JMenuItem("Eliminar ejercicio");
        itemDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boxID = tbpTraining.getSelectedIndex();
                List<EjerciciosInBox> ejs = new ArrayList<>();
                for (EjerciciosInBox item : ejerciciosInBoxs) {
                    if (item.getBox_id() == boxID) {
                        ejs.add(item);
                    }

                }
                for (EjerciciosInBox item : ejs) {
                    int f = tblEjercicios.getSelectedRow();
                    int ind = ejs.indexOf(item);
                    if (ejs.indexOf(item) == tblEjercicios.getSelectedRow()) {
                        ejerciciosInBoxs.remove(item);
                        modelEjercicio1.removeRow(tblEjercicios.getSelectedRow());
                    }

                }
            }
        });
        menu.add(itemDelete);
        tblEjercicios.setComponentPopupMenu(menu);

    }

    public void copyPane(int valor, String nameSt, String rondas, List<String> lines, boolean sep) {

        modelNoEditable modelEjercicio1 = new modelNoEditable();
        modelEjercicio1.addColumn("Ejercicios");

        JPanel pnl = new JPanel();
        pnl.setBounds(pnlGrupo1.getBounds());
        pnl.setBackground(pnlGrupo1.getBackground());
        pnl.setSize(pnlGrupo1.getSize());
        pnl.setAlignmentX(pnlGrupo1.getAlignmentX());
        pnl.setAlignmentY(pnlGrupo1.getAlignmentY());
        pnl.setLayout(null);

        JTextField txtNameStage = new JTextField();
        txtNameStage.setBounds(txtNameStages1.getBounds());
        txtNameStage.setText(nameSt);
        txtNameStage.setSize(txtNameStages1.getSize());
        txtNameStage.setAlignmentX(txtNameStages1.getAlignmentX());
        txtNameStage.setAlignmentY(txtNameStages1.getAlignmentY());
        txtNameStage.setEnabled(sep);
        pnl.add(txtNameStage);

        JCheckBox cmxSeparador = new JCheckBox();
        cmxSeparador.setBounds(cbxSeparador1.getBounds());
        cmxSeparador.setSize(cbxSeparador1.getSize());
        cmxSeparador.setAlignmentX(cbxSeparador1.getAlignmentX());
        cmxSeparador.setAlignmentY(cbxSeparador1.getAlignmentY());
        cmxSeparador.setText(cbxSeparador1.getText());
        cmxSeparador.setSelected(sep);
        cmxSeparador.setEnabled(true);
        pnl.add(cmxSeparador);
        cbxSeparadores.add(cmxSeparador);

        JLabel lblNameStage = new JLabel();
        lblNameStage.setBounds(lblNameStage1.getBounds());
        lblNameStage.setAlignmentX(lblNameStage1.getAlignmentX());
        lblNameStage.setAlignmentY(lblNameStage1.getAlignmentY());
        lblNameStage.setText(lblNameStage1.getText());
        lblNameStage.setEnabled(true);
        pnl.add(lblNameStage);

        JButton btnUp = new JButton();
        btnUp.setBounds(btnUp1.getBounds());
        btnUp.setAlignmentX(btnUp1.getAlignmentX());
        btnUp.setAlignmentY(btnUp1.getAlignmentY());
        btnUp.setText(btnUp1.getText());
        pnl.add(btnUp);

        JButton btnDown = new JButton();
        btnDown.setBounds(btnDown1.getBounds());
        btnDown.setAlignmentX(btnDown1.getAlignmentX());
        btnDown.setAlignmentY(btnDown1.getAlignmentY());
        btnDown.setText(btnDown1.getText());
        pnl.add(btnDown);

        JLabel lblRondas = new JLabel();
        lblRondas.setBounds(lblRondas1.getBounds());
        lblRondas.setAlignmentX(lblRondas1.getAlignmentX());
        lblRondas.setAlignmentY(lblRondas1.getAlignmentY());
        lblRondas.setText(lblRondas1.getText());
        pnl.add(lblRondas);

        JTextArea txtRondas = new JTextArea(txtRondas1.getHeight(), txtRondas1.getWidth());
        txtRondas.setBounds(txtRondas1.getBounds());
        txtRondas.setAlignmentX(txtRondas1.getAlignmentX());
        txtRondas.setAlignmentY(txtRondas1.getAlignmentY());
        txtRondas.setText(rondas);
        txtRondas.setSize(txtRondas1.getSize());
        pnl.add(txtRondas);
        
        JLabel lblPesos = new JLabel();
        lblPesos.setBounds(lblPesos1.getBounds());
        lblPesos.setAlignmentX(lblPesos1.getAlignmentX());
        lblPesos.setAlignmentY(lblPesos1.getAlignmentY());
        lblPesos.setText(lblPesos1.getText());
        pnl.add(lblPesos);

        JTextArea txtPesos = new JTextArea(txtPesos1.getHeight(), txtPesos1.getWidth());
        txtPesos.setBounds(txtPesos1.getBounds());
        txtPesos.setAlignmentX(txtPesos1.getAlignmentX());
        txtPesos.setAlignmentY(txtPesos1.getAlignmentY());
        txtPesos.setSize(txtPesos1.getSize());
        pnl.add(txtPesos);

//        JPanel panel = new JPanel();
//        panel.setBounds(pnlRondas.getBounds());
//        panel.setAlignmentX(pnlRondas.getAlignmentX());
//        panel.setAlignmentY(pnlRondas.getAlignmentY());
//        panel.setSize(pnlRondas.getSize());
//        panel.add(txtRondas);
//        panel.setBackground(pnlRondas.getBackground());
//        pnl.add(panel);
        JButton btnAddEjercicio = new JButton();
        btnAddEjercicio.setBounds(btnAddEjercicio1.getBounds());
        btnAddEjercicio.setAlignmentX(btnAddEjercicio1.getAlignmentX());
        btnAddEjercicio.setAlignmentY(btnAddEjercicio1.getAlignmentY());
        btnAddEjercicio.setText("Agregar Ejercicio");
        pnl.add(btnAddEjercicio);

        JButton btSaveGroup = new JButton();
        btSaveGroup.setBounds(btnSaveGroup1.getBounds());
        btSaveGroup.setAlignmentX(btnSaveGroup1.getAlignmentX());
        btSaveGroup.setAlignmentY(btnSaveGroup1.getAlignmentY());
        btSaveGroup.setText(btnSaveGroup1.getText());
        
        
        JButton btnDeleteGroup = new JButton();
        btnDeleteGroup.setBounds(btnDeleteGroup1.getBounds());
        btnDeleteGroup.setAlignmentX(btnDeleteGroup1.getAlignmentX());
        btnDeleteGroup.setAlignmentY(btnDeleteGroup1.getAlignmentY());
        btnDeleteGroup.setText(btnDeleteGroup1.getText());
        
        
        pnl.add(btnDeleteGroup);
        pnl.add(btSaveGroup);

        JLabel lblReps = new JLabel();
        lblReps.setBounds(lblReps1.getBounds());
        lblReps.setAlignmentX(lblReps1.getAlignmentX());
        lblReps.setAlignmentY(lblReps1.getAlignmentY());
        lblReps.setText(lblReps1.getText());
        pnl.add(lblReps);

        JTextField txtReps = new JTextField();
        txtReps.setBounds(txtReps1.getBounds());
        txtReps.setSize(txtReps1.getSize());
        txtReps.setAlignmentX(txtReps1.getAlignmentX());
        txtReps.setAlignmentY(txtReps1.getAlignmentY());
        pnl.add(txtReps);

        JLabel lblEjercicios = new JLabel();
        lblEjercicios.setBounds(lblEjercicios1.getBounds());
        lblEjercicios.setAlignmentX(lblEjercicios1.getAlignmentX());
        lblEjercicios.setAlignmentY(lblEjercicios1.getAlignmentY());
        lblEjercicios.setText(lblEjercicios1.getText());
        pnl.add(lblEjercicios);
        
        JComboBox cmbEjercicios = new JComboBox();
        cmbEjercicios.setBounds(cmbEjercicios1.getBounds());
        cmbEjercicios.setAlignmentX(cmbEjercicios1.getAlignmentX());
        cmbEjercicios.setAlignmentY(cmbEjercicios1.getAlignmentY());
        cmbEjercicios.setEditable(true);
        for (Ejercicio e : ejerciciosList) {
            cmbEjercicios.addItem(e.getName());
        }
        AutoCompleteDecorator.decorate(cmbEjercicios);
        pnl.add(cmbEjercicios);

        JLabel lblComentarios = new JLabel();
        lblComentarios.setBounds(lblComentarios1.getBounds());
        lblComentarios.setAlignmentX(lblComentarios1.getAlignmentX());
        lblComentarios.setAlignmentY(lblComentarios1.getAlignmentY());
        lblComentarios.setText(lblComentarios1.getText());
        pnl.add(lblComentarios);

        JTextField txtComentarios = new JTextField();
        txtComentarios.setBounds(txtComentarios1.getBounds());
        txtComentarios.setSize(txtComentarios1.getSize());
        txtComentarios.setAlignmentX(txtComentarios1.getAlignmentX());
        txtComentarios.setAlignmentY(txtComentarios1.getAlignmentY());
        pnl.add(txtComentarios);

        JLabel lblListEjercicios = new JLabel();
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
        tblEjercicios.setModel(modelEjercicio1);

        tbpTraining.addTab("Grupo" + valor, pnl);
        for (String st : lines) {
            modelEjercicio1.addRow(new Object[]{st, "", "", "", "", "", ""});
        }
        btnAddEjercicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ejercicio ejercicioSelected = null;

                for (Ejercicio ej : ejerciciosList) {
                    if (ej.getName().equals(cmbEjercicios.getSelectedItem().toString())) {
                        ejercicioSelected = ej;
                    }
                }
                if (ejercicioSelected != null) {
                    ejerciciosInBoxs.add(new EjerciciosInBox(/*ejercicio_id*/ejercicioSelected.getId(),
                            /*tepeticiones*/ txtReps.getText(),
                            /*box_id*/ tbpTraining.getSelectedIndex(),
                            /*comentario*/ txtComentarios.getText(),txtPesos.getText(), /*index in box*/ 0));
                    String linea = "";

                    linea += ejercicioSelected.getName() + " " + txtReps.getText() + " " + txtComentarios.getText();

                    modelEjercicio1.addRow(new Object[]{linea, "", "", "", "", "", ""});
                } else {
                    javax.swing.JOptionPane.showMessageDialog(EditarEntrenamiento.this, "No existe el ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        namesStages.add(txtNameStage);
        btSaveGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                if (cmxSeparador.isSelected()) {
//                    int idStage = 1;
//                    boolean flag = true;
//                    for (int i = 1; i <= stagesList.size(); i++) {
//                        if (stagesList.get(i - 1).getId() != i) {
//                            idStage = i;
//                            flag = false;
//                        }
//                    }
//                    if (flag) {
//                        idStage = stagesList.size() + 1;
//                    }
//                    idStageActual = idStage;
//                    stagesList.add(new Stage(idStage, txtNameStage.getText()));
//                    boxes.add(new Box(tbpTraining.getSelectedIndex(), tbpTraining.getSelectedIndex(),
//                            txtRondas.getText(), idStage));
//                } else {
                Box boxReplace = null;
                for (Box b : boxes) {
                    if (b.getId() == tbpTraining.getSelectedIndex()) {
                        boxReplace = b;
                    }
                }
                if (boxReplace != null) {
                    boxes.remove(boxReplace);
                }
                boxes.add(new Box(tbpTraining.getSelectedIndex(), tbpTraining.getSelectedIndex(),
                        txtRondas.getText(), 0));
//                }
                if (txtNameStage.getText().equals("") && txtRondas.getText().equals("") && modelEjercicio1.getRowCount() == 0) {
                    boxes.removeIf(n->(n.getId()==tbpTraining.getSelectedIndex()));
                    ACEPTAR(true);
                    tbpTraining.remove(tbpTraining.getSelectedIndex());
                } else {
                    ACEPTAR(true);
                }
               updateRowHeights(); 
            }
        });
        
        btnDeleteGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxes.removeIf(n->(n.getId()==tbpTraining.getSelectedIndex()));
                ejerciciosInBoxs.removeIf(n->(n.getBox_id()==tbpTraining.getSelectedIndex()));
                ACEPTAR(true);
                tbpTraining.remove(tbpTraining.getSelectedIndex());
                int valueSpn = (Integer) spnEjercicios.getValue();
                spnEjercicios.setValue(valueSpn -1);
                updateRowHeights();
            }
        });
        
        cmxSeparador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtNameStage.setEnabled(cmxSeparador.isSelected());

            }
        });

        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePositionExersise(tblEjercicios, true, modelEjercicio1);
            }
        });

        btnDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePositionExersise(tblEjercicios, false, modelEjercicio1);
            }
        });

        JPopupMenu menu = new JPopupMenu();
        JMenuItem itemDelete = new JMenuItem("Eliminar ejercicio");
        itemDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boxID = tbpTraining.getSelectedIndex();
                List<EjerciciosInBox> ejs = new ArrayList<>();
                for (EjerciciosInBox item : ejerciciosInBoxs) {
                    if (item.getBox_id() == boxID) {
                        ejs.add(item);
                    }

                }
                for (EjerciciosInBox item : ejs) {
                    int f = tblEjercicios.getSelectedRow();
                    int ind = ejs.indexOf(item);
                    if (ejs.indexOf(item) == tblEjercicios.getSelectedRow()) {
                        ejerciciosInBoxs.remove(item);
                        modelEjercicio1.removeRow(tblEjercicios.getSelectedRow());
                    }

                }
            }
        });
        menu.add(itemDelete);
        tblEjercicios.setComponentPopupMenu(menu);

    }
    
    private void ACEPTAR(boolean f) {

        //Datos entrando
        List<Stage> stagesEntrando = stagesList;
        List<Box> boxsEntrando = boxes;
        List<UserStage> usEntrando = usersStageList;
        List<EjerciciosInBox> ebEntrando = ejerciciosInBoxs;

        //limpio los datos de esa celda
        List<Stage> stageDelets = new ArrayList<>();
        List<Box> boxDelets = new ArrayList<>();
        List<UserStage> usDeletes = new ArrayList<>();
        List<EjerciciosInBox> ebDeletes = new ArrayList<>();

        for (int i = 0; i < usersStageListSave.size(); i++) {
            UserStage us = usersStageListSave.get(i);
            if (us.getWeek_day() == daySelected && us.getParameter_id() == paramsId[parameterSelected]) {

                for (Stage st : stagesListSave) {

                    if (st.getId() == us.getStage_id()) {

                        for (Box b : boxListSave) {

                            if (b.getStage_id() == st.getId()) {

                                for (EjerciciosInBox ej : ejerciciosInBoxsSave) {
                                    if (ej.getBox_id() == b.getId()) {
                                        ebDeletes.add(ej);
                                    }
                                }
                                boxDelets.add(b);
                            }
                        }
                        stageDelets.add(st);
                    }
                }
                usDeletes.add(us);

            }
        }
        stagesListSave.removeAll(stageDelets);
        usersStageListSave.removeAll(usDeletes);
        boxListSave.removeAll(boxDelets);
        ejerciciosInBoxsSave.removeAll(ebDeletes);
        ///////////////////////Aqui alineo ids
        if (f) {
            List<EjerciciosInBox> auxiliarEIB = new ArrayList<>();
            List<Box> auxiliarB = new ArrayList<>();
            int idStage = 0;
            int idBox = 1;
            int indexInStage = 0;
            stagesList = new ArrayList<>();//OJO!!
            usersStageList = new ArrayList<>();//OJO!!
            List<JCheckBox> cbxs = new ArrayList<>();
            List<Box> boxs = new ArrayList<>();
            for (int i = 0; i < tbpTraining.getTabCount(); i++) {
                cbxs.add(cbxSeparadores.get(i));

            }
            cbxSeparadores = cbxs;
            for (JCheckBox check : cbxSeparadores) {

                for (Box b : boxes) {
                    int index = cbxSeparadores.indexOf(check);
                    if (b.getId() == index) {

                        String name;

                        if (!check.isSelected()) {
                            for (EjerciciosInBox e : ejerciciosInBoxs) {
                                if (e.getBox_id() == b.getId()) {
                                    auxiliarEIB.add(new EjerciciosInBox(e.getEjercicio_id(), e.getRepeticiones(),
                                            idBox, e.getComentario(),e.getPesos(), e.getIndex_in_box()));
                                }
                            }
                            auxiliarB.add(new Box(idBox, b.getIndex_in_stage(), b.getRondas(), idStage));
                            indexInStage++;
                            idBox++;

                        } else if (check.isSelected()) {
                            idStage++;

                            name = namesStages.get(b.getId()).getText();

                            indexInStage = 0;
                            stagesList.add(new Stage(idStage, name));
                            for (EjerciciosInBox e : ejerciciosInBoxs) {
                                if (e.getBox_id() == b.getId()) {
                                    auxiliarEIB.add(new EjerciciosInBox(e.getEjercicio_id(), e.getRepeticiones(),
                                            idBox, e.getComentario(), e.getPesos(),e.getIndex_in_box()));
                                }
                            }
                            auxiliarB.add(new Box(idBox, b.getIndex_in_stage(), b.getRondas(), idStage));
                            indexInStage++;
                            idBox++;
                        }

                    }
                }
            }
            ejerciciosInBoxs = auxiliarEIB;
            boxes = auxiliarB;

            for (Stage st : stagesList) {

                usersStageList.add(new UserStage(usuario.getId(), st.getId()/*stage_id*/, 0/*index in parameter*/,
                        paramsId[parameterSelected], daySelected));
            }
            ////////////////////////////////////////////////////////////////////////
            //save listas
            ////////////////////////////////////////////////////////////////////////
            //Vincular los id

            List<Stage> auxStage = new ArrayList<>();
            List<Box> auxBox = new ArrayList<>();
            List<UserStage> auxUS = new ArrayList<>();
            List<EjerciciosInBox> auxEB = new ArrayList<>();
            List<Integer> ids = new ArrayList<>();
            for (Stage stage : stagesListSave) {
                ids.add(stage.getId());
            }
            for (Stage stage : stagesList) {
                int id = 1;
                boolean flagStage = true;
                while (flagStage) {
                    id++;
                    if (!ids.contains(id)) {
                        ids.add(id);
                        flagStage = false;
                    }

                }

                for (UserStage us : usersStageList) {
                    if (us.getStage_id() == stage.getId()) {
                        auxUS.add(new UserStage(us.getUser_id(), id, us.getIndex_in_parameter(),
                                us.getParameter_id(), us.getWeek_day()));
                    }
                }
                for (Box box : boxes) {
                    if (box.getStage_id() == stage.getId()) {
                        //box.setStage_id(id);
                        auxBox.add(new Box(box.getId(), box.getIndex_in_stage(), box.getRondas(), id));
                    }
                }
                stage.setId(id);
                ids.add(id);
                stagesListSave.add(stage);
            }
            boxes = auxBox;
            usersStageList = auxUS;
            List<Integer> idsBox = new ArrayList<>();
            for (Box box : boxListSave) {
                idsBox.add(box.getId());
            }
            for (Box box : boxes) {
                int id = 1;
                boolean flagBox = true;
                while (flagBox) {
                    id++;
                    if (!idsBox.contains(id)) {
                        idsBox.add(id);
                        flagBox = false;
                    }

                }
                for (EjerciciosInBox eb : ejerciciosInBoxs) {
                    if (eb.getBox_id() == box.getId()) {
                        //eb.setBox_id(id);
                        auxEB.add(new EjerciciosInBox(eb.getEjercicio_id(),
                                eb.getRepeticiones(), id, eb.getComentario(),eb.getPesos(), eb.getIndex_in_box()));
                    }
                }
                box.setId(id);
                idsBox.add(id);
                boxListSave.add(box);
            }
            ejerciciosInBoxs = auxEB;
            for (EjerciciosInBox ej : ejerciciosInBoxs) {
                ejerciciosInBoxsSave.add(ej);
            }
            for (UserStage us : usersStageList) {
                usersStageListSave.add(us);
            }
        }
        //////////////////////////////////////
        //Mostrar
        boolean flag1 = false;
        String parrafo = "<html>";
        parrafo = parrafo + "<p align=\"center\">";
        for (UserStage us : usersStageListSave) {
            if (flag1) {
                parrafo += "---<br>";
                flag1 = false;
            }
            if (us.getParameter_id() == paramsId[parameterSelected] && us.getWeek_day() == daySelected) {

                for (Stage s : stagesListSave) {

                    if (s.getId() == us.getStage_id()) {

                        parrafo = parrafo + s.getName() + "<br>";
                        for (Box b : boxListSave) {
                            if (b.getStage_id() == s.getId()) {
                                flag1 = true;
                                String r = b.getRondas();
                                parrafo = parrafo + r.replace("\n", "<br>") + "<br>";
                                for (EjerciciosInBox eb : ejerciciosInBoxsSave) {
                                    if (eb.getBox_id() == b.getId()) {
                                        for (Ejercicio ej : ejerciciosList) {
                                            if (ej.getId() == eb.getEjercicio_id()) {
                                                parrafo = parrafo + ej.getName() + " " + eb.getRepeticiones() + " "
                                                        + eb.getComentario() + "<br>";
                                                if(!eb.getPesos().equals("")){
                                                parrafo=parrafo+eb.getPesos().replace("\n", "<br>") + "<br>";
                                                }

                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }

                }
            }
        }
        parrafo += "</p>";

        model.setValueAt(parrafo, parameterSelected, daySelected + 1);
        updateRowHeights();

        //Datos saliendo
        stagesList = stagesEntrando;
        boxes = boxsEntrando;
        usersStageList = usEntrando;
        ejerciciosInBoxs = ebEntrando;

    }

    private void updateRowHeights() {
        for (int row = 0; row < tblTraining.getRowCount(); row++) {
            int rowHeight = tblTraining.getRowHeight();

            for (int column = 0; column < tblTraining.getColumnCount(); column++) {
                Component comp = tblTraining.prepareRenderer(tblTraining.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            tblTraining.setRowHeight(row, rowHeight);
        }
    }

    

    private String addTraining() {
        List<Stage> auxStageDB = stagesListDB;
        List<Box> auxBoxDB = boxListDB;
        List<EjerciciosInBox> auxEinBDB = ejerciciosInBoxsByUserDB;
        List<UserStage> auxUSDB = usersStageListByUserDB;

        //eliminar datos del usuario FALTA(no indispensable)
        List<Stage> auxStage = new ArrayList<>();
        List<Box> auxBox = new ArrayList<>();
        List<UserStage> auxUS = new ArrayList<>();
        List<EjerciciosInBox> auxEB = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        for (Stage stage : auxStageDB) {
            ids.add(stage.getId());
        }
        for (Stage stage : stagesListSave) {
            int id = 0;
            boolean flagStage = true;

            while (flagStage) {
                id++;
                if (!ids.contains(id)) {
                    ids.add(id);
                    flagStage = false;
                }

            }
            for (UserStage us : usersStageListSave) {
                if (us.getStage_id() == stage.getId()) {
                    auxUS.add(new UserStage(us.getUser_id(), id, us.getIndex_in_parameter(),
                            us.getParameter_id(), us.getWeek_day()));
                }
            }
            for (Box box : boxListSave) {
                if (box.getStage_id() == stage.getId()) {
                    //box.setStage_id(id);
                    auxBox.add(new Box(box.getId(), box.getIndex_in_stage(), box.getRondas(), id));
                }
            }
            stage.setId(id);
            auxStage.add(stage);
            auxStageDB.add(stage);
        }
        stagesListSave = auxStage;
        boxListSave = auxBox;
        usersStageListSave = auxUS;
        List<Box> auxBox2 = new ArrayList<>();
        List<Integer> idsBox = new ArrayList<>();

        for (Box box : auxBoxDB) {
            idsBox.add(box.getId());
        }
        for (Box box : boxListSave) {
            int id = 1;
            boolean flagBox = true;
            while (flagBox) {
                id++;
                if (!idsBox.contains(id)) {
                    idsBox.add(id);
                    flagBox = false;
                }
            }
            for (EjerciciosInBox eb : ejerciciosInBoxsSave) {
                if (eb.getBox_id() == box.getId()) {
                    //eb.setBox_id(id);
                    auxEB.add(new EjerciciosInBox(eb.getEjercicio_id(),
                            eb.getRepeticiones(), id, eb.getComentario(),eb.getPesos(), eb.getIndex_in_box()));
                }
            }
            box.setId(id);
            auxBox2.add(box);
            auxBoxDB.add(box);
        }
        ejerciciosInBoxsSave = auxEB;
        boxListSave = auxBox2;
        Http http=new Http();
        String resp=http.PostTrainning(stagesListSave, boxListSave, usersStageListSave, ejerciciosInBoxsSave);
        return resp;
        //addStageDB();
        //addUSDB();
        //addBoxDB();
        //addEBDB();

    }

    public void addStageDB() {
        Http http;

        http = new Http();
        http.PostStages(stagesListSave);

    }

    public void addUSDB() {
        Http http;

        http = new Http();

        http.PostUserStages(usersStageListSave);

    }

    public void addBoxDB() {
        Http http = new Http();
//        for (Box item : boxListSave) {
//            http = new Http();
//            http.GET(URLs.insertBox(item));
//        }
        http.PostBoxs(boxListSave);
    }

    public void addEBDB() {
        Http http = new Http();
//        for (EjerciciosInBox item : ejerciciosInBoxsSave) {
//            http = new Http();
//            http.GET(URLs.insertExersisesBox(item));
//        }
        http.PostEinB(ejerciciosInBoxsSave);
    }
    
    

    private void deleteTraining() {
        Http http = new Http();

//        String result2 = http.GET(URLs.deleteExersisesInBoxByUser(usuario.getId()));
//        http = new Http();
//        String result1 = http.GET(URLs.deleteBoxByUser(usuario.getId()));
        http = new Http();
        String result3 = http.GET(URLs.deleteStageByUser(usuario.getId()));

//        http = new Http();
//        String result4 = http.GET(URLs.deleteUserStageByUser(usuario.getId()));
    }

    private void changePositionExersise(JTable tableEjercicios, boolean up, modelNoEditable model) {
        int rowSelected = tableEjercicios.getSelectedRow();

        if (rowSelected < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No esta seleccionado ningún ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (up && rowSelected == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No puedes subir el ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!up && rowSelected == tableEjercicios.getRowCount() - 1) {
            javax.swing.JOptionPane.showMessageDialog(this, "No puedes bajar el ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            List<EjerciciosInBox> ejercicios = new ArrayList<>();
            for (EjerciciosInBox ej : ejerciciosInBoxs) {
                if (ej.getBox_id() == tbpTraining.getSelectedIndex()) {
                    ejercicios.add(ej);
                }
            }
            ejerciciosInBoxs.removeAll(ejercicios);
            EjerciciosInBox aux = ejercicios.get(rowSelected);
            ejercicios.remove(rowSelected);
            if (up) {
                ejercicios.add(rowSelected - 1, aux);
                model.moveRow(rowSelected, rowSelected, rowSelected - 1);
            } else {
                ejercicios.add(rowSelected + 1, aux);
                model.moveRow(rowSelected, rowSelected, rowSelected + 1);
            }
            ejerciciosInBoxs.addAll(ejercicios);

        }
    }

    public void saveTraining() {
        try {

            Http http;
            String resp="";
            //si el usuario tiene un entrenamiento, se elimina
            if (!haveTraining) {

                http = new Http();

                http.GET(URLs.insertUserFormat(usuario.getId(), formatSelected.getId()));
                Progress.progressBar.setValue(3);
                //AGREGAR ENTRENAMIENTOS
                resp=addTraining();
                Progress.progressBar.setValue(7);

            } else {
                //ELIMINAR 
                Progress.progressBar.setValue(3);
                deleteTraining();
                Progress.progressBar.setValue(6);
                http = new Http();
                http.GET(URLs.UpdateUserFormat(usuario.getId(), formatSelected.getId()));
                Progress.progressBar.setValue(8);
                //AGREGAR ENTRENAMIENTOS
                resp=addTraining();
            }
            Progress.close();
            javax.swing.JOptionPane.showMessageDialog(this, resp, "Exito!", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);

        }
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
        lblListEjercicios1 = new javax.swing.JLabel();
        lblEjercicios1 = new javax.swing.JLabel();
        cmbEjercicios1 = new javax.swing.JComboBox<>();
        lblReps1 = new javax.swing.JLabel();
        txtReps1 = new javax.swing.JTextField();
        lblComentarios1 = new javax.swing.JLabel();
        txtComentarios1 = new javax.swing.JTextField();
        btnAddEjercicio1 = new javax.swing.JButton();
        tblEjercicios1 = new javax.swing.JTable();
        txtNameStages1 = new javax.swing.JTextField();
        lblNameStage1 = new javax.swing.JLabel();
        cbxSeparador1 = new javax.swing.JCheckBox();
        btnSaveGroup1 = new javax.swing.JButton();
        btnUp1 = new javax.swing.JButton();
        btnDown1 = new javax.swing.JButton();
        lblPesos1 = new javax.swing.JLabel();
        txtPesos1 = new javax.swing.JTextArea();
        txtRondas1 = new javax.swing.JTextArea();
        btnDeleteGroup1 = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuNuevo = new javax.swing.JMenuItem();
        mnuFormato = new javax.swing.JMenu();
        JMenu2 = new javax.swing.JMenu();
        mnuSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuCargarGym = new javax.swing.JMenuItem();
        mnuCargarCross = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblTraining.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
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

        cmbEjercicios1.setEditable(true);

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

        txtNameStages1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameStages1ActionPerformed(evt);
            }
        });

        lblNameStage1.setText("Nombre:");

        cbxSeparador1.setText("Agregar Separador");

        btnSaveGroup1.setText("Guardar grupo");
        btnSaveGroup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveGroup1ActionPerformed(evt);
            }
        });

        btnUp1.setText("Subir");
        btnUp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUp1ActionPerformed(evt);
            }
        });

        btnDown1.setText("Bajar");
        btnDown1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDown1ActionPerformed(evt);
            }
        });

        lblPesos1.setText("Pesos:");

        txtPesos1.setColumns(20);
        txtPesos1.setRows(5);

        txtRondas1.setColumns(20);
        txtRondas1.setRows(5);

        btnDeleteGroup1.setText("Eliminar grupo");
        btnDeleteGroup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteGroup1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGrupo1Layout = new javax.swing.GroupLayout(pnlGrupo1);
        pnlGrupo1.setLayout(pnlGrupo1Layout);
        pnlGrupo1Layout.setHorizontalGroup(
            pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cbxSeparador1))
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                        .addComponent(lblNameStage1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNameStages1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                        .addComponent(lblRondas1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRondas1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(lblEjercicios1))
                                            .addComponent(lblReps1)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGrupo1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPesos1))))
                            .addComponent(lblComentarios1))
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtReps1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtComentarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtPesos1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblListEjercicios1)
                    .addComponent(tblEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUp1)
                    .addComponent(btnDown1)
                    .addComponent(btnAddEjercicio1)
                    .addComponent(btnSaveGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteGroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGrupo1Layout.setVerticalGroup(
            pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSeparador1)
                    .addComponent(lblListEjercicios1))
                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tblEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(lblNameStage1)
                                                    .addComponent(txtNameStages1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtRondas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblRondas1)))
                                            .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(lblEjercicios1)
                                                .addGap(21, 21, 21)
                                                .addComponent(lblReps1)))
                                        .addGap(20, 111, Short.MAX_VALUE))
                                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                                        .addComponent(cmbEjercicios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(txtReps1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtComentarios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblComentarios1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlGrupo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtPesos1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPesos1))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(pnlGrupo1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnUp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDown1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddEjercicio1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveGroup1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteGroup1)
                        .addContainerGap())))
        );

        tbpTraining.addTab("Grupo 1", pnlGrupo1);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jMenu1.setText("Nuevo");

        mnuNuevo.setText("Nuevo");
        mnuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(mnuNuevo);

        jMenuBar1.add(jMenu1);

        mnuFormato.setText("Formato");
        jMenuBar1.add(mnuFormato);

        JMenu2.setText("Guardar");
        JMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenu2ActionPerformed(evt);
            }
        });

        mnuSave.setText("Guardar Todo");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        JMenu2.add(mnuSave);

        jMenuBar1.add(JMenu2);

        jMenu2.setText("Cargar");

        mnuCargarGym.setText("Cargar de Gimnasio");
        mnuCargarGym.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCargarGymActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCargarGym);

        mnuCargarCross.setText("Cargar de Cross");
        mnuCargarCross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCargarCrossActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCargarCross);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(504, 504, 504)
                .addComponent(lblName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnAceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(spnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpTraining))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblName)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAceptar))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel2))
                            .addComponent(spnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tbpTraining, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        setSize(new java.awt.Dimension(1132, 762));
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

        Ejercicio ejercicioSelected = null;

        for (Ejercicio e : ejerciciosList) {
            if (e.getName().equals(cmbEjercicios1.getSelectedItem().toString())) {
                ejercicioSelected = e;
            }
        }
        if (ejercicioSelected != null) {
            ejerciciosInBoxs.add(new EjerciciosInBox(/*ejercicio_id*/ejercicioSelected.getId(),
                    /*tepeticiones*/ txtReps1.getText(),
                    /*box_id*/ tbpTraining.getSelectedIndex(),
                    /*comentario*/ txtComentarios1.getText(),txtPesos1.getText(), /*index in box*/ 0));

            String linea = "";

            linea += ejercicioSelected.getName() + " " + txtReps1.getText() + " " + txtComentarios1.getText();

            modelEjercicios.addRow(new Object[]{linea, "", "", "", "", "", ""});
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No existe el ejercicio", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddEjercicio1ActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        ACEPTAR(true);


    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnSaveGroup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveGroup1ActionPerformed
//        int idStage = 1;
//        boolean flag = true;
//        for (int i = 1; i <= stagesList.size(); i++) {
//            if (stagesList.get(i - 1).getId() != i) {
//                idStage = i;
//                flag = false;
//            }
//        }
//        if (flag) {
//            idStage = stagesList.size() + 1;
//        }
//        idStageActual = idStage;
//        stagesList.add(new Stage(idStage, txtNameStages1.getText()));

//        String ronda = txtRondas1.getText();
//        List<Box> boxAux = boxes;
//        for (Box b : boxes) {
//            if (b.getId() == tbpTraining.getSelectedIndex()) {
//                boxAux.remove(b);
//
//            }
//
//        }
//        boxes = boxAux;
        Box boxReplace1 = null;
        for (Box b : boxes) {
            if (b.getId() == tbpTraining.getSelectedIndex()) {
                boxReplace1 = b;
            }
        }
        if (boxReplace1 != null) {
            boxes.remove(boxReplace1);
        }
        boxes.add(new Box(tbpTraining.getSelectedIndex(), tbpTraining.getSelectedIndex(),
                txtRondas1.getText(), 0));
//                }
        if (txtNameStages1.getText().equals("") && txtRondas1.getText().equals("") && modelEjercicios.getRowCount() == 0) {
            ACEPTAR(false);
        } else {
            ACEPTAR(true);
        }

    }//GEN-LAST:event_btnSaveGroup1ActionPerformed

    private void JMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenu2ActionPerformed

    }//GEN-LAST:event_JMenu2ActionPerformed

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        
        Loading lod = new Loading();

        SaveAll save = new SaveAll();
        lod.start();
        save.start();


    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNuevoActionPerformed
        newPlann = true;
        clearTable(model);

        stagesList = new ArrayList<>();
        stagesListSave = new ArrayList<>();
        boxListSave = new ArrayList<>();
        boxes = new ArrayList<>();
        ejerciciosInBoxs = new ArrayList<>();
        ejerciciosInBoxsSave = new ArrayList<>();
        usersStageList = new ArrayList<>();
        usersStageListSave = new ArrayList<>();

        List<Parameter> list = getParams(formatSelected.getId());
        paramsId = new int[list.size()];
        paramsActuales = new int[15];
        for (Parameter p : list) {
            model.addRow(new Object[]{p.getParameter(), "", "", "", "", "", ""});
            paramsId[list.indexOf(p)] = p.getId();
            paramsActuales[p.getId()] = list.indexOf(p);
            tblTraining.setModel(model);
        }
        crearUsersStage(6, list.size());
        spnEjercicios.setValue(1);
    }//GEN-LAST:event_mnuNuevoActionPerformed

    private void txtNameStages1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameStages1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameStages1ActionPerformed

    private void btnUp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUp1ActionPerformed
        changePositionExersise(tblEjercicios1, true, modelEjercicios);
    }//GEN-LAST:event_btnUp1ActionPerformed

    private void btnDown1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDown1ActionPerformed
        changePositionExersise(tblEjercicios1, false, modelEjercicios);

    }//GEN-LAST:event_btnDown1ActionPerformed

    private void mnuCargarCrossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCargarCrossActionPerformed
       CargarEntrenamiento ce = new CargarEntrenamiento(true);
        ce.setVisible(true);
        ce.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
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
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            if (userToLoad != null) {
            clearTable(model);

            getTrainningToLoad(userToLoad);
            //formatUser=getFormatUser(userToLoad);
            printTraining();

            }
            }
        });

        
    }//GEN-LAST:event_mnuCargarCrossActionPerformed

    private void mnuCargarGymActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCargarGymActionPerformed
        CargarEntrenamiento ce = new CargarEntrenamiento(false);
        ce.setVisible(true);
        ce.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
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
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            if (userToLoad != null) {
            clearTable(model);

            getTrainningToLoad(userToLoad);

            printTraining();

            }
            }
        });

    }//GEN-LAST:event_mnuCargarGymActionPerformed

    private void btnDeleteGroup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteGroup1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteGroup1ActionPerformed

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

    public class Proceso1 extends Thread {

        Usuario user;

        public Proceso1(Usuario u) {
            user = u;
        }

        @Override
        public void run() {
            getBoxUser(user);
            getBoxs();
            formatUser = getFormatUser(usuario);
            
        }

    }

    public class Proceso2 extends Thread {

        Usuario user;

        public Proceso2(Usuario us) {
            user = us;
        }

        @Override
        public void run() {

            getSategeUser(user);
            getStages();
            
        }

    }

    public class Proceso3 extends Thread {

        Usuario user;

        public Proceso3(Usuario us) {
            user = us;
        }

        @Override
        public void run() {

            getUserStageUser(user);
            getExersisesInBoxUser(user);
            getFormats();
            
        }
    }
    
    public class Proceso4 extends Thread {

        Usuario user;

        public Proceso4(Usuario us) {
            user = us;
        }

        @Override
        public void run() {

            getUserStageUser(user);
            getExersisesInBoxUser(user);
            formatUser=getFormatUser(user);
            for (UserStage us:usersStageListByUserDB){
            us.setUser_id(usuario.getId());
            }
        }
    }

    public class Loading extends Thread {

        @Override
        public void run() {
            Progress p = new Progress();
            p.setVisible(true);
        }

    }

    public class SaveStage extends Thread {

        @Override
        public void run() {
            addStageDB();
        }

    }

    public class SaveUS extends Thread {

        @Override
        public void run() {
            addUSDB();
        }

    }

    public class SaveBox extends Thread {

        @Override
        public void run() {
            addBoxDB();
        }

    }

    public class SaveEB extends Thread {

        @Override
        public void run() {
            addEBDB();
        }

    }

    public class SaveAll extends Thread {

        @Override
        public void run() {
            saveTraining();
        }

    }

    public class LoadTraining extends Thread {

        @Override
        public void run() {
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMenu2;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAddEjercicio1;
    private javax.swing.JButton btnDeleteGroup1;
    private javax.swing.JButton btnDown1;
    private javax.swing.JButton btnSaveGroup1;
    private javax.swing.JButton btnUp1;
    private javax.swing.JCheckBox cbxSeparador1;
    private javax.swing.JComboBox<String> cmbEjercicios1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblComentarios1;
    private javax.swing.JLabel lblEjercicios1;
    private javax.swing.JLabel lblListEjercicios1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNameStage1;
    private javax.swing.JLabel lblPesos1;
    private javax.swing.JLabel lblReps1;
    private javax.swing.JLabel lblRondas1;
    private javax.swing.JMenuItem mnuCargarCross;
    private javax.swing.JMenuItem mnuCargarGym;
    private javax.swing.JMenu mnuFormato;
    private javax.swing.JMenuItem mnuNuevo;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JPanel pnlGrupo1;
    private javax.swing.JSpinner spnEjercicios;
    private javax.swing.JTable tblEjercicios1;
    private javax.swing.JTable tblTraining;
    private javax.swing.JTabbedPane tbpTraining;
    private javax.swing.JTextField txtComentarios1;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameStages1;
    private javax.swing.JTextArea txtPesos1;
    private javax.swing.JTextField txtReps1;
    private javax.swing.JTextArea txtRondas1;
    // End of variables declaration//GEN-END:variables
}
