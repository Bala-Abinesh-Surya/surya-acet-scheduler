package com.surya.scheduler.activities.removing_stuffs.staff;

import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.STAFF_DEPARTMENT;
import static com.surya.scheduler.constants.data.STAFF_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.staff_remove_adapter;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

public class remove_staffs_conformation extends AppCompatActivity {

    // UI Elements
    private TextView infText1, infText2, infText3, nameText, depText;
    private Button button, back;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;

    // variables
    private String staffName = null;
    private String department = null;

    private String classAdvisoredClass = null;
    private boolean teaching = false;

    // back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), remove_staffs.class);
        startActivity(intent);
        finish();
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_staffs_conformation);

        // getting the staff name and the department through the intent
        Intent intent = getIntent();
        staffName = intent.getStringExtra(STAFF_NAME);
        department = intent.getStringExtra(STAFF_DEPARTMENT);

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.denied));

        // method to initialise the UI Elements
        init();
        setImage(department);

        // changing the text of the text views
        nameText.setText(staffName);
        depText.setText(department);

        // is the staff a class advisor?
        isStaffAClassAdvisor(staffName);

        // is the staff handling subjects for at least one class?
        isStaffTakingClasses(staffName);

        // if the staff does not handle and classes and is not a class advisor also
        if(classAdvisoredClass == null && teaching == false){
            // changing the text of the button
            button.setText(getString(R.string.remove_the_staff));

            infText1.setText(getString(R.string.free_from));
            infText2.setText(getString(R.string.make_sure));

            // hiding the infText3
            infText3.setVisibility(View.GONE);

            // setting the title of the action bar .., changing in fact
            getSupportActionBar().setTitle(getString(R.string.confirmation));

            // changing the background of the layout to green color
            constraintLayout.setBackground(getDrawable(R.drawable.stroke_bg_positive));
        }

        // on click listeners for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classAdvisoredClass == null && teaching == false){
                    // the staff can be deleted
                    removeStaff(staffName);
                    Toast.makeText(getApplicationContext(), staffName + " removed successfully", Toast.LENGTH_SHORT).show();
                    // redirecting the user back to the removing_staffs activity
                    Intent intent1 = new Intent(getApplicationContext(), remove_staffs.class);
                    startActivity(intent1);
                    finishAffinity();
                }
            }
        });

        // back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        // text views
        nameText = findViewById(R.id.remove_staff_name_text);
        depText = findViewById(R.id.remove_staff_department);

        infText1 = findViewById(R.id.inference_text_one);
        infText2 = findViewById(R.id.inference_text_two);
        infText3 = findViewById(R.id.inference_text_three);

        // remove / go back button
        button = findViewById(R.id.remove_staff_conform);
        back = findViewById(R.id.remove_staff_go_back);

        // image view
        imageView = findViewById(R.id.staff_image);

        // constraint layout
        constraintLayout = findViewById(R.id.staff_remove_conform_layout);

        // hiding the inference text 3 and delete button by default
        infText3.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
    }

    /*Setting the image in the activity*/
    private void setImage(String department){
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

    // method to check if the staff is a class advisor
    private void isStaffAClassAdvisor(String staffName){
        for(Class classx : Class.allClasses){
            if(classx.getTeachers()[0].contains(staffName)){
                classAdvisoredClass = classx.getName();
                infText1.setText(getString(R.string.class_advisor) + " " + classAdvisoredClass);
                infText1.setTextColor(getResources().getColor(R.color.errorColor));

                // hiding the delete button
                button.setVisibility(View.GONE);
                // showing the back button
                back.setVisibility(View.VISIBLE);

                return;
            }
        }
    }

    // method to check if the staff is taking any subjects for any classes
    private void isStaffTakingClasses(String staffName){
        for(Class classx : Class.allClasses){
            for(String staff : classx.getTeachers()){
                if(staff.contains(staffName)){
                    // if the staff is not a class advisor, setting the text in infText1
                    if(classAdvisoredClass == null){
                        infText1.setText(getString(R.string.teaching_staff));
                        infText1.setTextColor(getResources().getColor(R.color.errorColor));

                        infText2.setText(R.string.cannot_remove);

                        // hiding the infText3
                        infText3.setVisibility(View.GONE);
                    }
                    // else, setting the text in infText2
                    else{
                        infText2.setText(getString(R.string.teaching_staff));
                        infText2.setTextColor(getResources().getColor(R.color.errorColor));

                        // making the infText3 visible
                        infText3.setVisibility(View.VISIBLE);
                        infText3.setText(R.string.cannot_remove);
                    }

                    teaching = true;
                    return;
                }
            }
        }

        // hiding the back button, if teaching is false
        if(! teaching){
            back.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    // method to remove the staff
    private void removeStaff(String staffName){
        for(staff Staff : staff.allStaffs){
            String name = Staff.getName();
            if(staffName.equals(name)){
                staff.allStaffs.remove(Staff);

                // updating the ALL_STAFFS array
                updateAllStaffs(staffName);

                // creating an instance of store class
                store store = new store(getApplicationContext());

                return;
            }
        }
        //a.notifyDataSetChanged();
    }

    // method to update the ALL_STAFFS array
    private void updateAllStaffs(String staffName){
        String[] temp = new String[ALL_STAFFS.length - 1];
        int index = 0;

        for(String staff : ALL_STAFFS){
            if(! staff.substring(4).equals(staffName)){
                temp[index] = staff;
                index++;
            }
        }

        data.setAllStaffs(temp);
    }
}