package com.surya.scheduler.activities.adding_stuffs.admins;

import static com.surya.scheduler.constants.data.ADMINS;
import static com.surya.scheduler.constants.data.ADMINS_ARRAY;
import static com.surya.scheduler.constants.data.STAFF_DEPARTMENT;
import static com.surya.scheduler.constants.data.STAFF_NAME;
import static com.surya.scheduler.constants.data.STATUS_ONLY_ONE;
import static com.surya.scheduler.constants.data.STATUS_SUCCESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.surya.scheduler.R;
import com.surya.scheduler.constants.data;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.storage.store;

public class conform_admin extends AppCompatActivity {

    // UI Elements
    private TextView name, dep, classAdvisor;
    private ImageView imageView;
    private Button promote;

    // variables
    private String staffName = null;
    private String department = null;

    // Back Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), add_admin.class);
        startActivity(intent);
        finishAffinity();
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
        setContentView(R.layout.activity_conform_admin);

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.admin_promotion));

        // method to initialise the UI Elements
        init();

        // getting the intents
        Intent intent = getIntent();
        staffName = intent.getStringExtra(STAFF_NAME);
        department = intent.getStringExtra(STAFF_DEPARTMENT);

        // setting the text of the text views
        name.setText(staffName);
        dep.setText(department);
        setImage(department);
        displayAdvisor(staffName);

        // on click listener for the button
        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdmins(staffName);
                Toast.makeText(getApplicationContext(), staffName + " is an Admin now", Toast.LENGTH_SHORT).show();
                // redirecting the user back to the add_admin activity
                Intent intent1 = new Intent(getApplicationContext(), add_admin.class);
                startActivity(intent1);
                finishAffinity();
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        name = findViewById(R.id.admin_name);
        dep = findViewById(R.id.admin_dep);
        classAdvisor = findViewById(R.id.admin_class);
        imageView = findViewById(R.id.admin_image);
        promote = findViewById(R.id.promote);
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
    }

    // method to display if the staff is a class advisor or not
    private void displayAdvisor(String staffName){
        for(Class classx : Class.allClasses){
            if(classx.getTeachers()[0].contains(staffName)){
                classAdvisor.setText("Class advisor of : " + classx.getName());
                return;
            }
        }

        classAdvisor.setText("Not a class advisor");
    }

    // method to add the given staff to the admins list
    private void updateAdmins(String staffName){
        // updating the ADMINS hashtable
        ADMINS.put(staffName, 1);

        // updating the ADMINS_ARRAY
        String[] temp = new String[ADMINS_ARRAY.length + 1];

        int index = 0;
        for(String admin : ADMINS_ARRAY){
            temp[index] = admin;
            index++;
        }

        temp[temp.length - 1] = staffName;

        data.setAdminsArray(temp);

        // creating an instance for store class
        store store = new store(getApplicationContext());
    }

    // method to remove the staff from the admins list
    public String removeFromAdminsList(String staffName, Context context){
        // checking the size of the ADMINS hashtable size
        if(ADMINS.size() == 1){
            // if size is 1, the staff should be de promoted
            return STATUS_ONLY_ONE;
        }
        else{
            ADMINS.remove(staffName);

            // updating the ADMINS_ARRAY
            String[] temp = new String[ADMINS_ARRAY.length - 1];

            int index = 0;
            for(String admin : ADMINS_ARRAY){
                if(! admin.equals(staffName)){
                    temp[index] = admin;
                    index++;
                }
            }

            data.setAdminsArray(temp);

            // creating an instance for store class
            store store = new store(context
            );
        }

        return STATUS_SUCCESS;
    }
}