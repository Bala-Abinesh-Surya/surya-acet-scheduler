package com.surya.scheduler.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.logic.utility;

import java.util.ArrayList;

public class parent_schedule_adapter extends RecyclerView.Adapter<parent_schedule_adapter.parent_class_viewHolder>{

    private Context context;
    private ArrayList<String[]> schedules;
    private com.surya.scheduler.logic.utility utility;

    // Constructor
    public parent_schedule_adapter(Context context, ArrayList<String[]> schedules) {
        this.context = context;
        this.schedules = schedules;
        utility = new utility();
    }

    @NonNull
    @Override
    public parent_class_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_rec_view, parent, false);
        return new parent_class_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull parent_class_viewHolder holder, int position) {
        // manipulating the data
        // in this case setting up the adapter for another recycler view
        String[] periods;
        if(position == 0){
            periods = utility.returnPeriodNumberArray().clone();
        }
        else{
            periods = utility.returnDayedPeriods(schedules.get(position - 1), position - 1).clone();
        }

        child_rec_view_adapter adapter = new child_rec_view_adapter(context, periods);
        holder.recyclerView.setAdapter(adapter);
        // as the subjects have to displayed in one single row, setting as grid layout
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, periods.length));

        // changing the color of the alternate rows
        if(position % 2 == 0){
            holder.recyclerView.setBackgroundColor(Color.rgb(229,204,201));
        }
    }

    @Override
    public int getItemCount() {
        return schedules.size() + 1; // plus 1 for the period number row
    }

    public class parent_class_viewHolder extends RecyclerView.ViewHolder{
        // UI Elements
        private RecyclerView recyclerView;

        public parent_class_viewHolder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI Elements
            recyclerView = itemView.findViewById(R.id.child_rec_view);
        }
    }
}
