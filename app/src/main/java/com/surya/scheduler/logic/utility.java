package com.surya.scheduler.logic;

import static com.surya.scheduler.app.surya.UPDATE_CHANNEL;
import static com.surya.scheduler.constants.data.DAYS;
import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LABORATORY_INDICATORS;
import static com.surya.scheduler.constants.data.LECTURE;
import static com.surya.scheduler.constants.data.MORNING;
import static com.surya.scheduler.constants.data.PLACEMENT;
import static com.surya.scheduler.constants.data.PLACEMENT_INDICATORS;
import static com.surya.scheduler.constants.data.SHORT_FORM_SUBJECTS;
import static com.surya.scheduler.constants.data.SUBJECTS;
import static com.surya.scheduler.constants.settings.LABORATORY_AFTERNOON_SESSION_PERIODS;
import static com.surya.scheduler.constants.settings.LABORATORY_MORNING_SESSIONS_PERIODS;
import static com.surya.scheduler.constants.settings.MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY;
import static com.surya.scheduler.constants.settings.MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY_WITH_LAB;
import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_PER_DAY;
import static com.surya.scheduler.constants.status.SUBJECT_REPEATED;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.surya.scheduler.R;
import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

import java.util.ArrayList;
import java.util.Hashtable;

public class utility {
    // class solely meant for many functions that may be repeated several times in the project
    int morningSlot1 = LABORATORY_MORNING_SESSIONS_PERIODS[0];
    int morningSlot2 = LABORATORY_MORNING_SESSIONS_PERIODS[1];
    int morningSlot3 = LABORATORY_MORNING_SESSIONS_PERIODS[2];

    int afternoonSlot1 = LABORATORY_AFTERNOON_SESSION_PERIODS[0];
    int afternoonSlot2 = LABORATORY_AFTERNOON_SESSION_PERIODS[1];
    int afternoonSlot3 = LABORATORY_AFTERNOON_SESSION_PERIODS[2];

    // Constructor
    public utility(){

    }

    /*Method to identify if a subject is a LAB, LECTURE or a PLACEMENT class*/
    /*PASS THE RAW STRING AS THE ARGUMENT*/
    public String identifyTag(String subject){

        for(String labIndicator : LABORATORY_INDICATORS){
            if(subject.contains(labIndicator)){
                return LAB;
            }
        }

        for(String placementIndicator : PLACEMENT_INDICATORS){
            if(subject.contains(placementIndicator)){
                return PLACEMENT;
            }
        }

        return LECTURE;
    }

    /*Method to return the staff names and labNames from the rawString*/
    public String[] labStaffsDetails(String teachersCombo){
        String staff1 = null;
        String staff2 = null;
        String lab1Name = null;

        if(teachersCombo.contains("@")){
            // "Mrs. C Karpagavalli+Mrs. Archana V Nair S+"+CSE_LAB_2+STAND_ALONE,
            String[] temp = teachersCombo.split("@");
            staff1 = temp[0];
            staff2 = temp[1];
        }

        else{
            /*Getting the staff details*/
            for(staff Staff : staff.allStaffs){
                if(teachersCombo.contains(Staff.getName())){
                    if(staff1 == null){
                        staff1 = Staff.getName();
                    }
                    else{
                        if(! Staff.getName().equals(staff1)){
                            if(staff2 == null){
                                staff2 = Staff.getName();
                            }
                        }
                    }
                }
            }
        }

        /*Getting the lab details*/
        for(room Room : room.allRooms){
            if(teachersCombo.contains(Room.getName())){
                if(lab1Name == null){
                    lab1Name = Room.getName();
                }
            }
        }

        return new String[]{
                staff1, staff2, lab1Name
        };
    }

    /*Method to return the staff names from the rawString*/
    public String[] subjectStaffDetails(String teacherCombo){
        String staff1 = null;
        String staff2 = null;

        /*Getting the staff details*/
        for(staff Staff : staff.allStaffs){
            if(teacherCombo.contains(Staff.getName())){
                if(staff1 == null){
                    staff1 = Staff.getName();
                }
                else{
                    if(! (Staff.getName().equals(staff1))){
                        staff2 = Staff.getName();
                        // loop can be broken once the staff2 is assigned
                        break;
                    }
                }
            }
        }

        return new String[]{
                staff1, staff2
        };
    }

    // method to return a className from the teachersCombo
    public String returnClassName(String teachersCombo){
        for(Class classx : Class.allClasses){
            if(teachersCombo.contains(classx.getName())){
                return classx.getName();
            }
        }

        // unreachable statement
        return FREE;
    }

    // method to return the hash table for the given class
    // class name is the argument
    public Hashtable<String, String[]> returnClassSchedule(String className){
        for(Class classx : Class.allClasses){
            if(classx.getName().equals(className)){
                return classx.getSchedule();
            }
        }

        // unreachable statement
        return new Hashtable<>();
    }

    public Hashtable<String, String[]> returnShortFormClassSchedule(String className){
        for(Class classx : Class.allClasses){
            if(classx.getName().equals(className)){
                return classx.getShortFormSchedule();
            }
        }

        // unreachable statement
        return new Hashtable<>();
    }

    // method to return the hash table for the given staff
    // staff name is the argument
    public Hashtable<String, String[]> returnStaffSchedule(String staffName){
        for(staff Staff : staff.allStaffs){
            if(Staff.getName().contains(staffName)){
                return Staff.getSchedule();
            }
        }

        // unreachable statement
        return new Hashtable<>();
    }

    // method to return the hash table for the given lab
    // lab name is the argument
    public Hashtable<String, String[]> returnLabSchedule(String labName){
        for(room Room : room.allRooms){
            if(Room.getName().equals(labName)){
                return Room.getSchedule();
            }
        }

        // unreachable statement
        return new Hashtable<>();
    }

    // method to return all the ArrayList<String[]> present in a hash table
    public ArrayList<String[]> returnActualPeriods(Hashtable<String, String[]> schedule){
        ArrayList<String[]> periods = new ArrayList<>();

        // going through the hash table
        // TODO : using the whole DAYS_OF_THE_WEEK as the key
        for(String day : DAYS_OF_THE_WEEK){
            periods.add(schedule.get(day).clone());
        }

        return periods;
    }

    // method to alter the array for class, staffs, labs
    // setting the subject name in the arrays
    // String[] is the argument
    // can also be used for a staff
    public String[] setArrayForLabForAClass(String[] periods, String session, String subjectName){
        if(session.equals(MORNING)){
            periods[morningSlot1] = periods[morningSlot2] = periods[morningSlot3] = subjectName;
            return periods;
        }
        else{
            // session is AFTERNOON
            periods[afternoonSlot1] = periods[afternoonSlot2] = periods[afternoonSlot3] = subjectName;
            return periods;
        }
    }

    // setting the subjects' short forms in the arrays
    // String[] is the argument
    public String[] setArrayForLabForAClassShortForm(String[] periods, String session, String shortForm){
        if(session.equals(MORNING)){
            periods[morningSlot1] = periods[morningSlot2] = periods[morningSlot3] = shortForm;
            return periods;
        }
        else{
            // session is AFTERNOON
            periods[afternoonSlot1] = periods[afternoonSlot2] = periods[afternoonSlot3] = shortForm;
            return periods;
        }
    }

    // method to set the arrays for a staff (can also be used for labs)
    // setting the classNames in the array
    // String[] is the argument
    public String[] setArrayForLabForAStaff(String[] periods, String session, String className){
        if(session.equals(MORNING)){
            periods[morningSlot1] = periods[morningSlot2] = periods[morningSlot3] = className;
            return periods;
        }
        else{
            // session is AFTERNOON
            periods[afternoonSlot1] = periods[afternoonSlot2] = periods[afternoonSlot3] = className;
            return periods;
        }
    }

    /**************************************************************************************************************************************/
    /****************************************************************************************************************************************/
    // NORMAL SCHEDULE MODE
    /*Method to check if the slots are FREE for the labs*/
    public boolean labsSlotChecker(String[] schedule, String session){
        if(session.equals(MORNING)){
            /*If the session is MORNING, checking if the slots 2, 3, 4 are FREE*/
            /*Returns true only if it is FREE, else false*/
            return ( (schedule[morningSlot1].equals(FREE)) && (schedule[morningSlot2].equals(FREE)) && (schedule[morningSlot3].equals(FREE)) );
        }
        else{
            /*If the session is AFTERNOON, checking if the slots 5, 6, 7th are FREE*/
            /*Returns true only if it is FREE, else false*/
            return ( (schedule[afternoonSlot1].equals(FREE)) && (schedule[afternoonSlot2].equals(FREE)) && (schedule[afternoonSlot3].equals(FREE)) );
        }
    }

    // method to check if the particular slot is FREE
    public boolean isTheSlotFree(String[] periods, int slot){
        return periods[slot].equals(FREE);
    }

    // method to check if the subject is already there
    // a subject can be allocated twice in a day, that too only once in a week
    public boolean isTheSubjectRepeated(String className, String subject, String dayx){
        boolean alreadyRepeated = false;

        if(paired.subjectRepeatedForClass.containsKey(className + subject)){
            // if the key is present, that means this subject is already allocated twice on some day
            // now if dayx == day, we can straightly return true
            // as, already allocated twice so there is no need of any checking
            String inDay = paired.subjectRepeatedForClass.get(className + subject);
            alreadyRepeated = true;
            if(dayx.equals(inDay)){
                return true;
            }
        }

        // hash table for the class
        Hashtable<String, String[]> classTable = returnClassSchedule(className);

        if(alreadyRepeated){
            // means the subject is allocated twice on some day
            // so just checking if the subject is already there on the day, dayx
            String[] periods = classTable.get(dayx).clone();
            for(String period : periods){
                if(period.equals(subject)){
                    // means the subject is already allocated once in that day
                    // so cannot be done yet another time
                    return true;
                }
            }

            // subject is not yet allocated on that day dayx
            // so can be allocated
            return false;
        }

        else{
            // subject is not yet allocated twice, not yet logged in the paired class to say the least
            // going through the class hash table
            boolean repeated = false;
            for(String day : DAYS_OF_THE_WEEK){
                String[] periods = classTable.get(day).clone();
                int count = 0;

                for(int i = 0; i < periods.length; i++){
                    if(periods[i].equals(subject)){
                        count++;

                        if(count > 1){
                            repeated = true;
                            // entering the details into the paired class
                            paired.subjectRepeatedForClass.put(className + subject, day);
                            Log.d(SUBJECT_REPEATED, className + " / " + subject + " / " + day);
                        }
                    }
                }
            }

            if(repeated){
                // means the subject is repeated on some day of the week
                // means, the subject can be allocated only once hereafter no matter what
                String[] periods = classTable.get(dayx).clone();
                int count = 0;

                for(String period : periods){
                    if(period.equals(subject)){
                        count++;

                        if(count >= 1){
                            // means the subject is allocated once or more than once
                            return true;
                        }
                    }
                }

                // subject is not allocated at all
                return false;
            }
        }

        // the subject is not allocated twice on any given day in a week
        return false;
    }

    // method to check if the subject to be allocated is already present before or after the given slot
    public boolean isTheSubjectContinuous(String[] periods, int slot, String subject){
        if(slot == 0){
            // first period
            // then, the second period must not be this subject
            return (! (periods[1].contains(subject)));
        }
        else if(slot == NUMBER_OF_PERIODS_PER_DAY - 1){
            // last period
            // then, the period before that must not be this subject
            return (! (periods[NUMBER_OF_PERIODS_PER_DAY - 2].contains(subject)));
        }
        else{
            // some random period
            // then, the period before or after that slot must not contain the subject
            return ((! (periods[slot - 1].contains(subject))) && (! (periods[slot + 1].contains(subject))));
        }
    }

    /*Method used to check if a lab is already assigned to the class in the particular day*/
    /*Returns true if already one lab is assigned, else false*/
    public boolean isALabAlreadyThere(String[] schedule){
        for(String period : schedule){
            for(String labIndicator : LABORATORY_INDICATORS){
                if(period.contains(labIndicator)){
                    return true;
                }
            }
        }
        return false;
    }

    // method to check the constraints for a class while assigning labs
    // schedule - schedule of a class on a given day
    // session - MORNING or AFTERNOON
    public boolean overAllClassConstraintsForLab(String[] schedule, String session){
        return ( (labsSlotChecker(schedule, session)) && (! isALabAlreadyThere(schedule)));
    }

    public boolean overAllClassConstraints(String className, String[] periods, int slot, String subject, String day){
        return ( (isTheSlotFree(periods, slot)) && (! (isTheSubjectRepeated(className, subject, day))) && (isTheSubjectContinuous(periods, slot, subject)));
    }

    /***********************************************************************************************************************************/
    // teachers constraints
    // method to check the daily working hours of the staffs
    public boolean dailyWorkingHoursOfAStaff(String[] periods, int number){
        int count = 0;

        for(String period : periods){
            if(! (period.equals(FREE))){
                count ++;
            }
        }

        count = count + number;

        // checking any lab is assigned to that staff on that given day
        // if lab is assigned, the staff can have a maximum of 5 periods
        if(periods[morningSlot1].equals(periods[morningSlot2]) && periods[morningSlot2].equals(periods[morningSlot3])){
            if(! (periods[morningSlot1].equals(FREE))){
                return count <= MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY_WITH_LAB;
            }
        }

        if(periods[afternoonSlot1].equals(periods[afternoonSlot2]) && periods[afternoonSlot2].equals(periods[afternoonSlot3])){
            if(! (periods[afternoonSlot1].equals(FREE))){
                return count <= MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY_WITH_LAB;
            }
        }

        // if no labs assigned, the staff can have only 3 periods at maximum
        return count <= MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY;
    }

    // method to check the work load of a staff
    public boolean isTheSlotOkayForTheStaff(String[] periods, int slot){
        if(slot == 0){
            // first period
            // either second or third period must be FREE
            return periods[1].equals(FREE) || periods[2].equals(FREE);
        }
        else if(slot == NUMBER_OF_PERIODS_PER_DAY - 1){
            // last period
            // either the period before the last period or the one before that must be FREE
            return periods[NUMBER_OF_PERIODS_PER_DAY - 2].equals(FREE) || periods[NUMBER_OF_PERIODS_PER_DAY - 3].equals(FREE);
        }
        else{
            // some random periods
            // then, either the period before or the one after must be FREE
            return periods[slot - 1].equals(FREE) || periods[slot + 1].equals(FREE);
        }
    }

    // overall constraints for a staff
    public boolean overAllConstraintsForAStaffLab(String[] periods, String session, int number){
        return ( (labsSlotChecker(periods, session)) && (dailyWorkingHoursOfAStaff(periods, number)) );
    }

    public boolean overAllConstraintsForAStaff(String[] periods, int slot, int number){
        // number - number of hours for a subject
        return ( (isTheSlotFree(periods, slot)) && (dailyWorkingHoursOfAStaff(periods, number)) && (isTheSlotOkayForTheStaff(periods, slot)));
    }

    /*********************************************************************************************************************************/
    // method to return the position of a class in the Class.allClasses arrayList
    public int returnClassPosition(String className){
        int index = 0;

        for(Class classx : Class.allClasses){
            if(classx.getName().equals(className)){
                return index;
            }

            index++;
        }

        // unreachable statement
        return index;
    }

    // method to return the position of a staff in the staff.allStaff arraylist
    public int returnStaffPosition(String staffName){
        int index = 0;

        for(staff Staff : staff.allStaffs){
            if(Staff.getName().equals(staffName)){
                return index;
            }

            index++;
        }

        // unreachable statement
        return index;
    }

    // method to return the position of a lab in the room.allRooms arraylist
    public int returnLabPosition(String labName){
        int index = 0;

        for(room Room : room.allRooms){
            if(Room.getName().equals(labName)){
                return index;
            }

            index++;
        }

        // unreachable statement
        return index;
    }

    // count is the index of the particular subject in the SUBJECTS array
    public void addToHashedSubjectsArray(String subject, int count){
        boolean alreadyThere = false;
        for(String subject1 : paired.hashedSubjectsArray){
            if(subject1.equals(subject)){
                alreadyThere = true;
                break;
            }
        }
        if(! alreadyThere){
            paired.hashedSubjectsArray.add(subject);
            paired.hashedSubjectsInteger.add(count);
        }
    }

    // method to set all the periods of the labs as FREE
    // during normal scheduling time, 1st and 5th periods are NOT ALLOWED
    // making them as FREE here
    public void freeLabSchedules(){
        for(room Room : room.allRooms){
            if(Room.isLab()){
                for(String day : DAYS_OF_THE_WEEK){
                   // Room.getSchedule().replace(day, PERIODS);
                }
            }
        }
    }

    // method to return the index of the subject
    // shortForm is th4 parameter
    public int returnSubjectIndex(String subjectShortForm){
        // shortForm details
        String department = subjectShortForm.substring(0, 3);
        int year = Integer.parseInt(String.valueOf(subjectShortForm.charAt(4)));

        // going through the SHORT_FORM_SUBJECTS array
        int index = 0;
        int previousYear = 0;

        for(String subject : SHORT_FORM_SUBJECTS){
            // subject details
            String dep = subject.substring(0, 3);
            int yearx = Integer.parseInt(String.valueOf(subject.charAt(4)));

            if(previousYear == 0){
                previousYear = yearx;
            }
            else{
                if(previousYear == yearx){
                    index++;
                }
                else{
                    previousYear = yearx;
                    index = 0;
                }
            }

            if(yearx == year){
                if(department.equals(dep)){
                    if(subject.equals(subjectShortForm)){
                        return index;
                    }
                }
            }
        }

        // unreachable statement
        return index;
    }

    // method to check if the slot is ready for the omitted_subject allocation
    // returns true if the slot if fit
    public boolean isTheSlotFitForOmittedAllocation(String omittedSubject){
        return false;
    }

    // method to return the Days, period number row
    public String[] returnPeriodNumberArray(){
        String[] temp = new String[NUMBER_OF_PERIODS_PER_DAY + 1];

        temp[0] = DAYS + " / Periods";

        for(int i = 1; i < temp.length; i++){
            temp[i] = i+"";
        }

        return temp;
    }

    // method to return the String[] periods with the day Text in the 0th index
    public String[] returnDayedPeriods(String[] wPeriods, int dayNo){
        String[] oPeriods = new String[wPeriods.length + 1];

        // adding the day to the 0th index
        oPeriods[0] = DAYS_OF_THE_WEEK[dayNo];

        // adding the periods
        for(int i = 0; i < wPeriods.length; i++){
            oPeriods[i + 1] = wPeriods[i];
        }

        return oPeriods;
    }

    // create a notification to show to the user when a time table has been generated
    public void showUpdateNotification(Context context){
        // creating a notification
        Notification notification = new NotificationCompat.Builder(context, UPDATE_CHANNEL)
                .setContentTitle("ACET")
                .setContentText("New time table has been generated..")
                .addAction(R.drawable.sim, "VIEW", null)
                .setSmallIcon(R.drawable.sim)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }
}
