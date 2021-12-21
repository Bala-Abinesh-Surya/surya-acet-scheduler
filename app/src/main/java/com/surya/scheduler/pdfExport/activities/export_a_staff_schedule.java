package com.surya.scheduler.pdfExport.activities;

import static com.surya.scheduler.constants.data.ACADEMIC_YEAR;
import static com.surya.scheduler.constants.data.ALL_STAFFS;
import static com.surya.scheduler.constants.data.BREAK_TEXT;
import static com.surya.scheduler.constants.data.CLASSES_HANDLED;
import static com.surya.scheduler.constants.data.CLASS_ADVISOR_OF;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.COLLEGE_NAME;
import static com.surya.scheduler.constants.data.COLLEGE_PLACE;
import static com.surya.scheduler.constants.data.COLON;
import static com.surya.scheduler.constants.data.COORDINATOR;
import static com.surya.scheduler.constants.data.COUNT;
import static com.surya.scheduler.constants.data.DAYS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.DEPARTMENT;
import static com.surya.scheduler.constants.data.DEPARTMENT_CIVIL_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_CSE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_ECE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_EEE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_FIRST_YEAR_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_MECH_TEXT;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.FREE_HOURS_INDICATOR;
import static com.surya.scheduler.constants.data.GENERATED;
import static com.surya.scheduler.constants.data.HOD;
import static com.surya.scheduler.constants.data.HYPHEN;
import static com.surya.scheduler.constants.data.LUNCH;
import static com.surya.scheduler.constants.data.NA;
import static com.surya.scheduler.constants.data.NAME_OF_THE_CLASS_ADVISOR;
import static com.surya.scheduler.constants.data.NAME_OF_THE_STAFF;
import static com.surya.scheduler.constants.data.NOT_ALLOWED;
import static com.surya.scheduler.constants.data.NULL;
import static com.surya.scheduler.constants.data.NUMBER_OF_STUDENTS;
import static com.surya.scheduler.constants.data.OFFLINE_TIMETABLE;
import static com.surya.scheduler.constants.data.PERIODS_NUMBER;
import static com.surya.scheduler.constants.data.PRINCIPAL;
import static com.surya.scheduler.constants.data.SUBJECTS;
import static com.surya.scheduler.constants.data.SUBJECTS_HANDLED;
import static com.surya.scheduler.constants.data.SUBJECT_DETAILS;
import static com.surya.scheduler.constants.data.TIMETABLE_INCHARGE;
import static com.surya.scheduler.constants.data.TIMETABLE_ODD_SEMESTER;
import static com.surya.scheduler.constants.data.TIMETABLE_VERSION;
import static com.surya.scheduler.constants.data.WORKING_HOURS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.MainActivity;
import com.surya.scheduler.adapters.staff_remove_adapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class export_a_staff_schedule extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    // adapter for the recycler view
    private staff_remove_adapter adapter;

    // variables
    private ArrayList<String> staffs = new ArrayList<>();
    int pageWidth = 595;
    int pageHeight = 842;

    int workColor = Color.rgb(229,204,201);
    int color = Color.rgb(255,221,244);
    int red = Color.rgb(255, 0, 0);

    /*Handling the menu clicks*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                /*Do exactly what happens when the back button is pressed*/
                onBackPressed();
                break;
            }
        }
        return true;
    }

    // back button
    @Override
    public void onBackPressed() {
        // redirecting the user back to the Main Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_astaff_schedule);

        // setting the title of the action bar
        getSupportActionBar().setTitle(getString(R.string.export_staff_schedule));

        // enabling the home button'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // method to initialise the UI Elements
        init();

        // adding the staffs
        staffs.addAll(Arrays.asList(ALL_STAFFS));

        // setting up the adapter for the recycler view
        adapter = new staff_remove_adapter(getApplicationContext(), 2);
        recyclerView.setAdapter(adapter);
        adapter.setAllStaffs(staffs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // on click listener for the floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // going to the top of the recyclerView
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    // method to initialise the UI Elements
    private void init(){
        recyclerView = findViewById(R.id.staff_export_recView);
        floatingActionButton = findViewById(R.id.export_staff_to_top);
    }

    // constructor
    public export_a_staff_schedule(){

    }

    // constructor to export all staffs schedules
    public export_a_staff_schedule(ArrayList<staff> allStaffs){
        exportAllStaffsSchedules(allStaffs);
    }

    public export_a_staff_schedule(String staffName, Context context){
        exportAStaffSchedule(staffName);
        Toast.makeText(context, staffName + " schedule generated..", Toast.LENGTH_SHORT).show();
    }

    // method to export a staff schedule
    private void exportAStaffSchedule(String staffName){
        // creating an instance to PdfDocument class
        PdfDocument pdfDocument = new PdfDocument();
        // specifying the height, width and number of pages of the pdf
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // fetching the staff details
        Hashtable<String, String[]> schedule = new Hashtable<>();
        String staffDepartment = null;
        for(staff staffx : staff.allStaffs){
            if(staffx.getName().contains(staffName)){
                staffDepartment = staffx.getDepartment().substring(0, 3);
                schedule = new Hashtable<>();
                break;
            }
        }

        // staff details
        ArrayList<String[]> periods = new ArrayList<>();
        for(String day : DAYS_OF_THE_WEEK){
            periods.add(schedule.get(day));
        }

        // writing to the pdf
        Paint paint = new Paint();
        Canvas canvas = page.getCanvas();

        // creating a border line
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawRect(25, 25, 570, 817, paint);

        // AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12f);
        canvas.drawText(COLLEGE_NAME, pageWidth/2, 43, paint);

        // ERACHAKULAM TEXT
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText(COLLEGE_PLACE, pageWidth/2, 56, paint);

        // department text
        String text = "surya";
        if(staffDepartment.equals("CSE")){
            text = DEPARTMENT_CSE_TEXT;
        }
        else if(staffDepartment.equals("ECE")){
            text = DEPARTMENT_ECE_TEXT;
        }
        else if(staffDepartment.equals("MEC")){
            text = DEPARTMENT_MECH_TEXT;
        }
        else if(staffDepartment.equals("EEE")){
            text = DEPARTMENT_EEE_TEXT;
        }
        else if(staffDepartment.equals("CIV")){
            text = DEPARTMENT_CIVIL_TEXT;
        }
        else if(staffDepartment.equals("FYR")){
            text = DEPARTMENT_FIRST_YEAR_TEXT;
        }

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText(text, pageWidth/2, 75, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(10f);
        canvas.drawText(TIMETABLE_ODD_SEMESTER, pageWidth/2, 90, paint);

        // name, department, class advisor, generated, academic year, working hours text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(9f);
        canvas.drawText(NAME_OF_THE_STAFF, 75, 120, paint);
        canvas.drawText(DEPARTMENT, 75, 135, paint);
        canvas.drawText(CLASS_ADVISOR_OF, 75, 150, paint);

        // colon for the above texts
        canvas.drawText(COLON, 155, 120, paint);
        canvas.drawText(COLON, 155, 135, paint);
        canvas.drawText(COLON, 155, 150, paint);

        // academic year, number of students, generated text
        canvas.drawText(WORKING_HOURS, 310, 120, paint);
        canvas.drawText(ACADEMIC_YEAR, 310, 135, paint);
        canvas.drawText(GENERATED, 310, 150, paint);

        // colon for the above texts
        canvas.drawText(COLON, 385, 120, paint);
        canvas.drawText(COLON, 385, 135, paint);
        canvas.drawText(COLON, 385, 150, paint);

        // displaying the data
        // staffName
        canvas.drawText(staffName, 165, 120, paint);
        // department
        canvas.drawText(staffDepartment, 165, 135, paint);
        // class advisor
        canvas.drawText(classAdvisor(staffName), 165, 150, paint);
        // academic year
        canvas.drawText(NA, 395, 135, paint);
        // generated
        canvas.drawText(LocalDateTime.now().toString(), 395, 150, paint);

        // OFFLINE TIMETABLE TEXT
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText(OFFLINE_TIMETABLE, pageWidth/2, 175, paint);

        // drawing the table
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        int startY = 185;

        for(int i = 0; i < 8; i++){

            // coloring the alternate rows
            if(i % 2 ==0){
                /*for(int j = 1; j < 30; j++){
                    paint.setColor(Color.rgb(229,204,201));
                    canvas.drawLine(51, startY + j, 544, startY + j, paint);
                }*/
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(workColor);
                canvas.drawRect(50, startY, 545, startY + 30, paint);
            }

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);

            canvas.drawLine(50, startY, 545, startY, paint);

            startY += 30;
        }

        for(int i = 0; i < 10; i++){
            int x = 50 + (495/9) * i;
            canvas.drawLine(x, 185, x, startY-30, paint);
        }

        // inserting the days of the week
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText(DAYS, 78, 205, paint);

        for(int i = 1; i <= DAYS_OF_THE_WEEK.length; i++){
            canvas.drawText(DAYS_OF_THE_WEEK[i-1].substring(0, 3), 78, 205 + (30) * i, paint);
        }

        // period numbers
        for(int i = 0 ; i < 8 ; i++){
            canvas.drawText(String.valueOf(i+1), 105 + (495 / 9) * i + 27, 205, paint);
        }

        int y = 235;
        ArrayList<String[]> modified = new ArrayList<>();
        // entering the periods
        for(int i = 0; i < periods.size(); i++){
            String[] tempx = periods.get(i);

            // replacing every period in the staff schedule to displayable format
            for(int j = 0; j < tempx.length; j++){
                tempx[j] = alterStaffPeriods(tempx[j]);
            }

            modified.add(tempx);

            // displaying the periods
            for(int k = 0; k < tempx.length; k++){
                int x = 105 + (495 / 9) * k + 27;
                canvas.drawText(tempx[k], x, y, paint);
            }

            y+=30;
        }

        // working hours text
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(9f);
        canvas.drawText(String.valueOf(returnWorkingHours(modified)), 395, 120, paint);

        // displaying the break and lunch text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(8f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(BREAK_TEXT, 50, 405, paint);
        canvas.drawText(LUNCH, 300, 405, paint);

        // SUBJECTS HANDLED text
        paint.setTextSize(10f);
        canvas.drawText(SUBJECTS_HANDLED, 50, 430, paint);

        ArrayList<String> subjects = subjectsHandled(staffName);
        y = 430;
        // adding the subjects to the pdf
        for(int i = 0; i < subjects.size(); i++){
            // s.no
            canvas.drawText(String.valueOf(i+1)+".", 50, y + 18, paint);
            // subject itself
            canvas.drawText(subjects.get(i).substring(6), 60, y + 18, paint);

            y += 18;
        }

        if(subjects.size() == 0){
            // displaying a null text
            canvas.drawText(NULL, 50, 440, paint);
        }

        // CLASSES HANDLED text
        canvas.drawText(CLASSES_HANDLED, 350, 430, paint);

        ArrayList<String> classes = classesHandled(staffName);
        y = 430;
        // adding the classes to the pdf
        for(int i = 0; i < classes.size(); i++){
            // s.no
            canvas.drawText(String.valueOf(i+1)+".", 350, y + 18, paint);
            // subject itself
            canvas.drawText(classes.get(i), 360, y + 18, paint);

            y += 18;
        }

        if(classes.size() == 0){
            // displaying a null text
            canvas.drawText(NULL, 350, 440, paint);
        }

        // graph
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        //canvas.drawRect(50, 550, 275, 700, paint);
        canvas.drawLine(50, 700, 280, 700, paint);
        canvas.drawLine(50, 700, 50, 545, paint);

        int xv = 275 - 50;
        int yv = 700 - 550;

        // dividing the y-axis into 9 parts
        ArrayList<Integer> yValues = new ArrayList<>();
        for(int i = 0; i <=8; i++){
            int yval = 700 - ((yv / 8) * i);
            yValues.add(yval);
            //canvas.drawLine(50, yval, 545, yval, paint);
        }

        // entering the period numbers vertically
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(8f);
        for(int i = 0; i < yValues.size(); i++){
            canvas.drawText(String.valueOf(i), 40, yValues.get(i) + 2, paint);
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        if(! (subjects.size() == 0)){
            int x = 65;
            for(int i = 1; i <= 6; i++){
                // getting the working hours count
                int count = dailyWorkingHours(modified.get(i-1));
                y = yValues.get(count);

                // the area above the line denotes the free area
                paint.setColor(color);
                // y bottom value - y - 1
                // y top value 551
                /*for(int k = 1; k <= 19; k++){
                    canvas.drawLine(x + k, y - 1, x + k, 551, paint);
                }*/
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x, 550, x+20, y, paint);

                // below denotes the working hours
                paint.setColor(workColor);
                //paint.setStyle(Paint.Style.STROKE);
                // y bottom value - 699
                // top value y + 1
                /*for(int k = 1; k <= 19; k++){
                    canvas.drawLine(x + k, 699, x + k, y + 1, paint);
                }*/
                canvas.drawRect(x, 700, x+20, y, paint);

                // intermediate black line
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                paint.setColor(Color.BLACK);
                canvas.drawLine(x, y, x+20, y, paint);

                // covering rectangle -> black color
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                canvas.drawRect(x, 550, x+20, 700, paint);

                // day text
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(8f);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(DAYS_OF_THE_WEEK[i-1].substring(0, 3), x+10, 710, paint);

                x+=35;
            }

            // drawing the indicator rectangles
            paint.setColor(workColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(65, 720, 75, 730, paint);

            paint.setColor(color);
            canvas.drawRect(65, 740, 75, 750, paint);

            // drawing the Working Hours count and Leisure periods count texts
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(9f);

            canvas.drawText(HYPHEN, 80, 728, paint);
            canvas.drawText(HYPHEN, 80, 748, paint);

            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(WORKING_HOURS, 85, 728, paint);
            canvas.drawText(FREE_HOURS_INDICATOR, 85, 748, paint);

            // period wise count graph
            // drawing the lines
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            canvas.drawLine(350, 700, 550, 700, paint);
            canvas.drawLine(350, 700, 350, 545, paint);

            // dividing the y - axis into 7 parts
            yv = 700 - 550;
            yValues.clear();

            for(int i = 0; i <= 6; i++){
                y = 700 - (yv / 6) * i;
                yValues.add(y);
            }

            // entering the total period counts in the y-axis
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(8f);

            for(int i = 0; i < yValues.size(); i++){
                canvas.drawText(String.valueOf(i), 340, yValues.get(i) + 2, paint);
            }

            xv = 545 - 350;
            ArrayList<Integer> xValues = new ArrayList<>();
            // dividing the x - axis into 8 parts
            for(int i = 1; i <= 8; i++){
                x = 350 + (xv / 8) * i;
                xValues.add(x);
            }

            // periods and counts text
            paint.setTextSize(10f);
            canvas.drawText(PERIODS_NUMBER, 430, 725, paint);

            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(COUNT, 350, 538, paint);

            // getting the counts of each periods
            Hashtable<Integer, Integer> counts = returnPeriodCounts(modified);

            // entering the period numbers in the x - axis
            for(int i = 0; i < xValues.size(); i++){
                canvas.drawText(String.valueOf(i+1), xValues.get(i), 712, paint);
            }

            // getting the y - axis values for each period counts
            ArrayList<Integer> countsYValues = new ArrayList<>();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            for(int i = 1; i <= 8; i++){
                int count = counts.get(i);

                countsYValues.add(yValues.get(count));
            }

            // connecting the dots
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(2);

            for(int i = 1; i < xValues.size(); i++){
                paint.setColor(Color.BLUE);
                int x1 = xValues.get(i-1);
                int y1 = countsYValues.get(i-1);

                int x2 = xValues.get(i);
                int y2 = countsYValues.get(i);

                canvas.drawLine(x1, y1, x2, y2, paint);
            }

            // drawing the circles
            paint.setColor(Color.RED);
            for(int i = 0; i < xValues.size(); i++){
                int x1 = xValues.get(i);
                int y1 = countsYValues.get(i);

                canvas.drawCircle(x1, y1, 1, paint);
            }

            // clearing all the array lists
            yValues.clear();
            countsYValues.clear();
            modified.clear();
            counts.clear();
            xValues.clear();
        }

        // hod, principal, incharge, co-ordinator text
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        canvas.drawText(TIMETABLE_INCHARGE, 119, 790, paint);
        canvas.drawText(HOD, 238, 790, paint);
        canvas.drawText(COORDINATOR, 357, 790, paint);
        canvas.drawText(PRINCIPAL, 476, 790, paint);

        // writing to the external storage
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() +staffName+".pdf";
        // creating a File object
        File file = new File(path);

        // finishing the page
        pdfDocument.finishPage(page);

        // writing the pdf to the file created
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            //Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_LONG).show();
        }
        catch (Exception exception){
            exception.printStackTrace();
            //Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
    }

    // method to export all staffs schedules
    private void exportAllStaffsSchedules(ArrayList<staff> allStaffs){
        // creating an instance to PdfDocument class
        PdfDocument pdfDocument = new PdfDocument();
        // specifying the height, width and number of pages of the pdf

        for(int num = 0; num < allStaffs.size(); num++){
            // fetching the staff details
            Hashtable<String, String[]> schedule = new Hashtable<>();

            schedule = new Hashtable<>();

            // specifying the height, width and number of pages of the pdf
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            String staffName = allStaffs.get(num).getName();
            String staffDepartment = allStaffs.get(num).getDepartment().substring(0, 3);

            // staff details
            ArrayList<String[]> periods = new ArrayList<>();
            for(String day : DAYS_OF_THE_WEEK){
                periods.add(schedule.get(day));
            }

            // writing to the pdf
            Paint paint = new Paint();
            Canvas canvas = page.getCanvas();

            // creating a border line
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            canvas.drawRect(25, 25, 570, 817, paint);

            // AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(12f);
            canvas.drawText(COLLEGE_NAME, pageWidth/2, 43, paint);

            // ERACHAKULAM TEXT
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(COLLEGE_PLACE, pageWidth/2, 56, paint);

            // department text
            String text = null;
            if(staffDepartment.equals("CSE")){
                text = DEPARTMENT_CSE_TEXT;
            }
            else if(staffDepartment.equals("ECE")){
                text = DEPARTMENT_ECE_TEXT;
            }
            else if(staffDepartment.equals("MEC")){
                text = DEPARTMENT_MECH_TEXT;
            }
            else if(staffDepartment.equals("EEE")){
                text = DEPARTMENT_EEE_TEXT;
            }
            else if(staffDepartment.equals("CIV")){
                text = DEPARTMENT_CIVIL_TEXT;
            }
            else if(staffDepartment.equals("FYR")){
                text = DEPARTMENT_FIRST_YEAR_TEXT;
            }

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(text, pageWidth/2, 75, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(10f);
            canvas.drawText(TIMETABLE_ODD_SEMESTER, pageWidth/2, 90, paint);

            // name, department, class advisor, generated, academic year, working hours text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(9f);
            canvas.drawText(NAME_OF_THE_STAFF, 75, 120, paint);
            canvas.drawText(DEPARTMENT, 75, 135, paint);
            canvas.drawText(CLASS_ADVISOR_OF, 75, 150, paint);

            // colon for the above texts
            canvas.drawText(COLON, 155, 120, paint);
            canvas.drawText(COLON, 155, 135, paint);
            canvas.drawText(COLON, 155, 150, paint);

            // academic year, number of students, generated text
            canvas.drawText(WORKING_HOURS, 310, 120, paint);
            canvas.drawText(ACADEMIC_YEAR, 310, 135, paint);
            canvas.drawText(GENERATED, 310, 150, paint);

            // colon for the above texts
            canvas.drawText(COLON, 385, 120, paint);
            canvas.drawText(COLON, 385, 135, paint);
            canvas.drawText(COLON, 385, 150, paint);

            // displaying the data
            // staffName
            canvas.drawText(staffName, 165, 120, paint);
            // department
            canvas.drawText(staffDepartment, 165, 135, paint);
            // class advisor
            canvas.drawText(classAdvisor(staffName), 165, 150, paint);
            // academic year
            canvas.drawText(NA, 395, 135, paint);
            // generated
            canvas.drawText(LocalDateTime.now().toString(), 395, 150, paint);

            // OFFLINE TIMETABLE TEXT
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(OFFLINE_TIMETABLE, pageWidth/2, 175, paint);

            // drawing the table
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            int startY = 185;

            for(int i = 0; i < 8; i++){

                // coloring the alternate rows
                if(i % 2 ==0){
                /*for(int j = 1; j < 30; j++){
                    paint.setColor(Color.rgb(229,204,201));
                    canvas.drawLine(51, startY + j, 544, startY + j, paint);
                }*/
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(workColor);
                    canvas.drawRect(50, startY, 545, startY + 30, paint);
                }

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                paint.setColor(Color.BLACK);

                canvas.drawLine(50, startY, 545, startY, paint);

                startY += 30;
            }

            for(int i = 0; i < 10; i++){
                int x = 50 + (495/9) * i;
                canvas.drawLine(x, 185, x, startY-30, paint);
            }

            // inserting the days of the week
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(DAYS, 78, 205, paint);

            for(int i = 1; i <= DAYS_OF_THE_WEEK.length; i++){
                canvas.drawText(DAYS_OF_THE_WEEK[i-1].substring(0, 3), 78, 205 + (30) * i, paint);
            }

            // period numbers
            for(int i = 0 ; i < 8 ; i++){
                canvas.drawText(String.valueOf(i+1), 105 + (495 / 9) * i + 27, 205, paint);
            }

            int y = 235;
            ArrayList<String[]> modified = new ArrayList<>();
            // entering the periods
            for(int i = 0; i < periods.size(); i++){
                String[] tempx = periods.get(i);

                // replacing every period in the staff schedule to displayable format
                for(int j = 0; j < tempx.length; j++){
                    tempx[j] = alterStaffPeriods(tempx[j]);
                }

                modified.add(tempx);

                // displaying the periods
                for(int k = 0; k < tempx.length; k++){
                    int x = 105 + (495 / 9) * k + 27;
                    canvas.drawText(tempx[k], x, y, paint);
                }

                y+=30;
            }

            // working hours text
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(9f);
            canvas.drawText(String.valueOf(returnWorkingHours(modified)), 395, 120, paint);

            // displaying the break and lunch text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(8f);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(BREAK_TEXT, 50, 405, paint);
            canvas.drawText(LUNCH, 300, 405, paint);

            // SUBJECTS HANDLED text
            paint.setTextSize(10f);
            canvas.drawText(SUBJECTS_HANDLED, 50, 430, paint);

            ArrayList<String> subjects = subjectsHandled(staffName);
            y = 430;
            // adding the subjects to the pdf
            for(int i = 0; i < subjects.size(); i++){
                // s.no
                canvas.drawText(String.valueOf(i+1)+".", 50, y + 18, paint);
                // subject itself
                canvas.drawText(subjects.get(i).substring(6), 60, y + 18, paint);

                y += 18;
            }

            if(subjects.size() == 0){
                // displaying a null text
                canvas.drawText(NULL, 50, 440, paint);
            }

            // CLASSES HANDLED text
            canvas.drawText(CLASSES_HANDLED, 350, 430, paint);

            ArrayList<String> classes = classesHandled(staffName);
            y = 430;
            // adding the classes to the pdf
            for(int i = 0; i < classes.size(); i++){
                // s.no
                canvas.drawText(String.valueOf(i+1)+".", 350, y + 18, paint);
                // subject itself
                canvas.drawText(classes.get(i), 360, y + 18, paint);

                y += 18;
            }

            if(classes.size() == 0){
                // displaying a null text
                canvas.drawText(NULL, 350, 440, paint);
            }

            // graph
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            //canvas.drawRect(50, 550, 275, 700, paint);
            canvas.drawLine(50, 700, 280, 700, paint);
            canvas.drawLine(50, 700, 50, 545, paint);

            int xv = 275 - 50;
            int yv = 700 - 550;

            // dividing the y-axis into 9 parts
            ArrayList<Integer> yValues = new ArrayList<>();
            for(int i = 0; i <=8; i++){
                int yval = 700 - ((yv / 8) * i);
                yValues.add(yval);
                //canvas.drawLine(50, yval, 545, yval, paint);
            }

            // entering the period numbers vertically
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(8f);
            for(int i = 0; i < yValues.size(); i++){
                canvas.drawText(String.valueOf(i), 40, yValues.get(i) + 2, paint);
            }

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            if(! (subjects.size() == 0)){
                int x = 65;
                for(int i = 1; i <= 6; i++){
                    // getting the working hours count
                    int count = dailyWorkingHours(modified.get(i-1));
                    y = yValues.get(count);

                    // the area above the line denotes the free area
                    paint.setColor(color);
                    // y bottom value - y - 1
                    // y top value 551
                /*for(int k = 1; k <= 19; k++){
                    canvas.drawLine(x + k, y - 1, x + k, 551, paint);
                }*/
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(x, 550, x+20, y, paint);

                    // below denotes the working hours
                    paint.setColor(workColor);
                    //paint.setStyle(Paint.Style.STROKE);
                    // y bottom value - 699
                    // top value y + 1
                /*for(int k = 1; k <= 19; k++){
                    canvas.drawLine(x + k, 699, x + k, y + 1, paint);
                }*/
                    canvas.drawRect(x, 700, x+20, y, paint);

                    // intermediate black line
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(1);
                    paint.setColor(Color.BLACK);
                    canvas.drawLine(x, y, x+20, y, paint);

                    // covering rectangle -> black color
                    paint.setColor(Color.BLACK);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(1);
                    canvas.drawRect(x, 550, x+20, 700, paint);

                    // day text
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextSize(8f);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(DAYS_OF_THE_WEEK[i-1].substring(0, 3), x+10, 710, paint);

                    x+=35;
                }

                // drawing the indicator rectangles
                paint.setColor(workColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(65, 720, 75, 730, paint);

                paint.setColor(color);
                canvas.drawRect(65, 740, 75, 750, paint);

                // drawing the Working Hours count and Leisure periods count texts
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLACK);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(9f);

                canvas.drawText(HYPHEN, 80, 728, paint);
                canvas.drawText(HYPHEN, 80, 748, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(WORKING_HOURS, 85, 728, paint);
                canvas.drawText(FREE_HOURS_INDICATOR, 85, 748, paint);

                // period wise count graph
                // drawing the lines
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);

                canvas.drawLine(350, 700, 550, 700, paint);
                canvas.drawLine(350, 700, 350, 545, paint);

                // dividing the y - axis into 7 parts
                yv = 700 - 550;
                yValues.clear();

                for(int i = 0; i <= 6; i++){
                    y = 700 - (yv / 6) * i;
                    yValues.add(y);
                }

                // entering the total period counts in the y-axis
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(8f);

                for(int i = 0; i < yValues.size(); i++){
                    canvas.drawText(String.valueOf(i), 340, yValues.get(i) + 2, paint);
                }

                xv = 545 - 350;
                ArrayList<Integer> xValues = new ArrayList<>();
                // dividing the x - axis into 8 parts
                for(int i = 1; i <= 8; i++){
                    x = 350 + (xv / 8) * i;
                    xValues.add(x);
                }

                // periods and counts text
                paint.setTextSize(10f);
                canvas.drawText(PERIODS_NUMBER, 430, 725, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(COUNT, 350, 538, paint);

                // getting the counts of each periods
                Hashtable<Integer, Integer> counts = returnPeriodCounts(modified);

                // entering the period numbers in the x - axis
                for(int i = 0; i < xValues.size(); i++){
                    canvas.drawText(String.valueOf(i+1), xValues.get(i), 712, paint);
                }

                // getting the y - axis values for each period counts
                ArrayList<Integer> countsYValues = new ArrayList<>();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);

                for(int i = 1; i <= 8; i++){
                    int count = counts.get(i);

                    countsYValues.add(yValues.get(count));
                }

                // connecting the dots
                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(2);

                for(int i = 1; i < xValues.size(); i++){
                    int x1 = xValues.get(i-1);
                    int y1 = countsYValues.get(i-1);

                    int x2 = xValues.get(i);
                    int y2 = countsYValues.get(i);

                    canvas.drawLine(x1, y1, x2, y2, paint);
                }

                // drawing the circles
                paint.setColor(Color.RED);
                for(int i = 0; i < xValues.size(); i++){
                    int x1 = xValues.get(i);
                    int y1 = countsYValues.get(i);

                    canvas.drawCircle(x1, y1, 1, paint);
                }

                // clearing all the array lists
                yValues.clear();
                countsYValues.clear();
                modified.clear();
                counts.clear();
                xValues.clear();
            }

            // hod, principal, incharge, co-ordinator text
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);

            canvas.drawText(TIMETABLE_INCHARGE, 119, 790, paint);
            canvas.drawText(HOD, 238, 790, paint);
            canvas.drawText(COORDINATOR, 357, 790, paint);
            canvas.drawText(PRINCIPAL, 476, 790, paint);

            // finishing the page
            pdfDocument.finishPage(page);
        }

        // writing to the external storage
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() +"All Staffs Schedules"+".pdf";
        // creating a File object
        File file = new File(path);

        // writing the pdf to the file created
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            //Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_LONG).show();
        }
        catch (Exception exception){
            exception.printStackTrace();
            //Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
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

    // method to get the subjects handled by the staff
    private ArrayList<String> subjectsHandled(String staffName){
        ArrayList<String> subjectsHandled = new ArrayList<>();

        // going through all the classes
        for(Class classx : Class.allClasses){
            for(int i = 0; i < classx.getTeachers().length; i++){
                if(i == 0){
                    // do nothing
                    // class advisor column
                }
                else{
                    if(classx.getTeachers()[i].contains(staffName)){
                        // getting the year and department of the class
                        int year = classx.getYear();
                        String department = classx.getDepartment().substring(0, 3);
                        int position = i - 1;

                        String subject = returnSubject(department, year, position);

                        //adding the subject to the array list if it does not exist before
                        if(isTheSubjectAlreadyThere(subjectsHandled, subject)){
                            // do nothing
                        }
                        else{
                            // add the subject to the array list
                            subjectsHandled.add(subject);
                        }
                    }
                }
            }
        }

        return subjectsHandled;
    }

    // method to return the subject
    private String returnSubject(String department, int year, int position){
        // helper variables
        int previousYear = 0;
        int helper = 0;

        for(String subject : SUBJECTS){
            // subject details
            String subjectDepartment = subject.substring(0, 3);
            int subjectYear = Integer.parseInt(String.valueOf(subject.charAt(4)));

            if(previousYear == 0){
                previousYear = subjectYear;
            }
            else{
                if(subjectYear == previousYear){
                    helper++;
                }
                else{
                    helper = 0;
                    previousYear = subjectYear;
                }
            }

            if(subjectYear == year){
                if(subjectDepartment.equals(department)){
                    if(position == helper){
                        // reached the subject handled by the staff
                        // have to return the subject
                        return subject;
                    }
                }
            }
        }

        // happens may be for a new staff
        return null;
    }

    // method to check if a period is in arraylist
    private boolean isTheSubjectAlreadyThere(ArrayList<String> subjects, String subject){
        ArrayList<String> temp = subjects;
        if(temp.size() == 0){
            return false;
        }
        else{
            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i).equals(subject)){
                    return true;
                }
            }
        }

        // returns false, if the subject is not in the array list
        return false;
    }

    // method to return the classes handled by the staff
    private ArrayList<String> classesHandled(String staffName){
        ArrayList<String> classes = new ArrayList<>();
        // going through all the classes
        for(Class classx : Class.allClasses){
            for(int i = 0; i < classx.getTeachers().length; i++){
                if(i == 0){
                    // do nothing
                    // class advisor
                }
                else{
                    String staff = classx.getTeachers()[i];

                    if(staff.contains(staffName)){
                        // have to add to the array list if the class is not present
                        if(isTheSubjectAlreadyThere(classes, classx.getName())){
                            // do nothing
                            // class is already there
                        }
                        else{
                            classes.add(classx.getName());
                        }
                    }
                }
            }
        }

        return classes;
    }

    // method to return the daily working hours of the staff
    private int dailyWorkingHours(String[] schedule){
        int count = 0;
        for(String period : schedule){
            if(! period.equals("-")){
                count++;
            }
        }

        return count;
    }

    // method to return the number of counts of each periods
    private Hashtable<Integer, Integer> returnPeriodCounts(ArrayList<String[]> schedules){
        Hashtable<Integer, Integer> counts = new Hashtable<>();
        // setting up the hashtable
        // putting count as 0 for each periods initially
        for(int i = 1; i <= 8; i++){
            counts.put(i, 0);
        }

        for(int i = 0; i < schedules.size(); i++){
            for(int j = 0; j < schedules.get(i).length; j++){
                String period = schedules.get(i)[j];

                if(! period.equals("-")){
                    // increasing the period number's count by 1
                    int count = counts.get(j+1);
                    count = count + 1;

                    // replacing the original count with the modified ones
                    counts.replace(j + 1, count);
                }
            }
        }

        return counts;
    }

    // method to return the clas for which the staff is a class advisor
    private String classAdvisor(String staffName){
        for(Class classx : Class.allClasses){
            if(classx.getTeachers()[0].contains(staffName)){
                return classx.getName();
            }
        }

        // if not return this..,
        return "Not a class advisor";
    }

    // method to return the working hours of the staff
    private int returnWorkingHours(ArrayList<String[]> schedule){
        int count = 0;
        for(int i = 0; i < schedule.size(); i++){
            for(int j = 0; j < schedule.get(i).length; j++){
                if(! schedule.get(i)[j].equals("-")){
                    count = count + 1;
                }
            }
        }

        return count;
    }
}