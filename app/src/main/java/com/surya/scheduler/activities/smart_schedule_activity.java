package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.FROM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.surya.scheduler.R;
import com.surya.scheduler.adapters.parent_schedule_adapter;
import com.surya.scheduler.logic.utility;

import java.util.ArrayList;

public class smart_schedule_activity extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private TextView giverName, adminText;

    private parent_schedule_adapter adapter;

    private com.surya.scheduler.logic.utility utility;

    private ArrayList<String[]> schedules = new ArrayList<>();

    // variables
    private String intentName;
    private String intentFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_schedule);

        // method to initialise the UI Elements
        init();

        // getting the extras from the intent
        intentName = getIntent().getStringExtra(CLASS_NAME);
        intentFrom = getIntent().getStringExtra(FROM);

        // getting the schedule
        schedules = utility.returnActualPeriods(utility.returnShortFormClassSchedule(intentName));

        // setting up the adapter for the recycler view
        adapter = new parent_schedule_adapter(this, schedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // method to initialise the UI Elements
    private void init(){
        // recycler view
        recyclerView = findViewById(R.id.parent_rec_view);

        // text views
        giverName = findViewById(R.id.giverName);
        adminText = findViewById(R.id.smart_admin_text);

        // instantiating utility class
        utility = new utility();
    }
}