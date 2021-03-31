/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTTP;

import Clases.Box;
import Clases.Ejercicio;
import Clases.EjerciciosInBox;
import Clases.Stage;
import Clases.URLs;
import Clases.UserStage;
import Clases.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Alejandro Correa
 */
public class Http {

    private HttpClient httpClient;
    private HttpGet get;
    private HttpPost post;

    private HttpResponse httpResponse;
    private String resource;

    public Http() {
        this.httpClient = HttpClients.createDefault();
        this.get = null;
        this.resource = null;
        this.post = null;
    }

    public String PostUsuario(Usuario user) {

        //id=3&first_name=Jaime&last_name=Gorocica&user_name=manuelito
        //&password=pass&planning_start=2020-07-25&state=1&user_type=1&training_type=1&connected=1
        this.get = new HttpGet(URLs.InsertUser(user));

        try {
            this.httpResponse = this.httpClient.execute(this.get);
            this.resource = EntityUtils.toString(this.httpResponse.getEntity());
            //this.resource=EntityUtils.toString(this.httpResponse.getEntity());

        } catch (Exception e) {
        }

        return resource;

    }

    public String GET(String url) {

        this.get = new HttpGet(url);
        try {
            this.httpResponse = this.httpClient.execute(this.get);
            this.resource = EntityUtils.toString(this.httpResponse.getEntity());
            //this.resource=EntityUtils.toString(this.httpResponse.getEntity());

        } catch (Exception e) {
        }

        return resource;
    }

    public String PostEjercicio(Ejercicio ejercicio) {

        //id=3&first_name=Jaime&last_name=Gorocica&user_name=manuelito
        //&password=pass&planning_start=2020-07-25&state=1&user_type=1&training_type=1&connected=1
        String url = URLs.InsertEjercicio(ejercicio);

        this.get = new HttpGet(url);

        try {
            this.httpResponse = this.httpClient.execute(this.get);
            this.resource = EntityUtils.toString(this.httpResponse.getEntity());
            //this.resource=EntityUtils.toString(this.httpResponse.getEntity());

        } catch (Exception e) {
        }

        return resource;

    }

    public String PostStages(List<Stage> stages) {
        String str = "";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://idaxmx.com/JG_Programming/interface.php?funcion=InsertStage");
        int val = stages.size() * 2;
        List<NameValuePair> params = new ArrayList<NameValuePair>(val + 1);

        params.add(new BasicNameValuePair("num_Stage", String.valueOf(stages.size())));

        for (int i = 0; i < stages.size(); i++) {
            params.add(new BasicNameValuePair("id_Stage" + i, String.valueOf(stages.get(i).getId())));
            params.add(new BasicNameValuePair("stage" + i, stages.get(i).getName()));
        }

//Execute and get the response.
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            str = entity.getContent().toString();
            if (entity != null) {
                this.resource = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR", "MAL", JOptionPane.ABORT);
        }
        return this.resource;
    }

    public String PostBoxs(List<Box> boxs) {
        String str = "";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://idaxmx.com/JG_Programming/interface.php?funcion=InsertBox");
        int val = boxs.size() * 4;
        List<NameValuePair> params = new ArrayList<NameValuePair>(val + 1);

        params.add(new BasicNameValuePair("num_Box", String.valueOf(boxs.size())));

        for (int i = 0; i < boxs.size(); i++) {
            params.add(new BasicNameValuePair("id_Box" + i, String.valueOf(boxs.get(i).getId())));
            params.add(new BasicNameValuePair("index_stage" + i, String.valueOf(boxs.get(i).getIndex_in_stage())));
            params.add(new BasicNameValuePair("rounds" + i, boxs.get(i).getRondas()));
            params.add(new BasicNameValuePair("stage_id" + i, String.valueOf(boxs.get(i).getStage_id())));
        }

//Execute and get the response.
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            str = entity.getContent().toString();
            if (entity != null) {
                this.resource = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR", "MAL", JOptionPane.ABORT);
        }
        return this.resource;
    }

    public String PostUserStages(List<UserStage> userStages) {
        String str = "";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://idaxmx.com/JG_Programming/interface.php?funcion=InsertUserStage");
        int val = userStages.size() * 5;
        List<NameValuePair> params = new ArrayList<NameValuePair>(val + 1);

        params.add(new BasicNameValuePair("num_US", String.valueOf(userStages.size())));

        for (int i = 0; i < userStages.size(); i++) {
            params.add(new BasicNameValuePair("user_id" + i, String.valueOf(userStages.get(i).getUser_id())));
            params.add(new BasicNameValuePair("stage_idUS" + i, String.valueOf(userStages.get(i).getStage_id())));
            params.add(new BasicNameValuePair("index_parameter" + i, String.valueOf(userStages.get(i).getIndex_in_parameter())));
            params.add(new BasicNameValuePair("parameter_id" + i, String.valueOf(userStages.get(i).getParameter_id())));
            params.add(new BasicNameValuePair("week_day" + i, String.valueOf(userStages.get(i).getWeek_day())));
           
        }

//Execute and get the response.
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            str = entity.getContent().toString();
            if (entity != null) {
                this.resource = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR", "MAL", JOptionPane.ABORT);
        }
        return this.resource;
    }

    public String PostEinB(List<EjerciciosInBox> ejInBox) {
        String str = "";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://idaxmx.com/JG_Programming/interface.php?funcion=InsertExericisesInBox");
        int val = ejInBox.size() * 5;
        List<NameValuePair> params = new ArrayList<NameValuePair>(val + 1);

        params.add(new BasicNameValuePair("num_EB", String.valueOf(ejInBox.size())));

        for (int i = 0; i < ejInBox.size(); i++) {
            params.add(new BasicNameValuePair("exercise_id" + i, String.valueOf(ejInBox.get(i).getEjercicio_id())));
            params.add(new BasicNameValuePair("reps" + i, ejInBox.get(i).getRepeticiones()));
            params.add(new BasicNameValuePair("box_id" + i, String.valueOf(ejInBox.get(i).getBox_id())));
            params.add(new BasicNameValuePair("comment" + i, ejInBox.get(i).getComentario()));
            params.add(new BasicNameValuePair("index_box" + i, String.valueOf(ejInBox.get(i).getIndex_in_box())));
            params.add(new BasicNameValuePair("pesos" + i, String.valueOf(ejInBox.get(i).getPesos())));
            
        }

//Execute and get the response.
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            str = entity.getContent().toString();
            if (entity != null) {
                this.resource = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR", "MAL", JOptionPane.ABORT);
        }
        return this.resource;
    }
    
    public String PostTrainning(List<Stage> stages,List<Box> boxs,List<UserStage> userStages,List<EjerciciosInBox> ejInBox) {
        String str = "";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://idaxmx.com/JG_Programming/interface.php?funcion=InsertTrainning");
        int val = ejInBox.size() * 5;
        val=val+(stages.size()*2);
        val=val+(boxs.size()*4);
        val=val+(userStages.size()*5);
        List<NameValuePair> params = new ArrayList<NameValuePair>(val + 1);
        
        params.add(new BasicNameValuePair("num_Stage", String.valueOf(stages.size())));
        for (int i = 0; i < stages.size(); i++) {
            params.add(new BasicNameValuePair("id_Stage" + i, String.valueOf(stages.get(i).getId())));
            params.add(new BasicNameValuePair("stage" + i, stages.get(i).getName()));
        }
        
        params.add(new BasicNameValuePair("num_Box", String.valueOf(boxs.size())));
        for (int i = 0; i < boxs.size(); i++) {
            params.add(new BasicNameValuePair("id_Box" + i, String.valueOf(boxs.get(i).getId())));
            params.add(new BasicNameValuePair("index_stage" + i, String.valueOf(boxs.get(i).getIndex_in_stage())));
            params.add(new BasicNameValuePair("rounds" + i, boxs.get(i).getRondas()));
            params.add(new BasicNameValuePair("stage_id" + i, String.valueOf(boxs.get(i).getStage_id())));
        }
        
        params.add(new BasicNameValuePair("num_US", String.valueOf(userStages.size())));
        for (int i = 0; i < userStages.size(); i++) {
            params.add(new BasicNameValuePair("user_idUS" + i, String.valueOf(userStages.get(i).getUser_id())));
            params.add(new BasicNameValuePair("stage_idUS" + i, String.valueOf(userStages.get(i).getStage_id())));
            params.add(new BasicNameValuePair("index_parameter" + i, String.valueOf(userStages.get(i).getIndex_in_parameter())));
            params.add(new BasicNameValuePair("parameter_id" + i, String.valueOf(userStages.get(i).getParameter_id())));
            params.add(new BasicNameValuePair("week_day" + i, String.valueOf(userStages.get(i).getWeek_day())));
           
        }

        params.add(new BasicNameValuePair("num_EB", String.valueOf(ejInBox.size())));
        for (int i = 0; i < ejInBox.size(); i++) {
            params.add(new BasicNameValuePair("exercise_id" + i, String.valueOf(ejInBox.get(i).getEjercicio_id())));
            params.add(new BasicNameValuePair("reps" + i, ejInBox.get(i).getRepeticiones()));
            params.add(new BasicNameValuePair("box_id" + i, String.valueOf(ejInBox.get(i).getBox_id())));
            params.add(new BasicNameValuePair("comment" + i, ejInBox.get(i).getComentario()));
            params.add(new BasicNameValuePair("index_box" + i, String.valueOf(ejInBox.get(i).getIndex_in_box())));
            params.add(new BasicNameValuePair("pesos" + i, String.valueOf(ejInBox.get(i).getPesos())));
            
        }

//Execute and get the response.
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            str = entity.getContent().toString();
            if (entity != null) {
                this.resource = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR", "MAL", JOptionPane.ABORT);
        }
        return this.resource;
    }

}
