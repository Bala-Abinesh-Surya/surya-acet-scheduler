package com.surya.scheduler.activities.adding_stuffs.staffs;

import static com.surya.scheduler.constants.data.ALLOWED;
import static com.surya.scheduler.constants.data.ALLOWED_STRINGS;
import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.MONDAY;
import static com.surya.scheduler.constants.data.NOT_ALLOWED;
import static com.surya.scheduler.constants.data.STAFF_CONSTRAINT_SCHEDULE;
import static com.surya.scheduler.constants.data.STAFF_DEPARTMENT;
import static com.surya.scheduler.constants.data.STAFF_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.timetableAdapter;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class staff_constraints extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private Button add;

    // adapter for the recycler view
    private timetableAdapter adapter;

    private String staffName = null;
    private String department = null;
    private Hashtable<String, String[]> sum = new Hashtable<>();

    // back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // handling the menu clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                /*Do exactly what happens when the back button is pressed*/
                onBackPressed();
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_constraints);

        // method to initialise the UI Elements'
        init();

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // getting the intents
        Intent intent = getIntent();
        staffName = intent.getStringExtra(STAFF_NAME);
        department = intent.getStringExtra(STAFF_DEPARTMENT);

        // setting up the adapter for the recycler view
        adapter = new timetableAdapter(getApplicationContext(), STAFF_CONSTRAINT_SCHEDULE);

        // setting up the arraylist for the adapter
        ArrayList<String[]> sample = new ArrayList<>();
        if(sample.size() != 0){
            sample.clear();
        }
        int index = 0;

        for(String day : DAYS_OF_THE_WEEK){
            sample.add(index, ALLOWED_STRINGS);
            sum.put(day, ALLOWED_STRINGS);
            index++;
        }

        adapter.setClassSchedule(sample);

        // setting up the adapter for the recycler view
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // on click listener for the button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> simple = adapter.getPositions();
                setHashTable(simple);

                // creating an instance for the staff class
                staff Staff = new staff();

                Staff.setName(staffName);
                Staff.setDepartment(department);
                Staff.setHasConstraints(true);
                Staff.setWorkingHours(0);
                //Staff.setSchedule(sum);

                // adding the Staff to the allStaffs arraylist
                staff.allStaffs.add(Staff);

                // updating the ALL_STAFFS array
                setAllStaffsList(staffName, department);

                // creating an instance for the store class
                store store = new store(getApplicationContext());

                // showing the toast to the user
                Toast.makeText(staff_constraints.this, "Staff " + staffName + " added successfully", Toast.LENGTH_SHORT).show();

                // redirecting the user to the MainActivity
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finishAffinity();
            }
        });
    }

    // method to initialise all UI Elements
    private void init(){
        recyclerView = findViewById(R.id.staff_constraint_recView);
        add = findViewById(R.id.add_the_staff);
    }

    // method to update the ALL_STAFFS array
    private void setAllStaffsList(String staffName, String department){
        String[] temp = new String[ALL_STAFFS.length + 1];
        int index = 0;

        for(String staff : ALL_STAFFS){
            temp[index] = staff;
            index++;
        }

        String modifiedName = department + "-" + staffName;
        temp[temp.length - 1] = modifiedName;

        data.setAllStaffs(temp);
    }

    // setting up the hashtable as per the staff's constraint
    private void setHashTable(ArrayList<String> sample){
        for(String x : sample){
            String[] temp = x.split(":");

            String day = null;
            int position = 0;
            String value = null;

            if(temp.length == 2){
                day = temp[0];
                value = temp[1];

                String[] imp = sum.get(day);
                // replacing all the fields in the imp to value
                for(int i = 0; i < imp.length; i++){
                    imp[i] = value;
                }

                // replacing the original array with the modified one
                //sum.replace(day, imp);
            }

            else{
                day = temp[0];
                position = Integer.parseInt(temp[1]);
                value = temp[2];
            }
        }
    }
}