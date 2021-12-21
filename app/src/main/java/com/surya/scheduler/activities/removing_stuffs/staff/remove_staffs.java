package com.surya.scheduler.activities.removing_stuffs.staff;

import static com.surya.scheduler.constants.data.ALL_STAFFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.staff_remove_adapter;

import java.util.ArrayList;
import java.util.Arrays;

public class remove_staffs extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton floatingActionButton;

    // adapter for the recycler view
    private staff_remove_adapter adapter;

    // variables
    private ArrayList<String> staffs = new ArrayList<>();

    // back button
    @Override
    public void onBackPressed() {
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
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_staffs);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.removing_staffs));

        // displaying the default home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // method to initialise the UI Elements
        init();

        // adding the staffs to the array list
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        // setting up the adapter for the recycler view
        adapter = new staff_remove_adapter(getApplicationContext(), 0);
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
        recyclerView = findViewById(R.id.staff_remove_recView);
        constraintLayout = findViewById(R.id.staff_remove_layout);
        floatingActionButton = findViewById(R.id.remove_staffs_to_top);
    }
}