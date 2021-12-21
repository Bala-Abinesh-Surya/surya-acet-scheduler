package com.surya.scheduler.models.offline;

import java.util.ArrayList;
import java.util.Hashtable;

public class room {
    private String name;
    private String department;
    private boolean isLab;
    private Hashtable<String, String[]> schedule = new Hashtable<>();

    public static ArrayList<room> allRooms = new ArrayList<>();

    /*Constructor*/
    public room(){

    }

    public room(String name, String department, boolean isLab, Hashtable<String, String[]> schedule) {
        this.name = name;
        this.department = department;
        this.isLab = isLab;
        this.schedule = schedule;
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

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean lab) {
        isLab = lab;
    }

    public Hashtable<String, String[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(Hashtable<String, String[]> schedule) {
        this.schedule = schedule;
    }

    public static ArrayList<room> getAllRooms() {
        return allRooms;
    }

    public static void setAllRooms(ArrayList<room> allRooms) {
        room.allRooms = allRooms;
    }
}

