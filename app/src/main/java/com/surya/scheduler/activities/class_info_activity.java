package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.APP_DEFAULTS;
import static com.surya.scheduler.constants.data.CLASS;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.FROM;
import static com.surya.scheduler.constants.data.MODE_OF_SCHEDULES;
import static com.surya.scheduler.constants.data.OFFLINE_MODE;
import static com.surya.scheduler.constants.data.ONLINE_MODE;
import static com.surya.scheduler.constants.data.SUBJECTS;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.surya.scheduler.R;
import com.surya.scheduler.adapters.class_info_adapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;

public class class_info_activity extends AppCompatActivity {

    private ImageView imageView;
    private TextView className;
    private RecyclerView recyclerView;
    private class_info_adapter adapter;
    private TextView classAdvisor, classAdvisor2, classAdvisorText;
    private Button schedule;
    private String classAdvisorName, classAdvisorName2;

    private ArrayList<String> staffs = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();

    private String name;

    /*Handling the menu clicks*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            /*Default back button in the action bar*/
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);

        /*Getting the name of the class through the intent*/
        Intent intent = getIntent();
        name = intent.getStringExtra(CLASS_NAME);

        /*Enabling the back button in the ActionBar*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Setting the title for the Action Bar*/
        getSupportActionBar().setTitle(name);

        /*Initialise UI Elements*/
        init();

        /*Calling the methods to get the values and storing it in respective ArrayLists*/
        getSubjects(name);
        getAllStaffs(name);

        /*Changing the values of the UI Elements*/
        className.setText(name);
        /*classAdvisor name came from getAllStaffs method*/
        classAdvisor.setText(classAdvisorName);
        setImage(name);

        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new class_info_adapter(this, subjects, staffs);
        recyclerView.setAdapter(adapter);

        /*Setting the onclick listener for the schedule button*/
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting the mode of schedules from the shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
                String mode = sharedPreferences.getString(MODE_OF_SCHEDULES, null);

                // opening the normal schedule activity
                Intent intent = new Intent(class_info_activity.this, smart_schedule_activity.class);
                intent.putExtra(CLASS_NAME, name);
                intent.putExtra(FROM, CLASS);
                startActivity(intent);

                /*else{
                    // COACHING_MODE
                    Intent intent = new Intent(class_info_activity.this, coaching_schedule.class);
                    // passing the className as the intent
                    intent.putExtra(CLASS_NAME, name);
                    startActivity(intent);
                }*/
            }
        });
    }

    /*Method to initialise UI Elements and adapter*/
    private void init(){
        imageView = findViewById(R.id.class_info_image);
        recyclerView = findViewById(R.id.class_info_recView);
        className = findViewById(R.id.class_name);
        schedule = findViewById(R.id.class_schedule);
        classAdvisor = findViewById(R.id.class_info_class_advisor);
        classAdvisor2 = findViewById(R.id.class_info_class_advisor2);
        classAdvisorText = findViewById(R.id.class_info_classAdvisorText);

        // hiding the classAdvisor by default
        classAdvisor2.setVisibility(View.GONE);
    }

    /*Method to get the subjects for a class*/
    private void getSubjects(String className){
        for(Class classes : Class.allClasses){
            if(classes.getName().equals(className)){
                int year = classes.getYear();
                String department = classes.getDepartment().substring(0, 3);

                for(String subject : SUBJECTS){
                    if(subject.contains(department)){
                        if(Integer.parseInt(String.valueOf(subject.charAt(4))) == year){
                            //Toast.makeText(getApplicationContext(), String.valueOf(year), Toast.LENGTH_SHORT).show();
                            subjects.add(subject.substring(6));
                        }
                        //break;
                    }
                }
            }
        }
    }

    /*Method to get allStaffs for a class*/
    private void getAllStaffs(String className){
        for(Class classes : Class.allClasses){
            if(classes.getName().equals(className)){
                for(int i = 0; i < classes.getTeachers().length; i++){
                    if(i == 0){
                        /*Do nothing*/
                        /*Class advisor will be in the 0th position*/
                        classAdvisorName = classes.getTeachers()[i];
                    }
                    else{
                        String got = classes.getTeachers()[i];

                        String staff1 = null;
                        String staff2 = null;

                        for(staff Staff : staff.allStaffs){
                            if(got.contains(Staff.getName())){
                                if(staff1 == null){
                                    staff1 = Staff.getName();
                                }
                                else{
                                    staff2 = Staff.getName();
                                }
                            }
                        }

                        if(staff2 == null){
                            staffs.add(staff1);
                        }
                        else{
                            staffs.add(staff1+"/"+staff2);
                        }
                    }
                }
            }
        }
    }

    /*Setting the image in the activity*/
    private void setImage(String className){
        String department = className.substring(0, 3);
        if(department.equals("CSE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cse));
        }
        else if(department.equals("ECE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ece));
        }
        else if(department.equals("MEC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mech));
        }
        else if(department.equals("EEE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.eee));
        }
        else if(department.equals("CIV")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.civil));
        }
        else{
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fyr));
        }
    }
}