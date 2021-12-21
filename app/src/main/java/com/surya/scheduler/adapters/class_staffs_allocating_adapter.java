package com.surya.scheduler.adapters;

import static com.surya.scheduler.constants.data.AIDED_DRAWING;
import static com.surya.scheduler.constants.data.CONTINUOUS;
import static com.surya.scheduler.constants.data.DUMMY;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LAB1;
import static com.surya.scheduler.constants.data.LECTURE;
import static com.surya.scheduler.constants.data.LIBRARY;
import static com.surya.scheduler.constants.data.NORMAL;
import static com.surya.scheduler.constants.data.PAIRED;
import static com.surya.scheduler.constants.data.PLACEMENT;
import static com.surya.scheduler.constants.data.SPEAKING;
import static com.surya.scheduler.constants.data.STAND_ALONE;
import static com.surya.scheduler.constants.data.VALUE;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.surya.scheduler.R;
import com.surya.scheduler.models.details.subject_details;
import com.surya.scheduler.models.offline.Class;

import java.util.ArrayList;

public class class_staffs_allocating_adapter extends RecyclerView.Adapter{

    // variables
    private ArrayList<String> subjects = new ArrayList<>();
    private Context context;
    private int num;
    private ArrayList<String> staffs = new ArrayList<>();
    private ArrayList<String> rooms = new ArrayList<>();

    private subject_details subject_details = com.surya.scheduler.models.details.subject_details.getInstance();

    // Array Adapter for the spinner
    private ArrayAdapter<String> adapter, roomAdapter;

    // getters and setters method
    public ArrayList<String> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<String> staffs) {
        this.staffs = staffs;

        // setting up the adapter for the staffs spinner
        setStaffsAdapter(staffs, context);
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;

        // setting up the adapter for the rooms spinner
        setRoomAdapter(rooms, context);

        // clearing the subject_details_list array list for safety
        subject_details.getSubject_details_list().clear();
    }

    // Constructor
    public class_staffs_allocating_adapter(ArrayList<String> subjects, Context context, int num) {
        this.subjects = subjects;
        this.context = context;
        this.num = num;
    }

    public class_staffs_allocating_adapter(){

    }

    // view type
    @Override
    public int getItemViewType(int position) {
        String subject = subjects.get(position);
        if(identifyTag(subject).equals(LECTURE)){
            return 0;
        }
        else if(identifyTag(subject).equals(LAB)){
            return 1;
        }
        else{
            return 3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            // inflating the subject adding layout
            View view = LayoutInflater.from(context).inflate(R.layout.subjects_details_adding_layout, parent, false);
            return new class_subject_staffs_allocating_view_holder(view);
        }
        else if(viewType == 1){
            // inflating the labs adding layout
            View view = LayoutInflater.from(context).inflate(R.layout.labs_details_adding_layout, parent, false);
            return new class_labs_staffs_allocating_view_holder(view);
        }
        else{
            // adding the placement adding layout
            View view = LayoutInflater.from(context).inflate(R.layout.placement_details_adding_layout, parent, false);
            return new class_placement_staffs_allocating_view_holder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // manipulating the data
        if(holder.getClass() == class_subject_staffs_allocating_view_holder.class){
            // for the lecture subjects
            // subject
            ((class_subject_staffs_allocating_view_holder) holder).subjectName.setText(subjects.get(position));
            // spinners
            ((class_subject_staffs_allocating_view_holder) holder).staff1.setAdapter(adapter);
            ((class_subject_staffs_allocating_view_holder) holder).staff2.setAdapter(adapter);

            // listener for the radio group
            // staff2 radio group
            // if yes selected, showing the staff2Text and staff2Spinner
            // else, hiding it
            ((class_subject_staffs_allocating_view_holder) holder).staffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()){
                        case R.id.staff2_yes:{
                            ((class_subject_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.VISIBLE);
                            ((class_subject_staffs_allocating_view_holder) holder).staff2.setVisibility(View.VISIBLE);
                            break;
                        }
                        case R.id.staff2_no:{
                            ((class_subject_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.GONE);
                            ((class_subject_staffs_allocating_view_holder) holder).staff2.setVisibility(View.GONE);

                            // also setting the staff2 text to Dummy, if the user selected any staff2 before
                            setStaffsNames(holder.getAdapterPosition(), DUMMY, 2);

                            // resetting the spinner
                            ((class_subject_staffs_allocating_view_holder) holder).staff2.setAdapter(adapter);

                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            });

            // listener for the constraint radio group
            ((class_subject_staffs_allocating_view_holder) holder).constraintGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(group.getCheckedRadioButtonId() == R.id.subject_normal){
                        // setting the nature as Normal
                        setNature(holder.getAdapterPosition(), NORMAL);
                    }
                    else if(group.getCheckedRadioButtonId() == R.id.subject_continuous){
                        // setting the nature as Continuous
                        setNature(holder.getAdapterPosition(), CONTINUOUS);
                        //Toast.makeText(context, subject_details.getSubject_details_list().get(position).getNature(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // listener for the staff1 spinner
            ((class_subject_staffs_allocating_view_holder) holder).staff1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position, because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 1);
                        //Toast.makeText(context, subject_details.getSubject_details_list().get(position).getStaff1(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // listener for the staff2 spinner
            ((class_subject_staffs_allocating_view_holder) holder).staff2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position, because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 2);
                        //Toast.makeText(context, subject_details.getSubject_details_list().get(position).getStaff2(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /********************************************************************************************************************/
            // details setting section
            // setting the subject name
            setSubjectName(position, subjects.get(position));
            // setting if the subject if it is a lab or not
            setIsItALab(position, false);
            // setting the tag of the subject
            setTag(position, identifyTag(subjects.get(position)));

            //Toast.makeText(context, subject_details.getSubject_details_list().get(position).getStaff1(), Toast.LENGTH_SHORT).show();
        }

        else if(holder.getClass() == class_labs_staffs_allocating_view_holder.class){
            // for the ;ab subjects
            // lab name
            ((class_labs_staffs_allocating_view_holder) holder).labName.setText(subjects.get(position));
            // spinners for the staffs
            ((class_labs_staffs_allocating_view_holder) holder).staff1Spinner.setAdapter(adapter);
            ((class_labs_staffs_allocating_view_holder) holder).staff2Spinner.setAdapter(adapter);
            // spinner for the rooms
            ((class_labs_staffs_allocating_view_holder) holder).roomSpinner.setAdapter(roomAdapter);

            // listener for the radio groups
            // for the staffGroup
            // if the user checks the YES option, showing the staff2Text and staff2Spinner
            // else, hiding it
            ((class_labs_staffs_allocating_view_holder) holder).staffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()){
                        case R.id.staff2_yes:{
                            ((class_labs_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.VISIBLE);
                            ((class_labs_staffs_allocating_view_holder) holder).staff2Spinner.setVisibility(View.VISIBLE);
                            break;
                        }
                        case R.id.staff2_no:{
                            ((class_labs_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.GONE);
                            ((class_labs_staffs_allocating_view_holder) holder).staff2Spinner.setVisibility(View.GONE);

                            // resetting the staff2 to Dummy, in case the user selected staff2 before
                            setStaffsNames(holder.getAdapterPosition(), DUMMY, 2);

                            // resetting the staff2 spinner, by setting the adapter
                            ((class_labs_staffs_allocating_view_holder) holder).staff2Spinner.setAdapter(adapter);

                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            });

            // listener for the staff1 spinner
            ((class_labs_staffs_allocating_view_holder) holder).staff1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // listener for the staff2 spinner
            ((class_labs_staffs_allocating_view_holder) holder).staff2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 2);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // listener for the labs spinner
            ((class_labs_staffs_allocating_view_holder) holder).roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0, because of the Choose the labs text
                        setLabName(holder.getAdapterPosition(), rooms.get(position1));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // for the paired or continuous group
            ((class_labs_staffs_allocating_view_holder) holder).constraintGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(group.getCheckedRadioButtonId() == R.id.labs_paired){
                        setNature(holder.getAdapterPosition(), PAIRED);
                    }
                    else if(group.getCheckedRadioButtonId() == R.id.labs_continuous){
                        setNature(holder.getAdapterPosition(), CONTINUOUS);
                    }
                }
            });

            /********************************************************************************************************************/
            // details setting section
            // setting the lab name
            setSubjectName(position, subjects.get(position));
            // setting the isLab boolean to true
            setIsItALab(position, true);
            // setting the tag of the subject
            setTag(position, identifyTag(subjects.get(position)));
        }

        else if(holder.getClass() == class_placement_staffs_allocating_view_holder.class){
            // for the placement subjects
            // placement subject name
            ((class_placement_staffs_allocating_view_holder) holder).placementName.setText(subjects.get(position));
            // spinners for the staffs
            ((class_placement_staffs_allocating_view_holder) holder).staff1Spinner.setAdapter(adapter);
            ((class_placement_staffs_allocating_view_holder) holder).staff2Spinner.setAdapter(adapter);

            // listener for the radio group
            // if the user checks YES, showing the staff2Text and staff2Spinner
            // else hiding it
            ((class_placement_staffs_allocating_view_holder) holder).staffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()){
                        case R.id.staff2_yes:{
                            ((class_placement_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.VISIBLE);
                            ((class_placement_staffs_allocating_view_holder) holder).staff2Spinner.setVisibility(View.VISIBLE);
                            break;
                        }
                        case R.id.staff2_no:{
                            ((class_placement_staffs_allocating_view_holder) holder).staff2Text.setVisibility(View.GONE);
                            ((class_placement_staffs_allocating_view_holder) holder).staff2Spinner.setVisibility(View.GONE);

                            // setting the staff2 to Dummy, in case the user selected any staff for the staff2
                            setStaffsNames(holder.getAdapterPosition(), DUMMY, 2);

                            // resetting the staff2Spinner by once again setting the adapter
                            ((class_placement_staffs_allocating_view_holder) holder).staff2Spinner.setAdapter(adapter);

                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
            });

            // listener for the staff1 spinner
            ((class_placement_staffs_allocating_view_holder) holder).staff1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // listener for the staff2 spinner
            ((class_placement_staffs_allocating_view_holder) holder).staff2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    if(! (position1 == 0)){
                        // avoiding the 0th position because of the Choose the staff text
                        setStaffsNames(holder.getAdapterPosition(), staffs.get(position1).substring(4), 2);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /********************************************************************************************************************/
            // details setting section
            // setting the placement name
            setSubjectName(position, subjects.get(position));
            // setting the isLab to false
            setIsItALab(position, false);
            // setting the tag of the subject
            setTag(position, identifyTag(subjects.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    // view holder for the lecture subjects
    public class class_subject_staffs_allocating_view_holder extends RecyclerView.ViewHolder{
        // UI Elements
        private TextView subjectName, staff2Text;
        private RadioGroup staffGroup, constraintGroup;
        private ConstraintLayout constraintLayout;
        private Spinner staff1, staff2;

        public class_subject_staffs_allocating_view_holder(@NonNull View itemView) {
            super(itemView);

            // Initialising the UI Elements
            // text views
            subjectName = itemView.findViewById(R.id.subject_name_text);
            staff2Text = itemView.findViewById(R.id.staff2_text);

            // spinners
            staff1 = itemView.findViewById(R.id.subject_staff_spinner_1);
            staff2 = itemView.findViewById(R.id.subject_staff_spinner_2);

            // radio groups
            staffGroup = itemView.findViewById(R.id.subject_staff2_radio_group);
            constraintGroup = itemView.findViewById(R.id.subject_constraint_radio_group);

            // constraint layout
            constraintLayout = itemView.findViewById(R.id.subject_staffs_layout);

            // hiding the staff2Text and staff2 auto complete text view by default
            staff2Text.setVisibility(View.GONE);
            staff2.setVisibility(View.GONE);
        }
    }

    // view holder for the labs subjects
    public class class_labs_staffs_allocating_view_holder extends RecyclerView.ViewHolder{
        // UI Elements
        private TextView labName, staff2Text;
        private Spinner staff1Spinner, staff2Spinner, roomSpinner;
        private RadioGroup staffGroup, constraintGroup;

        public class_labs_staffs_allocating_view_holder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI Elements
            // text views
            labName = itemView.findViewById(R.id.lab_name_text);
            staff2Text = itemView.findViewById(R.id.staff2_text);

            // spinners
            staff1Spinner = itemView.findViewById(R.id.lab_staffs_spinner_1);
            staff2Spinner = itemView.findViewById(R.id.lab_staffs_spinner_2);
            roomSpinner = itemView.findViewById(R.id.lab_rooms_spinner);

            // radio groups
            staffGroup = itemView.findViewById(R.id.lab_staff2_radio_group);
            constraintGroup = itemView.findViewById(R.id.lab_paired_alone_group);

            // hiding the staff2Text and staff2Spinner by default
            staff2Text.setVisibility(View.GONE);
            staff2Spinner.setVisibility(View.GONE);
        }
    }

    // view holder for the placement subjects
    public class class_placement_staffs_allocating_view_holder extends RecyclerView.ViewHolder{
        // UI Elements
        private TextView placementName, staff2Text;
        private RadioGroup staffGroup;
        private Spinner staff1Spinner, staff2Spinner;

        public class_placement_staffs_allocating_view_holder(@NonNull View itemView) {
            super(itemView);

            // initialising the UI Elements
            // text views
            placementName = itemView.findViewById(R.id.placement_name_text);
            staff2Text = itemView.findViewById(R.id.staff2_text);

            // radio group
            staffGroup = itemView.findViewById(R.id.placement_staff2_radio_group);

            // spinners
            staff1Spinner = itemView.findViewById(R.id.placement_staffs_spinner_1);
            staff2Spinner = itemView.findViewById(R.id.placement_staffs_spinner_2);
        }
    }

    // method to set up the adapter for the staffs spinner
    private void setStaffsAdapter(ArrayList<String> staffs, Context context){
        adapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                staffs
        );
    }

    // method to set up the adapter for the rooms spinner
    private void setRoomAdapter(ArrayList<String> rooms, Context context){
        roomAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                rooms
        );
    }

    // method to identify if the subject is a LECTURE or a LAB or a PLACEMENT class
    private String identifyTag(String subject){
        if((subject.contains(LAB)) || (subject.contains(SPEAKING)) || subject.contains(LAB1) || subject.contains(AIDED_DRAWING) || subject.contains("Professional Communication")){
            /*if the subject contains, Laboratory or Lab or Speaking or Aided Drawing then it is a Laboratory*/
            return LAB;
        }
        else{
            if(subject.contains(VALUE) || subject.contains(PLACEMENT) || subject.contains(LIBRARY) || subject.contains("seminar") || subject.contains("Modeling")){
                return PLACEMENT;
            }
            else{
                return LECTURE;
            }
        }
    }

    /****************************************************************************************************************************/
    // methods to set the details for the subjects
    // subject name
    private void setSubjectName(int position, String subjectName){
        subject_details s = new subject_details();
        // creating a dummy subject_details object and adding elementary details for it to be modified later
        s.setSubjectName(subjectName);

        // also adding dummy texts to the staff1 and staff2
        s.setStaff1(DUMMY);
        s.setStaff2(DUMMY);

        // lab names also as dummy
        s.setLabName(DUMMY);

        // setting the nature as Normal if lecture or STAND_ALONE if lab
        if(identifyTag(subjectName).equals(LAB)){
            s.setNature(STAND_ALONE);
        }
        else {
            // for the lecture and the placement subjects
            s.setNature(NORMAL);
        }

        subject_details.getSubject_details_list().add(position, s);
    }

    // setting if the subject is a lab or not in the array list
    private void setIsItALab(int position, boolean value){
        subject_details.getSubject_details_list().get(position).setLab(value);
    }

    // setting the tag of the subject in the array list
    private void setTag(int position, String tag){
        subject_details.getSubject_details_list().get(position).setTag(tag);
    }

    // setting the staff1 and staff2 names in the array list
    private void setStaffsNames(int position, String staffName, int indicator){
        if(indicator == 1){
            // have to set the staff1 name
            subject_details.getSubject_details_list().get(position).setStaff1(staffName);
        }
        else{
            // have to set the staff2 name
            subject_details.getSubject_details_list().get(position).setStaff2(staffName);
        }
    }

    // setting the nature of the subject/lab in the array list
    private void setNature(int position, String nature){
        subject_details.getSubject_details_list().get(position).setNature(nature);
    }

    // setting the lab name in the array list
    private void setLabName(int position, String labName){
        subject_details.getSubject_details_list().get(position).setLabName(labName);
    }

    // method to check if the user inputted all the necessary data, to add a class
    public int checkDetails(Context context){
        int index = 0;

        for(com.surya.scheduler.models.details.subject_details subject_details : subject_details.getSubject_details_list()){
            // subject details
            String subjectName = subject_details.getSubjectName();
            String tag = subject_details.getTag();
            String staff1 = subject_details.getStaff1();
            String staff2 = subject_details.getStaff2();
            String nature = subject_details.getNature();
            String labName = subject_details.getLabName();

            //Toast.makeText(context, subjectName, Toast.LENGTH_SHORT).show();
            if(tag.equals(LAB)){

            }

            else{
                // checking if the staff1 is DUMMY
                if(staff1.equals(DUMMY)){
                    return index;
                }
            }

            index++;
        }

        return index;
    }
}
