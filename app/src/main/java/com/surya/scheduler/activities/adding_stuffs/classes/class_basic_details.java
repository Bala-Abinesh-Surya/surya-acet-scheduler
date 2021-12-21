package com.surya.scheduler.activities.adding_stuffs.classes;

import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.CIVIL_DEPARTMENT;
import static com.surya.scheduler.constants.data.CLASS_ADVISOR_OF;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.CLASS_STRENGTH_TEXT;
import static com.surya.scheduler.constants.data.CSE_DEPARTMENT;
import static com.surya.scheduler.constants.data.ECE_DEPARTMENT;
import static com.surya.scheduler.constants.data.EEE_DEPARTMENT;
import static com.surya.scheduler.constants.data.MECH_DEPARTMENT;
import static com.surya.scheduler.constants.data.SH_DEPARTMENT;
import static com.surya.scheduler.constants.data.YEAR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.new_class;

import java.util.ArrayList;
import java.util.Arrays;

public class class_basic_details extends AppCompatActivity {

    // UI Elements
    private RadioGroup yearGroup, sectionGroup, departmentGroup;
    private Button next;
    private Spinner classAdvisorSpinner;
    private EditText strength;
    private ConstraintLayout constraintLayout;

    // Variables
    private String className = null;
    private String department = null;
    private int year = 0;
    private String section = null;
    private int classStrength = 0;
    private String classAdvisor = null;
    private ArrayList<String> staffs;

    // array adapter for the spinners
    private ArrayAdapter<String> adapter;

    // back button
    @Override
    public void onBackPressed() {
        // showing a snack bar to the user
        Snackbar.make(
               constraintLayout,
               getString(R.string.go_back_ask),
               Snackbar.LENGTH_SHORT
        ).setAction(getString(R.string.go_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(class_basic_details.this, MainActivity.class));
                finishAffinity();
            }
        }).show();
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_basic_details);

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.enter_class_basic_details));

        // method to initialise the UI Elements
        init();

        // adding the staffs to the staffs array list
        staffs = new ArrayList<>();
        staffs.add(getString(R.string.choose_the_staff));
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                staffs
        );

        // listeners for the radio groups
        yearGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.class_fourth_year) {
                    year = 4;
                }
                else if (checkedRadioButtonId == R.id.class_third_year) {
                    year = 3;
                }
                else if (checkedRadioButtonId == R.id.class_second_year) {
                    year = 2;
                }
                else if (checkedRadioButtonId == R.id.class_first_year) {
                    year = 1;
                }
            }
        });

        departmentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();

                if (checkedRadioButtonId == R.id.class_cse_dep) {
                    department = CSE_DEPARTMENT.substring(0, 3);
                }
                else if (checkedRadioButtonId == R.id.class_ece_dep) {
                    department = ECE_DEPARTMENT.substring(0, 3);
                }
                else if (checkedRadioButtonId == R.id.class_mech_dep) {
                    department = MECH_DEPARTMENT.substring(0, 3);
                }
                else if (checkedRadioButtonId == R.id.class_civil_dep) {
                    department = CIVIL_DEPARTMENT.substring(0, 3);
                }
                else if (checkedRadioButtonId == R.id.class_eee_dep) {
                    department = EEE_DEPARTMENT.substring(0, 3);
                }
                else if (checkedRadioButtonId == R.id.class_fyr_dep) {
                    department = SH_DEPARTMENT.substring(0, 3);
                }
            }
        });

        sectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.class_section_a) {
                    section = "A";
                }
                else if (checkedRadioButtonId == R.id.class_section_b) {
                    section = "B";
                }
                else if (checkedRadioButtonId == R.id.class_section_c) {
                    section = "C";
                }
                else if (checkedRadioButtonId == R.id.class_section_d) {
                    section = "D";
                }
                else if (checkedRadioButtonId == R.id.class_section_e) {
                    section = "E";
                }
            }
        });

        // spinner for the class advisor
        classAdvisorSpinner.setAdapter(adapter);
        classAdvisorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(! (parent.getSelectedItemPosition() == 0)){
                    classAdvisor = staffs.get(position).substring(4);
                }

                if(parent.getSelectedItemPosition() == 0){
                    classAdvisor = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on click listener for the button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! (strength.getText().length() == 0)){
                    classStrength = Integer.parseInt(String.valueOf(strength.getText()));
                }

                // checking if the user entered all the data
                if(checker(getApplicationContext())){
                    // forming the className
                    className = classNameFormatter(year, department, section);
                    Toast.makeText(getApplicationContext(), className, Toast.LENGTH_SHORT).show();

                    // creating an instance of new_class object
                    new_class newClass = new_class.getInstance();
                    // setting the name, year, department, numberOfStudents and class advisor
                    newClass.setName(className);
                    newClass.setDepartment(department.substring(0, 3));
                    newClass.setNumberOfStudents(classStrength);

                    // redirecting the user to the staffs allocating activity
                    Intent intent = new Intent(class_basic_details.this, class_staffs_allocating.class);
                    // passing className, year, class strength and class advisor as the intents
                    intent.putExtra(CLASS_NAME, className);
                    intent.putExtra(YEAR, year);
                    intent.putExtra(CLASS_ADVISOR_OF, classAdvisor);
                    intent.putExtra(CLASS_STRENGTH_TEXT, classStrength);
                    startActivity(intent);
                }
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        // radio groups
        yearGroup = findViewById(R.id.class_year_group);
        sectionGroup = findViewById(R.id.class_section_group);
        departmentGroup = findViewById(R.id.class_dep_group);

        // button
        next = findViewById(R.id.class_next);

        // spinner
        classAdvisorSpinner = findViewById(R.id.class_advisor_spinner);

        // edit text
        strength = findViewById(R.id.class_strength_text);

        // constraint layout
        constraintLayout = findViewById(R.id.class_basic_details_layout);
    }

    // method to check if the user has given all the necessary details
    private boolean checker(Context context){
        // if any of the fields is empty, return false with a toast message
        if(year == 0){
            Toast.makeText(context, getString(R.string.pick_year), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(department == null){
            Toast.makeText(context, getString(R.string.pick_department), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(section == null){
            Toast.makeText(context, getString(R.string.pick_section), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(classAdvisor == null){
            Toast.makeText(context, getString(R.string.pick_class_advisor), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(classAdvisorChecker(classAdvisor)){
            Toast.makeText(context, classAdvisor + " is already a class advisor", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(classStrength <= 0){
            Toast.makeText(context, getString(R.string.cannot_be_zero), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(classNameFormatter(year, department, section).equals(getString(R.string.class_already_exists))){
            Toast.makeText(context, getString(R.string.class_already_exists), Toast.LENGTH_SHORT).show();
            return false;
        }

        // if the user selected 1st Year and another department and vice-versa, showing error toast
        if((year == 1 && ! (department.equals("FYR"))) || (year != 1 && department.equals("FYR"))){
            Toast.makeText(context, getString(R.string.firstYearsError), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // method to return the class name from the inputs givcn
    private String classNameFormatter(int year, String department, String section){
        String years = "I";
        if(year == 1){
            years = "I";
        }
        else if(year == 2){
            years = "II";
        }
        else if(year == 3){
            years = "III";
        }
        else if(year == 4){
            years = "IV";
        }

        // class class names are in the format : department + "-" + years + "-" + section
        String className = department + "-" + years + "-" + section;

        for(Class classx : Class.allClasses){
            if(classx.getName().equals(className)){
                return getString(R.string.class_already_exists);
            }
        }

        return className;
    }

    // method to check if the staff is a class advisor
    private boolean classAdvisorChecker(String staffName){
        for(Class classx : Class.allClasses){
            if(classx.getTeachers()[0].contains(staffName)){
                return true;
            }
        }

        // returns false, if the staff is not a class advisor
        return false;
    }
}