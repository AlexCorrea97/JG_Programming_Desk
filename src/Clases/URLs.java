/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro Correa
 */
public class URLs {
    public static String GetStages="https://idaxmx.com/JG_Programming/interface.php?funcion=GetStages";
    public static String GetUserCross="https://idaxmx.com/JG_Programming/interface.php?funcion=getCrossUser";
    public static String GetUserGym="https://idaxmx.com/JG_Programming/interface.php?funcion=getGymUser";
    public static String GetBoxs="https://idaxmx.com/JG_Programming/interface.php?funcion=GetBoxs";
    public static String GetUsers = "https://idaxmx.com/JG_Programming/interface.php?funcion=obtenerTodosLosUsuarios";
    public static String GetEjercicios= "https://idaxmx.com/JG_Programming/interface.php?funcion=ObtenerEjercicios";
    public static String GetFormats="https://idaxmx.com/JG_Programming/interface.php?funcion=GetFormats";
    public static String GetParams="https://idaxmx.com/JG_Programming/interface.php?funcion=GetParams";
    
    public static String getStageByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=GetStagesByUser&user_id="+id;
    return url;
    
    }
    
    public static String updateLinks(String warmup,String recovery){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateLinks&warmup="+warmup+"&recovery="+recovery;
    return url;
    
    }
    
    public static String getBoxByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=GetBoxsByUser&user_id="+id;
    return url;
    
    }
    public static String getExersisesInBoxByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=GetExericisesInBoxByUser&user_id="+id;
    return url;
    
    }
    public static String getUserStageByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=GetUserStageByUser&user_id="+id;
    return url;
    
    }
    
    public static String deleteStageByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteStagesByUser&user_id="+id;
    return url;
    
    }
    public static String deleteBoxByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteBoxsByUser&user_id="+id;
    return url;
    
    }
    public static String deleteExersisesInBoxByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteExericisesInBoxByUser&user_id="+id;
    return url;
    
    }
    public static String deleteUserStageByUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteUserStageByUser&user_id="+id;
    return url;
    
    }
    
    public static String getUserFormatByUserId(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=GetUserFormatByUserId&user_id="+id;
    return url;
    
    }
    
    public static String insertUserFormat(int user_id,int format_id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=insertUserFormat&user_id="+user_id
                +"&format_id="+format_id;
    //1&format_id=2
    return url;
    }
    
    public static String InsertEjercicio(Ejercicio ejercicio){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=insertarEjercicios&";
        try {
            
            String link="";
            if(ejercicio.getLink()!=null){
                ejercicio.getLink();
            }
            String name= URLEncoder.encode(ejercicio.getName(), StandardCharsets.UTF_8.toString());
            //id=0&name=hol&link=link
            url+="id="+ejercicio.getId()+"&";
            url+="name="+name+"&";
            url+="link="+link;
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    public static String deleteExercise(int id){
    
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteExercise&id="+id;
    return url;
    }
    public static String updateExercise(Ejercicio ej){
        try {
            String name=URLEncoder.encode(ej.getName(), StandardCharsets.UTF_8.toString());
            String link=URLEncoder.encode(ej.getLink(), StandardCharsets.UTF_8.toString());
            String url="https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateExercise&id="+ej.getId()+"&name="+name+"&url="+link;
            return url;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String deleteUser(int id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=deleteUser&user_id="+id;
    return url;
    }
    public static String UpdateStateByUser(Usuario us){
        int id= us.getId();
        int state=0;
        if(us.isState()==1){
        state=0;
        }else{
            state=1;
        }
        
        
    String url= "https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateStateByUserId&user_id="+id+"&value="+state;
        
        
    return url;
    }
    
    public static String UpdateUserById(Usuario user){
        
        try {
            //https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateUserById&user_id=12&first_name=Celia&last_name=Correa&user_name=celia&password=celia&planning_start=2020-08-16&training_type=1
            
            String fecha;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getPlanning_start());
            fecha = String.valueOf(calendar.get(Calendar.YEAR)) + "-";
            fecha += String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-";
            fecha += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String first_name=URLEncoder.encode(user.getFirst_name(), StandardCharsets.UTF_8.toString());
            String last_name=URLEncoder.encode(user.getLast_name(), StandardCharsets.UTF_8.toString());
            String user_name=URLEncoder.encode(user.getUser_name(), StandardCharsets.UTF_8.toString());
            String pass=URLEncoder.encode(user.getPassword(), StandardCharsets.UTF_8.toString());
            String url="https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateUserById&user_id="+user.getId();
            url+="&first_name="+first_name+"&last_name="+last_name+"&user_name="+user_name+"&password="+pass+"&planning_start="+
                    fecha+"&training_type="+user.getTraining_type();
            
            
            
            return url;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String InsertUser(Usuario user) {
        String url;
        try {
            url = "http://idaxmx.com/JG_Programming/interface.php?funcion=insertarUsuarios&";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getPlanning_start());
            String state = "0";
            String con = "0";
            String fecha;
            if (user.isConnected()) {
                con = "1";
            }
            if (user.isState()==1) {
                state = "1";
            }
            
            fecha = String.valueOf(calendar.get(Calendar.YEAR)) + "-";
            fecha += String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-";
            fecha += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            
            String first_name=URLEncoder.encode(user.getFirst_name(), StandardCharsets.UTF_8.toString());
            String last_name=URLEncoder.encode(user.getLast_name(), StandardCharsets.UTF_8.toString());
            String user_name=URLEncoder.encode(user.getUser_name(), StandardCharsets.UTF_8.toString());
            String pass=URLEncoder.encode(user.getPassword(), StandardCharsets.UTF_8.toString());
            
            url += "id=" + user.getId() + "&";
            url += "first_name=" + first_name + "&";
            url += "last_name=" + last_name + "&";
            url += "user_name=" + user_name + "&";
            url += "password=" + pass + "&";
            url += "planning_start=" + fecha + "&";
            url += "state=" + state + "&";
            url += "user_type=" + user.getUser_type() + "&";
            url += "training_type=" + user.getTraining_type() + "&";
            url += "connected=" + con;
            return url;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String GetParametersByFormat(int format_id) {
        String url = "https://idaxmx.com/JG_Programming/interface.php?funcion=ObtenerParametrosByFormat&format_id=" + format_id;
        return url;
    }
    
    public static String insertStage(List<Stage> stages){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=InsertStage&num="+stages.size();
    //id="+stage.getId()+ "&stage="+URLEncoder.encode(stage.getName(), StandardCharsets.UTF_8);
    for(int i=0;i<stages.size();i++){
        try {
            url=url+"&id"+i+"="+URLEncoder.encode(stages.get(i).getId()+"", StandardCharsets.UTF_8.toString())+"&stage"+i+"="+URLEncoder.encode(stages.get(i).getName(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //1&stage=stagePrueba
        return url;
    }
    public static String insertBox(Box box){
    String url="";
        try {
            url = "https://idaxmx.com/JG_Programming/interface.php?funcion=InsertBox&id="+box.getId()+
                    "&index_stage="+box.getIndex_in_stage()+"&rounds="+URLEncoder.encode(box.getRondas(), StandardCharsets.UTF_8.toString())
                    +"&stage_id="
                    +box.getStage_id();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
    //1&index_stage=0&rounds=Rondas\\r\\nRondas2&stage_id=0
    //1&stage=stagePrueba
        return url;
    }
    public static String insertExersisesBox(EjerciciosInBox eb){
    String url="";
        try {
            url = "https://idaxmx.com/JG_Programming/interface.php?funcion=InsertExericisesInBox&exercise_id="+
                    eb.getEjercicio_id()+"&reps="+URLEncoder.encode(eb.getRepeticiones(), StandardCharsets.UTF_8.toString())+"&box_id="+
                    eb.getBox_id()+"&comment="+URLEncoder.encode(eb.getComentario(), StandardCharsets.UTF_8.toString())+"&index_box="+eb.getIndex_in_box();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLs.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            //0&reps=reps&box_id=0&comment=comment&index_box=0
        return url;
    }
    public static String insertUserStage(UserStage us){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=InsertUserStage&user_id="+
            us.getUser_id()+"&stage_id="+us.getStage_id()+"&index_parameter="+us.getIndex_in_parameter()+
            "&parameter_id="+us.getParameter_id()+
            "&week_day="+us.getWeek_day();
    //0&stage_id=0&index_parameter=0&parameter_id=3&week_day=4
        return url;
    }
    
    public static String UpdateUserFormat(int id,int format_id){
    String url="https://idaxmx.com/JG_Programming/interface.php?funcion=UpdateUserFormat&user_id="+id+
                "&format_id="+format_id;
    
    return url;    
    }
    
}
