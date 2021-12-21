package com.surya.scheduler.fragments;

import static android.app.Activity.RESULT_OK;
import static com.surya.scheduler.constants.data.APP_DEFAULTS;
import static com.surya.scheduler.constants.data.IS_THE_USER_A_ADMIN;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.activities.adding_stuffs.staffs.staff_basic_details;
import com.surya.scheduler.activities.splash_screen;
import com.surya.scheduler.adapters.all_classes_recViewAdapter;
import com.surya.scheduler.models.offline.Class;

public class all_classes_fragment extends Fragment {

    private RecyclerView recyclerView;
    private all_classes_recViewAdapter adapter;
    private FloatingActionButton floatingActionButton;

    public all_classes_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_classes_fragment, container, false);

        init(view);
        adapter = new all_classes_recViewAdapter(view.getContext(), 0);

        adapter.setAllClasses(Class.allClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
       // itemTouchHelper.attachToRecyclerView(recyclerView);

        // on click listener for the floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // going to the top of the recyclerView
                recyclerView.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    // method to initialise the UI Elements
    private void init(View view){
        recyclerView = view.findViewById(R.id.all_classes_recView);
        floatingActionButton = view.findViewById(R.id.class_to_top);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}