package com.surya.scheduler.activities.adding_stuffs.classes;

import static com.surya.scheduler.constants.data.ALL_ROOMS;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.CLASS_ADVISOR_OF;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.CLASS_STRENGTH_TEXT;
import static com.surya.scheduler.constants.data.CONTINUOUS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.DUMMY;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LECTURE;
import static com.surya.scheduler.constants.data.PERIODS;
import static com.surya.scheduler.constants.data.SUBJECTS;
import static com.surya.scheduler.constants.data.SUCCESSFUL_INT_INDICATOR;
import static com.surya.scheduler.constants.data.YEAR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.surya.scheduler.R;
import com.surya.scheduler.adapters.class_staffs_allocating_adapter;
import com.surya.scheduler.models.details.subject_details;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.new_class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class class_staffs_allocating extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private Button next;
    private ConstraintLayout constraintLayout;

    // adapter
    private class_staffs_allocating_adapter adapter;

    // variables
    private String className;
    private int year;
    private int numberOfStudents;
    private ArrayList<String> staffs = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> rooms = new ArrayList<>();
    private String classAdvisor;

    // back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        setContentView(R.layout.activity_class_staffs_allocating);

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.enter_staff_details));

        // getting the class name and year through intent
        Intent intent = getIntent();
        className = intent.getStringExtra(CLASS_NAME);
        year = intent.getIntExtra(YEAR, 0);
        classAdvisor = intent.getStringExtra(CLASS_ADVISOR_OF);
        numberOfStudents = intent.getIntExtra(CLASS_STRENGTH_TEXT, 0);

        // method to initialise the UI elements
        init();

        // subjects array list
        subjects = returnSubjects(year, className.substring(0, 3));

        // setting the class advisor in the new_class object
        setClassAdvisor(classAdvisor.substring(4), subjects.size());

        // adding the staffs to the array list
        staffs.add("Choose the staff");
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        // adding the rooms to the array list
        rooms.add("Choose the Lab");
        for(String room : ALL_ROOMS){
            if(room.contains("LAB")){
                rooms.add(room);
            }
        }

        // setting up the adapter for the recycler view
        adapter = new class_staffs_allocating_adapter(subjects, this, 0);
        adapter.setStaffs(staffs);
        adapter.setRooms(rooms);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // on click listener for the button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adding a class
                //addAClass(className, classAdvisor, year, numberOfStudents);

                // altering the schedules of the newly added classes' department
                //dep_allocate allocate = new dep_allocate(className.substring(0, 3), className);

                //Toast.makeText(getApplicationContext(), "Added a class...", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(class_staffs_allocating.this, MainActivity.class));
                //finishAffinity();
                /*class_staffs_allocating_adapter adapter = new class_staffs_allocating_adapter();
                int helper = adapter.checkDetails(class_staffs_allocating.this);

                if(helper != SUCCESSFUL_INT_INDICATOR){
                    recyclerView.smoothScrollToPosition(helper);
                    Log.d("subject", helper+"");
                }
                else{

                }*/

            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        recyclerView = findViewById(R.id.class_staffs_allocating_recView);
        next = findViewById(R.id.class_staffs_next);
        constraintLayout = findViewById(R.id.class_staffs_allocating_layout);
    }

    // method to return the subjects based on the department and year of the class
    private ArrayList<String> returnSubjects(int year, String department){
        ArrayList<String> subjects = new ArrayList<>();

        // going through the SUBJECTS array list
        for(String subjectx : SUBJECTS){
            String subject = subjectx.substring(6);
            int subjectYear = Integer.parseInt(String.valueOf(subjectx.charAt(4)));
            String subjectDepartment = subjectx.substring(0, 3);

            if(year == subjectYear){
                if(subjectDepartment.equals(department)){
                    subjects.add(subject);
                }
            }
        }

        return subjects;
    }

    // method to set the class advisor in the new_class object
    private void setClassAdvisor(String classAdvisor, int size){
        // temp is of size, subject's size + 1, plus 1 to accommodate the class advisor
        String[] temp = new String[size + 1];

        // setting the class advisor at the 0th position
        temp[0] = classAdvisor;

        new_class newClass = new_class.getInstance();
        newClass.setTeachers(temp);
    }

    // method to add the class, with the gotten details
    private void addAClass(String className, String classAdvisor, int year, int numberOfStudents){
        // creating a Class object
        Class classx = new Class();

        // details required for adding a new class
        /*public Class(String name, String department, int year, int numberOfStudents, String[] teachers, Hashtable<String, String[]> schedule) {
            this.name = name;
            this.department = department;
            this.year = year;
            this.numberOfStudents = numberOfStudents;
            this.teachers = teachers;
            this.schedule = schedule;
        }*/

        // array for the teachers
        String[] teachers = new String[subject_details.getInstance().getSubject_details_list().size() + 1];

        // class name
        classx.setName(className);

        // department
        classx.setDepartment(className.substring(0, 3));

        // year
        classx.setYear(year);

        // class strength
        classx.setNumberOfStudents(numberOfStudents);

        // class advisor
        teachers[0] = classAdvisor;

        // adding the teachers, nature, lab names in the teachers array
        ArrayList<subject_details> subject_details_list = subject_details.getInstance().getSubject_details_list();
        int index = 0;

        for(subject_details details : subject_details_list){
            // details of the subject
            String tag = details.getTag();
            String staff1 = details.getStaff1();
            String staff2 = details.getStaff2();
            String labName = details.getLabName();
            String nature = details.getNature();

            // teacherCombo text, that is to inserted into the teachers array
            String teachersCombo = "";

            if(tag.equals(LECTURE)){
                // labName no need, as it is a LECTURE subject
                // adding staff1
                if(! staff1.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff1;
                }

                // adding staff2
                if(! staff2.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff2;
                }

                // adding the nature text, only if it is continuous
                if(nature.equals(CONTINUOUS)){
                    teachersCombo = teachersCombo + CONTINUOUS;
                }
            }

            else if(tag.equals(LAB)){
                // adding staff1
                if(! staff1.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff1;
                }

                // adding staff2
                if(! staff2.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff2;
                }

                // adding the PAIRED or STAND_ALONE text
                teachersCombo = teachersCombo + nature;

                // adding the labName
                // labName cannot be dummy, as it will be made a labName before reaching this stage
                teachersCombo = teachersCombo + labName;
            }

            else{
                // tag is PLACEMENT
                // labName and the nature is of no use here
                // adding the staff1
                if(! staff1.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff1;
                }

                // adding the staff2
                if(! staff2.equals(DUMMY)){
                    teachersCombo = teachersCombo + staff2;
                }
            }

            // adding the teachersCombo to the teachers array
            teachers[index + 1] = teachersCombo;

            index++;
        }

        // adding the teachers array
        classx.setTeachers(teachers);

        // adding the schedule
        //classx.setSchedule(returnSchedules());

        // adding the classx to the allClasses array list
        Class.allClasses.add(classx);
    }

    // method to return the hash table for the classes
    private Hashtable<String, String[]> returnSchedules(){
        Hashtable<String, String[]> schedule = new Hashtable<>();

        for(String day : DAYS_OF_THE_WEEK){
            schedule.put(day, PERIODS);
        }

        return schedule;
    }
}