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
public class EjerciciosInBox {
   private int exercise_id;
   private String reps;
   private int box_id;
   private String comment;
   private String pesos;
   private int index_box;

    public EjerciciosInBox(int ejercicio_id, String repeticiones, int box_id, String comentario,String pesos, int index_in_box) {
        this.exercise_id = ejercicio_id;
        this.reps = repeticiones;
        this.box_id = box_id;
        this.comment = comentario;
        this.index_box = index_in_box;
        this.pesos=pesos;
    }

    public String getPesos() {
        return pesos;
    }

    public void setPesos(String pesos) {
        this.pesos = pesos;
    }
    
    public int getEjercicio_id() {
        return exercise_id;
    }

    public void setEjercicio_id(int ejercicio_id) {
        this.exercise_id = ejercicio_id;
    }

    public String getRepeticiones() {
        return reps;
    }

    public void setRepeticiones(String repeticiones) {
        this.reps = repeticiones;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public String getComentario() {
        return comment;
    }

    public void setComentario(String comentario) {
        this.comment = comentario;
    }

    public int getIndex_in_box() {
        return index_box;
    }

    public void setIndex_in_box(int index_in_box) {
        this.index_box = index_in_box;
    }
   
}
