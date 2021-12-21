package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.FROM;
import static com.surya.scheduler.constants.data.LABSS;
import static com.surya.scheduler.constants.data.LAB_NAME;
import static com.surya.scheduler.constants.data.ROOMS_REC_VIEW;
import static com.surya.scheduler.constants.data.STAFF;
import static com.surya.scheduler.constants.data.STAFFS_REC_VIEW;
import static com.surya.scheduler.constants.data.STAFF_NAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.schedule;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;

public class all_staffs_recViewAdapter extends RecyclerView.Adapter{

    private ArrayList<staff> allStaffs = new ArrayList<staff>();
    private ArrayList<room> allRooms = new ArrayList<room>();
    private Context context;
    private int pos;

    /*Constructor*/
    public all_staffs_recViewAdapter(Context context, int position) {
        this.context = context;
        this.pos = position;
    }

    /*Getter and Setters*/
    public ArrayList<staff> getAllStaffs() {
        return allStaffs;
    }

    public void setAllStaffs(ArrayList<staff> allStaffs) {
        this.allStaffs = allStaffs;
    }

    public ArrayList<room> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms(ArrayList<room> allRooms) {
        this.allRooms = allRooms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == STAFFS_REC_VIEW){
            View view = LayoutInflater.from(context).inflate(R.layout.all_staffs_list, parent, false);
            return new staffsViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.all_staffs_list, parent, false);
            return new roomsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getClass() == staffsViewHolder.class){
            ((staffsViewHolder) holder).staffName.setText(allStaffs.get(position).getName());
            ((staffsViewHolder) holder).staffDepartment.setText(allStaffs.get(position).getDepartment().substring(0, 3));

            /*Setting the onClick listener for the schedule button*/
            ((staffsViewHolder) holder).schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, schedule.class);
                    intent.putExtra(STAFF_NAME, allStaffs.get(position).getName());
                    intent.putExtra(FROM, STAFF);
                    context.startActivity(intent);
                }
            });
        }
        else if(holder.getClass() == roomsViewHolder.class){
            /*Variable names are same ones which are used for the allStaffs recView*/
            ((roomsViewHolder) holder).staffName.setText(allRooms.get(position).getName());
            ((roomsViewHolder) holder).staffName.setTextSize(17);
            ((roomsViewHolder) holder).staffDepartment.setText(allRooms.get(position).getDepartment().substring(0, 3));

            /*Setting the onClick listener for the schedule button*/
            ((roomsViewHolder) holder).schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, schedule.class);
                    intent.putExtra(LAB_NAME, allRooms.get(position).getName());
                    intent.putExtra(FROM, LABSS);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(pos == STAFFS_REC_VIEW){
            return allStaffs.size();
        }
        return allRooms.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(pos == STAFFS_REC_VIEW){
            return STAFFS_REC_VIEW;
        }
        return ROOMS_REC_VIEW;
    }

    /*class for the all staffs*/
    public class staffsViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private TextView staffName, staffDepartment;
        private Button schedule;

        public staffsViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.all_staffs_layout);
            staffName = itemView.findViewById(R.id.all_staffs_name);
            staffDepartment = itemView.findViewById(R.id.all_staffs_dep);
            schedule = itemView.findViewById(R.id.all_staffs_schedule);
        }
    }

    /*class for the all rooms*/
    public class roomsViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private TextView staffName, staffDepartment;
        private Button schedule;

        public roomsViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.all_staffs_layout);
            staffName = itemView.findViewById(R.id.all_staffs_name);
            staffDepartment = itemView.findViewById(R.id.all_staffs_dep);
            schedule = itemView.findViewById(R.id.all_staffs_schedule);
        }
    }
}
