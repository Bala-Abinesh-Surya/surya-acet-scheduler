package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.ALL_CLASSES_DETAILS;
import static com.surya.scheduler.constants.data.APP_DEFAULTS;
import static com.surya.scheduler.constants.data.APP_OPENED_ONCE;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.MODE_OF_SCHEDULES;
import static com.surya.scheduler.constants.data.OFFLINE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.surya.scheduler.R;
import com.surya.scheduler.adapters.all_classes_recViewAdapter;
import com.surya.scheduler.logic.generate;
import com.surya.scheduler.logic.setup;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.firebaseModels.firebase_class;
import com.surya.scheduler.models.firebaseModels.firebase_room;
import com.surya.scheduler.models.firebaseModels.firebase_staff;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.retrieve;
import com.surya.scheduler.storage.store;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;

public class splash_screen extends AppCompatActivity{

    private boolean openedOnce;
    private ImageView imageView;
    private Thread thread;

    private Context context;

    private firebase_class firebase_class;
    private firebase_room firebase_room;
    private firebase_staff firebase_staff;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    public interface notifyAllClassesAdded {
        void notifyTheAdapter();
    }

    private Gson gson = new Gson();
    private Type type = new TypeToken<String[]>(){}.getType();

    private static boolean roomFirstTime = false;
    private static boolean classFirstTime = false;
    private static boolean staffFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*hiding the action bar*/
        getSupportActionBar().hide();

        // method to initialise the UI Elements
        init();

        context = this;

        imageView.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

      /*  setup.getInstance();
        generate.getGenerateInstance();

        Intent intent = new Intent(splash_screen.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
        finish(); */

        // 21-12-2021

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(50);
                    SharedPreferences sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
                    openedOnce = sharedPreferences.getBoolean(APP_OPENED_ONCE, false);


                    //online.getOnlineInstance();
                    //Log.d("completed", "opened once");

                    /*if(! openedOnce){
                        setup.getInstance(OFFLINE);
                        generate.getGenerateInstance();

                        store store = new store(getApplicationContext());

                        Log.d("completed", "Completed successfully");
                    }

                    else{
                        //retrieve r = new retrieve(9);
                        //setup.getInstance(OFFLINE);

                        retrieve retrieve = new retrieve(getApplicationContext());
                    }*/

                    setup.getInstance();
                    generate.getGenerateInstance();
                    //coaching.getInstance();

                    /*All Classes details*/
                  /*   firebaseDatabase.getReference()
                            .child(ALL_CLASSES_DETAILS)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                            if(snapshot1.exists()){
                                                firebase_class = snapshot1.getValue(firebase_class.class);
                                                Log.d("antonidhish", firebase_class.getClassName());
                                                addAllClasses(firebase_class);
                                               // Log.d("taggg", firebase_class.getClassName());
                                            }
                                        }

                                        all_classes_recViewAdapter adapter = new all_classes_recViewAdapter(context, 0);
                                        adapter.setAllClasses(Class.allClasses);
                                        adapter.notifyDataSetChanged();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });   */

                    /*Adding all the staffs*/
                    /*firebaseDatabase.getReference()
                            .child(ALL_STAFFS_DETAILS)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                            if(snapshot1.exists()){
                                                firebase_staff = snapshot1.getValue(firebase_staff.class);
                                                addAllStaffs(firebase_staff);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    /*Adding all the labs details*/
                    /*firebaseDatabase.getReference()
                            .child(ALL_LABS_DETAILS)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                            if(snapshot1.exists()){
                                                firebase_room = snapshot1.getValue(firebase_room.class);
                                                addAllLabsDetails(firebase_room);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    /*entering in the shared preferences that the app opened once, for the future use*/
                    //sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(APP_OPENED_ONCE, true);
                    // entering the mode of schedules
                    editor.putString(MODE_OF_SCHEDULES, OFFLINE);
                    editor.apply();
                }
                catch (Exception exception){
                    Log.d("SplashScreenError", exception.toString());
                }
                finally {
                    Intent intent = new Intent(splash_screen.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    finish();
                }
            };
        };

        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /*method to initialise the UI Elements*/
    private void init(){
        imageView = findViewById(R.id.splash_image);
    }

    /*method to add the firebase_class to the normal classes*/
    private void addAllClasses(firebase_class firebase_class){
        /*fetching the details from the firebase_class*/
        /*public firebase_class(String className, String department, int year, int numberOfStudents, List<String> staffs, List<String> schedules, List<String> shortForm) {
            this.className = className;
            this.department = department;
            this.year = year;
            this.numberOfStudents = numberOfStudents;
            this.staffs = staffs;
            this.schedules = schedules;
            this.shortForm = shortForm;
        }*/

        String className = firebase_class.getClassName();
        int year = firebase_class.getYear();
        String department = firebase_class.getDepartment();
        int numberOfStudents = firebase_class.getNumberOfStudents();

        /*converting the List<String> staffs list to String[] staffs array*/
        List<String> fStaffs = firebase_class.getStaffs();
        String[] staffs = new String[fStaffs.size()];
        for(int i = 0; i < fStaffs.size(); i++){
            staffs[i] = fStaffs.get(i);
        }

        /*getting the schedules*/
        List<String> tempSchedules = firebase_class.getSchedules();
        List<String> tempShortForm = firebase_class.getShortForm();

        /*creating the hashtable*/
        Hashtable<String, String[]> table = new Hashtable<>(); // for the schedules
        Hashtable<String, String[]> shortForm = new Hashtable<>(); // for the short forms

        /*converting the json string to string[]*/
        int index = 0;
        for(String day : DAYS_OF_THE_WEEK){
            String[] schedule = gson.fromJson(tempSchedules.get(index), type);
            String[] shortFormArray = gson.fromJson(tempShortForm.get(index), type);
            index++;

            /*inputting the array in the hashtable*/
            table.put(day, schedule);
            shortForm.put(day, shortFormArray);
        }

        /*Actual Allocation*/
        /*public Class(String name, String department, int year, int numberOfStudents, String[] teachers, Hashtable<String, String[]> schedule, Hashtable<String, String[]> shortFormSchedule) {
            this.name = name;
            this.department = department;
            this.year = year;
            this.numberOfStudents = numberOfStudents;
            this.teachers = teachers;
            this.schedule = schedule;
            this.shortFormSchedule = shortFormSchedule;
        }*/

        if(! classFirstTime){
            Class.allClasses.clear();
            classFirstTime = true;
        }

        /*Adding the class*/
        Class.allClasses.add(new Class(
                className,
                department,
                year,
                numberOfStudents,
                staffs,
                table,
                shortForm
        ));

        Log.d("suryaa", Class.allClasses.size()+"");
    }

    /*method to add the firebase_staff to the normal staffs*/
    private void addAllStaffs(firebase_staff firebase_staff){
        /*fetching the details from the firebase_staff*/
        /*public firebase_staff(String name, String department, boolean hasConstraints, int workingHours, List<String> schedules) {
            this.name = name;
            this.department = department;
            this.hasConstraints = hasConstraints;
            this.workingHours = workingHours;
            this.schedules = schedules;
        }*/

        String staffName = firebase_staff.getName();
        String department = firebase_staff.getDepartment();
        boolean constraints = firebase_staff.isHasConstraints();
        int numberOfWorkingHours = firebase_staff.getWorkingHours();

        /*schedules will be a List of json strings*/
        List<String> tempSchedules = firebase_staff.getSchedules();

        /*creating the hashtable*/
        Hashtable<String, String[]> table = new Hashtable<>();

        /*converting the json string to string[]*/
        int index = 0;
        for(String day : DAYS_OF_THE_WEEK){
            String[] schedule = gson.fromJson(tempSchedules.get(index), type);
            index++;

            /*inputting the string[] to the hashtable*/
            table.put(day, schedule);
        }

        if(! staffFirstTime){
            staff.allStaffs.clear();
            staffFirstTime = true;
        }

        /*Adding the staff*/
        /*public staff(String name, String department, boolean hasConstraints, int workingHours, Hashtable<String, String[]> schedule) {
            this.name = name;
            this.department = department;
            this.hasConstraints = hasConstraints;
            this.workingHours = workingHours;
            this.schedule = schedule;
        }*/

        staff.allStaffs.add(new staff(
                staffName,
                department,
                constraints,
                numberOfWorkingHours,
                new Hashtable<>(),
                new Hashtable<>() // TODO : change made
        ));
    }

    /*Method to add all the lab details*/
    private void addAllLabsDetails(firebase_room firebase_room){
        /*fetching the details from the firebase_room*/
        /*public firebase_room(String name, String department, boolean isLab, List<String> schedules) {
            this.name = name;
            this.department = department;
            this.isLab = isLab;
            this.schedules = schedules;
        }*/

        String roomName = firebase_room.getName();
        String department = firebase_room.getDepartment();
        boolean isLab = firebase_room.isLab();
        /*schedules will be a List of json strings*/
        List<String> tempSchedule = firebase_room.getSchedules();

        /*creating a hashtable*/
        Hashtable<String, String[]> table = new Hashtable<>();

        /*converting the json string to string[]*/
        int index = 0;
        for(String day : DAYS_OF_THE_WEEK){
            String[] schedule = gson.fromJson(tempSchedule.get(index), type);
            index++;

            /*inputting the schedule[] in the hashtable*/
            table.put(day, schedule);
        }

        /*adding the firebase_room to the actual rooms*/
        /*public room(String name, String department, boolean isLab, Hashtable<String, String[]> schedule) {
            this.name = name;
            this.department = department;
            this.isLab = isLab;
            this.schedule = schedule;
        }*/

        if(! roomFirstTime){
            room.allRooms.clear();
            roomFirstTime = true;
        }

        room.allRooms.add(new room(
                roomName,
                department,
                isLab,
                new Hashtable<>()
        ));
    }

}