package com.surya.scheduler.logic;

import static com.surya.scheduler.constants.data.DAYS_OF_THE_WEEK;
import static com.surya.scheduler.constants.data.FREE;
import static com.surya.scheduler.constants.data.LAB;
import static com.surya.scheduler.constants.data.LABORATORY_INDICATORS;
import static com.surya.scheduler.constants.data.LECTURE;
import static com.surya.scheduler.constants.data.MORNING;
import static com.surya.scheduler.constants.data.PLACEMENT;
import static com.surya.scheduler.constants.data.PLACEMENT_INDICATORS;
import static com.surya.scheduler.constants.settings.LABORATORY_AFTERNOON_SESSION_PERIODS;
import static com.surya.scheduler.constants.settings.LABORATORY_MORNING_SESSIONS_PERIODS;
import static com.surya.scheduler.constants.settings.MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY;
import static com.surya.scheduler.constants.settings.MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY_WITH_LAB;

import com.surya.scheduler.models.offline.Class;
import com.surya.scheduler.models.offline.paired;
import com.surya.scheduler.models.offline.room;
import com.surya.scheduler.models.offline.staff;

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

    // method to set the arrays based on the number of periods entered while

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

    // method to check if the subject is already there
    public boolean isTheSubjectAlreadyThere(String[] periods, String subject){
        boolean isThere = false;

        for(String period : periods){
            if(period.contains(subject)){
                isThere = true;
                return isThere;
            }
        }

        return isThere;
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
}
