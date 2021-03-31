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
public class Ejercicio {
    int id;
    String exercises;
    String url;

    public Ejercicio(int id, String name, String Link) {
        this.id = id;
        this.exercises = name;
        this.url = Link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return exercises;
    }

    public void setName(String name) {
        this.exercises = name;
    }

    public String getLink() {
        return url;
    }

    public void setLink(String Link) {
        this.url = Link;
    }
    public String toString(){
    return this.getName();
    }
    
    
}
