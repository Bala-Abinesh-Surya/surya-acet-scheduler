package com.surya.scheduler.pdfExport.activities;

import static com.surya.scheduler.constants.data.ABBREVIATION;
import static com.surya.scheduler.constants.data.ACADEMIC_YEAR;
import static com.surya.scheduler.constants.data.BREAK_TEXT;
import static com.surya.scheduler.constants.data.CLASS_NAME;
import static com.surya.scheduler.constants.data.CLASS_NAME_DF;
import static com.surya.scheduler.constants.data.COLLEGE_NAME;
import static com.surya.scheduler.constants.data.COLLEGE_PLACE;
import static com.surya.scheduler.constants.data.COLON;
import static com.surya.scheduler.constants.data.COORDINATOR;
import static com.surya.scheduler.constants.data.DAYS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.DEPARTMENT_CIVIL_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_CSE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_ECE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_EEE_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_FIRST_YEAR_TEXT;
import static com.surya.scheduler.constants.data.DEPARTMENT_MECH_TEXT;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.GENERATED;
import static com.surya.scheduler.constants.data.HOD;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LAB1;
import static com.surya.scheduler.constants.data.LUNCH;
import static com.surya.scheduler.constants.data.NA;
import static com.surya.scheduler.constants.data.NAME_OF_THE_CLASS_ADVISOR;
import static com.surya.scheduler.constants.data.NUMBER_OF_STUDENTS;
import static com.surya.scheduler.constants.data.OFFLINE_TIMETABLE;
import static com.surya.scheduler.constants.data.PRINCIPAL;
import static com.surya.scheduler.constants.data.SESSIONS;
import static com.surya.scheduler.constants.data.SHORT_FORM_SUBJECTS;
import static com.surya.scheduler.constants.data.SPEAKING;
import static com.surya.scheduler.constants.data.SUBJECTS;
import static com.surya.scheduler.constants.data.SUBJECT_DETAILS;
import static com.surya.scheduler.constants.data.SUBJECT_NAME;
import static com.surya.scheduler.constants.data.SUBJECT_STAFFS;
import static com.surya.scheduler.constants.data.S_NO;
import static com.surya.scheduler.constants.data.TIMETABLE_INCHARGE;
import static com.surya.scheduler.constants.data.TIMETABLE_ODD_SEMESTER;
import static com.surya.scheduler.constants.data.TIMETABLE_VERSION;
import static com.surya.scheduler.constants.data.YEAR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.surya.scheduler.adapters.all_classes_recViewAdapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.staff;

import java.io.File;
import java.io.FileOutputStream;
import java.security.cert.CertificateEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class export_a_class_schedule extends AppCompatActivity {

    // UI Elements
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    // adapter for the recycler view
    private all_classes_recViewAdapter adapter;

    // variables
    int pageWidth = 595;
    int pageHeight = 842;
    Bitmap bitmap, scaledBitmap;
    private ArrayList<String> staffs = new ArrayList<>();

    int workColor = Color.rgb(229,204,201);
    int color = Color.rgb(255,221,244);
    int red = Color.rgb(255, 0, 0);

    // back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_aclass_schedule);

        // enabling the home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setting the title
        getSupportActionBar().setTitle(getString(R.string.pick_one_class));

        // method to initialise the UI Elements
        init();

        // setting up the adapter for the recycler view
        adapter = new all_classes_recViewAdapter(getApplicationContext(), 1);
        adapter.setAllClasses(Class.allClasses);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
        recyclerView = findViewById(R.id.export_class_schedule);
        floatingActionButton = findViewById(R.id.export_class_to_top);
    }

    // constructor
    public export_a_class_schedule(Class classx, Context context){
        createAClassSchedule(classx);
        Toast.makeText(context, classx.getName() + " schedule exported", Toast.LENGTH_SHORT).show();
    }

    public export_a_class_schedule(){

    }

    public export_a_class_schedule(ArrayList<Class> allClasses){
        createAllClassesSchedule(allClasses);
    }

    // method to export a particular class schedule
    private void createAClassSchedule(Class classx){
        // creating an instance to PdfDocument class
        PdfDocument pdfDocument = new PdfDocument();
        // specifying the height, width and number of pages of the pdf
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Class details
        String className = classx.getName();
        String department = classx.getDepartment().substring(0, 3);
        String classAdvisor = classx.getTeachers()[0];
        int year = classx.getYear();
        int numberOfStudents = classx.getNumberOfStudents();
        Hashtable<String, String[]> schedule = new Hashtable<>();

        ArrayList<String[]> periods = new ArrayList<>();

        for(String day : DAYS_OF_THE_WEEK){
            periods.add(schedule.get(day).clone());
        }

        // writing the pdf
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
        if(classx.getDepartment().substring(0, 3).equals("CSE")){
            text = DEPARTMENT_CSE_TEXT;
        }
        else if(classx.getDepartment().substring(0, 3).equals("ECE")){
            text = DEPARTMENT_ECE_TEXT;
        }
        else if(classx.getDepartment().substring(0, 3).equals("MEC")){
            text = DEPARTMENT_MECH_TEXT;
        }
        else if(classx.getDepartment().substring(0, 3).equals("EEE")){
            text = DEPARTMENT_EEE_TEXT;
        }
        else if(classx.getDepartment().substring(0, 3).equals("CIV")){
            text = DEPARTMENT_CIVIL_TEXT;
        }
        else if(classx.getDepartment().substring(0, 3).equals("FYR")){
            text = DEPARTMENT_FIRST_YEAR_TEXT;
        }

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText(text, pageWidth/2, 75, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(10f);
        canvas.drawText(TIMETABLE_ODD_SEMESTER, pageWidth/2, 90, paint);

        // year, class advisor text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(9f);
        canvas.drawText(CLASS_NAME_DF, 75, 120, paint);
        canvas.drawText(NAME_OF_THE_CLASS_ADVISOR, 75, 135, paint);
        canvas.drawText(TIMETABLE_VERSION, 75, 150, paint);

        // colon for the above texts
        canvas.drawText(COLON, 185, 120, paint);
        canvas.drawText(COLON, 185, 135, paint);
        canvas.drawText(COLON, 185, 150, paint);

        // displaying the data
        // year
        String inYear = (year == 4) ? "IV" : (year == 3) ? "III" : (year == 2) ? "II" : (year == 1) ? "I" : "I";
        canvas.drawText(classx.getName(), 195, 120, paint);
        // class advisor
        canvas.drawText(classAdvisor, 195, 135, paint);
        // version
        canvas.drawText(NA, 195, 150, paint);

        // academic year, number of students, generated text
        canvas.drawText(ACADEMIC_YEAR, 310, 120, paint);
        canvas.drawText(NUMBER_OF_STUDENTS, 310, 135, paint);
        canvas.drawText(GENERATED, 310, 150, paint);

        // colon for the above texts
        canvas.drawText(COLON, 395, 120, paint);
        canvas.drawText(COLON, 395, 135, paint);
        canvas.drawText(COLON, 395, 150, paint);

        // displaying the data
        canvas.drawText(NA, 405, 120, paint);
        canvas.drawText(String.valueOf(numberOfStudents), 405, 135, paint);
        canvas.drawText(LocalDateTime.now().toString(), 405, 150, paint);

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
                paint.setColor(workColor);
                paint.setStyle(Paint.Style.FILL);
                /*for(int j = 1; j < 30; j++){
                    paint.setColor(Color.rgb(229,204,201));
                    canvas.drawLine(51, startY + j, 544, startY + j, paint);
                }*/
                canvas.drawRect(50, startY, 545, startY+30, paint);
            }
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
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
        // entering the periods
        for(int i = 0; i < periods.size(); i++){
            String[] temp = periods.get(i);

            // replacing every period in the temp[] to displayable format
            for(int j = 0; j < temp.length; j++){
                temp[j] = alterClassPeriods(temp[j]);
            }

            String period1 = periods.get(i)[0];
            String period2 = periods.get(i)[1];
            String period3 = periods.get(i)[2];
            String period4 = periods.get(i)[3];
            String period5 = periods.get(i)[4];
            String period6 = periods.get(i)[5];
            String period7 = periods.get(i)[6];
            String period8 = periods.get(i)[7];

            if(period2.equals(period3) && period3.equals(period4)){
                // setting the period2 and period4 to ""
                period2 = "";
                period4 = "";

                temp[1] = temp[3] = "";
            }

            if(period6.equals(period7) && period7.equals(period8)){
                // setting the period6 and period8 to ""
                period6 = "";
                period8 = "";

                temp[5] = temp[7] = "";
            }

            // hiding the vertical bars
            if(temp[1].equals("") && temp[3].equals("")){
                int x1 = 105 + (495 / 9) + 1;
                int x2 = 105 + (495 / 9) * 4 - 1;

                int y1 = y - 20 + 1;
                int y2 = y + 10 - 1;

                paint.setStyle(Paint.Style.FILL);
                if(! (i%2 == 0)){
                    paint.setColor(workColor);
                }
                else{
                    paint.setColor(Color.WHITE);
                }

                canvas.drawRect(x1, y1, x2, y2, paint);
            }

            if(temp[5].equals("") && temp[7].equals("")){
                int x1 = 545 - (495 / 9) * 3  + 1;
                int x2 = 545 - 1;

                int y1 = y - 20 + 1;
                int y2 = y + 10 - 1;

                paint.setStyle(Paint.Style.FILL);
                if(! (i%2 == 0)){
                    paint.setColor(workColor);
                }
                else{
                    paint.setColor(Color.WHITE);
                }

                canvas.drawRect(x1, y1, x2, y2, paint);
            }

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(10f);
            // displaying the periods
            for(int k = 0; k < temp.length; k++){
                int x = 105 + (495 / 9) * k + 27;
                canvas.drawText(temp[k], x, y, paint);
            }

            y+=30;
        }

        // displaying the break and lunch text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(8f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(BREAK_TEXT, 50, 405, paint);
        canvas.drawText(LUNCH, 300, 405, paint);

        // Subject Details text
        paint.setTextSize(10f);
        canvas.drawText(SUBJECT_DETAILS, 50, 430, paint);

        int summa = 440;
        // drawing the subjects table
        // getting the Subjects from the SUBJECTS array
        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<Integer> short_form_positions = new ArrayList<>();

        int index = 0;
        for(String subject : SUBJECTS){
            int subjectYear = Integer.parseInt(String.valueOf(subject.charAt(4)));
            String subjectDepartment = subject.substring(0, 3);

            if(subjectYear == year){
                if(subjectDepartment.equals(department)){
                    subjects.add(subject);
                    short_form_positions.add(index);
                }
            }

            index++;
        }

        // drawing the header rectangle
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawRect(50, 440, 545, 465, paint);

        // dividing the rectangle into 5 columns
        canvas.drawLine(80, 440, 80, 465, paint);
        canvas.drawLine(237, 440, 237, 465, paint);
        canvas.drawLine(395, 440, 395, 465, paint);
        canvas.drawLine(470, 440, 470, 465, paint);

        // S.No, Subject, Subject Staffs, Abbreviation, Sessions text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(10f);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(S_NO, 65, 455, paint);
        canvas.drawText(SUBJECT_NAME, 153, 455, paint);
        canvas.drawText(SUBJECT_STAFFS, 310, 455, paint);
        canvas.drawText(ABBREVIATION, 433, 455, paint);
        canvas.drawText(SESSIONS, 507, 455, paint);

        // drawing the subjects rectangle
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        int endY = 465;
        for(int i = 0; i < subjects.size(); i++){
            endY = 465 + (25 * (i+1));
            canvas.drawLine(50, endY, 545, endY, paint);
        }

        // drawing the vertical lines seperating each columns
        canvas.drawLine(50, 465, 50, endY, paint);
        canvas.drawLine(545, 465, 545, endY, paint);
        canvas.drawLine(80, 465, 80, endY, paint);
        canvas.drawLine(237, 465, 235, endY, paint);
        canvas.drawLine(395, 465, 395, endY, paint);
        canvas.drawLine(470, 465, 470, endY, paint);

        // drawing the serial numbers
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(8f);
        for(int i = 0; i < subjects.size(); i++){
            canvas.drawText(String.valueOf(i+1), 65, 465 + (25 * (i+1)) - 10, paint);
        }

        // entering the subjects, subject staffs, abbreviation, sessions details
        getAllStaffs(className);

        /*hashtable containing number of sessions of each subject*/
        Hashtable<String, Integer> details = new Hashtable<>();
        for(String day : DAYS_OF_THE_WEEK){
            String[] tempSchedule = schedule.get(day);

            for(String period : tempSchedule){
                if(period.contains("/") && (period.contains(LAB) || period.contains(LAB1))){
                    String[] temp = period.split("/");

                    String sub1 = temp[0];
                    String sub2 = temp[1];

                    if(details.containsKey(sub1)){
                        details.put(sub1, details.get(sub1) + 1);
                    }
                    else{
                        details.put(sub1, 1);
                    }

                    if(details.containsKey(sub2)){
                        details.put(sub2, details.get(sub2) + 1);
                    }
                    else{
                        details.put(sub2, 1);
                    }
                }
                else if(period.contains(LAB) || period.contains(LAB1) || period.contains(SPEAKING)){
                    if(details.containsKey(period)){
                        details.put(period, details.get(period) + 1);
                    }
                    else{
                        details.put(period, 1);
                    }
                }
                else{
                    if(details.containsKey(period)){
                        details.put(period, details.get(period) + 1);
                    }
                    else{
                        details.put(period, 1);
                    }
                }
            }
        }

        for(int i = 0; i < subjects.size(); i++){
            y = 465 + (25 * (i+1)) - 10;

            // inputting the subjects
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(7f);
            canvas.drawText(subjects.get(i).substring(6), 85, y, paint);

            // inputting the staffs
            paint.setTextSize(8f);
            canvas.drawText(staffs.get(i), 242, y, paint);

            // adding the abbreviations of the subjects
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(9f);
            canvas.drawText(SHORT_FORM_SUBJECTS[short_form_positions.get(i)].substring(6), 433, y, paint);

            // adding the number of sessions
            int particularPeriodCount = 0;
            particularPeriodCount = details.get(subjects.get(i).substring(6));
            canvas.drawText(String.valueOf(particularPeriodCount), 507, y, paint);
        }

        // hod, principal, incharge, co-ordinator text
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(TIMETABLE_INCHARGE, 119, 790, paint);
        canvas.drawText(HOD, 238, 790, paint);
        canvas.drawText(COORDINATOR, 357, 790, paint);
        canvas.drawText(PRINCIPAL, 476, 790, paint);

        pdfDocument.finishPage(page);

        // clearing the staffs array list
        staffs.clear();

        // writing to the external storage
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() +classx.getName()+".pdf";
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

    // method to export all the class schedules
    private void createAllClassesSchedule(ArrayList<Class> allClasses){
        // creating an instance to PdfDocument class
        PdfDocument pdfDocument = new PdfDocument();
        for(int num = 0; num < allClasses.size(); num++){
            Class classx = allClasses.get(num);

            // specifying the height, width and number of pages of the pdf
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, num + 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            // Class details
            String className = classx.getName();
            String department = classx.getDepartment().substring(0, 3);
            String classAdvisor = classx.getTeachers()[0];
            int year = classx.getYear();
            int numberOfStudents = classx.getNumberOfStudents();
            Hashtable<String, String[]> schedule = new Hashtable<>();

            ArrayList<String[]> periods = new ArrayList<>();

            for(String day : DAYS_OF_THE_WEEK){
                periods.add(schedule.get(day).clone());
            }

            // writing the pdf
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
            canvas.drawText("AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY", pageWidth/2, 43, paint);

            // ERACHAKULAM TEXT
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText("Erachakulam, Nagercoil", pageWidth/2, 56, paint);

            // department text
            String text = null;
            if(classx.getDepartment().substring(0, 3).equals("CSE")){
                text = DEPARTMENT_CSE_TEXT;
            }
            else if(classx.getDepartment().substring(0, 3).equals("ECE")){
                text = DEPARTMENT_ECE_TEXT;
            }
            else if(classx.getDepartment().substring(0, 3).equals("MEC")){
                text = DEPARTMENT_MECH_TEXT;
            }
            else if(classx.getDepartment().substring(0, 3).equals("EEE")){
                text = DEPARTMENT_EEE_TEXT;
            }
            else if(classx.getDepartment().substring(0, 3).equals("CIV")){
                text = DEPARTMENT_CIVIL_TEXT;
            }
            else if(classx.getDepartment().substring(0, 3).equals("FYR")){
                text = DEPARTMENT_FIRST_YEAR_TEXT;
            }

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(text, pageWidth/2, 75, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(10f);
            canvas.drawText(TIMETABLE_ODD_SEMESTER, pageWidth/2, 90, paint);

            // year, class advisor text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(9f);
            canvas.drawText(CLASS_NAME_DF, 75, 120, paint);
            canvas.drawText(NAME_OF_THE_CLASS_ADVISOR, 75, 135, paint);
            canvas.drawText(TIMETABLE_VERSION, 75, 150, paint);

            // colon for the above texts
            canvas.drawText(COLON, 185, 120, paint);
            canvas.drawText(COLON, 185, 135, paint);
            canvas.drawText(COLON, 185, 150, paint);

            // displaying the data
            // year
            String inYear = (year == 4) ? "IV" : (year == 3) ? "III" : (year == 2) ? "II" : (year == 1) ? "I" : "I";
            canvas.drawText(classx.getName(), 195, 120, paint);
            // class advisor
            canvas.drawText(classAdvisor, 195, 135, paint);
            // version
            canvas.drawText(NA, 195, 150, paint);

            // academic year, number of students, generated text
            canvas.drawText(ACADEMIC_YEAR, 310, 120, paint);
            canvas.drawText(NUMBER_OF_STUDENTS, 310, 135, paint);
            canvas.drawText(GENERATED, 310, 150, paint);

            // colon for the above texts
            canvas.drawText(COLON, 395, 120, paint);
            canvas.drawText(COLON, 395, 135, paint);
            canvas.drawText(COLON, 395, 150, paint);

            // displaying the data
            canvas.drawText(NA, 405, 120, paint);
            canvas.drawText(String.valueOf(numberOfStudents), 405, 135, paint);
            canvas.drawText(LocalDateTime.now().toString(), 405, 150, paint);

            // OFFLINE TIMETABLE TEXT
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            canvas.drawText(OFFLINE_TIMETABLE, pageWidth/2, 175, paint);

            // drawing the table
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            int startY = 185;

            for(int i = 0; i < 8; i++){
                canvas.drawLine(50, startY, 545, startY, paint);
                // coloring the alternate rows
                if(i % 2 ==0){
                    for(int j = 1; j < 30; j++){
                        paint.setColor(Color.rgb(229,204,201));
                        canvas.drawLine(51, startY + j, 544, startY + j, paint);
                    }
                }
                paint.setColor(Color.BLACK);
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
            // entering the periods
            for(int i = 0; i < periods.size(); i++){
                String[] temp = periods.get(i);

                // replacing every period in the temp[] to displayable format
                for(int j = 0; j < temp.length; j++){
                    temp[j] = alterClassPeriods(temp[j]);
                }

                String period1 = periods.get(i)[0];
                String period2 = periods.get(i)[1];
                String period3 = periods.get(i)[2];
                String period4 = periods.get(i)[3];
                String period5 = periods.get(i)[4];
                String period6 = periods.get(i)[5];
                String period7 = periods.get(i)[6];
                String period8 = periods.get(i)[7];

                if(period2.equals(period3) && period3.equals(period4)){
                    // setting the period2 and period4 to ""
                    period2 = "";
                    period4 = "";

                    temp[1] = temp[3] = "";
                }

                if(period6.equals(period7) && period7.equals(period8)){
                    // setting the period6 and period8 to ""
                    period6 = "";
                    period8 = "";

                    temp[5] = temp[7] = "";
                }

                // hiding the vertical bars
                if(temp[1].equals("") && temp[3].equals("")){
                    int x1 = 105 + (495 / 9) + 1;
                    int x2 = 105 + (495 / 9) * 4 - 1;

                    int y1 = y - 20 + 1;
                    int y2 = y + 10 - 1;

                    paint.setStyle(Paint.Style.FILL);
                    if(! (i%2 == 0)){
                        paint.setColor(workColor);
                    }
                    else{
                        paint.setColor(Color.WHITE);
                    }

                    canvas.drawRect(x1, y1, x2, y2, paint);
                }

                if(temp[5].equals("") && temp[7].equals("")){
                    int x1 = 545 - (495 / 9) * 3  + 1;
                    int x2 = 545 - 1;

                    int y1 = y - 20 + 1;
                    int y2 = y + 10 - 1;

                    paint.setStyle(Paint.Style.FILL);
                    if(! (i%2 == 0)){
                        paint.setColor(workColor);
                    }
                    else{
                        paint.setColor(Color.WHITE);
                    }

                    canvas.drawRect(x1, y1, x2, y2, paint);
                }

                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLACK);
                paint.setTextSize(10f);

                // displaying the periods
                for(int k = 0; k < temp.length; k++){
                    int x = 105 + (495 / 9) * k + 27;
                    canvas.drawText(temp[k], x, y, paint);
                }

                y+=30;
            }

            // displaying the break and lunch text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(8f);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(BREAK_TEXT, 50, 405, paint);
            canvas.drawText(LUNCH, 300, 405, paint);

            // Subject Details text
            paint.setTextSize(10f);
            canvas.drawText(SUBJECT_DETAILS, 50, 430, paint);

            int summa = 440;
            // drawing the subjects table
            // getting the Subjects from the SUBJECTS array
            ArrayList<String> subjects = new ArrayList<>();
            ArrayList<Integer> short_form_positions = new ArrayList<>();

            int index = 0;
            for(String subject : SUBJECTS){
                int subjectYear = Integer.parseInt(String.valueOf(subject.charAt(4)));
                String subjectDepartment = subject.substring(0, 3);

                if(subjectYear == year){
                    if(subjectDepartment.equals(department)){
                        subjects.add(subject);
                        short_form_positions.add(index);
                    }
                }

                index++;
            }

            // drawing the header rectangle
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            canvas.drawRect(50, 440, 545, 465, paint);

            // dividing the rectangle into 5 columns
            canvas.drawLine(80, 440, 80, 465, paint);
            canvas.drawLine(237, 440, 237, 465, paint);
            canvas.drawLine(395, 440, 395, 465, paint);
            canvas.drawLine(470, 440, 470, 465, paint);

            // S.No, Subject, Subject Staffs, Abbreviation, Sessions text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(10f);
            paint.setTextAlign(Paint.Align.CENTER);

            canvas.drawText(S_NO, 65, 455, paint);
            canvas.drawText(SUBJECT_NAME, 153, 455, paint);
            canvas.drawText(SUBJECT_STAFFS, 310, 455, paint);
            canvas.drawText(ABBREVIATION, 433, 455, paint);
            canvas.drawText(SESSIONS, 507, 455, paint);

            // drawing the subjects rectangle
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            int endY = 465;
            for(int i = 0; i < subjects.size(); i++){
                endY = 465 + (25 * (i+1));
                canvas.drawLine(50, endY, 545, endY, paint);
            }

            // drawing the vertical lines seperating each columns
            canvas.drawLine(50, 465, 50, endY, paint);
            canvas.drawLine(545, 465, 545, endY, paint);
            canvas.drawLine(80, 465, 80, endY, paint);
            canvas.drawLine(237, 465, 235, endY, paint);
            canvas.drawLine(395, 465, 395, endY, paint);
            canvas.drawLine(470, 465, 470, endY, paint);

            // drawing the serial numbers
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(8f);
            for(int i = 0; i < subjects.size(); i++){
                canvas.drawText(String.valueOf(i+1), 65, 465 + (25 * (i+1)) - 10, paint);
            }

            // entering the subjects, subject staffs, abbreviation, sessions details
            getAllStaffs(className);

            /*hashtable containing number of sessions of each subject*/
            Hashtable<String, Integer> details = new Hashtable<>();
            for(String day : DAYS_OF_THE_WEEK){
                String[] tempSchedule = schedule.get(day);

                for(String period : tempSchedule){
                    if(period.contains("/") && (period.contains(LAB) || period.contains(LAB1))){
                        String[] temp = period.split("/");

                        String sub1 = temp[0];
                        String sub2 = temp[1];

                        if(details.containsKey(sub1)){
                            details.put(sub1, details.get(sub1) + 1);
                        }
                        else{
                            details.put(sub1, 1);
                        }

                        if(details.containsKey(sub2)){
                            details.put(sub2, details.get(sub2) + 1);
                        }
                        else{
                            details.put(sub2, 1);
                        }
                    }
                    else if(period.contains(LAB) || period.contains(LAB1) || period.contains(SPEAKING)){
                        if(details.containsKey(period)){
                            details.put(period, details.get(period) + 1);
                        }
                        else{
                            details.put(period, 1);
                        }
                    }
                    else{
                        if(details.containsKey(period)){
                            details.put(period, details.get(period) + 1);
                        }
                        else{
                            details.put(period, 1);
                        }
                    }
                }
            }

            for(int i = 0; i < subjects.size(); i++){
                y = 465 + (25 * (i+1)) - 10;

                // inputting the subjects
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(7f);
                canvas.drawText(subjects.get(i).substring(6), 85, y, paint);

                // inputting the staffs
                paint.setTextSize(8f);
                canvas.drawText(staffs.get(i), 242, y, paint);

                // adding the abbreviations of the subjects
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(9f);
                canvas.drawText(SHORT_FORM_SUBJECTS[short_form_positions.get(i)].substring(6), 433, y, paint);

                // adding the number of sessions
                int particularPeriodCount = 0;
                particularPeriodCount = details.get(subjects.get(i).substring(6));
                canvas.drawText(String.valueOf(particularPeriodCount), 507, y, paint);
            }

            // hod, principal, incharge, co-ordinator text
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10f);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawText(TIMETABLE_INCHARGE, 119, 790, paint);
            canvas.drawText(HOD, 238, 790, paint);
            canvas.drawText(COORDINATOR, 357, 790, paint);
            canvas.drawText(PRINCIPAL, 476, 790, paint);

            pdfDocument.finishPage(page);

            // clearing the staffs array list
            staffs.clear();

            // clearing the details hashtable
            details.clear();
        }

        // writing to the external storage
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() +"All Classes Schedules"+".pdf";
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

    /*Method to get allStaffs for a class*/
    private void getAllStaffs(String className) {
        for (Class classes : Class.allClasses) {
            if (classes.getName().equals(className)) {
                for (int i = 0; i < classes.getTeachers().length; i++) {
                    if (i == 0) {
                        /*Do nothing*/
                        /*Class advisor will be in the 0th position*/
                        //classAdvisorName = classes.getTeachers()[i];
                    } else {
                        String got = classes.getTeachers()[i];

                        String staff1 = null;
                        String staff2 = null;

                        for (staff Staff : staff.allStaffs) {
                            if (got.contains(Staff.getName())) {
                                if (staff1 == null) {
                                    staff1 = Staff.getName();
                                } else {
                                    staff2 = Staff.getName();
                                }
                            }
                        }

                        if (staff2 == null) {
                            staffs.add(staff1);
                        } else {
                            staffs.add(staff1 + "/" + staff2);
                        }
                    }
                }
            }
        }
    }
}