/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Alejandro Correa
 */
public class Parameter {
    int id;
    String paramter;

    public Parameter(int id, String parameter) {
        this.id = id;
        this.paramter = parameter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParameter() {
        return paramter;
    }

    public void setParameter(String parameter) {
        this.paramter = parameter;
    }
    
    
}
