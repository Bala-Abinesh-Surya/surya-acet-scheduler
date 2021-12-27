package com.surya.scheduler.models.firebaseModels;

import java.util.List;

public class firebase_class {
    /*variables*/
    private String className;
    private String department;
    private int year;
    private int numberOfStudents;
    private List<String> staffs;
    private List<String> schedules;
    private List<String> shortForm;

    /*Constructor*/
    public firebase_class(String className, String department, int year, int numberOfStudents, List<String> staffs, List<String> schedules, List<String> shortForm) {
        this.className = className;
        this.department = department;
        this.year = year;
        this.numberOfStudents = numberOfStudents;
        this.staffs = staffs;
        this.schedules = schedules;
        this.shortForm = shortForm;
    }

    /*Empty constructor*/
    public firebase_class() {

    }

    /*Getter and Setter Methods*/
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public List<String> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<String> staffs) {
        this.staffs = staffs;
    }

    public List<String> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<String> schedules) {
        this.schedules = schedules;
    }

    public List<String> getShortForm() {
        return shortForm;
    }

    public void setShortForm(List<String> shortForm) {
        this.shortForm = shortForm;
    }
}
