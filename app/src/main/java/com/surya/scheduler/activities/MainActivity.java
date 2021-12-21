package com.surya.scheduler.activities;

import static com.surya.scheduler.constants.data.APP_DEFAULTS;
import static com.surya.scheduler.constants.data.IS_THE_USER_A_ADMIN;
import static com.surya.scheduler.constants.data.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.surya.scheduler.R;
import com.surya.scheduler.activities.adding_stuffs.admins.add_admin;
import com.surya.scheduler.activities.adding_stuffs.classes.class_basic_details;
import com.surya.scheduler.activities.adding_stuffs.staffs.staff_basic_details;
import com.surya.scheduler.activities.editing_stuffs.staffs.swap_time_table;
import com.surya.scheduler.activities.removing_stuffs.classes.remove_a_class;
import com.surya.scheduler.activities.removing_stuffs.staff.remove_staffs;
import com.surya.scheduler.adapters.fragmentAdapter;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.pdfExport.activities.export_a_class_schedule;
import com.surya.scheduler.pdfExport.activities.export_a_staff_schedule;
import com.surya.scheduler.pdfExport.all_classes_export;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager2 viewPager;
    private TabItem all_classes, all_staffs, all_rooms;
    private fragmentAdapter adapter;

    private int tabPosition;
    private static boolean opened = false;

    /*Inflating the menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*Checking if the user is an admin*/
        SharedPreferences sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean(IS_THE_USER_A_ADMIN, false);
        if(isAdmin){
            menu.clear();
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);
            return true;
        }
        else{
            return false;
        }
    }

    /*Handling the menu clicks*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        /*Exit admin mode menu*/
        if (itemId == R.id.main_exit_admin) {
            /*The user is no longer an admin hereafter..*/
            SharedPreferences sharedPreferences = getSharedPreferences(APP_DEFAULTS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(IS_THE_USER_A_ADMIN, false);
            editor.apply();
            /*clearing every array lists before switching to the normal mode*/
            Class.allClasses.clear();
            staff.allStaffs.clear();
            room.allRooms.clear();
            /*Redirecting the user once again to the MainActivity, without the menu this time*/
            Intent intent = new Intent(this, splash_screen.class);
            startActivity(intent);
            finishAffinity();
            finish();
        }
        /*Add a class menu*/
        else if (itemId == R.id.main_add_class) {
            Intent intent = new Intent(getApplicationContext(), class_basic_details.class);
            startActivity(intent);
        }
        // remove a class menu
        else if(itemId == R.id.main_remove_class){
            Intent intent = new Intent(this, remove_a_class.class);
            startActivity(intent);
        }
        // add a staff menu
        else if (itemId == R.id.main_add_staff) {
            Intent intent = new Intent(getApplicationContext(), staff_basic_details.class);
            startActivity(intent);
        }
        // export all class details menu
        else if (itemId == R.id.export_class_details) {
            all_classes_export export = new all_classes_export();
            Toast.makeText(getApplicationContext(), "Exported", Toast.LENGTH_SHORT).show();
        }
        // export a class schedule menu
        else if (itemId == R.id.main_export_class_schedule) {
            Intent intent = new Intent(getApplicationContext(), export_a_class_schedule.class);
            startActivity(intent);
        }
        // export all classes schedules
        else if (itemId == R.id.main_export_all_classes_schedules) {
            export_a_class_schedule export_a_class_schedule = new export_a_class_schedule(Class.allClasses);
            Toast.makeText(getApplicationContext(), "All classes schedules generated..", Toast.LENGTH_SHORT).show();
        }
        // export a staff schedule
        else if (itemId == R.id.main_export_a_staff_schedule) {
            Intent intent = new Intent(getApplicationContext(), export_a_staff_schedule.class);
            startActivity(intent);
        }
        // all staffs schedules
        else if (itemId == R.id.main_export_all_staffs_schedule) {
            export_a_staff_schedule export_a_staff_schedule = new export_a_staff_schedule(staff.allStaffs);
            Toast.makeText(MainActivity.this, "All staffs schedules exported..", Toast.LENGTH_SHORT).show();
        }
        /*remove a staff menu*/
        else if (itemId == R.id.main_remove_staff) {
            Intent intent = new Intent(getApplicationContext(), remove_staffs.class);
            startActivity(intent);
        }
        // swap menu
        else if (itemId == R.id.main_menu_swap) {
            Intent intent = new Intent(getApplicationContext(), swap_time_table.class);
            startActivity(intent);
        }
        // add admin menu
        else if (itemId == R.id.main_add_admin) {
            Intent intent = new Intent(getApplicationContext(), add_admin.class);
            startActivity(intent);
        }
        /*Update the database menu*/
        else if (itemId == R.id.main_menu_update) {
            /*Toast.makeText(getApplicationContext(), "Updating to Firebase", Toast.LENGTH_SHORT).show();

                update update = new update();
                update.updateAllClasses();
                update.updateAllStaffs();
                update.updateAllLabsSchedules();

                Toast.makeText(getApplicationContext(), "Update completed", Toast.LENGTH_SHORT).show();*/
            String bala = ";;";
        }
        return true;
    }

    /*Overriding the BACK BUTTON functionality*/
    @Override
    public void onBackPressed() {
        /*Exiting the application, if the BACK BUTTON is pressed*/
        /*If the selected tab position is either 1 or 2, direct the user to tab 0*/
        /*If the user is at tab 0, exit the application*/
        /*Similar to Whatsapp*/
        if(tabPosition != 0){
            tableLayout.selectTab(tableLayout.getTabAt(0));
        }
        else{
            finishAffinity();
        }
    }

    //Theme.MaterialComponents.DayNight.DarkActionBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            createDirectory();
        }
        else{
            askPermission();
        }

        /*Setting the title for the action bar*/
        getSupportActionBar().setTitle(getResources().getString(R.string.college));

        /*Method to initialise all the UI Elements*/
        init();

        //Toast.makeText(getApplicationContext(), allClassesTeachers.size()+"", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), staff.allStaffs.size()+"", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();

        adapter = new fragmentAdapter(fragmentManager, getLifecycle());
        viewPager.setAdapter(adapter);

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tableLayout.selectTab(tableLayout.getTabAt(position));
                tabPosition = tableLayout.getSelectedTabPosition();
            }
        });
    }

    // method to ask the permissions that are needed for the application to perform
    private void askPermission(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSION_REQUEST_CODE
        );
    }

    // method which returns the result for our askPermission() method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                createDirectory();
            }
            else{
                //Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void createDirectory() {
        // creating a directory for the scheduler app
        // let the name be ACET
        String name = "ACET";
        File file = new File(Environment.getExternalStorageDirectory(), name);

        if(! file.exists()){
            // creating a folder if the folder does not already exist

            file.mkdir();
            //Toast.makeText(getApplicationContext(), "folder created", Toast.LENGTH_SHORT).show();
        }

        else{
            //Toast.makeText(getApplicationContext(), "already existing", Toast.LENGTH_SHORT).show();
        }
    }

    /*Initialising the views*/
    private void init(){
        tableLayout = findViewById(R.id.main_tab_layout);
        viewPager = findViewById(R.id.main_view_pager);
        all_classes = findViewById(R.id.item_all_classes);
        all_staffs = findViewById(R.id.item_all_staffs);
        all_rooms = findViewById(R.id.item_all_rooms);
    }
}