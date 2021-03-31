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
public class UserStage {
    private int user_id;
    private int stage_id;
    private int index_parameter;
    private int parameter_id;
    private int week_day;

    public UserStage(int user_id, int stage_id, int index_in_parameter, int parameter_id, int week_day) {
        this.user_id = user_id;
        this.stage_id = stage_id;
        this.index_parameter = index_in_parameter;
        this.parameter_id = parameter_id;
        this.week_day = week_day;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStage_id() {
        return stage_id;
    }

    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
    }

    public int getIndex_in_parameter() {
        return index_parameter;
    }

    public void setIndex_in_parameter(int index_in_parameter) {
        this.index_parameter = index_in_parameter;
    }

    public int getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(int parameter_id) {
        this.parameter_id = parameter_id;
    }

    public int getWeek_day() {
        return week_day;
    }

    public void setWeek_day(int week_day) {
        this.week_day = week_day;
    }
    
}
