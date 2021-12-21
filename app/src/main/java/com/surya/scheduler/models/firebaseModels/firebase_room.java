package com.surya.scheduler.models.firebaseModels;

import java.util.List;

public class firebase_room {
    /*Variables*/
    private String name;
    private String department;
    private boolean isLab;
    private List<String> schedules;

    /*Constructor*/
    public firebase_room(){

    }

    public firebase_room(String name, String department, boolean isLab, List<String> schedules) {
        this.name = name;
        this.department = department;
        this.isLab = isLab;
        this.schedules = schedules;
    }

    /*Getter and Setter Methods*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean lab) {
        isLab = lab;
    }

    public List<String> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<String> schedules) {
        this.schedules = schedules;
    }
}
