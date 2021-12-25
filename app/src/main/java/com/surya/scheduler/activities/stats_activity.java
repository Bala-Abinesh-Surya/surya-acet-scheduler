package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.CLASS;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FROM;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LAB1;
import static com.surya.scheduler.constants.data.SHORT_FORM_SUBJECTS;
import static com.surya.scheduler.constants.data.SPEAKING;
import static com.surya.scheduler.constants.data.STAFF_NAME;
import static com.surya.scheduler.constants.data.SUBJECTS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.surya.scheduler.R;
import com.surya.scheduler.adapters.class_stats_adapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.class_stats;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;
import java.util.Hashtable;

public class stats_activity extends AppCompatActivity {

    /*UI Elements*/
    private RecyclerView classStatsRecView;
    private ConstraintLayout classStatsLayout;
    private class_stats_adapter class_stats_adapter;
    private TextView classNameText;

    /*variables*/
    private String className;
    private String staffName;
    private boolean fromClass = false;
    private boolean fromStaff = false;
    private ArrayList<class_stats> classStats = new ArrayList<>();

    /*Handling menu click events*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                /*default back button in the action bar*/
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
        setContentView(R.layout.activity_stats);

        /*Getting the intent from the previous activities*/
        Intent intent = getIntent();
        String from = intent.getStringExtra(FROM);

        /*Initialising the UI Elements*/
        init();

        if(from.equals(CLASS)){
            /*activity is called to show the stats of a class*/
            fromClass = true;
            fromStaff = false;

            className = intent.getStringExtra(CLASS_NAME);
            classNameText.setText(className);

            /*Getting the class details*/
            int year = 0;
            String department = className.substring(0, 3);
            int classPosition = 0;

            /*Getting the position of the particular class*/
            for(Class classes : Class.allClasses){
                if(classes.getName().equals(className)){
                    classPosition = Class.allClasses.indexOf(classes);
                    year = classes.getYear();
                    break;
                }
            }

            /*Getting the schedule for the class*/
            Hashtable<String, String[]> classTable = classTable = Class.allClasses.get(classPosition).getSchedule();

            /*hashtable containing number of sessions of each subject*/
            Hashtable<String, Integer> details = new Hashtable<>();
            for(String day : DAYS_OF_THE_WEEK){
                String[] schedule = classTable.get(day);

                for(String period : schedule){
                    if(period.contains("/") && (period.contains(LAB) || period.contains(LAB1))){
                        String[] temp = period.split("/");

                        String sub1 = temp[0];
                        String sub2 = temp[1];

                        if(details.containsKey(sub1)){
                            details.put(sub1, details.get(sub1) + 1);
                        }
                        else{
                            details.put(sub1, 1);
                        }

                        if(details.containsKey(sub2)){
                            details.put(sub2, details.get(sub2) + 1);
                        }
                        else{
                            details.put(sub2, 1);
                        }
                    }
                    else if(period.contains(LAB) || period.contains(LAB1) || period.contains(SPEAKING)){
                        if(details.containsKey(period)){
                            details.put(period, details.get(period) + 1);
                        }
                        else{
                            details.put(period, 1);
                        }
                    }
                    else{
                        if(details.containsKey(period)){
                            details.put(period, details.get(period) + 1);
                        }
                        else{
                            details.put(period, 1);
                        }
                    }
                }
            }

            /*Setting the title of the ActionBar*/
            getSupportActionBar().setTitle(className);

            int previousYear = 0;
            int helper = 0;
            int index = 0;

            /*Gathering enough details for the adapter*/
            for(String rawSubject : SUBJECTS){
                String subject = rawSubject.substring(6);
                int subjectYear = Integer.parseInt(String.valueOf(rawSubject.charAt(4)));
                String subjectDep = rawSubject.substring(0, 3);

                if(previousYear == 0){
                    previousYear = subjectYear;
                }
                else{
                    if(previousYear == subjectYear){
                        helper++;
                    }
                    else{
                        previousYear = subjectYear;
                        helper = 0;
                    }
                }

                if(year == subjectYear){
                    if(department.equals(subjectDep)){
                        /*Getting the particular staff*/
                        for(int m = 0; m < Class.allClasses.get(classPosition).getTeachers().length; m++){
                            if((m - 1) == helper){
                                String staff = Class.allClasses.get(classPosition).getTeachers()[m];

                                /*Getting the particular subject's short form*/
                                String shortForm = SHORT_FORM_SUBJECTS[index].substring(6);

                                int particularPeriodCount = 0;
                                if(details.containsKey(subject)){
                                    particularPeriodCount = details.get(subject);
                                }

                                String staff1 = null;
                                String staff2 = null;

                                String[] temp = staffDetails(staff);
                                staff1 = temp[0];
                                staff2 = temp[1];

                                /*Adding to the arraylist*/
                                if(staff2 == null){
                                    classStats.add(new class_stats(shortForm, staff1, particularPeriodCount));
                                }
                                else{
                                    classStats.add(new class_stats(shortForm, staff1+"/"+staff2, particularPeriodCount));
                                }
                                /*resetting the period count*/
                                particularPeriodCount = 0;
                            }
                        }
                    }
                }

                index++;
            }

            /*setting the necessary things up for the rrecycler view and the adapter*/
            class_stats_adapter = new class_stats_adapter(this);
            class_stats_adapter.setFrom(CLASS);
            class_stats_adapter.setClassStats(classStats);

            classStatsRecView.setLayoutManager(new LinearLayoutManager(this));
            classStatsRecView.setAdapter(class_stats_adapter);
        }

        else{
            /*activity is called to show the stats of a staff*/
            fromStaff = true;
            fromClass = false;

            staffName = intent.getStringExtra(STAFF_NAME);

            /*Setting the title of the ActionBar*/
            getSupportActionBar().setTitle(staffName);
        }

        /*Choosing which layout to show and which one to hide*/
        if(fromClass){
            classStatsLayout.setVisibility(View.VISIBLE);
        }
        else{
            /*from a staff*/
            classStatsLayout.setVisibility(View.GONE);
        }

        /*Enabling the default home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*Method to initialise UI Elements*/
    private void init(){
        classStatsLayout = findViewById(R.id.classStatsLayout);
        classStatsRecView = findViewById(R.id.classStatsRecView);
        classNameText = findViewById(R.id.class_stats_name);
    }

    /*Method to return the staff details*/
    private String[] staffDetails(String teacherCombo){
        String staff1 = null;
        String staff2 = null;

        /*Getting the staff details*/
        for(staff Staff : staff.allStaffs) {
            if (teacherCombo.contains(Staff.getName())) {
                if (staff1 == null) {
                    staff1 = Staff.getName();
                }
                else {
                    if (!Staff.getName().equals(staff1)) {
                        if (staff2 == null) {
                            staff2 = Staff.getName();
                        }
                    }
                }
            }
        }

        return new String[]{staff1, staff2};
    }
}