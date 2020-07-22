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
   private int ejercicio_id;
   private int repeticiones;
   private int box_id;
   private String comentario;
   private int index_in_box;

    public EjerciciosInBox(int ejercicio_id, int repeticiones, int box_id, String comentario, int index_in_box) {
        this.ejercicio_id = ejercicio_id;
        this.repeticiones = repeticiones;
        this.box_id = box_id;
        this.comentario = comentario;
        this.index_in_box = index_in_box;
    }

    public int getEjercicio_id() {
        return ejercicio_id;
    }

    public void setEjercicio_id(int ejercicio_id) {
        this.ejercicio_id = ejercicio_id;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIndex_in_box() {
        return index_in_box;
    }

    public void setIndex_in_box(int index_in_box) {
        this.index_in_box = index_in_box;
    }
   
}
