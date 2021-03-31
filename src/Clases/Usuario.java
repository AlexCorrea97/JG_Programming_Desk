/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;

/**
 *
 * @author Alejandro Correa
 */
public class Usuario {
    private int id;
    private String first_name;
    private String last_name;
    private String user_name;
    private String password;
    private Date planning_start;
    private int state;
    private int user_type;
    private int training_type;
    private boolean connected;
    public String photo;

    public Usuario(int id, String first_name, String last_name, String user_name, String password, Date planning_start, int state, int user_type, int training_type, boolean connected, String photo) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = password;
        this.planning_start = planning_start;
        this.state = state;
        this.user_type = user_type;
        this.training_type = training_type;
        this.connected = connected;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPlanning_start() {
        return planning_start;
    }

    public void setPlanning_start(Date planning_start) {
        this.planning_start = planning_start;
    }

    public int isState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getTraining_type() {
        return training_type;
    }

    public void setTraining_type(int training_type) {
        this.training_type = training_type;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    
    
    
    
    
}
