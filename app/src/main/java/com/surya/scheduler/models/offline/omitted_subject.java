package com.surya.scheduler.models.offline;

import static com.surya.scheduler.constants.status.GENERATE_STATUS;
import static com.surya.scheduler.constants.status.OMITTED_INSTANCE_CREATED;

import android.util.Log;

import java.util.ArrayList;

public class omitted_subject {
    // class which acts as a blue print for the subjects which were omitted during normal scheduling
    // this class must be a singleton class
    private static omitted_subject omitted_subject;

    // method to return the instance of this class
    public static omitted_subject getInstance(){
        if(omitted_subject == null){
            omitted_subject = new omitted_subject();
            Log.d(GENERATE_STATUS, OMITTED_INSTANCE_CREATED);
        }

        return omitted_subject;
    }

    private String className;
    private String subjectName;
    private String subjectShortForm;
    private String staff1;
    private String staff2;

    public static ArrayList<omitted_subject> omitted_subjects = new ArrayList<>();

    // Constructor
    public omitted_subject() {

    }

    public omitted_subject(String className, String subjectName, String subjectShortForm, String staff1, String staff2) {
        this.className = className;
        this.subjectName = subjectName;
        this.subjectShortForm = subjectShortForm;
        this.staff1 = staff1;
        this.staff2 = staff2;
    }

    // getter and setter methods
    public static com.surya.scheduler.models.offline.omitted_subject getOmitted_subject() {
        return omitted_subject;
    }

    public static void setOmitted_subject(com.surya.scheduler.models.offline.omitted_subject omitted_subject) {
        com.surya.scheduler.models.offline.omitted_subject.omitted_subject = omitted_subject;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectShortForm() {
        return subjectShortForm;
    }

    public void setSubjectShortForm(String subjectShortForm) {
        this.subjectShortForm = subjectShortForm;
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

    public static ArrayList<com.surya.scheduler.models.offline.omitted_subject> getOmitted_subjects() {
        return omitted_subjects;
    }

    public static void setOmitted_subjects(ArrayList<com.surya.scheduler.models.offline.omitted_subject> omitted_subjects) {
        com.surya.scheduler.models.offline.omitted_subject.omitted_subjects = omitted_subjects;
    }
}
