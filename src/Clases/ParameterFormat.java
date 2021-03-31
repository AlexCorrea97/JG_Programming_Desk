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
public class ParameterFormat {
    private int format_id;
    private int parameter_id;
    private int index_id;

    public ParameterFormat(int format_id, int parameter_id, int index_id) {
        this.format_id = format_id;
        this.parameter_id = parameter_id;
        this.index_id = index_id;
    }

    public int getFormat_id() {
        return format_id;
    }

    public void setFormat_id(int format_id) {
        this.format_id = format_id;
    }

    public int getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(int parameter_id) {
        this.parameter_id = parameter_id;
    }

    public int getIndex_id() {
        return index_id;
    }

    public void setIndex_id(int index_id) {
        this.index_id = index_id;
    }
    
}
