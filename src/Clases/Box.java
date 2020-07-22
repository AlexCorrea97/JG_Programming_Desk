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
public class Box {
    private int id;
    private int index_in_stage;
    private String rondas;
    private int stage_id;

    public Box(int id, int index_in_stage, String rondas, int stage_id) {
        this.id = id;
        this.index_in_stage = index_in_stage;
        this.rondas = rondas;
        this.stage_id = stage_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex_in_stage() {
        return index_in_stage;
    }

    public void setIndex_in_stage(int index_in_stage) {
        this.index_in_stage = index_in_stage;
    }

    public String getRondas() {
        return rondas;
    }

    public void setRondas(String rondas) {
        this.rondas = rondas;
    }

    public int getStage_id() {
        return stage_id;
    }

    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
    }
    
    
    
    
    
}
