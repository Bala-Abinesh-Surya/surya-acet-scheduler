package com.surya.scheduler.models.offline;

import static com.surya.scheduler.constants.data.DUMMY;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.PERIODS;
import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_PER_DAY;
import static com.surya.scheduler.constants.status.DUMMY_CLASS_ADDED;
import static com.surya.scheduler.constants.status.DUMMY_STAFF_ADDED;
import static com.surya.scheduler.constants.status.GENERATE_STATUS;
import static com.surya.scheduler.constants.status.PAIRED_INSTANCE_CREATED;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class paired {

    public static paired instance;

    public static Hashtable<String, String> pairedLabs = new Hashtable<>();
    public static Hashtable<String, String> pairedClasses = new Hashtable<>();
    public static Hashtable<String, String[]> pairedLabsStaffs = new Hashtable<>();
    public static Hashtable<String, String> pairedSubjects = new Hashtable<>();
    public static Hashtable<String, Integer> pairedSubjectsCount = new Hashtable<>();
    public static Hashtable<String, Integer> hashedSubjects = new Hashtable<>();
    public static Hashtable<String, Integer> laterDoIt = new Hashtable<>();
    public static ArrayList<String> hashedSubjectsArray = new ArrayList<>();
    public static ArrayList<Integer> hashedSubjectsInteger = new ArrayList<>();

    public static Hashtable<String, String> subjectRepeatedForClass = new Hashtable<>();

    /*Constructor*/
    public paired(){

    }

    /*This paired class must be a singleton class*/
    public static paired getPairedInstance(){
        if(instance == null){
            instance = new paired();
            omitted_subject.getInstance();
            Log.d(GENERATE_STATUS, PAIRED_INSTANCE_CREATED);

            setUpPeriodsArray();
        }
        return instance;
    }

    // method to setup the PERIODS array
    public static void setUpPeriodsArray(){
       // PERIODS = new String[NUMBER_OF_PERIODS_PER_DAY];
        for(int i = 0; i < NUMBER_OF_PERIODS_PER_DAY; i++){
            PERIODS[i] = FREE;
        }

        Log.d(GENERATE_STATUS, DUMMY_CLASS_ADDED);
    }

    /*Getter and Setter Methods*/
    public static Hashtable<String, String> getPairedLabs() {
        return pairedLabs;
    }

    public static void setPairedLabs(Hashtable<String, String> pairedLabs) {
        paired.pairedLabs = pairedLabs;
    }

    public static Hashtable<String, String[]> getPairedLabsStaffs() {
        return pairedLabsStaffs;
    }

    public static void setPairedLabsStaffs(Hashtable<String, String[]> pairedLabsStaffs) {
        paired.pairedLabsStaffs = pairedLabsStaffs;
    }

    public static Hashtable<String, String> getPairedSubjects() {
        return pairedSubjects;
    }

    public static void setPairedSubjects(Hashtable<String, String> pairedSubjects) {
        paired.pairedSubjects = pairedSubjects;
    }

    public static Hashtable<String, Integer> getHashedSubjects() {
        return hashedSubjects;
    }

    public static void setHashedSubjects(Hashtable<String, Integer> hashedSubjects) {
        paired.hashedSubjects = hashedSubjects;
    }

    public static Hashtable<String, Integer> getLaterDoIt() {
        return laterDoIt;
    }

    public static void setLaterDoIt(Hashtable<String, Integer> laterDoIt) {
        paired.laterDoIt = laterDoIt;
    }

    public static ArrayList<String> getHashedSubjectsArray() {
        return hashedSubjectsArray;
    }

    public static void setHashedSubjectsArray(ArrayList<String> hashedSubjectsArray) {
        paired.hashedSubjectsArray = hashedSubjectsArray;
    }

    public static ArrayList<Integer> getHashedSubjectsInteger() {
        return hashedSubjectsInteger;
    }

    public static void setHashedSubjectsInteger(ArrayList<Integer> hashedSubjectsInteger) {
        paired.hashedSubjectsInteger = hashedSubjectsInteger;
    }

    public static Hashtable<String, Integer> getPairedSubjectsCount() {
        return pairedSubjectsCount;
    }

    public static void setPairedSubjectsCount(Hashtable<String, Integer> pairedSubjectsCount) {
        paired.pairedSubjectsCount = pairedSubjectsCount;
    }

    public static Hashtable<String, String> getPairedClasses() {
        return pairedClasses;
    }

    public static void setPairedClasses(Hashtable<String, String> pairedClasses) {
        paired.pairedClasses = pairedClasses;
    }

    public static Hashtable<String, String> getSubjectRepeatedForClass() {
        return subjectRepeatedForClass;
    }

    public static void setSubjectRepeatedForClass(Hashtable<String, String> subjectRepeatedForClass) {
        paired.subjectRepeatedForClass = subjectRepeatedForClass;
    }
}