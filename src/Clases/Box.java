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
    private int index_stage;
    private String rounds;
    private int stage_id;

    public Box(int id, int index_in_stage, String rondas, int stage_id) {
        this.id = id;
        this.index_stage = index_in_stage;
        this.rounds = rondas;
        this.stage_id = stage_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex_in_stage() {
        return index_stage;
    }

    public void setIndex_in_stage(int index_in_stage) {
        this.index_stage = index_in_stage;
    }

    public String getRondas() {
        return rounds;
    }

    public void setRondas(String rondas) {
        this.rounds = rondas;
    }

    public int getStage_id() {
        return stage_id;
    }

    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
    }
    
    
    
    
    
}
