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
public class Stage {
    private int id;
    private String stage;

    public Stage(int id, String name) {
        this.id = id;
        this.stage = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return stage;
    }

    public void setName(String name) {
        this.stage = name;
    }
    
}
