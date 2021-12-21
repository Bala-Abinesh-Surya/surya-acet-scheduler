package com.surya.scheduler.storage;

import static com.surya.scheduler.constants.data.ADMINS;
import static com.surya.scheduler.constants.data.ADMINS_ARRAY;
import static com.surya.scheduler.constants.data.ALL_ADMINS;
import static com.surya.scheduler.constants.data.ALL_CLASSES_LIST;
import static com.surya.scheduler.constants.data.ALL_CLASSES_STRENGTHS;
import static com.surya.scheduler.constants.data.ALL_LISTS;
import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.ALL_STAFFS_LIST;
import static com.surya.scheduler.constants.data.CLASS_SCHEDULE;
import static com.surya.scheduler.constants.data.CLASS_STRENGTH;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.HASHED;
import static com.surya.scheduler.constants.data.HASHED_SUBJECTS;
import static com.surya.scheduler.constants.data.SCHEDULES;
import static com.surya.scheduler.constants.data.allClassesTeachers;
import static com.surya.scheduler.constants.data.getAllStaffs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Locale;

public class retrieve {

    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson gson = new Gson();
    private Type type = new TypeToken<String[]>(){}.getType();

    /*Constructor*/
    public retrieve(Context context){
        this.context = context;

        retrieveAllClasses();
        Log.d("retrieved", "All classes lists retrieved");

        retrieveAllStaffs();
        Log.d("retrieved", "All staffs lists retrieved");

        retrieveAllClassesStaffs();
        Log.d("retrieved", "All classes' staffs retrieved");

        retrieveAllClassesStrengths();
        Log.d("retrieved", "All classes strengths retrieved");

        retrieveAllClassesSchedule();
        Log.d("retrieved", "All classes' and labs' schedules retrieved");

        retrieveAllStaffsSchedule();
        Log.d("retrieved", "All staffs' schedules retrieved");

        retrieveHashedSubjects();
        Log.d("retrieved", "All hashed subjects retrieved");

        retrieveAdminsList();
        Log.d("retrieved", "Admins lists retrieved");
    }

    /*Empty constructor*/
    public retrieve(){

    }

    /********************************************************************************************************************************************/
    /************************ RETRIEVAL  METHODS ************************/
    /*method to retrieve the all classes array*/
    private void retrieveAllClasses(){
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ALL_CLASSES_LIST, null);

        ALL_ROOMS = gson.fromJson(json, type);
    }

    /*method to retrieve the all staffs array*/
    private void retrieveAllStaffs(){
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ALL_STAFFS_LIST, null);

        String[] temp = gson.fromJson(json, type);
        data.setAllStaffs(temp);
    }

    /*method to retrieve all the classes staffs*/
    private void retrieveAllClassesStaffs(){
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        for(String room : ALL_ROOMS){
            if(! (room.contains("LAB") || room.contains("lab") || room.contains("Lab") || room.contains("Laboratory"))){
                String staffsJson = sharedPreferences.getString(room, null);

                String[] staffs = gson.fromJson(staffsJson, type);
                allClassesTeachers.put(room, staffs);
            }
        }
    }

    /*method to retrieve all classes strengths*/
    private void retrieveAllClassesStrengths(){
        sharedPreferences = context.getSharedPreferences(ALL_CLASSES_STRENGTHS, Context.MODE_PRIVATE);
        for(String room : ALL_ROOMS){
            if(! (room.contains("LAB") || room.contains("lab") || room.contains("Lab") || room.contains("Laboratory"))){
                String name = room;
                int strength = Integer.valueOf(sharedPreferences.getString(room, null));

                CLASS_STRENGTH.put(name, strength);
            }
        }
    }

    /*method to retrieve all the staffs schedules*/
    /*private void retrieveAllStaffsSchedule(){
        sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
        for(staff Staff : staff.allStaffs){
            for(String day : DAYS_OF_THE_WEEK){
                String jsonTemp = sharedPreferences.getString(Staff.getName()+day, null);
                String[] schedule = gson.fromJson(jsonTemp, type);

                Hashtable<String, String[]> tempTable = Staff.getSchedule();
                tempTable.put(day, schedule);

                Staff.setSchedule(tempTable);
            }
        }
    }*/

    private void retrieveAllStaffsSchedule(){
        sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
        for(String staffx : ALL_STAFFS){
            // staff details
            String staffName = staffx.substring(4);
            String department = staffx.substring(0, 3);

            /*public staff(String name, String department, boolean hasConstraints, int workingHours, Hashtable<String, String[]> schedule) {
                this.name = name;
                this.department = department;
                this.hasConstraints = hasConstraints;
                this.workingHours = workingHours;
                this.schedule = schedule;
            }*/

            // creating an instance for staff class
            staff Staff = new staff();
            Staff.setName(staffName);
            Staff.setDepartment(department);
            Staff.setWorkingHours(0);
            Staff.setHasConstraints(false);

            Hashtable<String, String[]> sample = new Hashtable<>();

            // fetching the schedule
            for(String day : DAYS_OF_THE_WEEK){
                String json = sharedPreferences.getString(staffName+day, null);
                // converting the json string to string[]
                String[] temp = gson.fromJson(json, type);
                sample.put(day, temp);
            }

            //Staff.setSchedule(sample);

            // adding the staff object to the array list
            staff.allStaffs.add(Staff);
        }
    }

    /*method to retrieve all the classes schedules*/
    /*private void retrieveAllClassesSchedule(){
        sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
        for(Class classes : Class.allClasses){
            for(String day : DAYS_OF_THE_WEEK){
                String jsonTemp = sharedPreferences.getString(classes.getName()+day, null);
                String[] schedule = gson.fromJson(jsonTemp, type);

                Hashtable<String, String[]> tempTable =  classes.getSchedule();
                tempTable.put(day, schedule);

                classes.setSchedule(tempTable);
            }
        }
    }*/

    /*method to retrieve complete details of all classes and labs*/
    private void retrieveAllClassesSchedule(){
        sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
        for(String roomx : ALL_ROOMS){
            String roomName = roomx;
            if (roomName.contains("LAB") || roomName.contains("Lab") || roomName.contains("Laboratory") || roomName.contains("lab")){
                // the room is a laboratory
                /*public room(String name, String department, boolean isLab, Hashtable<String, String[]> schedule) {
                    this.name = name;
                    this.department = department;
                    this.isLab = isLab;
                    this.schedule = schedule;
                }*/

                // creating an instance for the room class
                room Room = new room();

                Room.setName(roomName);
                Room.setDepartment(roomName.substring(0, 3));
                Room.setLab(true);

                // fetching the schedule
                Hashtable<String, String[]> sample = new Hashtable<>();
                for(String day : DAYS_OF_THE_WEEK){
                    String json = sharedPreferences.getString(roomName+day, null);

                    // converting the json to the String[]
                    String[] temp = gson.fromJson(json, type);

                    sample.put(day, temp);
                }

                //Room.setSchedule(sample);

                // adding the Room to the allRooms arraylist
                room.allRooms.add(Room);
            }
            else{
                // room is a normal class
                /*public Class(String name, String department, int year, int numberOfStudents, String[] teachers, Hashtable<String, String[]> schedule) {
                    this.name = name;
                    this.department = department;
                    this.year = year;
                    this.numberOfStudents = numberOfStudents;
                    this.teachers = teachers;
                    this.schedule = schedule;
                }*/

                // creating an instance for the class Class
                Class classx = new Class();

                classx.setName(roomName);
                classx.setDepartment(roomName.substring(0, 3));
                // setting the year
                //classx.setYear(roomName.contains("IV") ? 4 : roomName.contains("III") ? 3 : roomName.contains("II") ? 2 : roomName.contains("I") ? 1 : 1);
                int year = 0;
                String[] xump = roomName.split("-");
                for(int i = 0; i < xump.length; i++){
                    if(xump[1].equals("IV")){
                        year = 4;
                    }
                    else if(xump[1].equals("III")){
                        year = 3;
                    }
                    else if(xump[1].equals("II")){
                        year = 2;
                    }
                    else{
                        year = 1;
                    }
                }

                classx.setYear(year);

                String[] staffs = allClassesTeachers.get(roomName);
                classx.setTeachers(staffs);

                // setting the class strength
                classx.setNumberOfStudents(CLASS_STRENGTH.get(roomName));

                // fetching the schedules
                Hashtable<String, String[]> sample = new Hashtable<>();
                for(String day : DAYS_OF_THE_WEEK){
                    String json = sharedPreferences.getString(roomName+day, null);
                    // converting the json to String[]
                    String[] temp = gson.fromJson(json, type);

                    sample.put(day, temp);
                }

                //classx.setSchedule(sample);

                // adding the classx to the allClasses arraylist
                Class.allClasses.add(classx);
            }
        }
    }

    /*method to retrieve the hashed subjects names*/
    private void retrieveHashedSubjects(){
        sharedPreferences = context.getSharedPreferences(HASHED_SUBJECTS, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(HASHED, null);
        String json2 = sharedPreferences.getString(HASHED_SUBJECTS, null);

        String[] pairedSubjectsArray = gson.fromJson(json, type);

        Type type2 = new TypeToken<Integer[]>(){}.getType();
        Integer[] pairedSubjectsInteger = gson.fromJson(json2, type2);

        /*adding the strings in the array to the hashed subjects hashtable*/
        for(int i = 0; i < pairedSubjectsArray.length; i++){
            paired.hashedSubjects.put(pairedSubjectsArray[i], pairedSubjectsInteger[i]);
            Toast.makeText(context, pairedSubjectsInteger[i].toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // method to retrieve the admins array
    private void retrieveAdminsList(){
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ALL_ADMINS, null);

        // converting the json to String[]
        ADMINS_ARRAY = gson.fromJson(json, type);

        // clearing the ADMINS hashtable
        ADMINS.clear();

        // also updating the ADMINS hashtable
        for(String admin : ADMINS_ARRAY){
            ADMINS.put(admin, 1);
        }
    }
}
