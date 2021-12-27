package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.CLASS_NAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.activities.class_info_activity;
import com.surya.scheduler.logic.utility;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.pdfExport.activities.export_a_class_schedule;

import java.util.ArrayList;

public class all_classes_recViewAdapter extends RecyclerView.Adapter<all_classes_recViewAdapter.ViewHolder> {

    private ArrayList<Class> allClasses = new ArrayList<>();
    private Context context;
    private int num = 0;
    private utility utility = new utility();

    /*Constructor*/
    public all_classes_recViewAdapter(Context context, int num){
        this.context = context;
        this.num = num;
    }

    /*Getter and Setter Methods*/
    public ArrayList<Class> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(ArrayList<Class> allClasses) {
        this.allClasses = allClasses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_classes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*Changing the values*/
        holder.className.setText(allClasses.get(position).getName());
        //holder.classAdvisor.setText(allClasses.get(position).getTeachers()[0]);
        holder.strength.setText(String.valueOf(allClasses.get(position).getNumberOfStudents() + " students"));
        holder.department.setText(allClasses.get(position).getDepartment().substring(0, 3));

        /*Changing the imageView based on the department*/
        if(allClasses.get(position).getDepartment().substring(0, 3).equals("CSE")){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.cse));
        }
        else if(allClasses.get(position).getDepartment().substring(0, 3).equals("ECE")){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ece));
        }
        else if(allClasses.get(position).getDepartment().substring(0, 3).equals("MEC")){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.mech));
        }
        else if(allClasses.get(position).getDepartment().substring(0, 3).equals("EEE")){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.eee));
        }
        else if(allClasses.get(position).getDepartment().substring(0, 3).equals("CIV")){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.civil));
        }
        else{
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.fyr));
        }

        // class advsior portion
        String[] temp = utility.subjectStaffDetails(allClasses.get(position).getTeachers()[0]);
        if(temp.length == 1){
            // only one class advisor for the class
            // hiding the classAdvisor2
            holder.classAdvisor2.setVisibility(View.GONE);
            holder.classAdvisor.setText(temp[0]);
        }
        else{
            // there are 2 class advisors
            holder.classAdvisor2.setVisibility(View.VISIBLE);

            holder.classAdvisor.setText(temp[0]);
            holder.classAdvisor2.setText(temp[1]);
        }

        /*Heading to the class info activity, if the user clicks the layout*/
        /*Passing the className as the intent*/
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num == 0){
                    Intent intent = new Intent(context, class_info_activity.class);
                    intent.putExtra(CLASS_NAME, allClasses.get(position).getName());
                    context.startActivity(intent);
                }
                else if(num == 1){
                    // creating an instance for the export_a_class_schedule class
                    export_a_class_schedule export_a_class_schedule = new export_a_class_schedule(allClasses.get(position), context);
                    Toast.makeText(context, allClasses.get(position).getName() + " schedule created....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView className, strength, classAdvisor, department, classAdvisor2;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.all_classes_list_image);
            classAdvisor = itemView.findViewById(R.id.all_classes_class_advisor);
            className = itemView.findViewById(R.id.all_classes_class_name);
            strength = itemView.findViewById(R.id.all_classes_strength);
            department = itemView.findViewById(R.id.all_classes_dep);
            layout = itemView.findViewById(R.id.all_classes_layout);
            classAdvisor2 = itemView.findViewById(R.id.all_classes_class_advisor2);

            // hiding the classAdvisor2 text by default
            classAdvisor2.setVisibility(View.GONE);
        }
    }
}
