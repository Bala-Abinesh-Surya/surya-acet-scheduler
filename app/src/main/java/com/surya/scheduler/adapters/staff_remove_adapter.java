package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.ADMINS;
import static com.surya.scheduler.constants.data.STAFF;
import static com.surya.scheduler.constants.data.STAFF_DEPARTMENT;
import static com.surya.scheduler.constants.data.STAFF_NAME;
import static com.surya.scheduler.constants.data.STATUS_ONLY_ONE;
import static com.surya.scheduler.constants.data.STATUS_SUCCESS;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.adding_stuffs.admins.add_admin;
import com.surya.scheduler.activities.adding_stuffs.admins.conform_admin;
import com.surya.scheduler.activities.removing_stuffs.staff.remove_staffs_conformation;
import com.surya.scheduler.pdfExport.activities.export_a_staff_schedule;

import java.util.ArrayList;

public class staff_remove_adapter extends RecyclerView.Adapter<staff_remove_adapter.staff_remove_ViewHolder>{

    private ArrayList<String> allStaffs = new ArrayList<>();
    private Context context;
    private Integer num;

    // constructor
    public staff_remove_adapter(Context context, Integer num) {
        this.context = context;
        this.num = num;
    }

    public staff_remove_adapter(){

    }

    // getter and setter methods
    public ArrayList<String> getAllStaffs() {
        return allStaffs;
    }

    public void setAllStaffs(ArrayList<String> allStaffs) {
        this.allStaffs = allStaffs;
    }

    @NonNull
    @Override
    public staff_remove_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_staffs_list, parent, false);
        return new staff_remove_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull staff_remove_ViewHolder holder, int position) {
        // changing the values
        holder.staffName.setText(allStaffs.get(position).substring(4));
        holder.department.setText(allStaffs.get(position).substring(0, 3));
        boolean admin = isTheStaffAdmin(allStaffs.get(position).substring(4));

        // setting the text of the button
        if(num == 0){
            // num 0 : from the staff removal activity
            holder.remove.setText(context.getString(R.string.remove));
        }

        // num 1 : from the add_admin activity
        else if(num == 1){
            if(admin){
                holder.remove.setText(context.getString(R.string.remove));
            }
            else{
                holder.remove.setText(context.getString(R.string.add));
            }
        }

        // num 2 : export a staff schedule
        else if(num == 2){
            holder.remove.setText(context.getString(R.string.export_this));
        }

        // on click listener for the button
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num == 0){
                    // passing the staffName and the department as the intents
                    Intent intent = new Intent(context, remove_staffs_conformation.class);
                    intent.putExtra(STAFF_NAME, allStaffs.get(holder.getAdapterPosition()).substring(4));
                    intent.putExtra(STAFF_DEPARTMENT, allStaffs.get(holder.getAdapterPosition()).substring(0, 3));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(num == 1){
                    // if the staff is an admin, have to remove the staff
                    // else, have to promote the staff as the admin
                    if(admin){
                        conform_admin conform_admin = new conform_admin();
                        if(conform_admin.removeFromAdminsList(allStaffs.get(holder.getAdapterPosition()).substring(4), context).equals(STATUS_ONLY_ONE)){
                            Toast.makeText(context, STATUS_ONLY_ONE, Toast.LENGTH_SHORT).show();
                        }

                        else{
                            // status must be STATUS_SUCCESS
                            Toast.makeText(context, STATUS_SUCCESS, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, add_admin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        //context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, conform_admin.class);
                        intent.putExtra(STAFF_NAME, allStaffs.get(holder.getAdapterPosition()).substring(4));
                        intent.putExtra(STAFF_DEPARTMENT, allStaffs.get(holder.getAdapterPosition()).substring(0, 3));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                else if(num == 2){
                    export_a_staff_schedule export_a_staff_schedule = new export_a_staff_schedule(allStaffs.get(position).substring(4), context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allStaffs.size();
    }

    public class staff_remove_ViewHolder extends RecyclerView.ViewHolder{
        // UI Elements
        private TextView staffName, department;
        private Button remove;

        public staff_remove_ViewHolder(@NonNull View itemView) {
            super(itemView);

            staffName = itemView.findViewById(R.id.all_staffs_name);
            department = itemView.findViewById(R.id.all_staffs_dep);
            remove = itemView.findViewById(R.id.all_staffs_schedule);

            // setting the text of the button
            remove.setText(itemView.getContext().getString(R.string.remove));
        }
    }

    // method to tell if the given staff is an admin or not
    private boolean isTheStaffAdmin(String staffName){
        if(ADMINS.containsKey(staffName)){
            return true;
        }
        return false;
    }


}
