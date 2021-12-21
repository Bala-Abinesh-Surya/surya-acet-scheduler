package com.surya.scheduler.models.details;

import java.util.ArrayList;

public class subject_details {
    // this class must be a singleton class
    private static subject_details subject_details;

    public static subject_details getInstance(){
        if(subject_details == null){
            subject_details = new subject_details();
        }
        return subject_details;
    }

    // subject details
    private String subjectName;
    private String tag;
    private boolean isLab;
    private String staff1;
    private String staff2;
    private String labName;
    private String nature;

    public static ArrayList<subject_details> subject_details_list = new ArrayList<>();

    // Constructor
    public subject_details(){

    }

    // getter and setter methods
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean lab) {
        isLab = lab;
    }

    public String getStaff1() {
        return staff1;
    }

    public void setStaff1(String staff1) {
        this.staff1 = staff1;
    }

    public String getStaff2() {
        return staff2;
    }

    public void setStaff2(String staff2) {
        this.staff2 = staff2;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public ArrayList<com.surya.scheduler.models.details.subject_details> getSubject_details_list() {
        return subject_details_list;
    }

    public void setSubject_details_list(ArrayList<com.surya.scheduler.models.details.subject_details> subject_details_list) {
        com.surya.scheduler.models.details.subject_details.subject_details_list = subject_details_list;
    }
}
