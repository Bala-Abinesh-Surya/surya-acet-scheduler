package com.surya.scheduler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabItem;
import com.surya.scheduler.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class class_info_adapter extends RecyclerView.Adapter<class_info_adapter.classInfoViewHolder> {

    private Context context;
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> staffs = new ArrayList<>();

    /*Constructor*/
    public class_info_adapter(Context context, ArrayList<String> subjects, ArrayList<String> staffs) {
        this.context = context;
        this.subjects = subjects;
        this.staffs = staffs;
    }

    /*Getters and Setters*/
    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<String> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<String> staffs) {
        this.staffs = staffs;
    }

    @NonNull
    @Override
    public classInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_subjects_staffs, parent, false);
        return new classInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull classInfoViewHolder holder, int position) {
        holder.subject.setText(subjects.get(position));
        holder.staff.setText(staffs.get(position));

        /*Changing the background color of the alternate rows*/
        if(! (position%2 == 0)){
            holder.tableRow.setBackground(context.getDrawable(R.drawable.row_bg));
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class classInfoViewHolder extends RecyclerView.ViewHolder{

        private TextView subject, staff;
        private TableRow tableRow;

        public classInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.class_subjects);
            staff = itemView.findViewById(R.id.class_staffs);
            tableRow = itemView.findViewById(R.id.class_details_row);
        }
    }
}
