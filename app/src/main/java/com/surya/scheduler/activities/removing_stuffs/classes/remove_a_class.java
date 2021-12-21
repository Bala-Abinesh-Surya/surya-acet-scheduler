package com.surya.scheduler.activities.removing_stuffs.classes;

import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FREE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.remove_a_class_adapter;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

import java.util.Hashtable;

public class remove_a_class extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ConstraintLayout constraintLayout;

    // adapter
    private remove_a_class_adapter adapter;

    // back button
    @Override
    public void onBackPressed() {
        // going back to the main activity
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    // handling menu clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //  default back button in the action bar
        if(itemId == android.R.id.home){
            // do what the back button does
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_aclass);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.pick_a_class_to_remove));

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // method to initialise the UI Elements
        init();

        // setting up the adapter for the recycler view
        adapter = new remove_a_class_adapter(Class.allClasses, this, constraintLayout);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // on click listener for the floating action button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // going to the top most position
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        recyclerView = findViewById(R.id.remove_a_class_recView);
        floatingActionButton = findViewById(R.id.remove_a_class_fbtn);
        constraintLayout = findViewById(R.id.remove__a_class_layout);
    }

    // method to show the user a snack bar, to ask for another conformation
    public void showSnackBar(Class classx, ConstraintLayout constraintLayout, Context context){
        // defining the snack bar
        Snackbar.make(constraintLayout,
                "Do you want to remove " + classx.getName() + "?",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // removing the class
                        removeClass(classx);

                        // removing the className from all the staffs schedules
                        removeFromSchedule(classx);

                        // making the changes permanent by changing the sharedPreferences
                        // however the classx will be in sharedPreferences though
                        store store = new store(context);

                        // showing a toast to the user
                        Toast.makeText(context, classx.getName() + " removed successfully", Toast.LENGTH_SHORT).show();

                        // restarting the activity
                        Intent intent = new Intent(context, remove_a_class.class);
                        context.startActivity(intent);
                        finishAffinity();
                    }
                }).show();
    }

    // method to remove the class from the allClassesArrayList
    private void removeClass(Class classx){
        // removing
        Class.allClasses.remove(classx);

        // altering the ALL_ROOMS array
        String[] temp = new String[ALL_ROOMS.length - 1];
        String className = classx.getName();
        boolean reached = false;

        for(int i = 0; i < ALL_ROOMS.length; i++){
            if(reached){
                temp[i - 1] = ALL_ROOMS[i];
            }
            else{
                if(ALL_ROOMS[i].equals(className)){
                    reached = true;
                }
                else{
                    temp[i] = ALL_ROOMS[i];
                }
            }
        }

        // replacing the ALL_ROOMS array with the temp[]
        data.setAllRooms(temp);
    }

    // clearing the className in the staffs' schedule
    private void removeFromSchedule(Class classx){
        // class details
        String className = classx.getName();
        int year = classx.getYear();
        String department = classx.getDepartment().substring(0, 3);

        // going through all the staffs schedules
        for(staff Staff : staff.allStaffs){
            Hashtable<String, String[]> schedule = new Hashtable<>();

            for(String day : DAYS_OF_THE_WEEK){
                String[] temp = schedule.get(day).clone();
                int index = 0;

                for(String periods : temp){
                    if(periods.contains("/")){
                        // this case may be like this, CSE-III-A/CSE-III-B (hod sir's)
                        // fetching the details of the two classes present in the staff's period
                        String department1 = periods.substring(0, 3);

                        int yearx = 0;
                        String[] tempx = periods.split("-");
                        switch (tempx[1]) {
                            case "IV":{
                                yearx = 4;
                                break;
                            }
                            case "III":{
                                yearx = 3;
                                break;
                            }
                            case "II":{
                                yearx = 2;
                                break;
                            }
                            default:{
                                yearx = 1;
                                break;
                            }
                        }

                        // two classes present
                        String[] tempo = periods.split("/");
                        String className1 = tempo[0];
                        String className2 = tempo[1];

                        // checking now
                        if(department.equals(department1)){
                            if(year == yearx){
                                // either one of them only be true, if the logic reached thus far
                                // let's say the period is CSE-III-A/CSE-III-B
                                // B section needs to be removed (consider)
                                // then making the period as CSE-III-A
                                if(className.equals(className1)){
                                    // setting the period to className2
                                    temp[index] = className2;
                                }

                                if(className.equals(className2)){
                                    // setting the period to className1
                                    temp[index] = className1;
                                }
                            }
                        }
                    }
                    else{
                        // changing the period to FREE, only if the period name is className
                        // else doing nothing
                        if(periods.equals(className)){
                            // setting it to FREE
                            temp[index] = FREE;
                        }
                    }

                    index++;
                }

                // replacing the modified array with the original ones
                schedule.replace(day, temp);
            }
        }
    }

    // TODO : inu matha classes la poitu teachersCombo la remove pana pora className irukaa nu check panra method podanum
    // TODO : may be in PAIRED cases la apdi irukalam... like CSE-III-A teachersCombo la CSE-III-B irukum.. atha remove pana method venum
}