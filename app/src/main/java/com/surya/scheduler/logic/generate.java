package com.surya.scheduler.logic;

import static com.surya.scheduler.constants.data.AFTERNOON;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LECTURE;
import static com.surya.scheduler.constants.data.MONDAY;
import static com.surya.scheduler.constants.data.MORNING;
import static com.surya.scheduler.constants.data.PAIRED;
import static com.surya.scheduler.constants.data.SHORT_FORM_SUBJECTS;
import static com.surya.scheduler.constants.data.STAND_ALONE;
import static com.surya.scheduler.constants.data.SUBJECTS;
import static com.surya.scheduler.constants.settings.LABORATORY_MORNING_SESSIONS_PERIODS;
import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_LECTURE;
import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_PER_DAY;
import static com.surya.scheduler.constants.status.GENERATE_LABS_COMPLETED;
import static com.surya.scheduler.constants.status.GENERATE_PAIRED_SUBJECTS_COMPLETED;
import static com.surya.scheduler.constants.status.GENERATE_STATUS;
import static com.surya.scheduler.constants.status.PAIRED_INSTANCE_CREATED;
import static com.surya.scheduler.constants.status.PAIRED_LAB_GENERATED;
import static com.surya.scheduler.constants.status.STAND_ALONE_LAB_GENERATED;
import static com.surya.scheduler.constants.status.SUBJECTS_GENERATED;

import android.util.Log;

import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.lab_periods;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;
import com.surya.scheduler.storage.store;

import java.util.ArrayList;
import java.util.Hashtable;

public class generate {

    private static generate instance;
    private static boolean labsAssigned = false;
    private store store = new store();
    private utility utility = new utility();

    /*Getters and Setters*/
    public static generate getInstance() {
        return instance;
    }

    public static void setInstance(generate instance) {
        generate.instance = instance;
    }

    /*Constructor*/
    public generate() {
        // creating an instance of paired class
        paired.getPairedInstance();

        /*Generating schedules for all the labs*/
        generateLabSchedule();
        labsAssigned = true;

        // generating schedules for the paired subjects
        generatePairedSubjectsSchedule();
    }

    /*This generate class must be a singleton class*/
    public static generate getGenerateInstance(){
        if(instance == null){
            instance = new generate();
            return instance;
        }
        else{
            return instance;
        }
    }

    /*Generating the lab schedules first*/
    private void generateLabSchedule(){

        int previousYear = 0;
        int helper = 0;
        int count = 0;

        for(String rawSubject : SUBJECTS){

            int year = Integer.parseInt(String.valueOf(rawSubject.charAt(4)));
            String department = rawSubject.substring(0, 3);

            if(previousYear == 0){
                previousYear = year;
            }
            else{
                if(previousYear == year){
                    helper++;
                }
                else{
                    previousYear = year;
                    helper = 0;
                }
            }

            if(utility.identifyTag(rawSubject).equals(LAB)){
                /*The Subject is a laboratory*/
                /*rawSubject is in the form, CSE-3-Microprocessors and Microcontrollers Laboratory*/
                String subject = rawSubject.substring(6);

                for(Class classes : Class.allClasses){
                    if(classes.getDepartment().substring(0, 3).equals(department)){
                        if(classes.getYear() == year){
                            for(int m = 0; m < classes.getTeachers().length; m++){
                                if((m-1) == helper){
                                    /*Reached the staffs for the lab*/
                                    /*Will be like this, Mr. I Anantraj"+CSE_LAB_2+PAIRED*/
                                    /*Mrs. C KarpagavalliMrs. R P Sumithra"+CSE_LAB_3+STAND_ALONE*/
                                    /*Have to get the staff1, staff2(may be null), lab1Name, PAIRED or STAND_ALONE*/
                                    boolean standAlone = false;
                                    String allCombo = classes.getTeachers()[m];

                                    String[] temp = utility.labStaffsDetails(allCombo);
                                    String staff1 = temp[0];
                                    String staff2 = temp[1];
                                    String lab1Name = temp[2];

                                    /*Checking if it is a STAND_ALONE or PAIRED lab*/
                                    if(allCombo.contains(STAND_ALONE)){
                                        standAlone = true;
                                    }

                                    if(standAlone){
                                        /*If it is a STAND_ALONE lab...,*/
                                        /*There will be two staffs and one physical lab*/
                                        /*private void standAloneLabSchedule(
                                                String staff1,
                                                String staff2,
                                                String className,
                                                String labName,
                                                String subjectName,
                                                int shortFormIndex
                                         )*/

                                       /*Requires 5 parameters*/
                                        standAloneLabSchedule(
                                                staff1,
                                                staff2,
                                                classes.getName(),
                                                lab1Name,
                                                subject,
                                                count
                                        );
                                    }
                                    else{
                                        /*PAIRED lab*/
                                        if(paired.pairedLabs.containsKey(classes.getName())){
                                            /*Means, we already got a lab for this class entered*/
                                            /*private void pairedLabSchedule(
                                                String staff1,
                                                String staff2,
                                                String class1Name,
                                                String lab1Name,
                                                String lab2Name,
                                                String subject1,
                                                String subject2,
                                                int shortForm1,
                                                int shortForm2
                                            )*/
                                            pairedLabSchedule(
                                                    staff1,
                                                    paired.pairedLabsStaffs.get(classes.getName())[0],
                                                    classes.getName(),
                                                    lab1Name,
                                                    paired.pairedLabs.get(classes.getName()),
                                                    subject,
                                                    paired.pairedSubjects.get(classes.getName()),
                                                    count,
                                                    paired.getPairedSubjectsCount().get(classes.getName())
                                            );

                                            //Log.d("labs generated", "Paired lab : " + subject + " for " + classes.getName());

                                            paired.hashedSubjects.put(subject, count);
                                            utility.addToHashedSubjectsArray(subject, count);

                                            /*Clearing the hashtable*/
                                            paired.pairedSubjects.remove(classes.getName());
                                            paired.pairedLabs.remove(classes.getName());
                                            paired.pairedLabsStaffs.remove(classes.getName());
                                            paired.getPairedSubjectsCount().remove(classes.getName());
                                        }
                                        else{
                                            /*Entering the lab, staff, className to a hashtable*/
                                            paired.pairedLabs.put(classes.getName(), lab1Name);
                                            /*staff2 will be null for the PAIRED labs*/
                                            paired.pairedLabsStaffs.put(classes.getName(), new String[]{
                                                    staff1
                                            });
                                            paired.pairedSubjects.put(classes.getName(), subject);
                                            paired.hashedSubjects.put(subject, count);
                                            paired.pairedSubjectsCount.put(classes.getName(), count);

                                            utility.addToHashedSubjectsArray(subject, count);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            count ++;
        }

        Log.d(GENERATE_STATUS, GENERATE_LABS_COMPLETED);
    }

    /*Method to generate schedules for the STAND_ALONE labs*/
    private void standAloneLabSchedule(
            String staff1,
            String staff2,
            String className,
            String labName,
            String subjectName,
            int shortFormIndex
    )
    {
        /*Hashtable for each staffs, labs, class*/
        Hashtable<String, String[]> staffTable1 = utility.returnStaffSchedule(staff1);
        Hashtable<String, String[]> staffTable2 = new Hashtable<>();
        if(! (staff2 == null)){
            staffTable2 = utility.returnStaffSchedule(staff2);
        }

        Hashtable<String, String[]> classTable = utility.returnClassSchedule(className);

        Hashtable<String, String[]> labTable = utility.returnLabSchedule(labName);

        /*Positions for each staff and classes in the arrayList*/
        int staff1Position = utility.returnStaffPosition(staff1);
        int staff2Position = 0;
        if(! (staff2 == null)){
            staff2Position = utility.returnStaffPosition(staff2);
        }
        int class1Position = utility.returnClassPosition(className);
        int lab1Position = utility.returnLabPosition(labName);

        boolean spotted = false;
        int numberOfTimes = 0;
        String subjectShortForm = SUBJECTS[shortFormIndex];

        while(! spotted){
            /*Using Random method generating random numbers to denote the day of the week and session*/
            int randomDay = 0;
            int randomSession = 0;

            int dayMin = 1;
            int dayMax = 5; // friday

            int sessionMin = 1;
            int sessionMax = 10;

            randomDay = (int) (Math.random() * (dayMax - dayMin + 1) + dayMin);
            randomSession = (int) (Math.random() * (sessionMax - sessionMin + 1) + sessionMin);

            String day = DAYS_OF_THE_WEEK[randomDay - 1];
            String session = randomSession % 2 == 0 ? MORNING : AFTERNOON;

            // schedules of class, staffs and labs
            String[] classTempTable = classTable.get(day).clone();
            String[] staff1TempTable = staffTable1.get(day).clone();
            String[] labTempTable = labTable.get(day).clone();

            if(staff2 == null){
                /*As of now, only for ISLS lab, staff2 is null*/
                if(numberOfTimes > 50){
                    spotted = true;
                }

                else{
                    // checking the constraints
                    // for the class
                    if(utility.overAllClassConstraintsForLab(classTempTable, session)){
                        // for the staffs
                        if(utility.dailyWorkingHoursOfAStaff(staff1TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                            // for the lab
                            if(utility.labsSlotChecker(labTempTable, session)){
                                // slot is ready for allocation
                                // altering the arrays
                                // class
                                classTempTable = utility.setArrayForLabForAClass(classTempTable, session, subjectName);
                                // staffs
                                staff1TempTable = utility.setArrayForLabForAStaff(staff1TempTable, session, className);
                                // labs
                                labTempTable = utility.setArrayForLabForAStaff(labTempTable, session, className);

                                // modifying the hash tables in the array lists
                                Class.allClasses.get(class1Position).getSchedule().replace(day, classTempTable);
                                staff.allStaffs.get(staff1Position).getSchedule().replace(day, staff1TempTable);
                                room.allRooms.get(lab1Position).getSchedule().replace(day, labTempTable);

                                // altering the short forms array of a class
                                String[] classTemp = Class.allClasses.get(class1Position).getShortFormSchedule().get(day).clone();
                                classTemp = utility.setArrayForLabForAClassShortForm(classTemp, session, subjectShortForm);
                                Class.allClasses.get(class1Position).getShortFormSchedule().replace(day, classTemp);

                                // altering the subject names array of a staff
                                // staff1
                                String[] staffTemp = staff.allStaffs.get(staff1Position).getSubjectsSchedule().get(day).clone();
                                staffTemp = utility.setArrayForLabForAClass(staffTemp, session, subjectName);
                                staff.allStaffs.get(staff1Position).getSubjectsSchedule().replace(day, staffTemp);

                                Log.d(STAND_ALONE_LAB_GENERATED, className + "/" + subjectName);

                                spotted = true;
                            }
                        }
                    }
                }
            }

            else{
                /*Both staff1 and staff2 are not null*/
                String[] staff2TempTable = staffTable2.get(day).clone();

                if(numberOfTimes > 20){
                    // numberOfTimes is greater than 50
                    // ithuku mela may be free slots ilaama irukalam
                    // so, kedacha slot la allot panitu, antha slot la already ula lab ah allocate pana intha method ah thirumba call panalam
                    boolean slotIsNotFree = true;

                    // checking still if the slot is FREE
                    // for the class
                    if(utility.overAllClassConstraintsForLab(classTempTable, session)){
                        // for the staffs
                        if(utility.dailyWorkingHoursOfAStaff(staff1TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                            if(utility.dailyWorkingHoursOfAStaff(staff2TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                                // for the lab
                                if(utility.labsSlotChecker(labTempTable, session)){
                                    // slot is FREE and ready for allocation
                                    slotIsNotFree = false;

                                    spotted = true;
                                }
                            }
                        }
                    }

                    if(slotIsNotFree){
                        // the slot is NOT FREE
                        // already a lab is assigned

                        spotted = true;
                    }
                }

                else{
                    // checking the constraints
                    // for the class
                    if(utility.overAllClassConstraintsForLab(classTempTable, session)){
                        // for the staffs
                        if(utility.dailyWorkingHoursOfAStaff(staff1TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                            if(utility.dailyWorkingHoursOfAStaff(staff2TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                                // for the lab
                                if(utility.labsSlotChecker(labTempTable, session)){
                                    // slot is ready for allocation
                                    // altering the arrays
                                    // class
                                    classTempTable = utility.setArrayForLabForAClass(classTempTable, session, subjectName);
                                    // staffs
                                    staff1TempTable = utility.setArrayForLabForAStaff(staff1TempTable, session, className);
                                    staff2TempTable = utility.setArrayForLabForAStaff(staff2TempTable, session, className);
                                    // labs
                                    labTempTable = utility.setArrayForLabForAStaff(labTempTable, session, className);

                                    // modifying the hash tables in the array lists
                                    Class.allClasses.get(class1Position).getSchedule().replace(day, classTempTable);
                                    staff.allStaffs.get(staff1Position).getSchedule().replace(day, staff1TempTable);
                                    staff.allStaffs.get(staff2Position).getSchedule().replace(day, staff2TempTable);
                                    room.allRooms.get(lab1Position).getSchedule().replace(day, labTempTable);

                                    // altering the short forms array of a class
                                    String[] classTemp = Class.allClasses.get(class1Position).getShortFormSchedule().get(day).clone();
                                    classTemp = utility.setArrayForLabForAClassShortForm(classTemp, session, subjectShortForm);
                                    Class.allClasses.get(class1Position).getShortFormSchedule().replace(day, classTemp);

                                    // altering the subject names array of a staff
                                    // staff1
                                    String[] staffTemp = staff.allStaffs.get(staff1Position).getSubjectsSchedule().get(day).clone();
                                    staffTemp = utility.setArrayForLabForAClass(staffTemp, session, subjectName);
                                    staff.allStaffs.get(staff1Position).getSubjectsSchedule().replace(day, staffTemp);

                                    // staff2
                                    staffTemp = staff.allStaffs.get(staff2Position).getSubjectsSchedule().get(day).clone();
                                    staffTemp = utility.setArrayForLabForAClass(staffTemp, session, subjectName);
                                    staff.allStaffs.get(staff2Position).getSubjectsSchedule().replace(day, staffTemp);

                                    Log.d(STAND_ALONE_LAB_GENERATED, className + "/" + subjectName);

                                    spotted = true;
                                }
                            }
                        }
                    }
                }
            }

            numberOfTimes++;
        }
    }

    /*Method to generate schedules fpr the PAIRED labs*/
    private void pairedLabSchedule(
            String staff1,
            String staff2,
            String class1Name,
            String lab1Name,
            String lab2Name,
            String subject1,
            String subject2,
            int shortForm1,
            int shortForm2
    )
    {
        // hash table for the staffs, class, labs
        // staffs
        Hashtable<String, String[]> staff1Table = utility.returnStaffSchedule(staff1);
        Hashtable<String, String[]> staff2Table = utility.returnStaffSchedule(staff2);

        // class
        Hashtable<String, String[]> classTable = utility.returnClassSchedule(class1Name);

        // labs
        Hashtable<String, String[]> lab1Table = utility.returnLabSchedule(lab1Name);
        Hashtable<String, String[]> lab2Table = utility.returnLabSchedule(lab2Name);

        // position indicators for the staffs, class, labs
        int staff1Position = utility.returnStaffPosition(staff1);
        int staff2Position = utility.returnStaffPosition(staff2);
        int class1Position = utility.returnClassPosition(class1Name);
        int lab1Position = utility.returnLabPosition(lab1Name);
        int lab2Position = utility.returnLabPosition(lab2Name);

        // short forms for the subjects
        String subjectShortForm1 = SHORT_FORM_SUBJECTS[shortForm1];
        String subjectShortForm2 = SHORT_FORM_SUBJECTS[shortForm2];

        // paired labs will have 2 sessions per week
        for(int i = 0; i < 2; i++){
            boolean spotted = false;
            int numberOfTimes = 0;

            while (! spotted){
                /*Using Random method generating random numbers to denote the day of the week and session*/
                int randomDay = 0;
                int randomSession = 0;

                int dayMin = 1;
                int dayMax = 5; // friday

                int sessionMin = 1;
                int sessionMax = 10;

                randomDay = (int) (Math.random() * (dayMax - dayMin + 1) + dayMin);
                randomSession = (int) (Math.random() * (sessionMax - sessionMin + 1) + sessionMin);

                String day = DAYS_OF_THE_WEEK[randomDay - 1];
                String session = randomSession % 2 == 0 ? MORNING : AFTERNOON;

                // periods array of the class, staffs, labs
                String[] classTempTable = classTable.get(day).clone();
                String[] staff1TempTable = staff1Table.get(day).clone();
                String[] staff2TempTable = staff2Table.get(day).clone();
                String[] lab1TempTable = lab1Table.get(day).clone();
                String[] lab2TempTable = lab2Table.get(day).clone();

                if(numberOfTimes > 50){
                    spotted = true;
                }
                else{
                    // checking the constraints
                    // for the class
                    if(utility.overAllClassConstraintsForLab(classTempTable, session)){
                        // staffs
                        if(utility.dailyWorkingHoursOfAStaff(staff1TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                            if(utility.dailyWorkingHoursOfAStaff(staff2TempTable, LABORATORY_MORNING_SESSIONS_PERIODS.length)){
                                // labs
                                if(utility.labsSlotChecker(lab1TempTable, session)){
                                    if(utility.labsSlotChecker(lab2TempTable, session)){
                                        // slot is ready for allocation
                                        // modifying the arrays
                                        // class
                                        classTempTable = utility.setArrayForLabForAClass(classTempTable, session, subject1 + "/" + subject2);
                                        // staffs
                                        staff1TempTable = utility.setArrayForLabForAStaff(staff1TempTable, session, class1Name);
                                        staff2TempTable = utility.setArrayForLabForAStaff(staff2TempTable, session, class1Name);
                                        // labs
                                        lab1TempTable = utility.setArrayForLabForAClass(lab1TempTable, session, class1Name);
                                        lab2TempTable = utility.setArrayForLabForAClass(lab2TempTable, session, class1Name);

                                        // replacing the original arrays with the modified ones in the hash table
                                        classTable.replace(day, classTempTable);
                                        staff1Table.replace(day, staff1TempTable);
                                        staff2Table.replace(day, staff2TempTable);
                                        lab1Table.replace(day, lab1TempTable);
                                        lab2Table.replace(day, lab2TempTable);

                                        // altering the hash tables
                                        Class.allClasses.get(class1Position).setSchedule(classTable);
                                        staff.allStaffs.get(staff1Position).setSchedule(staff1Table);
                                        staff.allStaffs.get(staff2Position).setSchedule(staff2Table);
                                        room.allRooms.get(lab1Position).setSchedule(lab1Table);
                                        room.allRooms.get(lab2Position).setSchedule(lab2Table);

                                        // altering the short forms hash table of the class
                                        String[] classTemp = Class.allClasses.get(class1Position).getShortFormSchedule().get(day).clone();
                                        classTemp = utility.setArrayForLabForAClassShortForm(classTemp, session, subjectShortForm1 + "/" + subjectShortForm2);
                                        Class.allClasses.get(class1Position).getShortFormSchedule().replace(day, classTemp);

                                        // altering the subject names hash table for the staffs
                                        // staff1
                                        String[] staff1Temp = staff.allStaffs.get(staff1Position).getSubjectsSchedule().get(day).clone();
                                        staff1Temp = utility.setArrayForLabForAClass(staff1Temp, session, subject1 + "/" + subject2);
                                        staff.allStaffs.get(staff1Position).getSubjectsSchedule().replace(day, staff1Temp);

                                        // staff2
                                        String[] staff2Temp = staff.allStaffs.get(staff2Position).getSubjectsSchedule().get(day).clone();
                                        staff2Temp = utility.setArrayForLabForAClass(staff2Temp, session, subject1 + "/" + subject2);
                                        staff.allStaffs.get(staff2Position).getSubjectsSchedule().replace(day, staff2Temp);

                                        Log.d(PAIRED_LAB_GENERATED, class1Name + "/" + subject1 + "/" + subject2);

                                        spotted = true;
                                    }
                                }
                            }
                        }
                    }
                }

                numberOfTimes++;
            }
        }
    }

    // method to generate slots for PAIRED subjects
    private void generatePairedSubjectsSchedule() {
        int previousYear = 0;
        int helper = 0;
        int count = 0;

        for (String rawSubject : SUBJECTS) {
            // subject details
            int year = Integer.parseInt(String.valueOf(rawSubject.charAt(4)));
            String department = rawSubject.substring(0, 3);

            if (previousYear == 0) {
                previousYear = year;
            }
            else {
                if (previousYear == year) {
                    helper++;
                }
                else {
                    previousYear = year;
                    helper = 0;
                }
            }

            if (utility.identifyTag(rawSubject).equals(LECTURE)) {
                /*rawSubject is in the form, CSE-3-Microprocessors and Microcontrollers Laboratory*/
                String subject = rawSubject.substring(6);

                // going through all the classes' teachersCombo
                for(Class classx : Class.allClasses){
                    if(classx.getDepartment().substring(0, 3).equals(department)){
                        if(classx.getYear() == year){
                            String teachersCombo = classx.getTeachers()[helper + 1];

                            if(teachersCombo.contains(PAIRED)){
                                if(paired.pairedClasses.containsKey(classx.getName() + subject)){
                                    // getting the other class name
                                    String className2 = paired.getPairedClasses().get(classx.getName() + subject);

                                    // getting the staff details
                                    String[] temp = utility.subjectStaffDetails(teachersCombo);
                                    String staff1 = temp[0];
                                    String staff2 = temp[1];
                                    // TODO : the staff1 and staff2 should be same for both the classes
                                    // TODO : have to check this while getting the data from the excel sheets

                                    /*generatePairedSubjectsSchedule(
                                            String className1,
                                            String className2,
                                            String subject,
                                            int subjectIndex,
                                            String staff1,
                                            String staff2
                                    )*/

                                    generatePairedSubjectsSchedule(
                                            classx.getName(),
                                            className2,
                                            subject,
                                            count,
                                            staff1,
                                            staff2
                                    );
                                }

                                else{
                                    // getting the other class name from the teachersCombo
                                    String className2 = utility.returnClassName(teachersCombo);

                                    // entering the values into the paired class hash table
                                    // key - other class name + subject
                                    // value - current class name
                                    paired.pairedClasses.put(className2 + subject, classx.getName());

                                    // no need of entering the staff details, as the other class's teachersCombo will have it
                                }
                            }
                        }
                    }
                }
            }

            count++;
        }

        Log.d(GENERATE_STATUS, GENERATE_PAIRED_SUBJECTS_COMPLETED);
    }

    // method to generate slots for PAIRED subjects
    // staff1 and staff2 should be same for both the classes
    private void generatePairedSubjectsSchedule(
        String className1,
        String className2,
        String subject,
        int subjectIndex,
        String staff1,
        String staff2
    )
    {
        // hash tables for the classes, staffs
        // class
        Hashtable<String, String[]> class1Table = utility.returnClassSchedule(className1);
        Hashtable<String, String[]> class2Table = utility.returnClassSchedule(className2);

        // staffs
        Hashtable<String, String[]> staff1Table = utility.returnStaffSchedule(staff1);
        Hashtable<String, String[]> staff2Table = utility.returnStaffSchedule(staff2);

        // position indicators for the classes, staffs
        int class1Position = utility.returnClassPosition(className1);
        int class2Position = utility.returnClassPosition(className2);
        int staff1Position = utility.returnStaffPosition(staff1);
        int staff2Position = utility.returnStaffPosition(staff2);

        // short form for the subject
        String subjectShortForm = SHORT_FORM_SUBJECTS[subjectIndex];

        for(int i = 0; i < NUMBER_OF_PERIODS_LECTURE; i++){
            boolean spotted = false;
            int numberOfTimes = 0;

            while(! spotted){
                /*Using Random method generating random numbers to denote the day of the week and session*/
                int randomDay = 0;
                int randomSession = 0;

                int dayMin = 1;
                int dayMax = 6; // saturday

                int sessionMin = 0;
                int sessionMax = NUMBER_OF_PERIODS_PER_DAY - 1;

                randomDay = (int) (Math.random() * (dayMax - dayMin + 1) + dayMin);
                randomSession = (int) (Math.random() * (sessionMax - sessionMin + 1) + sessionMin);

                String day = DAYS_OF_THE_WEEK[randomDay - 1];

                // periods array of the classes, staffs
                String[] class1TempTable = class1Table.get(day).clone();
                String[] class2TempTable = class2Table.get(day).clone();
                String[] staff1TempTable = staff1Table.get(day).clone();
                String[] staff2TempTable = staff2Table.get(day).clone();

                if(numberOfTimes > 150){
                    spotted = true;
                }

                else{
                    // checking for the constraints
                    // classes
                    if(utility.overAllClassConstraints(className1, class1TempTable, randomSession, subject)){
                        if(utility.overAllClassConstraints(className2, class2TempTable, randomSession, subject)){
                            // staffs
                            if(utility.overAllConstraintsForAStaff(staff1TempTable, randomSession, 1)){
                                if(utility.overAllConstraintsForAStaff(staff2TempTable, randomSession, 1)){
                                    // slot is ready for allocation
                                    // altering the arrays
                                    // classes
                                    class1TempTable[randomSession] = subject;
                                    class2TempTable[randomSession] = subject;
                                    // staffs
                                    staff1TempTable[randomSession] = className1 + "/" + className2;
                                    staff2TempTable[randomSession] = className1 + "/" + className2;

                                    // replacing the original arrays with the modified ones
                                    class1Table.replace(day, class1TempTable);
                                    class2Table.replace(day, class2TempTable);
                                    staff1Table.replace(day, staff1TempTable);
                                    staff2Table.replace(day, staff2TempTable);

                                    // altering the short form arrays of the classes
                                    // class1
                                    String[] classTemp = Class.allClasses.get(class1Position).getShortFormSchedule().get(day).clone();
                                    classTemp[randomSession] = subjectShortForm;
                                    Class.allClasses.get(class1Position).getShortFormSchedule().replace(day, classTemp);

                                    // class2
                                    classTemp = Class.allClasses.get(class2Position).getShortFormSchedule().get(day).clone();
                                    classTemp[randomSession] = subjectShortForm;
                                    Class.allClasses.get(class2Position).getShortFormSchedule().replace(day, classTemp);

                                    // altering the subject name arrays of the staffs
                                    // staff1
                                    String[] staffTemp = staff.allStaffs.get(staff1Position).getSubjectsSchedule().get(day).clone();
                                    staffTemp[randomSession] = subject;
                                    staff.allStaffs.get(staff1Position).getSubjectsSchedule().replace(day, staffTemp);

                                    // staff2
                                    staffTemp = staff.allStaffs.get(staff2Position).getSubjectsSchedule().get(day).clone();
                                    staffTemp[randomSession] = subject;
                                    staff.allStaffs.get(staff2Position).getSubjectsSchedule().replace(day, staffTemp);

                                    Log.d(SUBJECTS_GENERATED, (i + 1) + " / " + className1 + " / " + className2 + " / " +subject);

                                    spotted = true;
                                }
                            }
                        }
                    }
                }

                numberOfTimes++;
            }
        }
    }
}
