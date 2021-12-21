package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.ADMINS;
import static com.surya.scheduler.constants.data.APP_DEFAULTS;
import static com.surya.scheduler.constants.data.CLASS;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.CLASS_SCHEDULE;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FROM;
import static com.surya.scheduler.constants.data.IS_THE_USER_A_ADMIN;
import static com.surya.scheduler.constants.data.LAB_NAME;
import static com.surya.scheduler.constants.data.ROOMS_REC_VIEW;
import static com.surya.scheduler.constants.data.STAFF;
import static com.surya.scheduler.constants.data.STAFF_NAME;
import static com.surya.scheduler.constants.data.STAFF_SCHEDULE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.surya.scheduler.R;
import com.surya.scheduler.adapters.timetableAdapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;
import java.util.Hashtable;

public class schedule extends AppCompatActivity {

    private RecyclerView recyclerView;
    private timetableAdapter adapter;
    private ArrayList<String[]> schedules = new ArrayList<>(6);
    private TextView textView;
    private Button showMore;
    private TextView adminText;

    private String className, staffName;

    private boolean fromClass = false;
    private boolean fromStaff = false;
    private boolean fromLab = false;

    private int adminTextClicked = 0;

    /*Handling the menu clicks*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                /*Do exactly what happens when the back button is pressed*/
                onBackPressed();
                break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        /*This gotten String from the intent may be className, or the staffName*/
        Intent intent = getIntent();
        String from = intent.getStringExtra(FROM);

        /*Method to initialise UI Elements and adapter*/
        init();

        if(from.equals(CLASS)){
            fromClass = true;
            fromStaff = false;
            fromLab = false;

            /*clearing the schedule array list*/
            schedules.clear();

            className = intent.getStringExtra(CLASS_NAME);
            /*Getting the schedule for the class*/
            for(Class classes : Class.allClasses){
                if(classes.getName().equals(className)){
                    int num = 0;
                    Hashtable<String, String[]> table = classes.getSchedule();

                    for(String x : DAYS_OF_THE_WEEK){
                        String[] temp = table.get(x).clone();
                        schedules.add(num, temp);
                        num++;
                    }
                    //break;
                }
            }
            /*Setting the title of the ActionBar*/
            getSupportActionBar().setTitle(className);
            /*Setting the text for the textView*/
            textView.setText(className);
            /*Setting the adapter*/
            adapter = new timetableAdapter(this, CLASS_SCHEDULE);
        }

        else if(from.equals(STAFF)){
            fromStaff = true;
            fromClass = false;
            fromLab = false;

            /*clearing the schedule array list*/
            schedules.clear();

            staffName = intent.getStringExtra(STAFF_NAME);
            /*Getting the schedule for the staff*/
            for(staff Staff : staff.allStaffs){
                if(Staff.getName().equals(staffName)){
                    int num = 0;
                    for(String x : DAYS_OF_THE_WEEK){
                        String[] temp = new String[4];
                        schedules.add(num, temp);
                        num++;
                    }
                }
            }
            /*Setting the title of the ActionBar*/
            getSupportActionBar().setTitle(staffName);
            /*Setting the text for the textView*/
            textView.setText(staffName);
            /*Setting the adapter*/
            adapter = new timetableAdapter(this, STAFF_SCHEDULE);

            /*Activating the admin mode*/
            adminText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Checking if the name of staff is present in ADMIN*/
                    if(ADMINS.containsKey(staffName)){
                        adminTextClicked++;

                        if(adminTextClicked > 5){

                            SharedPreferences sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
                            boolean isAdmin = sharedPreferences.getBoolean(IS_THE_USER_A_ADMIN, false);

                            if(isAdmin){
                                Toast.makeText(getApplicationContext(), getString(R.string.already_an_admin), Toast.LENGTH_SHORT).show();
                                adminTextClicked = 0;
                            }

                            else{
                                Toast.makeText(schedule.this, "admin mode activated", Toast.LENGTH_SHORT).show();
                                adminTextClicked = 0;

                                /*Entering that the user is ADMIN in the shared preferences*/
                                sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(IS_THE_USER_A_ADMIN, true);
                                editor.apply();

                                /*Passing the admin to the MainActivity*/
                                Intent intent = new Intent(schedule.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    }
                }
            });

            showMore.setVisibility(View.GONE);
        }

        else{
            /*must be from the all labs recycler view*/
            fromLab = true;
            fromClass = false;
            fromStaff = false;

            /*clearing the schedule array list*/
            schedules.clear();

            String labName = intent.getStringExtra(LAB_NAME);

            /*Setting the title of the Action bar*/
            getSupportActionBar().setTitle(labName);

            /*getting the schedule for the lab*/
            for(room Room : room.allRooms){
                if(Room.getName().equals(labName)){
                    int num = 0;
                    for(String day : DAYS_OF_THE_WEEK){
                        String[] temp = new String[3];
                        schedules.add(num, temp);
                        num++;
                    }
                }
            }

            /*setting the text view*/
            textView.setText(labName);

            adapter = new timetableAdapter(getApplicationContext(), ROOMS_REC_VIEW);

            showMore.setVisibility(View.INVISIBLE);
        }

        /*Enabling the back button in the ActionBar*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setClassSchedule(schedules);
        adapter.notifyDataSetChanged();

        /*Setting the onclick listener for the showMore button*/
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedule.this, stats_activity.class);
                if(fromClass){
                    intent.putExtra(FROM, CLASS);
                    intent.putExtra(CLASS_NAME, className);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Nothing to show now...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*Method to initialise UI Elements and adapter*/
    private void init(){
        recyclerView = findViewById(R.id.timetableRecView);
        textView = findViewById(R.id.classNName);
        showMore = findViewById(R.id.btn_schedule_show);
        adminText = findViewById(R.id.adminText);
    }
}