package com.surya.scheduler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.removing_stuffs.classes.remove_a_class;
import com.surya.scheduler.models.offline.Class;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class remove_a_class_adapter extends RecyclerView.Adapter{

    // variables
    private ArrayList<Class> classArrayList = new ArrayList<>();
    private Context context;
    private ConstraintLayout constraintLayout2;

    // Constructor
    public remove_a_class_adapter(ArrayList<Class> classArrayList, Context context, ConstraintLayout constraintLayout2) {
        this.classArrayList = classArrayList;
        this.context = context;
        this.constraintLayout2 = constraintLayout2;
    }

    // getter and setter methods
    public ArrayList<Class> getClassArrayList() {
        return classArrayList;
    }

    public void setClassArrayList(ArrayList<Class> classArrayList) {
        this.classArrayList = classArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_classes_list, parent, false);
        return new remove_a_class_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // manipulating the data
        if(holder.getClass() == remove_a_class_view_holder.class){
            // className
            ((remove_a_class_view_holder) holder).className.setText(classArrayList.get(position).getName());

            // department
            ((remove_a_class_view_holder) holder).department.setText(classArrayList.get(position).getDepartment().substring(0, 3));

            // classAdvisor
            ((remove_a_class_view_holder) holder).classAdvisor.setText(classArrayList.get(position).getTeachers()[0]);

            // strength
            String strength = String.valueOf(classArrayList.get(position).getNumberOfStudents()) + " students";
            ((remove_a_class_view_holder) holder).strength.setText(strength);

            // imageView
            setImage(classArrayList.get(position).getName(), ((remove_a_class_view_holder) holder).imageView);

            // on click listener for the constraint layout
            ((remove_a_class_view_holder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove_a_class remove_a_class = new remove_a_class();
                    remove_a_class.showSnackBar(classArrayList.get(position), constraintLayout2, context);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class remove_a_class_view_holder extends RecyclerView.ViewHolder{
        // UI Elements
        private ImageView imageView;
        private TextView className, strength, department, classAdvisor;
        private ConstraintLayout constraintLayout;

        public remove_a_class_view_holder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI Elements
            imageView = itemView.findViewById(R.id.all_classes_list_image);
            className = itemView.findViewById(R.id.all_classes_class_name);
            strength = itemView.findViewById(R.id.all_classes_strength);
            department = itemView.findViewById(R.id.all_classes_dep);
            classAdvisor = itemView.findViewById(R.id.all_classes_class_advisor);
            constraintLayout = itemView.findViewById(R.id.all_classes_layout);
        }
    }

    // setting the imageView based on the class's department
    private void setImage(String className, ImageView imageView){
        String department = className.substring(0, 3);
        if(department.equals("CSE")){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cse));
        }
        else if(department.equals("ECE")){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ece));
        }
        else if(department.equals("MEC")){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.mech));
        }
        else if(department.equals("EEE")){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.eee));
        }
        else if(department.equals("CIV")){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.civil));
        }
        else{
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.fyr));
        }
    }
}
