package com.surya.scheduler.activities.adding_stuffs.admins;

import static com.surya.scheduler.constants.data.ALL_STAFFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.staff_remove_adapter;

import java.util.ArrayList;
import java.util.Arrays;

public class add_admin extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    // adapter for the recycler view
    private staff_remove_adapter adapter;

    // variables
    private ArrayList<String> staffs = new ArrayList<>();

    // back button
    @Override
    public void onBackPressed() {
        // redirecting the user to the MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        setContentView(R.layout.activity_add_admin);

        // method to initialise the UI Elements
        init();

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title of the action bar
        getSupportActionBar().setTitle(R.string.admin_editing);

        // adding the staffs
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        // setting up the adapter
        adapter = new staff_remove_adapter(getApplicationContext(), 1);
        adapter.setAllStaffs(staffs);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // on click listener for the floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // going to the top of the recyclerView
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        recyclerView = findViewById(R.id.admin_recView);
        floatingActionButton = findViewById(R.id.add_admin_to_top);
    }
}