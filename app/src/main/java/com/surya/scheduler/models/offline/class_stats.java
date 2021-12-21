package com.surya.scheduler.models.offline;

public class class_stats {
    /*variables*/
    private String subject;
    private String staff;
    private int numberOfSessions;

    /*Constructor*/
    public class_stats(String subject, String staff, int numberOfSessions) {
        this.subject = subject;
        this.staff = staff;
        this.numberOfSessions = numberOfSessions;
    }

    public class_stats(){

    }

    /*Getter and Setter Methods*/
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public int getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(int numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }
}
