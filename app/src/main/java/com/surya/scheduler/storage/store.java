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
import static com.surya.scheduler.constants.data.CLASS;
import static com.surya.scheduler.constants.data.CLASS_STRENGTH;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.HASHED;
import static com.surya.scheduler.constants.data.HASHED_SUBJECTS;
import static com.surya.scheduler.constants.data.SCHEDULES;
import static com.surya.scheduler.constants.data.allClassesTeachers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

public class store {
    /*saving the ones that are liable to change in the shared preferences*/
    /*admin may delete some staff while using the app, and that change should be seen the next time he opens the app*/
    /*some constants like DAYS_OF_THE_WEEK , ALL_SUBJECTS need not be saved in shared preferences*/

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private Context context;

    /*Constructor*/
    public store(Context context){
        this.context = context;

        saveAllClasses();
        saveAllStaffs();
        saveAllClassesStrength();
        saveAllClassesStaffs();
        saveAllLabsSchedule();
        saveAllClassesSchedules();
        saveAllStaffsSchedules();
        saveHashedSubjects();
        saveAdminsList();
    }

    /*empty constructor*/
    public store(){

    }

    /********************************************************************************************************************************************/
    /************************ SAVING METHODS ************************/
    /*method to store the all classes array*/
    private void saveAllClasses(){
        String json = gson.toJson(ALL_ROOMS);
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_CLASSES_LIST, json);
        editor.apply();
    }

    /*method to save the all staffs array*/
    private void saveAllStaffs(){
        String json = gson.toJson(ALL_STAFFS);
        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_STAFFS_LIST, json);
        editor.apply();
    }

    // method to save all classes strength
    private void saveAllClassesStrength(){
        for(String classx : ALL_ROOMS){
            if(! ((classx.contains("LAB") || classx.contains("Lab") || classx.contains("lab") || (classx.contains("Laboratory"))))){
                int strength = CLASS_STRENGTH.get(classx);
                String modifiedStrength = classx+":"+strength;

                // converting the modifiedStrength to json
                //String json = gson.toJson(modifiedStrength);

                // entering the json string to the shared preferences
                sharedPreferences = context.getSharedPreferences(ALL_CLASSES_STRENGTHS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(classx, String.valueOf(strength));
                editor.apply();
            }
        }
    }

    /*method to save the staffs for each class*/
    private void saveAllClassesStaffs(){
        for(String room : ALL_ROOMS){
            if(! room.contains("LAB")){
                String[] staffs = allClassesTeachers.get(room);

                String json = gson.toJson(staffs);
                sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(room, json);
                editor.apply();
            }
        }
    }

    /*Method to save all the classes schedules*/
    private void saveAllClassesSchedules(){
        for(Class classes : Class.allClasses){
            for(String day : DAYS_OF_THE_WEEK){
                String[] temp = new String[4];

                String json = gson.toJson(temp);
                sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(classes.getName()+day, json);
                editor.apply();
            }
        }
    }

    /*Method to save all the staff schedules*/
    private void saveAllStaffsSchedules(){
        for(staff Staff : staff.allStaffs){
            for(String day : DAYS_OF_THE_WEEK){
                String[] temp = new String[4];

                String json = gson.toJson(temp);
                sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Staff.getName()+day, json);
                editor.apply();
            }
        }
    }

    /*Method to save all the labs schedule*/
    private void saveAllLabsSchedule(){
        for(room Room : room.allRooms){
            if(Room.getName().contains("LAB") || Room.getName().contains("Lab") || Room.getName().contains("Laboratory") || Room.getName().contains("lab")){
                /*neglecting the normal classes schedule*/
                for(String day : DAYS_OF_THE_WEEK){
                    String[] temp = new String[4];

                    String json = gson.toJson(temp);
                    sharedPreferences = context.getSharedPreferences(SCHEDULES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Room.getName()+day, json);
                    editor.apply();
                }
            }
        }
    }

    /*Method to store the paired labs names*/
    private void saveHashedSubjects(){
        String[] hashedSubjectsArray = new String[paired.hashedSubjectsArray.size()];
        Integer[] hashedSubjectsInteger = new Integer[paired.hashedSubjectsArray.size()];

        for(int i = 0; i < paired.hashedSubjectsArray.size(); i++){
            hashedSubjectsArray[i] = paired.hashedSubjectsArray.get(i);
            hashedSubjectsInteger[i] = paired.hashedSubjectsInteger.get(i);

        }

        /*converting the array to json*/
        String json = gson.toJson(hashedSubjectsArray);
        String json2 = gson.toJson(hashedSubjectsInteger);

        sharedPreferences = context.getSharedPreferences(HASHED_SUBJECTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HASHED, json);
        editor.putString(HASHED_SUBJECTS, json2);

        editor.apply();
    }

    // method to save the admins list
    private void saveAdminsList(){
        // converting the ADMINS_ARRAY to json file
        String json = gson.toJson(ADMINS_ARRAY);

        sharedPreferences = context.getSharedPreferences(ALL_LISTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ADMINS, json);
        editor.apply();
    }
}
