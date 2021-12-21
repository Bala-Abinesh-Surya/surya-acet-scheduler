package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.CLASS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.models.offline.class_stats;

import java.util.ArrayList;

public class class_stats_adapter extends RecyclerView.Adapter<class_stats_adapter.classStatsViewHolder> {

    private ArrayList<class_stats> classStats = new ArrayList<>();
    private Context context;
    private String from;

    /*Constructor*/
    public class_stats_adapter(Context context) {
        this.context = context;
    }

    /*Getter and Setter Methods*/
    public ArrayList<class_stats> getClassStats() {
        return classStats;
    }

    public void setClassStats(ArrayList<class_stats> classStats) {
        this.classStats = classStats;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @NonNull
    @Override
    public classStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_stats_list, parent, false);
        return new classStatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull classStatsViewHolder holder, int position) {
        /*Changing the values of the elements*/
        if(from.equals(CLASS)){
            holder.subject.setText(classStats.get(position).getSubject());
            holder.staff.setText(classStats.get(position).getStaff());
            holder.numberOfSessions.setText(String.valueOf(classStats.get(position).getNumberOfSessions()));
        }

        /*changing the color of the alternate rows*/
        if(! (position%2 == 0)){
            holder.tableRow.setBackgroundColor(context.getColor(R.color.tableHeader));
        }
    }

    @Override
    public int getItemCount() {
        if(from.equals(CLASS)){
            return classStats.size();
        }
        return 0;
    }

    public class classStatsViewHolder extends RecyclerView.ViewHolder{

        private TextView subject, staff, numberOfSessions;
        private TableRow tableRow;

        public classStatsViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.classStatsSubject);
            staff = itemView.findViewById(R.id.classStatsStaff);
            numberOfSessions = itemView.findViewById(R.id.classStatsNumber);

            tableRow = itemView.findViewById(R.id.classStatsRow);
        }
    }
}
