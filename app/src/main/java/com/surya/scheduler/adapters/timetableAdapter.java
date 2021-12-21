package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.ALLOWED;
import static com.surya.scheduler.constants.data.CLASS_SCHEDULE;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LAB1;
import static com.surya.scheduler.constants.data.NOT_ALLOWED;
import static com.surya.scheduler.constants.data.ROOM_SCHEDULE;
import static com.surya.scheduler.constants.data.SHORT_FORM_SUBJECTS;
import static com.surya.scheduler.constants.data.STAFF_CONSTRAINT_SCHEDULE;
import static com.surya.scheduler.constants.data.STAFF_SCHEDULE;
import static com.surya.scheduler.constants.data.SUBJECTS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.activities.adding_stuffs.staffs.staff_constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class timetableAdapter extends RecyclerView.Adapter{

    private Context context;
    private static int num;

    private ArrayList<String[]> classSchedule = new ArrayList<>();
    private ArrayList<String> positions = new ArrayList<>();

    /*Constructor*/
    public timetableAdapter(Context context, int num) {
        this.context = context;
        timetableAdapter.num = num;
    }

    /*Getter and Setter Methods*/
    public ArrayList<String[]> getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ArrayList<String[]> classSchedule) {
        this.classSchedule = classSchedule;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<String> positions) {
        this.positions = positions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timetable, parent, false);
        return new classTimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*Changing the element's values*/
        if(holder.getClass() == classTimeTableViewHolder.class){

            String[] temp = classSchedule.get(position).clone();

            String period1 = temp[0];
            String period2 = temp[1];
            String period3 = temp[2];
            String period4 = temp[3];
            String period5 = temp[4];
            String period6 = temp[5];
            String period7 = temp[6];
//            String period8 = temp[7];

            if(num == CLASS_SCHEDULE){
                /*Setting the periods for the classes*/
                /*have to check 3 things*/
                /*if period contains FREE change to "-"*/
                /*if period contains "/" and the subject is a lab, do something*/
                /*if it is just a subject, let the period as it is*/

               /* period1 = alterClassPeriods(period1);
                period2 = alterClassPeriods(period2);
                period3 = alterClassPeriods(period3);
                period4 = alterClassPeriods(period4);
                period5 = alterClassPeriods(period5);
                period6 = alterClassPeriods(period6);
                period7 = alterClassPeriods(period7);
                period8 = alterClassPeriods(period8); */


                ((classTimeTableViewHolder) holder).firstPeriod.setText(period1);
                ((classTimeTableViewHolder) holder).secondPeriod.setText(period2);
                ((classTimeTableViewHolder) holder).thirdPeriod.setText(period3);
                ((classTimeTableViewHolder) holder).fourthPeriod.setText(period4);
                ((classTimeTableViewHolder) holder).fifthPeriod.setText(period5);
                ((classTimeTableViewHolder) holder).sixthPeriod.setText(period6);
                ((classTimeTableViewHolder) holder).seventhPeriod.setText(period7);
              //  ((classTimeTableViewHolder) holder).eighthPeriod.setText(period8);
            }

            else{

                if(num == ROOM_SCHEDULE || num == STAFF_SCHEDULE){
                    /*staff schedule*/
                    if(num == STAFF_SCHEDULE){
                        /*Setting the periods*/
                        /*For the staff*/
                        /*have to check for three things*/
                        /*if period is NOT ALLOWED or FREE change to -*/
                        /*if the period contains "/", like CSE-III-A/CSE-III-B change to CSE-III-A/B*/
                        period1 = alterStaffPeriods(period1);
                        period2 = alterStaffPeriods(period2);
                        period3 = alterStaffPeriods(period3);
                        period4 = alterStaffPeriods(period4);
                        period5 = alterStaffPeriods(period5);
                        period6 = alterStaffPeriods(period6);
                        period7 = alterStaffPeriods(period7);
                     //   period8 = alterStaffPeriods(period8);
                    }

                    /*labs schedule*/
                    else if(num == ROOM_SCHEDULE){
                        /*setting the periods for the labs*/
                        /*2 things need to be checked*/
                        /*if period contains NOT ALLOWED or FREE, change to "-*/
                        /*else set the same period as the text*/

                        period1 = alterLabPeriods(period1);
                        period2 = alterLabPeriods(period2);
                        period3 = alterLabPeriods(period3);
                        period4 = alterLabPeriods(period4);
                        period5 = alterLabPeriods(period5);
                        period6 = alterLabPeriods(period6);
                        period7 = alterLabPeriods(period7);
                    //    period8 = alterLabPeriods(period8);
                    }

                    ((classTimeTableViewHolder) holder).firstPeriod.setText(period1);
                    ((classTimeTableViewHolder) holder).secondPeriod.setText(period2);
                    ((classTimeTableViewHolder) holder).thirdPeriod.setText(period3);
                    ((classTimeTableViewHolder) holder).fourthPeriod.setText(period4);
                    ((classTimeTableViewHolder) holder).fifthPeriod.setText(period5);
                    ((classTimeTableViewHolder) holder).sixthPeriod.setText(period6);
                    ((classTimeTableViewHolder) holder).seventhPeriod.setText(period7);
                //    ((classTimeTableViewHolder) holder).eighthPeriod.setText(period8);
                }

                else{
                    // for the staff constraints
                    ((classTimeTableViewHolder) holder).firstPeriod.setText(period1);
                    ((classTimeTableViewHolder) holder).secondPeriod.setText(period2);
                    ((classTimeTableViewHolder) holder).thirdPeriod.setText(period3);
                    ((classTimeTableViewHolder) holder).fourthPeriod.setText(period4);
                    ((classTimeTableViewHolder) holder).fifthPeriod.setText(period5);
                    ((classTimeTableViewHolder) holder).sixthPeriod.setText(period6);
                    ((classTimeTableViewHolder) holder).seventhPeriod.setText(period7);
                  //  ((classTimeTableViewHolder) holder).eighthPeriod.setText(period8);

                    // handling the clicks and changing the texts accordingly
                    ((classTimeTableViewHolder) holder).firstPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).firstPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).firstPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(0, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).firstPeriod.setText(ALLOWED);
                                wholeDayPosition(0, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).secondPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).secondPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).secondPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(1, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).secondPeriod.setText(ALLOWED);
                                wholeDayPosition(1, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).thirdPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).thirdPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).thirdPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(2, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).thirdPeriod.setText(ALLOWED);
                                wholeDayPosition(2, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).fourthPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).fourthPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).fourthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(3, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).fourthPeriod.setText(ALLOWED);
                                wholeDayPosition(3, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).fifthPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).fifthPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).fifthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(4, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).fifthPeriod.setText(ALLOWED);
                                wholeDayPosition(4, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).sixthPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).sixthPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).sixthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(5, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).sixthPeriod.setText(ALLOWED);
                                wholeDayPosition(5, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).seventhPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).seventhPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).seventhPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(6, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).seventhPeriod.setText(ALLOWED);
                                wholeDayPosition(6, ALLOWED, position);
                            }
                        }
                    });

                    ((classTimeTableViewHolder) holder).eighthPeriod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((classTimeTableViewHolder) holder).eighthPeriod.getText().equals(ALLOWED)){
                                ((classTimeTableViewHolder) holder).eighthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                wholeDayPosition(7, context.getString(R.string.not_allowed_stars), position);
                            }
                            else{
                                ((classTimeTableViewHolder) holder).eighthPeriod.setText(ALLOWED);
                                wholeDayPosition(7, ALLOWED, position);
                            }
                        }
                    });

                    // handling the click on the dy text
                    ((classTimeTableViewHolder) holder).dayText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if the first period is ******, changing everything to ALLOWED
                            if(((classTimeTableViewHolder) holder).firstPeriod.getText().equals(context.getString(R.string.not_allowed_stars))){
                                ((classTimeTableViewHolder) holder).firstPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).secondPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).thirdPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).fourthPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).fifthPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).sixthPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).seventhPeriod.setText(ALLOWED);
                                ((classTimeTableViewHolder) holder).eighthPeriod.setText(ALLOWED);

                                wholeDayPosition(holder.getAdapterPosition(), ALLOWED);
                            }
                            else{
                                // setting everything to ******
                                ((classTimeTableViewHolder) holder).firstPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).secondPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).thirdPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).fourthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).fifthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).sixthPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).seventhPeriod.setText(context.getString(R.string.not_allowed_stars));
                                ((classTimeTableViewHolder) holder).eighthPeriod.setText(context.getString(R.string.not_allowed_stars));

                                wholeDayPosition(holder.getAdapterPosition(), context.getString(R.string.not_allowed_stars));
                            }
                        }
                    });
                }
            }

            /*Setting the days text*/
            ((classTimeTableViewHolder) holder).dayText.setText(DAYS_OF_THE_WEEK[holder.getAdapterPosition()]);

            /*Changing the colors of the alternate rows*/
            if(! (position%2 == 0)){
                ((classTimeTableViewHolder) holder).tableRow.setBackgroundColor(context.getColor(R.color.tableHeader));
            }

        }
    }

    @Override
    public int getItemCount() {
        return classSchedule.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(num == CLASS_SCHEDULE){
            return CLASS_SCHEDULE;
        }
        else if(num == STAFF_SCHEDULE){
            return STAFF_SCHEDULE;
        }
        else if(num == STAFF_CONSTRAINT_SCHEDULE){
            return STAFF_CONSTRAINT_SCHEDULE;
        }
        else{
            return ROOM_SCHEDULE;
        }
    }

    /*Usually, there will be two layout files but one common adapter*/
    /*Here I have created only one layout file, so created only one class*/
    /*can create another but not necessary*/
    public class classTimeTableViewHolder extends RecyclerView.ViewHolder{

        private TextView firstPeriod, secondPeriod, thirdPeriod, fourthPeriod, fifthPeriod, sixthPeriod, seventhPeriod, eighthPeriod, dayText;
        private TableRow tableRow;

        public classTimeTableViewHolder(@NonNull View itemView) {
            super(itemView);

            firstPeriod = itemView.findViewById(R.id.firstPeriod);
            secondPeriod = itemView.findViewById(R.id.secondPeriod);
            thirdPeriod = itemView.findViewById(R.id.thirdPeriod);
            fourthPeriod = itemView.findViewById(R.id.fourthPeriod);
            fifthPeriod = itemView.findViewById(R.id.fifthPeriod);
            sixthPeriod = itemView.findViewById(R.id.sixthPeriod);
            seventhPeriod = itemView.findViewById(R.id.seventhPeriod);
            eighthPeriod = itemView.findViewById(R.id.eighthPeriod);

            dayText = itemView.findViewById(R.id.dayText);

            tableRow = itemView.findViewById(R.id.scheduleRow);
        }
    }

    /*method to alter the periods for the classes*/
    private String alterClassPeriods(String period){
        /*if the period is FREE, change to -*/
        if(period.contains(FREE)){
            return "-";
        }

        /*if the period contains "/" && "Laboratory" || "Lab"*/
        /*PAIRED LABS*/
        if((period.contains("/")) && (period.contains(LAB) || period.contains(LAB1))){
            String[] temp = period.split("/");
            String lab1 = temp[0];
            String lab2 = temp[1];

            int count1 = 0;
            int count2 = 0;

            /*getting the short forms of the labs from the SHORT_FORM_SUBJECTS*/
            int index = 0;
            for(String subject : SUBJECTS){
                if(subject.substring(6).contains(LAB) || subject.contains(LAB1)){
                    if(subject.contains(lab1)){
                        count1 = index;
                    }
                }
                if(subject.substring(6).contains(LAB) || subject.contains(LAB1)){
                    if(subject.contains(lab2)){
                        count2 = index;
                    }
                }
                index++;
            }

            /*returning the two short forms divided by /*/
            return SHORT_FORM_SUBJECTS[count1].substring(6)+"/"+SHORT_FORM_SUBJECTS[count2].substring(6);
        }

        /*if the subject is a STAND ALONE lab...*/
        if(period.contains(LAB) || period.contains(LAB1)){
            int index = 0;
            for(String subject : SUBJECTS){
                if((subject.contains(LAB) || subject.contains(LAB1))){
                    if(subject.contains(period)){
                        /*reached the subject*/
                        /*have to return the short form, which is at the same position in SHORT_FORMS_SUBJECTS array*/
                        return SHORT_FORM_SUBJECTS[index].substring(6);
                    }
                }
                index++;
            }
        }

        /*else if just a subject is there, return the short form of the period*/
        else{
            int index = 0;
            for(String subject : SUBJECTS){
                if(! (subject.contains(LAB) || subject.contains(LAB1))){
                    if(subject.contains(period)){
                        /*reached the subject*/
                        /*have to return the short form, which is at the same position in SHORT_FORMS_SUBJECTS array*/
                        return SHORT_FORM_SUBJECTS[index].substring(6);
                    }
                }
                index++;
            }
        }

        /*following return statement wont be reached on any situations*/
        return "";
    }

    /*method to alter the periods for the staffs*/
    private String alterStaffPeriods(String period){
        /*if period is NOT ALLOWED or FREE change to -*/
        if(period.contains(FREE)){
            return "-";
        }
        if(period.contains(NOT_ALLOWED)){
            return "-";
        }
        /*if the period contains "/", like CSE-III-A/CSE-III-B change to CSE-III-A/B*/
        if(period.contains("/")){
            String[] temp = period.split("/");
            /*temp will have two class names, say CSE-III-A and CSE-III-B*/
            /*have to return temp[0] as it is and the last character(section of the class) of temp[1]*/
            return temp[0] + "/" + String.valueOf(temp[1].charAt(temp[1].length() - 1));
        }
        /*if just a class name is there, return the same name*/
        return period;
    }

    /*method to alter the periods for the labs*/
    private String alterLabPeriods(String period){
        /*if period contains NOT ALLOWED or FREE change to -*/
        if(period.contains(NOT_ALLOWED) || period.contains(FREE)){
            return "-";
        }
        /*if just a class name is there, return the string as it is*/
        return period;
    }

    // constraint pointers for a whole day
    private void wholeDayPosition(int position, String value){
        String day = DAYS_OF_THE_WEEK[position];
        positions.add(day+":"+value);
    }

    // constraint pointers for a whole day
    private void wholeDayPosition(int position, String value, int num){
        String day = DAYS_OF_THE_WEEK[num];
        positions.add(day+":"+position+":"+value);
    }
}
