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
public class Ejercicios {
    private int id;
    private String name;
    private String Link;

    public Ejercicios(int id, String name, String Link) {
        this.id = id;
        this.name = name;
        this.Link = Link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }
    
    
}
