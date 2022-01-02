package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;

public class child_rec_view_adapter extends RecyclerView.Adapter<child_rec_view_adapter.child_recView_holder>{

    private Context context;
    private String[] periods;

    // Constructor
    public child_rec_view_adapter(Context context, String[] periods) {
        this.context = context;
        this.periods = periods;
    }

    // getter and setter methods
    public String[] getPeriods() {
        return periods;
    }

    public void setPeriods(String[] periods) {
        this.periods = periods;
    }

    @NonNull
    @Override
    public child_recView_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.smart_period, parent, false);
        return new child_recView_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull child_recView_holder holder, int position) {
        // manipulating the data
        holder.period.setText(periods[position]);
    }

    @Override
    public int getItemCount() {
        return periods.length;
    }

    public class child_recView_holder extends RecyclerView.ViewHolder{
        // UI Elements
        private TextView period;
        private ConstraintLayout constraintLayout;

        public child_recView_holder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI elements
            period = itemView.findViewById(R.id.smart_period);
            constraintLayout = itemView.findViewById(R.id.smart_period_layout);
        }
    }
}
