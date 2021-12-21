package com.surya.scheduler.firebase;

import static com.surya.scheduler.constants.data.ALL_CLASSES_DETAILS;
import static com.surya.scheduler.constants.data.ALL_LABS_DETAILS;
import static com.surya.scheduler.constants.data.ALL_STAFFS_DETAILS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.firebaseModels.firebase_class;
import com.surya.scheduler.models.firebaseModels.firebase_room;
import com.surya.scheduler.models.firebaseModels.firebase_staff;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.util.Arrays;
import java.util.List;

public class update {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private Gson gson = new Gson();

    /*Constructor*/
    public update(){

    }

    /*method is public and not inside the constructor because, it can be done separately*/
    /*method to update all the classes data and schedules*/
    public void updateAllClasses(){

        /*have to delete the already existing data*/
        /*so setting some duplicate value to delete athe already existing data*/
        firebaseDatabase.getReference()
                .child(ALL_CLASSES_DETAILS)
                .setValue("duplicate");

        for(Class classes : Class.allClasses){
            String className = classes.getName();
            String department = classes.getDepartment();
            int year = classes.getYear();
            int numberOfStudents = classes.getNumberOfStudents();
            List<String> staffs = Arrays.asList(classes.getTeachers());
            String[] schedules = new String[6];

            /*Converting the schedules to the array of json strings*/
            int index = 0;
            for(String day : DAYS_OF_THE_WEEK){
                String[] temp = new String[3];

                /*converting the temp[] to json file first*/
                String json = gson.toJson(temp);

                /*adding the file to the array*/
                schedules[index] = json;
                index++;
            }

            /*Firebase wont support arrays... so converting the array to List*/
            List<String> finalSchedules = Arrays.asList(schedules);

            /*public firebase_class(String className, String department, int year, int numberOfStudents, List<String> staffs, List<String> schedules) {
                this.className = className;
                this.department = department;
                this.year = year;
                this.numberOfStudents = numberOfStudents;
                this.staffs = staffs;
                this.schedules = schedules;
            }*/

            /*creating an object for the firebase_class*/
            firebase_class firebase_class = new firebase_class(
                    className,
                    department,
                    year,
                    numberOfStudents,
                    staffs,
                    finalSchedules
            );

            /*updating in the firebase*/
            firebaseDatabase.getReference()
                    .child(ALL_CLASSES_DETAILS)
                    .push()
                    .setValue(firebase_class);
        }
    }

    /*method to update all the staffs schedules*/
    public void updateAllStaffs(){

        /*have to delete the already existing data*/
        /*so setting some duplicate value to delete athe already existing data*/
        firebaseDatabase.getReference()
                .child(ALL_STAFFS_DETAILS)
                .setValue("duplicate");

        for(staff Staff : staff.allStaffs){
            String staffName = Staff.getName();
            String department = Staff.getDepartment();
            int workingHours = Staff.getWorkingHours();
            boolean constraints = Staff.isHasConstraints();
            String[] schedule = new String[6];

            /*Converting the schedules to the array of json strings*/
            int index = 0;
            for(String day : DAYS_OF_THE_WEEK){
                String[] temp = new String[2];

                /*converting the array to json*/
                String json = gson.toJson(temp);

                /*adding json to the array*/
                schedule[index] = json;
                index++;
            }

            /*Converting the array to list*/
            List<String> finalSchedules = Arrays.asList(schedule);

            /*creating an object for firebase_staff class*/
            /* firebase_staff(String name, String department, boolean hasConstraints, int workingHours, List<String> schedules) {
                this.name = name;
                this.department = department;
                this.hasConstraints = hasConstraints;
                this.workingHours = workingHours;
                this.schedules = schedules;
            }*/

            firebase_staff firebase_staff = new firebase_staff(
                    staffName,
                    department,
                    constraints,
                    workingHours,
                    finalSchedules
            );

            /*updating in the database*/
            firebaseDatabase.getReference()
                    .child(ALL_STAFFS_DETAILS)
                    .push()
                    .setValue(firebase_staff);
        }
    }

    /*method to update all the labs schedules*/
    public void updateAllLabsSchedules(){
        /*have to delete the already existing data*/
        /*so setting some duplicate value to delete athe already existing data*/
        firebaseDatabase.getReference()
                .child(ALL_LABS_DETAILS)
                .setValue("duplicate");

        for(room Room : room.allRooms){
            /*Ignoring all the normal classes*/
            /*Considering only the labs*/
            String labName = Room.getName();
            boolean isLab = Room.isLab();
            String department = Room.getDepartment();
            String[] schedule = new String[6];

            if(isLab){
                /*converting the labs schedules to jso file*/
                int index = 0;
                for(String day : DAYS_OF_THE_WEEK){
                    String[] temp = new String[4];

                    /*converting the array to json file*/
                    String json = gson.toJson(temp);

                    /*adding the json string to the array*/
                    schedule[index] = json;
                    index++;
                }

                /*converting the array to the list*/
                List<String> finalSchedule = Arrays.asList(schedule);

                /*creating an object for firebase_room class*/
                /*public firebase_room(String name, String department, boolean isLab, List<String> schedules) {
                    this.name = name;
                    this.department = department;
                    this.isLab = isLab;
                    this.schedules = schedules;
                }*/

                firebase_room firebase_room = new firebase_room(
                        labName,
                        department,
                        isLab,
                        finalSchedule
                );

                /*Updating the firebase*/
                firebaseDatabase.getReference()
                        .child(ALL_LABS_DETAILS)
                        .push()
                        .setValue(firebase_room);
            }
        }
    }
}
