package com.surya.scheduler.fragments;

import static com.surya.scheduler.constants.data.STAFFS_REC_VIEW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.surya.scheduler.R;
import com.surya.scheduler.adapters.all_staffs_recViewAdapter;
import com.surya.scheduler.models.offline.staff;

public class all_staffs_fragment extends Fragment {

    private RecyclerView recyclerView;
    private all_staffs_recViewAdapter adapter;
    private FloatingActionButton floatingActionButton;

    public all_staffs_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_staffs_fragment, container, false);

        /*Method to initialise UI elements and adapter*/
        init(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setAllStaffs(staff.allStaffs);

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

    /*Initialising the UI Elements*/
    private void init(View view){
        recyclerView = view.findViewById(R.id.all_staffs_recView);
        adapter = new all_staffs_recViewAdapter(view.getContext(), STAFFS_REC_VIEW);
        floatingActionButton = view.findViewById(R.id.staffs_to_top);
    }
}