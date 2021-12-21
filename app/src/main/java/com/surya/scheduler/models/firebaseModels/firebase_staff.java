package com.surya.scheduler.models.firebaseModels;

import java.util.List;

public class firebase_staff {
    /*Variables*/
    private String name;
    private String department;
    private boolean hasConstraints;
    private int workingHours;
    private List<String> schedules;

    /*Constructor*/
    public firebase_staff() {

    }

    public firebase_staff(String name, String department, boolean hasConstraints, int workingHours, List<String> schedules) {
        this.name = name;
        this.department = department;
        this.hasConstraints = hasConstraints;
        this.workingHours = workingHours;
        this.schedules = schedules;
    }

    /*Getters and Setter Methods*/
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

    public boolean isHasConstraints() {
        return hasConstraints;
    }

    public void setHasConstraints(boolean hasConstraints) {
        this.hasConstraints = hasConstraints;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public List<String> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<String> schedules) {
        this.schedules = schedules;
    }
}
