package com.surya.scheduler.activities.adding_stuffs.staffs;

import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.PERIODS;
import static com.surya.scheduler.constants.data.STAFF_DEPARTMENT;
import static com.surya.scheduler.constants.data.STAFF_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class staff_basic_details extends AppCompatActivity {

    // UI Elements
    private TextInputLayout textInputLayout;
    private RadioGroup designationGroup, departmentGroup, constraintsGroup;
    private Button next;

    // variables
    private String staffName = null;
    private String designation = null;
    private String department = null;
    private boolean constraints = false;

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
        setContentView(R.layout.activity_staff_basic_details);

        // enabling the home button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title on the action bar
        getSupportActionBar().setTitle(getString(R.string.enter_basic));

        // method to initialise the UI Elements
        init();

        // item selected listeners
        // designation
        designationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.dr:{
                        designation = getResources().getString(R.string.Dr);
                        break;
                    }
                    case R.id.mr:{
                        designation = getResources().getString(R.string.mr);
                        break;
                    }
                    case R.id.mrs:{
                        designation = getResources().getString(R.string.mrs);
                        break;
                    }
                    case R.id.ms:{
                        designation = getResources().getString(R.string.ms);
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }
        });

        // department
        departmentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.dep_cse:{
                        department = getString(R.string.cseDep).substring(0, 3);
                        break;
                    }
                    case R.id.dep_ece:{
                        department = getString(R.string.eceDep).substring(0, 3);
                        break;
                    }
                    case R.id.dep_mech:{
                        department = getString(R.string.mechDep).substring(0, 3);
                        break;
                    }
                    case R.id.dep_civil:{
                        department = getString(R.string.civilDep).substring(0, 3);
                        break;
                    }
                    case R.id.dep_eee:{
                        department = getString(R.string.eeeDep).substring(0, 3);
                        break;
                    }
                    case R.id.dep_fyr:{
                        department = getString(R.string.firstYear).substring(0, 3);
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }
        });

        // constraints
        constraintsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.yes:{
                        constraints = true;
                        break;
                    }
                    case R.id.no:{
                        constraints = false;
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }
        });

        // on click listener for the button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailsChecker()){
                    if(constraints){
                        // if the staff has any constraints, go to the constraints activity
                        Intent intent = new Intent(getApplicationContext(), staff_constraints.class);
                        intent.putExtra(STAFF_NAME, textInputLayout.getEditText().getText().toString());
                        intent.putExtra(STAFF_DEPARTMENT, department);
                        startActivity(intent);
                    }

                    else{
                        // adding the staff to the current staffs list
                        staffName = textInputLayout.getEditText().getText().toString();

                        // checking if the staff name is already there...,
                        if(isStaffAlreadyThere(staffName)){
                            // if there, showing a toast to the user to change the name
                            Toast.makeText(staff_basic_details.this, getString(R.string.staff_name_already_present), Toast.LENGTH_SHORT).show();
                        }

                        else{
                            /*public staff(String name, String department, boolean hasConstraints, int workingHours, Hashtable<String, String[]> schedule) {
                            this.name = name;
                            this.department = department;
                            this.hasConstraints = hasConstraints;
                            this.workingHours = workingHours;
                            this.schedule = schedule;
                        }*/

                            staff Staff = new staff(
                                    staffName,
                                    department,
                                    constraints,
                                    0,
                                    new Hashtable<>(),
                                    new Hashtable<>() // TODO : change made
                            );

                            staff.allStaffs.add(Staff);

                            // updating the ALL_STAFFS array
                            setAllStaffsList(staffName, department);

                            //Toast.makeText(getApplicationContext(), Staff.getName(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), ALL_STAFFS.length+"", Toast.LENGTH_SHORT).show();
                            //ALL_STAFFS[ALL_STAFFS.length] = staffName;

                            store store = new store(getApplicationContext());

                            // showing the toast to the user
                            Toast.makeText(staff_basic_details.this, "Staff " + staffName + " added successfully", Toast.LENGTH_SHORT).show();

                            // directing the user to the main activity
                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent1);
                            finishAffinity();
                        }
                    }
                }
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        // text input layout
        textInputLayout = findViewById(R.id.staff_name_text);
        // radio groups
        designationGroup = findViewById(R.id.designation_group);
        departmentGroup = findViewById(R.id.department_group);
        constraintsGroup = findViewById(R.id.constraint_group);
        // button
        next = findViewById(R.id.add_the_staff_btn);
    }

    // method to check if the user has provided all the required details
    private boolean detailsChecker(){
        if(textInputLayout.getEditText().getText().length() == 0){
            Toast.makeText(getApplicationContext(), getString(R.string.enter_staff_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(designation == null){
            Toast.makeText(getApplicationContext(), getString(R.string.pick_designation), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(department == null){
            Toast.makeText(getApplicationContext(), getString(R.string.pick_department), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // method to check if the staff name entered is already present of not..
    private boolean isStaffAlreadyThere(String staffName){
        for(String staff : ALL_STAFFS){
            if(staff.substring(4).contains(staffName)){
                return true;
            }
        }

        // returns false, if the staffName is not present
        return false;
    }

    // method to return the empty hashtable for the staffs
    private Hashtable<String, String[]> staffTable(){
        Hashtable<String, String[]> sample = new Hashtable<>();
        for(String day : DAYS_OF_THE_WEEK){
            /*PERIODS = {FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE};*/
            /*Periods array contains every value as FREE*/
            // since the staff has not timing constraints, this is okay
            sample.put(day, PERIODS);
        }
        return sample;
    }

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
}