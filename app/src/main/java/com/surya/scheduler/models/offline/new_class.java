package com.surya.scheduler.models.offline;

import java.util.Hashtable;

public class new_class {
    // this class must be a singleton class
    // class used while adding a new class to the allClasses arraylist
    private static new_class instance;

    // variables
    private String name;
    private String department;
    private int year;
    private int numberOfStudents;
    private String[] teachers;
    private Hashtable<String, String[]> schedule;

    // Constructor
    public new_class() {

    }

    public static new_class getInstance(){
        if(instance == null){
            // creating an instance of new_class
            instance = new new_class();
        }

        return instance;
    }

    // getters and setters methods
    public static void setInstance(new_class instance) {
        new_class.instance = instance;
    }

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
}
