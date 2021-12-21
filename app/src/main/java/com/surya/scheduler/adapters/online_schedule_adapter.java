package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.ONLINE_CLASS_SCHEDULE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;

import java.util.ArrayList;

public class online_schedule_adapter extends RecyclerView.Adapter<online_schedule_adapter.online_schedule_viewHolder>{

    private ArrayList<String[]> online_schedules = new ArrayList<>();
    private int num;
    private Context context;

    /*Constructor*/
    public online_schedule_adapter(Context context, int num) {
        this.context = context;
        this.num = num;
    }

    // getters and setters method
    public ArrayList<String[]> getOnline_schedules() {
        return online_schedules;
    }

    public void setOnline_schedules(ArrayList<String[]> online_schedules) {
        this.online_schedules = online_schedules;
    }

    @NonNull
    @Override
    public online_schedule_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_staff_schedule, parent, false);
        return new online_schedule_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull online_schedule_viewHolder holder, int position) {

        if(num == ONLINE_CLASS_SCHEDULE){
            // a class's online schedule
        }

        else{
            // must be a staff's online schedule
        }
    }

    @Override
    public int getItemCount() {
        return online_schedules.size();
    }

    // view holder class
    public class online_schedule_viewHolder extends RecyclerView.ViewHolder{
        // UI elements
        private TextView firstPeriod, secondPeriod, thirdPeriod, fourthPeriod, fifthPeriod, sixthPeriod;
        private TableRow tableRow;

        public online_schedule_viewHolder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI Elements
            tableRow = itemView.findViewById(R.id.onlineScheduleRow);

            firstPeriod = itemView.findViewById(R.id.onlineFirstPeriod);
            secondPeriod = itemView.findViewById(R.id.onlineSecondPeriod);
            thirdPeriod = itemView.findViewById(R.id.onlineThirdPeriod);
            fourthPeriod = itemView.findViewById(R.id.onlineFourthPeriod);
            fifthPeriod = itemView.findViewById(R.id.onlineFifthPeriod);
            sixthPeriod = itemView.findViewById(R.id.onlineSixthPeriod);
        }
    }
}
