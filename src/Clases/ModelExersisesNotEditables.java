/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejandro Correa
 */
public class ModelExersisesNotEditables extends DefaultTableModel{
    public boolean isCellEditable(int row, int col){
        if(col==0){
            return false;
        }
        else{
            return true;
        }
        //return true;
    
    }
    
}
