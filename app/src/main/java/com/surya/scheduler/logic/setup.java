package com.surya.scheduler.logic;

import static com.surya.scheduler.constants.data.ADMIN_BLOCK;
import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.CIVIL_DEPARTMENT;
import static com.surya.scheduler.constants.data.CLASS_STRENGTH;
import static com.surya.scheduler.constants.data.CSE_DEPARTMENT;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.DUMMY;
import static com.surya.scheduler.constants.data.ECE_DEPARTMENT;
import static com.surya.scheduler.constants.data.EEE_DEPARTMENT;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.MECH_DEPARTMENT;
import static com.surya.scheduler.constants.data.NOT_ALLOWED;
import static com.surya.scheduler.constants.data.PERIODS;
import static com.surya.scheduler.constants.data.SH_DEPARTMENT;
import static com.surya.scheduler.constants.data.allClassesTeachers;
import static com.surya.scheduler.constants.settings.LABORATORY_AFTERNOON_SESSION_PERIODS;
import static com.surya.scheduler.constants.settings.LABORATORY_MORNING_SESSIONS_PERIODS;
import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_PER_DAY;
import static com.surya.scheduler.constants.status.GENERATE_STATUS;
import static com.surya.scheduler.constants.status.PAIRED_INSTANCE_CREATED;
import static com.surya.scheduler.constants.status.SETUP_ALL_CLASSES_ADDED;
import static com.surya.scheduler.constants.status.SETUP_ALL_LABS_ADDED;
import static com.surya.scheduler.constants.status.SETUP_ALL_STAFFS_ADDED;
import static com.surya.scheduler.constants.status.SETUP_CLASS_ADDED;
import static com.surya.scheduler.constants.status.SETUP_LAB_ADDED;
import static com.surya.scheduler.constants.status.SETUP_STAFF_ADDED;
import static com.surya.scheduler.constants.status.SETUP_STATUS;

import android.util.Log;

import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.lab_periods;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;
import java.util.Hashtable;

public class setup {

    private static setup instance;

    /*It is a singleton class, as the setup should be made only once not more*/
    public static setup getInstance(){
        if(instance == null){
            instance = new setup();
            // creating an instance of paired class
            paired.getPairedInstance();
            Log.d(GENERATE_STATUS, PAIRED_INSTANCE_CREATED);

            return instance;
        }
        else{
            return instance;
        }
    }

    /*Constructor*/
    public setup(){
        // method to add all the classes
        addAllClasses();

        // method to add all the staffs
        addAllStaffs();

        // method to add all the labs
        addAllRooms();
    }

    /*Instantiating each class and adding to allClasses ArrayList*/
    private void addAllClasses(){
        for(int j = 0; j < ALL_ROOMS.length; j++){
            /*Considering only the classes, not the labs*/
            String className = ALL_ROOMS[j];

            if(className.contains("LAB") || className.contains("Lab") || className.contains("Laboratory") || className.contains("lab")){
                /*DO NOTHING*/
            }
            else{
                /*public Class(String name, String department, int year, int numberOfStudents, String[] teachers, Hashtable<String, ArrayList<class_periods>> schedule) {
                    this.name = name;
                    this.department = department;
                    this.year = year;
                    this.numberOfStudents = numberOfStudents;
                    this.teachers = teachers;
                    this.schedule = schedule;
                }*/

                /*Class requires 6 parameters*/
                /*1. Name of the class*/
                String name = className;

                /*2. Department*/
                String dep = className.substring(0, 3);
                if(dep.equals("CSE")){
                    dep = CSE_DEPARTMENT;
                }
                else if(dep.equals("ECE")){
                    dep = ECE_DEPARTMENT;
                }
                else if(dep.equals("MEC")){
                    dep = MECH_DEPARTMENT;
                }
                else if(dep.equals("EEE")){
                    dep = EEE_DEPARTMENT;
                }
                else if(dep.equals("CIV")){
                    dep = CIVIL_DEPARTMENT;
                }
                else if(dep.equals("FYR")){
                    dep = SH_DEPARTMENT.substring(0, 3);
                }

                /*3. Year*/
                int year = 0;
                String[] temp = className.split("-");
                for(int i = 0; i < temp.length; i++){
                    if(temp[1].equals("IV")){
                        year = 4;
                    }
                    else if(temp[1].equals("III")){
                        year = 3;
                    }
                    else if(temp[1].equals("II")){
                        year = 2;
                    }
                    else {
                        year = 1;
                    }
                }

                /*4. Number of students*/
                int numberOfStudents = CLASS_STRENGTH.get(className);

                /*5. Teachers array*/
                /*Can be added directly while instantiating*/

                /*6. Schedule*/
                /*Can be added directly while instantiating*/
                Class.allClasses.add(new Class(
                        name,
                        dep,
                        year,
                        numberOfStudents,
                        allClassesTeachers.get(name),
                        returnTable(), // table for the schedule
                        returnTable()  // table for the short forms
                ));

                Log.d(SETUP_CLASS_ADDED, name);
            }
        }

        Log.d(SETUP_STATUS, SETUP_ALL_CLASSES_ADDED);
    }

    /*Returns the HashTable for each class and teachers with some initial data
     * isLab boolean is the parameter*/
    /*Can also return Hashtable for staffs, if false is passed as a parameter*/
    private Hashtable<String, String[]> returnTable(){
        Hashtable<String, String[]> sample = new Hashtable<>();

        for(String day : DAYS_OF_THE_WEEK){
            sample.put(day, PERIODS);
        }

        return sample;
    }

    /*Instantiating each staff and adding to the allStaffs ArrayList*/
    private void addAllStaffs(){
        for(String Staff : ALL_STAFFS){
            /*public staff(String name, String department, boolean hasConstraints, int workingHours, Hashtable<String, ArrayList<staff_periods>> schedule) {
                this.name = name;
                this.department = department;
                this.hasConstraints = hasConstraints;
                this.workingHours = workingHours;
                this.schedule = schedule;
            }*/
            /*staff requires 5 parameters*/

            /*1. Name of the staff*/
            /*Using substring method because the staff name is in the format, department-Mr/Ms/Mrs. staff_name in the ALL_STAFFS array*/
            String name = Staff.substring(4);

            /*2. Department*/
            String dep = Staff.substring(0, 3);
            if(dep.equals("CSE")){
                dep = CSE_DEPARTMENT;
            }
            else if(dep.equals("ECE")){
                dep = ECE_DEPARTMENT;
            }
            else if(dep.equals("FYR")){
                dep = "FYR";
            }
            else if(dep.equals("EEE")){
                dep = EEE_DEPARTMENT;
            }
            else if(dep.equals("MEC")){
                dep = MECH_DEPARTMENT;
            }
            else if(dep.equals("CIV")){
                dep = CIVIL_DEPARTMENT;
            }

            /*3. hasConstraints?*/
            /*If a staff has any constraints?*/
            boolean hasConstraints = false;

            /*4. Working Hours*/
            /*Can be incremented once creating the schedule*/
            int workingHours = 0;

            /*5. Schedule for the staff*/
            staff.allStaffs.add(new staff(
                    name,
                    dep,
                    hasConstraints,
                    workingHours,
                    returnTable(), // table for class names
                    returnTable()  // table for subject names
            ));

            Log.d(SETUP_STAFF_ADDED, name);
        }

        Log.d(SETUP_STATUS, SETUP_ALL_STAFFS_ADDED);
    }

    /*Instantiating each room and adding them to allRooms ArrayList*/
    private void addAllRooms(){
        for(String Room : ALL_ROOMS){
            /*public room(String name, String department, boolean isLab, Hashtable<String, String[]> schedule) {
                this.name = name;
                this.department = department;
                this.isLab = isLab;
                this.schedule = schedule;
            }*/
            /*room requires four parameters*/

            /*3. Is it a lab?*/
            boolean isLab;
            //String temp = Room.substring(4, 7);
            //isLab = temp.equals("LAB") || temp.equals("Lab");
            isLab = Room.contains("LAB") || Room.contains("Lab") || Room.contains("Laboratory") || Room.contains("lab");

            if(isLab){
                /*1, Name of the room*/
                String name = Room;

                /*2. Department*/
                String dep = Room.substring(0, 3);
                if(dep.equals("CSE")){
                    dep = CSE_DEPARTMENT;
                }
                else if(dep.equals("ECE")){
                    dep = ECE_DEPARTMENT;
                }
                else if(dep.equals("MEC")){
                    dep = MECH_DEPARTMENT;
                }
                else if(dep.equals("FYR")){
                    dep = SH_DEPARTMENT;
                }
                else if(dep.equals("ADM")){
                    dep = ADMIN_BLOCK;
                }

                /*4. Schedule for the class*/
                /*Passing the schedule directly inside the instantiation*/

                /*Adding to the allRooms ArrayList*/
                room.allRooms.add(new room(
                        name,
                        dep,
                        isLab,
                        returnTable()
                ));

                Log.d(SETUP_LAB_ADDED, name);
            }
        }

        Log.d(SETUP_STATUS, SETUP_ALL_LABS_ADDED);
    }
}
