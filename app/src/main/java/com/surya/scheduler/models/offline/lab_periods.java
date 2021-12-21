package com.surya.scheduler.models.offline;

public class lab_periods {
    // class that defines each period of all the labs
    private String labName;
    private String isFree;
    private String className;
    private String staff1;
    private String staff2;
    private String day;
    private int periodNo;

    // Constructor
    public lab_periods(){

    }

    public lab_periods(String labName, String isFree, String className, String staff1, String staff2, String day, int periodNo) {
        this.labName = labName;
        this.isFree = isFree;
        this.className = className;
        this.staff1 = staff1;
        this.staff2 = staff2;
        this.day = day;
        this.periodNo = periodNo;
    }

    // getter and setter methods
    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String isFree() {
        return isFree;
    }

    public void setFree(String free) {
        isFree = free;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(int periodNo) {
        this.periodNo = periodNo;
    }
}
