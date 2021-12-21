package com.surya.scheduler.activities.editing_stuffs.staffs;

import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.CONTINUOUS;
import static com.surya.scheduler.constants.data.PAIRED;
import static com.surya.scheduler.constants.data.STAND_ALONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class swap_time_table extends AppCompatActivity {

    // UI Elements
    private Button swap, choose1, choose2;
    private TextView name1, name2, dep1, dep2, class1, class2;
    private Spinner staff1Spinner, staff2Spinner;

    // variables
    private String staffName1 = null;
    private String staffName2 = null;
    private String className1 = null;
    private String className2 = null;
    private ArrayList<String> staffs = new ArrayList<>();

    // array adapter for the spinners
    private ArrayAdapter<String> adapter;

    /*Handling the menu clicks*/
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

    // back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_time_table);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.swapping_schedules));

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // method to initialise the UI Elements
        init();

        // adding the staffs to the array list
        staffs.add("Choose a staff");
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        // setting up the adapter for the spinners
        adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                staffs
        );

        staff1Spinner.setAdapter(adapter);
        staff2Spinner.setAdapter(adapter);

        // on click listeners for the buttons
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding the button and showing the spinner
                choose1.setVisibility(View.GONE);
                staff1Spinner.setVisibility(View.VISIBLE);
            }
        });

        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding the button and showing the spinner
                choose2.setVisibility(View.GONE);
                staff2Spinner.setVisibility(View.VISIBLE);
            }
        });

        // listeners for both the spinners
        staff1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String gotten = staffs.get(parent.getSelectedItemPosition());

                if(! gotten.equals("Choose a staff")){
                    // hiding the spinner
                    staff1Spinner.setVisibility(View.GONE);

                    // showing the three text views concerned with the staff1
                    name1.setVisibility(View.VISIBLE);
                    dep1.setVisibility(View.VISIBLE);
                    class1.setVisibility(View.VISIBLE);

                    className1 = advisorClass(gotten.substring(4));
                    staffName1 = gotten.substring(4);

                    // displaying the data in the text views
                    name1.setText(staffName1);
                    dep1.setText(gotten.substring(0, 3));
                    if(className1 == null){
                        class1.setText(getString(R.string.not_a_class_advisor));
                    }

                    else{
                        class1.setText("Class Advisor of : "+ className1);
                        class1.setTextColor(getResources().getColor(R.color.errorColor));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        staff2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gotten = staffs.get(parent.getSelectedItemPosition());

                if(! gotten.equals("Choose a staff")){
                    // hiding the spinner
                    staff2Spinner.setVisibility(View.GONE);

                    // showing the three text views concerned with the staff2
                    name2.setVisibility(View.VISIBLE);
                    dep2.setVisibility(View.VISIBLE);
                    class2.setVisibility(View.VISIBLE);

                    className2 = advisorClass(gotten.substring(4));
                    staffName2 = gotten.substring(4);

                    // displaying the data in the text views
                    name2.setText(staffName2);
                    dep2.setText(gotten.substring(0, 3));
                    if(className2 == null){
                        class2.setText(getString(R.string.not_a_class_advisor));
                    }

                    else{
                        class2.setText("Class Advisor of : "+ className2);
                        class2.setTextColor(getResources().getColor(R.color.errorColor));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on click listeners for the button
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(staffName1 != null && staffName2 != null){
                    // checking if the suer selected same staff in both the card views
                    if(staffName1.equals(staffName2)){
                        Toast.makeText(getApplicationContext(), getString(R.string.same_staff), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), getString(R.string.select_diff_staff), Toast.LENGTH_SHORT).show();
                        // redirecting the suer once again to this activity
                        Intent intent = new Intent(getApplicationContext(), swap_time_table.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    else{
                        // the situation is apt for swapping
                        swap(staffName1, staffName2);
                        Toast.makeText(getApplicationContext(), getString(R.string.successfully_swapped), Toast.LENGTH_SHORT).show();
                        // redirecting the user to the MainActivity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
                else{
                    // telling the user to choose a staff
                    Toast.makeText(getApplicationContext(), getString(R.string.select_2_staffs), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        // buttons
        swap = findViewById(R.id.swap_button);
        choose1 = findViewById(R.id.staff_1_button);
        choose2 = findViewById(R.id.staff_2_button);

        // text views
        // for the staff1
        name1 = findViewById(R.id.staff_1_name);
        dep1 = findViewById(R.id.staff_1_dep);
        class1 = findViewById(R.id.staff_1_class);

        // for the staff2
        name2 = findViewById(R.id.staff_2_name);
        dep2 = findViewById(R.id.staff_2_dep);
        class2 = findViewById(R.id.staff_2_class);

        // spinners
        staff1Spinner = findViewById(R.id.staff_1_spinner);
        staff2Spinner = findViewById(R.id.staff_2_spinner);

        // hiding all the text views and the spinners initially
        name1.setVisibility(View.GONE);
        dep1.setVisibility(View.GONE);
        class1.setVisibility(View.GONE);
        name2.setVisibility(View.GONE);
        dep2.setVisibility(View.GONE);
        class2.setVisibility(View.GONE);
        staff1Spinner.setVisibility(View.GONE);
        staff2Spinner.setVisibility(View.GONE);

        // showing the buttons initially
        choose1.setVisibility(View.VISIBLE);
        choose2.setVisibility(View.VISIBLE);
    }

    // method to find if the staff is a class advisor for any class
    private String advisorClass(String staffName){
        String classx = null;
        for(Class classy : Class.allClasses){
            if(classy.getTeachers()[0].equals(staffName)){
                return classy.getName();
            }
        }

        // if the staff is not a class advisor for any classes, return null
        return classx;
    }

    // method to swap the schedules for two staffs
    private void swap(String staffName1, String staffName2){
        // hash table for two staffs
        Hashtable<String, String[]> staffTable1 = new Hashtable<>();
        Hashtable<String, String[]> staffTable2 = new Hashtable<>();

        // position for the staffs
        int staff1Position = 0;
        int staff2Position = 0;

        // temp hash table
        Hashtable<String, String[]> temp = new Hashtable<>();

        // getting the data for the hashtable
        for(staff Staff : staff.allStaffs){
            if(Staff.getName().equals(staffName1)){
                //staffTable1 = Staff.getSchedule();
                staff1Position = staff.allStaffs.indexOf(Staff);
            }
            if(Staff.getName().equals(staffName2)){
                //staffTable2 = Staff.getSchedule();
                staff2Position = staff.allStaffs.indexOf(Staff);
            }
        }

        // first swapping the schedules
        // same way of swapping 2 numbers a, b
        temp = staffTable1;
        staffTable1 = staffTable2;
        staffTable2 = temp;

        // replacing the hashtables in the allStaffs array list
        //staff.allStaffs.get(staff1Position).setSchedule(staffTable1);
        //staff.allStaffs.get(staff2Position).setSchedule(staffTable2);

        // swapping the class advisor role or the subject handling subjects role
        String class1 = advisorClass(staffName1);
        String class2 = advisorClass(staffName2);

        swapClassAdvisor(staffName1, class1, staffName2, class2);
        //swapSubjectHandlingAreas(staffName1, staffName2);

        // creating an instance for the store class
        store s = new store(getApplicationContext());
    }

    // method to swap the class advisor role
    private void swapClassAdvisor(String staffName1, String class1, String staffName2, String class2){
        // there are 3 cases
        // both class1 and class2 are not null
        // class1 = null
        // class2 = null

        for(Class classx : Class.allClasses){
            if(class1 != null && class2 != null){
                if(classx.getName().equals(class1)){
                    // class1 will have staff1 as the class advisor..
                    // changing that to staff2
                    classx.getTeachers()[0] = staffName2;
                }
                if(classx.getName().equals(class2)){
                    // class2 will have staff2 as the class advisor..
                    // changing that to staff1
                    classx.getTeachers()[0] = staffName1;
                }
            }
            else if(class1 != null){
                // class1 will have staff1 as the class advisor
                // changing that to staff2
                // meaning staff1 is no longer a class advisor
                if(classx.getName().equals(class1)){
                    classx.getTeachers()[0] = staffName2;
                }
            }
            else{
                // class2 != null
                // class2 will have staff2 as the class advisor
                // changing that to staff1
                // meaning staff1 is no longer a class advisor
                if(classx.getName().equals(class2)){
                    classx.getTeachers()[0] = staffName1;
                }
            }
        }
    }

    // method to swap the subject handling areas
    private void swapSubjectHandlingAreas(String staffName1, String staffName2){
        // using the same old swapping the two numbers logic
        // changing the staffName1 to staffName2 and staffName2 to "surya" in this iteration
        String temp = "suryaaaaaaaaaaa";

        // there may be number of cases
        // only staffName in the teacherCombo
        // may be staffName along with other staffName
        // contains PAIRED , CONTINUOUS, STAND_ALONE terms.. along with some lab names
        for(Class classx : Class.allClasses){
            for(int i = 1; i < classx.getTeachers().length; i++){
                // ignoring 0, because 0 is allocated to class advisor
                String teacherCombo = classx.getTeachers()[i];

                if(teacherCombo.equals(staffName1)) {
                    // replacing the teacherCombo with temp
                    classx.getTeachers()[i] = temp;
                }

                if(teacherCombo.equals(staffName2)){
                    // replacing the teacherCombo with the staffName1
                    classx.getTeachers()[i] = staffName1;
                }
            }
        }
    }
}