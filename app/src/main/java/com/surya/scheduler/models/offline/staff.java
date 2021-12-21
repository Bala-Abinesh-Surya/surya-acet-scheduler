package com.surya.scheduler.models.offline;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

public class staff {
    private String name;
    private String department;
    private boolean hasConstraints;
    private int workingHours;
    private Hashtable<String, String[]> schedule = new Hashtable<>();
    private Hashtable<String, String[]> subjectsSchedule = new Hashtable<>();

    public static ArrayList<staff> allStaffs = new ArrayList<>();

    /*Constructor*/
    public staff(){

    }

    public staff(String name, String department, boolean hasConstraints, int workingHours, Hashtable<String, String[]> schedule, Hashtable<String, String[]> subjectsSchedule) {
        this.name = name;
        this.department = department;
        this.hasConstraints = hasConstraints;
        this.workingHours = workingHours;
        this.schedule = schedule;
        this.subjectsSchedule = subjectsSchedule;
    }

    // getter and setter methods
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

    public Hashtable<String, String[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(Hashtable<String, String[]> schedule) {
        this.schedule = schedule;
    }

    public Hashtable<String, String[]> getSubjectsSchedule() {
        return subjectsSchedule;
    }

    public void setSubjectsSchedule(Hashtable<String, String[]> subjectsSchedule) {
        this.subjectsSchedule = subjectsSchedule;
    }

    public static ArrayList<staff> getAllStaffs() {
        return allStaffs;
    }

    public static void setAllStaffs(ArrayList<staff> allStaffs) {
        staff.allStaffs = allStaffs;
    }
}
