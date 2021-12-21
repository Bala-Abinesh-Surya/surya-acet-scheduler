package com.surya.scheduler.models.offline;

import java.util.ArrayList;
import java.util.Hashtable;

public class Class {
    // variables
    private String name;
    private String department;
    private int year;
    private int numberOfStudents;
    private String[] teachers;
    private Hashtable<String, String[]> schedule;
    private Hashtable<String, String[]> shortFormSchedule;

    public static ArrayList<Class> allClasses = new ArrayList<>();

    /*Constructor*/
    public Class(){

    }

    public Class(String name, String department, int year, int numberOfStudents, String[] teachers, Hashtable<String, String[]> schedule, Hashtable<String, String[]> shortFormSchedule) {
        this.name = name;
        this.department = department;
        this.year = year;
        this.numberOfStudents = numberOfStudents;
        this.teachers = teachers;
        this.schedule = schedule;
        this.shortFormSchedule = shortFormSchedule;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String[] getTeachers() {
        return teachers;
    }

    public void setTeachers(String[] teachers) {
        this.teachers = teachers;
    }

    public Hashtable<String, String[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(Hashtable<String, String[]> schedule) {
        this.schedule = schedule;
    }

    public Hashtable<String, String[]> getShortFormSchedule() {
        return shortFormSchedule;
    }

    public void setShortFormSchedule(Hashtable<String, String[]> shortFormSchedule) {
        this.shortFormSchedule = shortFormSchedule;
    }

    public static ArrayList<Class> getAllClasses() {
        return allClasses;
    }

    public static void setAllClasses(ArrayList<Class> allClasses) {
        Class.allClasses = allClasses;
    }
}
