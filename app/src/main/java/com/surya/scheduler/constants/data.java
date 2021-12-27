package com.surya.scheduler.constants;

import static com.surya.scheduler.constants.settings.NUMBER_OF_PERIODS_PER_DAY;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

public class data {

    /*SHARED PREFERENCES CONSTANTS*/
    public static final String OFFLINE_MODE = "OFFLINE";
    public static final String ONLINE_MODE = "ONLINE";
    public static final String MODE_OF_SCHEDULES = "MODE_OF_SCHEDULES";
    public static final String COACHING_MODE = "COACHING";
    public static final String IS_THE_USER_A_ADMIN = "isTheUserAAdmin";
    public static final String APP_DEFAULTS = "appDefaults";
    public static final String APP_OPENED_ONCE = "appOpenedBefore";
    public static final String ALL_CLASSES_LIST = "allClassesList";
    public static final String ALL_STAFFS_LIST = "allStaffsList";
    public static final String ALL_CLASSES_STAFFS_LIST = "allClassesStaffsList";
    public static final String TIMETABLE = "timeTable";
    public static final String ALL_LISTS = "allLists";
    public static final String SCHEDULES = "schedules";
    public static final String HASHED_SUBJECTS = "hashedSubjects";
    public static final String HASHED = "hashed";
    public static final String HASHED_INTEGERS = "hashed_integers";
    public static final String ALL_CLASSES_STRENGTHS = "all_classes_strengths";
    public static final String ALL_ADMINS = "All Admins";

    /*firebase constants*/
    public static final String ALL_CLASSES_DETAILS = "ALL CLASSES DETAILS";
    public static final String ALL_STAFFS_DETAILS = "ALL STAFFS DETAILS";
    public static final String ALL_LABS_DETAILS = "ALL LABS DETAILS";
    public static final String BASIC_DETAILS = "BASIC DETAILS";
    public static final String ADMINS_LIST = "ADMINS LIST";
    public static final String CLASS_SCHEDULES = "CLASS SCHEDULES";
    public static final String STAFF_SCHEDULES = "STAFF SCHEDULES";

    /*ALL THE RAW DATA*/
    public static final int STAFFS_REC_VIEW = 0;
    public static final int ROOMS_REC_VIEW = 1;

    public static final int CLASS_SCHEDULE = 0;
    public static final int STAFF_SCHEDULE = 1;
    public static final int ROOM_SCHEDULE  = 2;
    public static final int STAFF_CONSTRAINT_SCHEDULE = 3;

    /*INTENT CONSTANTS*/
    public static final String CLASS_NAME = "class_name";
    public static final String STAFF_NAME = "Staff_name";
    public static final String STAFF_DEPARTMENT = "Staff_department";
    public static final String LAB_NAME = "Lab_name";
    public static final String FROM = "from";
    public static final String STAFF = "staff";
    public static final String CLASS = "class";
    public static final String LABSS = "Labs";
    public static final String ROOM = "room";

    /*DAYS OF THE WEEK*/
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String TAG = "TAG";

    public static String[] DAYS_OF_THE_WEEK = {
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY
    };

    /*ONLINE AND OFFLINE MODE TIMING*/
    // OFFLINE MODE - 50 minutes per period and 10 minutes break, excepting 40 minutes lunch break
    public static final Hashtable<Integer, LocalTime> OFFLINE_MODE_PERIODS_START_TIME = new Hashtable<Integer, LocalTime>(){
        /*period number and their starting times*/
        {
            put(1, LocalTime.of(9, 10));
            put(2, LocalTime.of(10, 0));
            // 10 minutes break b/w 2nd and 3rd period
            put(3, LocalTime.of(11, 0));
            put(4, LocalTime.of(11, 50));
            // lunch break
            // 12:40 - 13:20 lunch break
            put(5, LocalTime.of(13, 20));
            put(6, LocalTime.of(14, 10));
            // 10 minutes break b/w 6th and 7th period
            put(7, LocalTime.of(15, 10));
            put(8, LocalTime.of(16, 0));
        }
    };

    public static final Hashtable<Integer, LocalTime> OFFLINE_MODE_PERIODS_END_TIME = new Hashtable<Integer, LocalTime>(){
        // period numbers and their ending times
        {
            put(1, LocalTime.of(10, 0));
            put(2, LocalTime.of(10, 50));
            // 10 minutes break b/w 2nd and 3rd period
            put(3, LocalTime.of(11, 50));
            put(4, LocalTime.of(12, 40));
            // lunch break
            // 12:40 - 13:20 lunch break
            put(5, LocalTime.of(14, 10));
            put(6, LocalTime.of(15, 0));
            // 10 minutes break b/w 6th and 7th period
            put(7, LocalTime.of(16, 0));
            put(8, LocalTime.of(16, 50));
        }
    };

    /*ONLINE OFFLINE HASHTABLE*/
    public static final Hashtable<Integer, String> ONLINE_OFFLINE_INDICATOR = new Hashtable<Integer, String>(){
        {
            // day and the years who have the offline classes on that day
            put(2, MONDAY+WEDNESDAY+FRIDAY);
            put(3, TUESDAY+THURSDAY+SATURDAY);
            put(4, MONDAY+TUESDAY+WEDNESDAY+THURSDAY+FRIDAY+SATURDAY);
        }
    };

    public static final int ONLINE_CLASS_SCHEDULE = 0;
    public static final int ONLINE_STAFF_SCHEDULE = 1;

    /*ALL DEPARTMENTS*/
    public static String CSE_DEPARTMENT = "CSE-DEPARTMENT";
    public static String ECE_DEPARTMENT = "ECE-DEPARTMENT";
    public static String SH_DEPARTMENT = "FYR-DEPARTMENT";
    public static String CIVIL_DEPARTMENT = "CIVIL-DEPARTMENT";
    public static String EEE_DEPARTMENT = "EEE-DEPARTMENT";
    public static String MECH_DEPARTMENT = "MEC-DEPARTMENT";
    public static final String ADMIN_BLOCK = "ADM-BLOCK";

    public static String[] ALL_DEPARTMENTS = {
            SH_DEPARTMENT,
            CSE_DEPARTMENT,
            ECE_DEPARTMENT,
            MECH_DEPARTMENT,
            CIVIL_DEPARTMENT,
            EEE_DEPARTMENT
    };

    /*Rough words*/
    public static String FREE = "free";
    public static final String ALLOWED = "Allowed";
    public static String NOT_ALLOWED = "Not Allowed";
    public static final String AFTERNOON = "Afternoon";
    public static final String MORNING = "Morning";
    public static final int SUCCESSFUL_INT_INDICATOR = 100;
    public static final int FAILED_INT_INDICATOR = -100;

    public static final String ONLINE = "Online";
    public static final String OFFLINE = "Offline";
    public static final String HI = "+";
    public static String[] PERIODS = new String[NUMBER_OF_PERIODS_PER_DAY];
    public static String[] LAB_PERIODS = {NOT_ALLOWED, FREE, FREE, FREE, FREE, FREE, FREE};
    public static String[] NOT_AVAILABLE = {NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED};
    public static String[] ONLINE_PERIODS = {FREE, FREE, FREE, FREE, FREE, FREE};
    public static String[] ALLOWED_STRINGS = {ALLOWED, ALLOWED, ALLOWED, ALLOWED, ALLOWED, ALLOWED, ALLOWED};
    public static String[] STAFF_CONSTRAINT_LESS_PERIODS = {HI, HI, HI, HI, HI, HI, HI};
    public static String[] COACHING_LABS_PERIODS = {NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, NOT_ALLOWED, FREE, FREE, FREE, FREE};

    /*NAMES OF THE CLASSES AND THE LABS*/
    public static String CSE_IV_A = "CSE-IV-A";
    public static String CSE_III_A = "CSE-III-A";
    public static String CSE_III_B = "CSE-III-B";
    public static String CSE_II_A = "CSE-II-A";
    public static String CSE_II_B = "CSE-II-B";

    public static final String ECE_IV_A = "ECE-IV-A";
    public static final String ECE_III_A = "ECE-III-A";
    public static final String ECE_II_A = "ECE-II-A";

    public static final String MECH_II_A = "MEC-II-A";
    public static final String MECH_III_A = "MEC-III-A";
    public static final String MECH_IV_A = "MEC-IV-A";

    public static final String EEE_IV_A = "EEE-IV-A";
    public static final String EEE_III_A = "EEE-III-A";
    public static final String EEE_II_A = "EEE-II-A";

    public static final String CIV_IV_A = "CIV-IV-A";
    public static final String CIV_III_A = "CIV-III-A";
    public static final String CIV_II_A = "CIV-II-A";

    public static final String FYR_I_A = "FYR-I-A";
    public static final String FYR_I_B = "FYR-I-B";
    public static final String FYR_I_C = "FYR-I-C";
    public static final String FYR_I_D = "FYR-I-D";

    public static final String FYR_I_CHE = "FYR-I-CHE LAB";

    public static String CSE_LAB_1 = "CSE-LAB-1";
    public static String CSE_LAB_2 = "CSE-LAB-2";
    public static String CSE_LAB_3 = "CSE-LAB-3";
    public static String CSE_LAB_4 = "CSE-LAB-4";

    public static String ECE_LAB_1 = "ECE-LAB-1";
    public static String ECE_LAB_2 = "ECE-LAB-2";
    public static String ECE_LAB_3 = "ECE-LAB-3";
    public static final String ECE_LAB_4 = "ECE-LAB-Physics";

    public static final String FYR_LAB_1 = "FYR-Electrical Machines I Lab";
    public static final String ADM_COM_LAB = "ADM-LAB-computer Lab";

    public static final String CIVIL_LAB_1 = "CIV-LAB-1";
    public static final String CIVIL_LAB_2 = "CIV-LAB-2";

    public static String MECH_LAB_1 = "MEC-LAB-AW"; // English Lab
    public static String MECH_LAB_2 = "MEC-LAB-AW2";
    public static String MECH_LAB_3 = "MEC-MT LAB";
    public static String MECH_LAB_4 = "MEC-CAD LAB";
    public static String MECH_LAB_5 = "MEC-Kinematics and Dynamics Laboratory";
    public static String MECH_LAB_6 = "MEC-Thermal lab";
    public static String MECH_LAB_7 = "MEC-Metrology and Measurements Laboratory";
    public static final String MECH_LAB_8 = "MEC-Mechatronics Lab";

    public static String EEE_LAB_1 = "EEE-Electrical LAB";
    public static final String EEE_LAB_2 = "EEE-LAB-2";
    public static final String EEE_LAB_3 = "EEE-LAB-3";
    public static final String EEE_LAB_4 = "EEE-LAB-4";

    public static String[] ALL_ROOMS = {
            /*CSE ROOMS*/
            CSE_IV_A,
            CSE_III_B,
            CSE_III_A,
            CSE_II_A,
            CSE_II_B,
            /*CSE LABS*/
            CSE_LAB_1,
            CSE_LAB_2,
            CSE_LAB_3,
            CSE_LAB_4,
            /*ECE LABS*/
            ECE_LAB_1,
            ECE_LAB_2,
            ECE_LAB_3,
            /*MECH LABS*/
            MECH_LAB_1,
            MECH_LAB_2,
            MECH_LAB_3,
            MECH_LAB_4,
            MECH_LAB_5,
            MECH_LAB_6,
            MECH_LAB_7,
            MECH_LAB_8,
            ADM_COM_LAB,
            /*FYR LABS*/
            FYR_LAB_1,
            FYR_I_CHE,
            /*CIVIL LABS*/
            CIVIL_LAB_1,
            CIVIL_LAB_2,
            /*ECE ROOMS*/
            ECE_IV_A,
            ECE_III_A,
            ECE_II_A,
            /*EEE LABS*/
            EEE_LAB_1,
            EEE_LAB_2,
            EEE_LAB_3,
            EEE_LAB_4,
            ECE_LAB_4,
            /*MECH ROOMS*/
            MECH_IV_A,
            MECH_III_A,
            MECH_II_A,
            /*CIVIL ROOMS*/
            CIV_IV_A,
            CIV_III_A,
            CIV_II_A,
            /*EEE CLASSES*/
            EEE_IV_A,
            EEE_III_A,
            EEE_II_A,
            // First Years
            FYR_I_A,
            FYR_I_B,
            //FYR_I_C,
            //FYR_I_D
    };

    // ignore this line

    /*Total strength of each classes*/
    public static HashMap<String, Integer> CLASS_STRENGTH = new HashMap<String, Integer>(){
        {
            put(CSE_IV_A, 61);
            put(CSE_III_A, 51);
            put(CSE_III_B, 50);
            put(CSE_II_A, 46);
            put(CSE_II_B, 45);
            put(ECE_IV_A, 43);
            put(ECE_III_A, 41);
            put(ECE_II_A, 52);
            put(MECH_II_A, 29);
            put(MECH_III_A, 57);
            put(MECH_IV_A, 57);
            put(EEE_IV_A, 27);
            put(EEE_III_A, 20);
            put(EEE_II_A, 29);
            put(CIV_IV_A, 20);
            put(CIV_III_A, 23);
            put(CIV_II_A, 4);
            put(FYR_I_A, 57);
            put(FYR_I_B, 57);
            put(FYR_I_C, 44);
            put(FYR_I_D, 43);
        }
    };

    /*TAGS*/
    public static String LECTURE = "Lecture";
    public static String LAB = "Laboratory";
    public static final String LAB1 = "Lab";
    public static String PLACEMENT = "Placement";
    public static final String LIBRARY = "Library";
    public static String VALUE = "Value";
    public static String SPEAKING = "Speaking";
    public static final String AIDED_DRAWING = "Aided Machine Drawing";
    public static String STAND_ALONE = "Stand Alone";
    public static String PAIRED = "Paired";
    public static final String CONTINUOUS = "continuous";
    public static final String TWO = "two";
    public static final String ONE = "one";
    public static final String THREE = "three";
    public static final String DUMMY = "Dummy";
    public static final String NORMAL = "Normal";
    public static final String CLASS_STRENGTH_TEXT = "Class strength";

    // designations
    public static final String MR = "Mr. ";
    public static final String MRS = "Mrs. ";
    public static final String MS = "Ms. ";
    public static final String DR = "Dr. ";

    /*SUBJECTS*/
    public static String[] SUBJECTS = {
            /*FOURTH YEAR CSE SUBJECTS*/
            "CSE-4-Principles of Management",
            "CSE-4-Cryptography and Network Security",
            "CSE-4-Cloud Computing",
            "CSE-4-Robotics(OE II)",
            "CSE-4-Service Oriented Architecture/Software Project Management",
            "CSE-4-Multi-core Architectures and Programming",
            "CSE-4-Cloud Computing Lab",
            "CSE-4-Security Lab",
            "CSE-4-Value Added Course",
            "CSE-4-Placement Track",
            "CSE-4-Placement Training",
            /*THIRD YEAR CSE SUBJECTS*/
            "CSE-3-Algebra and Number Theory",
            "CSE-3-Computer Networks",
            "CSE-3-Microprocessors and Microcontrollers",
            "CSE-3-Theory of Computation",
            "CSE-3-Object Oriented Analysis and Design",
            "CSE-3-Soft Computing/Sensors and Transducers(OE I)",
            "CSE-3-Object Oriented Analysis and Design Lab",
            "CSE-3-Networks Lab",
            "CSE-3-Microprocessors and Microcontrollers Lab",
            "CSE-3-Value Added Course-Advanced Python",
            "CSE-3-Placement Training - Java",
            /*SECOND YEAR CSE SUBJECTS*/
            "CSE-2-Discrete Mathematics",
            "CSE-2-Digital Principles and System Design",
            "CSE-2-Data Structures",
            "CSE-2-Object Oriented Programming",
            "CSE-2-Communication Engineering",
            "CSE-2-Data Structures Lab",
            "CSE-2-Object Oriented Programming Lab",
            "CSE-2-Digital Systems Lab",
            "CSE-2-Interpersonal Skills / Listening and Speaking",
            "CSE-2-Value Added Course-Problem Solving Techniques",
            "CSE-2-Placement Training -– OOPS",
            /*FOURTH YEAR ECE SUBJECTS*/
            "ECE-4-Antennas and Microwave Engineering",
            "ECE-4-Optical Communication",
            "ECE-4-Embedded and Real Time Systems",
            "ECE-4-Ad hoc and Wireless Sensor Networks",
            "ECE-4-Machine Learning Technique",
            "ECE-4-Transducer Engineering",
            "ECE-4-Embedded Lab",
            "ECE-4-Advanced Communication Lab",
            "ECE-4-Value Added Course",
            "ECE-4-Placement Track",
            "ECE-4-Placement Training",
            "ECE-4-Library",
            /*Third year ECE subjects*/
            "ECE-3-Digital Communication",
            "ECE-3-Computer Architecture and Organization",
            "ECE-3-Discrete-Time Signal Processing",
            "ECE-3-Communication Networks",
            "ECE-3-Total Quality Management",
            "ECE-3-Basics of Biomedical Instrumentation",
            "ECE-3-Communication Systems Lab",
            "ECE-3-Digital Signal Processing Lab",
            "ECE-3-Communication Networks Lab",
            // placement and VAC yet to be added
            "ECE-3-Library",
            /*SECOND YEAR ECE SUBJECTS*/
            "ECE-2-Linear Algebra and Partial Differential Equations",
            "ECE-2-Electronic Circuit I",
            "ECE-2-Fundamentals of Data Structures in C",
            "ECE-2-Signals and Systems",
            "ECE-2-Digital Electronics",
            "ECE-2-Control System Engineering",
            "ECE-2-Fundamentals of Data structures In C Lab",
            "ECE-2-Analog and Digital Circuits Lab",
            "ECE-2-Interpersonal Skills / Listening and Speaking",
            // placement and VAC
            "ECE-2-Library",
            /*Mechanical department subjects*/
            /*4th year*/
            "MEC-4-Power Plant Engineering",
            "MEC-4-Process Planning and Cost Estimation",
            "MEC-4-Mechatronics",
            "MEC-4-Open Elective – II Robotics",
            "MEC-4-P E – II Unconventional Machining Processes",
            "MEC-4-P E – III Non Destructive Testing",
            "MEC-4-Simulation and Analysis Lab",
            "MEC-4-Mechatronics Lab",
            "MEC-4-Technical seminar",
            "MEC-4-Welding and Inspection Techniques",
            /*3rd year*/
            "MEC-3-Thermal Engineering - II",
            "MEC-3-Design of Machine Elements",
            "MEC-3-Metrology and Measurements",
            "MEC-3-Dynamics of Machines",
            "MEC-3-Renewable Energy Sources",
            "MEC-3-Kinematics and Dynamics Lab",
            "MEC-3-Thermal Engineering Lab",
            "MEC-3-Metrology and Measurements Lab",
            "MEC-3-Modeling for Design Engineers",
            /*2nd year*/
            "MEC-2-Transforms and Partial Differential Equations",
            "MEC-2-Engineering Thermodynamics",
            "MEC-2-Fluid Mechanics and Machinery",
            "MEC-2-Manufacturing Technology - I",
            "MEC-2-Electrical Drives and Controls",
            "MEC-2-Manufacturing Technology Lab - I",
            "MEC-2-Computer Aided Machine Drawing",
            "MEC-2-Electrical Engineering Lab",
            "MEC-2-Interpersonal Skills / Listening and Speaking",
            /*EEE DEPARTMENT*/
            /*4th year*/
            "EEE-4-High Voltage Engineering",
            "EEE-4-Power System Operation and Control",
            "EEE-4-Renewable Energy Systems",
            "EEE-4-Introduction to C programming",
            "EEE-4-Disaster Management",
            "EEE-4-Computer Architecture/Control of Electrical Drives",
            "EEE-4-Power System Simulation Lab",
            "EEE-4-Renewable Energy Systems Lab",
            "EEE-4-Value Added Course",
            /*"EEE-4-Placement Track",
            "EEE-4-Placement Training",
            "EEE-4-Library"*/
            /*3rd year*/
            "EEE-3-Power System Analysis",
            "EEE-3-Microprocessors and Microcontrollers",
            "EEE-3-Power Electronics",
            "EEE-3-Digital Signal Processing",
            "EEE-3-Object Oriented Programming",
            "EEE-3-Sensors and Transducers",
            "EEE-3-Control and Instrumentation Lab",
            "EEE-3-Professional Communication",
            "EEE-3-Object Oriented Programming Lab",
            "EEE-3-Value Added Course",
            /*P Track*/
            /*2nd year*/
            "EEE-2-Transforms and Partial Differential Equations",
            "EEE-2-Digital Logic Circuits",
            "EEE-2-Electromagnetic Theory",
            "EEE-2-Electrical Machines - I",
            "EEE-2-Electron Devices and Circuits",
            "EEE-2-Power Plant Engineering",
            "EEE-2-Electronics Lab",
            "EEE-2-Electrical Machines Lab - I",
            "EEE-2-Value Added Course",
            /*"EEE-2-Library",
            "EEE-2-Placement Track"*/
            /*CIVIL DEPARTMENT*/
            /*4th year*/
            "CIV-4-Estimation, Costing and Valuation Engineering",
            "CIV-4-Railway, Airport, Docks, Harbour Engineering",
            "CIV-4-Structural Design and Drawing",
            "CIV-4-Municipal Solid Waste Management",
            "CIV-4-Testing of Materials",
            "CIV-4-Creative and Innovative Project",
            "CIV-4-Industrial Training",
            /*"Value Added Course",
            "Placement Track",
            "Placement Training",
            "Library",*/
            /*3rd year*/
            "CIV-3-Design of Reinforced Cement Concrete Beams",
            "CIV-3-Structural Analysis - I",
            "CIV-3-Water Supply Engineering",
            "CIV-3-Foundation Engineering",
            "CIV-3-Disaster Management",
            "CIV-3-Renewable Energy Sources",
            "CIV-3-Soil Mechanics Lab",
            "CIV-3-Water and waste water analysis Lab",
            /*"CIV-3-Library"*/
            /*2nd year*/
            "CIV-2-Transform and Partial Differential",
            "CIV-2-Strength of Materials - I",
            "CIV-2-Fluid Mechanics",
            "CIV-2-Surveying",
            "CIV-2-Construction Materials",
            "CIV-2-Engineering Geology",
            "CIV-2-Construction Material Lab",
            "CIV-2-Survey Lab",
            "CIV-2-Interpersonal Skills / Listening and Speaking",
            /*"CIV-2-Value Added Course",
            "CIV-2-Library",
            "CIV-2-Placement Track"*/
            /*First year subjects*/
            "FYR-1-Professional English – I",
            "FYR-1-Matrices and Calculus",
            "FYR-1-Engineering Physics",
            "FYR-1-Engineering Chemistry",
            "FYR-1-Problem Solving and Python Programming",
            "FYR-1-Problem Solving and Python Programming Laboratory",
            "FYR-1-Physics Laboratory",
            "FYR-1-Chemistry Laboratory",
            "FYR-1-Library",
            "FYR-1-UHV/Sports and Games",
            "FYR-1-Mentor Hour"
    };

    public static String[] SHORT_FORM_SUBJECTS = {
            /*Fourth year CSE subject short forms*/
            "CSE-4-PM",
            "CSE-4-CNS",
            "CSE-4-CC",
            "CSE-4-ROB",
            "CSE-4-SOA/SPM",
            "CSE-4-MAP/CNT",
            "CSE-4-CCL",
            "CSE-4-SL",
            "CSE-4-VAC",
            "CSE-4-P Track",
            "CSE-4-PT",
            /*Third year CSE subject short forms*/
            "CSE-3-ANT",
            "CSE-3-CN",
            "CSE-3-MP",
            "CSE-3-TOC",
            "CSE-3-OOAD",
            "CSE-3-SC/ST",
            "CSE-3-OOAD Lab",
            "CSE-3-N/W Lab",
            "CSE-3-MP Lab",
            "CSE-3-VAC",
            "CSE-3-PT",
            /*Second year CSE subject short forms*/
            "CSE-2-DM",
            "CSE-2-DPSD",
            "CSE-2-DS",
            "CSE-2-OOPs",
            "CSE-2-CE",
            "CSE-2-DS Lab",
            "CSE-2-OOPs Lab",
            "CSE-2-Dig Lab",
            "CSE-2-ISLS",
            "CSE-2-VAC",
            "CSE-2-PT",
            /*ECE FOURTH YEAR SUBJECTS SHORT FORM*/
            "ECE-4-AME",
            "ECE-4-OC",
            "ECE-4-ERT",
            "ECE-4-AWN",
            "ECE-4-MLT",
            "ECE-4-TE",
            "ECE-4-ES LAB",
            "ECE-4-AC LAB",
            "ECE-4-VAC",
            "ECE-4-P Track",
            "ECE-4-P Training",
            "ECE-4-Library",
            /*Third year ECE subject short forms*/
            "ECE-3-DC",
            "ECE-3-CAO",
            "ECE-3-DTSP",
            "ECE-3-CN",
            "ECE-3-TQM",
            "ECE-3-BBI",
            "ECE-3-CS LAB",
            "ECE-3-DSP LAB",
            "ECE-3-CN LAB",
            // placement and VAC
            "ECE-3-Library",
            /*Second year ECE subject short forms*/
            "ECE-2-LAPDE",
            "ECE-2-EC I",
            "ECE-2-FDS",
            "ECE-2-SAS",
            "ECE-2-DE",
            "ECE-2-CS",
            "ECE-2-FDS Lab",
            "ECE-2-ADC Lab",
            "ECE-2-ISLS Lab",
            // placement and VAC
            "ECE-2-Library",
            /*Mechanical department subjects short forms*/
            /*4th year*/
            "MEC-4-PPE",
            "MEC-4-PPCE",
            "MEC-4-MCT",
            "MEC-4-ROB",
            "MEC-4-UCM",
            "MEC-4-NDT",
            "MEC-4-SAAL",
            "MEC-4-ML",
            "MEC-4-TS",
            "MEC-4-VAC",
            /*3rd year*/
            "MEC-3-TE-II",
            "MEC-3-DME",
            "MEC-3-MM",
            "MEC-3-DM",
            "MEC-3-RES",
            "MEC-3-K&D Lab",
            "MEC-3-TE Lab",
            "MEC-3-MM Lab",
            "MEC-3-VAC",
            /*2nd year*/
            "MEC-2-TPDE",
            "MEC-2-ET",
            "MEC-2-FMM",
            "MEC-2-MT – I",
            "MEC-2-EDC",
            "MEC-2-MT - I Lab",
            "MEC-2-CAD Lab",
            "MEC-2-EE Lab",
            "MEC-2-ISLS",
            /*EEE department*/
            "EEE-4-HVE",
            "EEE-4-PSOC",
            "EEE-4-RES",
            "EEE-4-ICP",
            "EEE-4-DM",
            "EEE-4-CA/CED",
            "EEE-4-PSS Lab",
            "EEE-4-RES Lab",
            "EEE-4-VAC",
            /*"EEE-4-P Track",
            "EEE-4-P Training",
            "EEE-4-Library"*/
            /*3rd year*/
            "EEE-3-PSA",
            "EEE-3-MP&MC",
            "EEE-3-PE",
            "EEE-3-DSP",
            "EEE-3-OOPs",
            "EEE-3-S&T",
            "EEE-3-C&T Lab",
            "EEE-3-PC",
            "EEE-3-OOPs Lab",
            "EEE-3-VAC",
            /*P Track*/
            /*2nd year*/
            "EEE-2-TDPE",
            "EEE-2-DLC",
            "EEE-2-EMT",
            "EEE-2-EM-I",
            "EEE-2-EDC",
            "EEE-2-PPE",
            "EEE-2-EC Lab",
            "EEE-2-EM-I Lab",
            "EEE-2-VAC",
            /*"EEE-2-Library",
            "EEE-2-P Track"*/
            /*CIVIL DEPARTMENT*/
            /*4th year*/
            "CIV-4-ECVE",
            "CIV-4-RADHE",
            "CIV-4-SDD",
            "CIV-4-MSWM",
            "CIV-4-TOM",
            "CIV-4-C&IP",
            "CIV-4-IT",
            /*"CIV-4-VAC",
            "CIV-4-P Track",
            "CIV-4-P Training",
            "CIV-4-Library"*/
            /*3RD YEAR*/
            "CIV-3-DRCCE",
            "CIV-3-SA-I",
            "CIV-3-WSE",
            "CIV-3-FE",
            "CIV-3-DM",
            "CIV-3-RES",
            "CIV-3-SM Lab",
            "CIV-3-WWWA Lab",
            /*"CIV-3-Library"*/
            /*2nd year*/
            "CIV-2-TDPE",
            "CIV-2-SOM I",
            "CIV-2-FM",
            "CIV-2-SUR",
            "CIV-2-CM",
            "CIV-2-FG",
            "CIV-2-CM Lab",
            "CIV-2-SUR Lab",
            "CIV-2-ISLS",
            /*"CIV-2-VAC",
            "CIV-2-Library",
            "CIV-2-P Track"*/
            // First year subjects
            "FYR-1-ENG",
            "FYR-1-MAT",
            "FYR-1-PHY",
            "FYR-1-CHE",
            "FYR-1-PSPP",
            "FYR-1-PSPP-Lab",
            "FYR-1-PHY Lab",
            "FYR-1-CHE Lab",
            "FYR-1-Library",
            "FYR-1-Sports",
            "FYR-1-Mentor"
    };

    /*Lab indicator*/
    public static final String[] LABORATORY_INDICATORS = new String[]{
            LAB,
            LAB1,
            "lab",
            SPEAKING,
            AIDED_DRAWING,
            "Professional Communication"
    };

    // Placement Indicators
    public static final String[] PLACEMENT_INDICATORS = new String[]{
            VALUE,
            PLACEMENT,
            LIBRARY,
            "seminar",
            "Modeling",
            "Sports",
            "Mentor",
    };

    /*ALL STAFFS*/
    /*In the format, department-Mr/Ms/Mrs. staff_name*/
    /*department may be, CSE; ECE; CIVIL, MECH, EEE; FYR*/
    public static String[] ALL_STAFFS = {
            /*CSE STAFFS*/
            "CSE-Dr. P.M. Sivaraja",
            "CSE-Mr. I Anantraj",
            "CSE-Mrs. S L Jothilakshmi",
            "CSE-Dr. Sanjivi Arul",
            "CSE-Mrs. R P Sumithra",
            "CSE-Mrs. Thanammal Indu V",
            "CSE-Mrs. Archana V Nair S",
            "CSE-Dr. V Viswanath Shenoi",
            "CSE-Mrs. K Rejini",
            "CSE-Mrs. Vidhya",
            "CSE-Mrs. C Karpagavalli",
            "CSE-Mr. S Senthil",
            "CSE-Mrs. V Anusooya",
            "CSE-Mrs. S. Anjana",
            "CSE-Dr. Sasikala T S",
            "CSE-Dr. Rajeesh Kumar",
            /*FYR STAFFS*/
            "FYR-Dr. S Anukumar Kathirvel",
            "FYR-Dr. S Raja",
            "FYR-Mrs. K.S. Priya",
            "FYR-Dr.M. Ayyappan",
            "FYR-Ms. D. Sindhuja",
            "FYR-Mr. N. Vairavasamy",
            "FYR-Dr. M. Kumara Dhas",
            "FYR-Dr. A. Kumar",
            "FYR-Dr. S. Aruna",
            "FYR-Dr. S. Saravana Veni",
            "FYR-Dr. S. R.Kalaivani",
            "FYR-Dr. Jebha Starling",
            /*ECE STAFFS*/
            "ECE-Dr. Mahesh Kumar",
            "ECE-Dr.G.Kannan",
            "ECE-Dr. P. Kannan",
            "ECE-Mr. Ramanathan",
            "ECE-Mr. S Arun",
            "ECE-Mr. Allan J Wilson",
            "ECE-Mr. Gnanakumar",
            "ECE-Mr. P. Srinivasan",
            "ECE-Mrs. A. S. Sarika",
            "ECE-Dr. A. S. Radhamani",
            "ECE-Ms. S. Maheswari",
            "ECE-Mr. A. Rajendran",
            "ECE-Dr. R. Dhaneesh",
            "ECE-SMART staff",
            /*EEE STAFFS*/
            "EEE-Mr. J. Sevugan Rajesh",
            "EEE-Mr. K. Siva Subramanian",
            "EEE-Dr. M. Sivagamasundarie",
            "EEE-Dr.K.R.Suja",
            "EEE-Mr.G.V.Chidambarathanu",
            "EEE-Mr. A V Sree Kumar",
            /*CIVIL STAFFS*/
            "CIV-Mr.R.Vigneshwaran",
            "CIV-Mr.S.Suresh",
            "CIV-Mr.C.Ramesh Babu",
            "CIV-Mr.D.Akhil Varma",
            "CIV-Mrs.Akiladevi",
            "CIV-Mr.M.S.Kishore",
            /*MECH STAFFS*/
            "MEC-Dr. Sampath Kumar",
            "MEC-Mr. G. Saravanaram",
            "MEC-Mr. M. Rajesh Kannan",
            "MEC-Mr. B. Babu",
            "MEC-Mr. P. Saravanamuthukumar",
            "MEC-Mr. K. Aravinth",
            "MEC-Dr. M. Navaneetha Krishnan",
    };

    /*ALL CLASSES' SUBJECT STAFFS*/
    public static Hashtable<String, String[]> allClassesTeachers = new Hashtable<String, String[]>(){
        {
            put(CSE_IV_A, new String[]{
                    "Mrs. R P Sumithra", // index 0 element -> class advisor
                    "Dr. P.M. Sivaraja",
                    "Mr. I Anantraj",
                    "Mrs. S L Jothilakshmi",
                    "Dr. Sanjivi Arul"+CONTINUOUS,
                    "Mrs. R P SumithraMrs. Thanammal Indu V",
                    "Mrs. Archana V Nair S",
                    "Mrs. S L Jothilakshmi"+CSE_LAB_1+PAIRED,
                    "Mr. I Anantraj"+CSE_LAB_2+PAIRED,
                    "Mrs. Thanammal Indu VDr. V Viswanath Shenoi",
                    "Mr. I Anantraj",
                    "Mrs. K Rejini"
            });

            put(CSE_III_A, new String[]{
                    "Mrs. Vidhya", // index 0 element -> class advisor
                    "Dr. S Anukumar Kathirvel",
                    "Mrs. K Rejini",
                    "Dr. Mahesh Kumar",
                    "Mrs. Vidhya",
                    "Mrs. C Karpagavalli",
                    "Dr. V Viswanath ShenoiDr.G.Kannan"+PAIRED+CSE_III_B,
                    "Mrs. C Karpagavalli@Mrs. R P Sumithra@"+CSE_LAB_3+STAND_ALONE,
                    "Mrs. K Rejini"+CSE_LAB_4+PAIRED,
                    "Mr. Ramanathan"+ECE_LAB_1+PAIRED,
                    "Mrs. C Karpagavalli",
                    "Mrs. Vidhya"
            });

            put(CSE_III_B, new String[]{
                    "Mrs. C Karpagavalli", // index 0 element -> class advisor
                    "Dr. S Anukumar Kathirvel",
                    "Mrs. K Rejini",
                    "Mr. S Arun",
                    "Mrs. Vidhya",
                    "Mrs. C Karpagavalli",
                    "Dr. V Viswanath ShenoiDr.G.Kannan"+PAIRED+CSE_III_A,
                    "Mrs. C Karpagavalli@Mrs. Archana V Nair S@"+CSE_LAB_2+STAND_ALONE,
                    "Mrs. K Rejini"+CSE_LAB_1+PAIRED,
                    "Mr. S Arun"+ECE_LAB_1+PAIRED,
                    "Dr. V Viswanath Shenoi",
                    "Mrs. Archana V Nair S"
            });

            put(CSE_II_A, new String[]{
                    "Mr. S Senthil", // index 0 element -> class advisor
                    "Dr. S Raja",
                    "Mr. Allan J Wilson",
                    "Mr. S Senthil",
                    "Mrs. Thanammal Indu V",
                    "Mr. Ramanathan",
                    "Mr. S Senthil"+CSE_LAB_2+PAIRED,
                    "Mrs. Thanammal Indu V@Mrs. Vidhya@"+CSE_LAB_3+STAND_ALONE,
                    "Mr. Allan J Wilson"+ECE_LAB_2+PAIRED,
                    "Mrs. K.S. Priya"+MECH_LAB_1+STAND_ALONE,
                    "Mrs. Archana V Nair S",
                    "Mrs. Thanammal Indu V"
            });

            put(CSE_II_B, new String[]{
                    "Mrs. S L Jothilakshmi", // index 0 element -> class advisor
                    "Dr.M. Ayyappan",
                    "Mr. Gnanakumar",
                    "Mrs. R P Sumithra",
                    "Mrs. S L Jothilakshmi",
                    "Mrs. V Anusooya",
                    "Mrs. R P Sumithra"+CSE_LAB_4+PAIRED,
                    "Mrs. S L Jothilakshmi@Mrs. Thanammal Indu V@"+CSE_LAB_1+STAND_ALONE,
                    "Mr. Allan J Wilson"+ECE_LAB_2+PAIRED,
                    "Ms. D. Sindhuja"+MECH_LAB_1+STAND_ALONE,
                    "Mrs. Archana V Nair S",
                    "Mrs. Archana V Nair S"
            });

            put(ECE_IV_A, new String[]{
                    "Mr. S Arun",
                    "Mrs. V Anusooya",
                    "Mr. P. Srinivasan",
                    "Mrs. A. S. Sarika",
                    "Dr. A. S. Radhamani",
                    "Dr. Mahesh Kumar",
                    "Dr.G.Kannan",
                    "Mrs. A. S. Sarika"+STAND_ALONE+ECE_LAB_3,
                    "Mrs. V Anusooya"+STAND_ALONE+MECH_LAB_2,
                    "Ms. S. Maheswari",
                    "SMART staff",
                    "Mr. P. Srinivasan",
                    "Ms. S. Maheswari"
            });

            put(ECE_III_A, new String[]{
                    "Mr. P. Srinivasan",
                    "Mr. Gnanakumar",
                    "Mr. A. Rajendran",
                    "Dr. P. Kannan",
                    "Dr. A. S. Radhamani",
                    "Dr. R. Dhaneesh",
                    "Mr. Allan J Wilson",
                    "Mr. Gnanakumar"+PAIRED+CSE_LAB_2,
                    "Dr. Mahesh Kumar"+STAND_ALONE+ECE_LAB_2,
                    "Dr. A. S. Radhamani"+PAIRED+CSE_LAB_4,
                    // placement and VAC
                    "Ms. S. Maheswari"
            });

            put(ECE_II_A, new String[]{
                    "Mr. A. Rajendran",
                    "Mr. N. Vairavasamy",
                    "Mrs. A. S. Sarika",
                    "Mr. I Anantraj",
                    "Dr. P. Kannan",
                    "Mr. S Arun",
                    "Mr. J. Sevugan Rajesh",
                    "Mr. I Anantraj"+PAIRED+CSE_LAB_1,
                    "Mr. P. Srinivasan"+PAIRED+ECE_LAB_3,
                    "Ms. D. Sindhuja"+STAND_ALONE+MECH_LAB_1,
                    // placement and VAC
                    "Ms. S. Maheswari"
            });

            put(MECH_II_A, new String[]{
                    "Mr. G. Saravanaram",
                    "Dr.M. Ayyappan",
                    "Dr. Sampath Kumar",
                    "Mr. M. Rajesh Kannan",
                    "Mr. B. Babu",
                    "Mr. K. Siva Subramanian",
                    "Mr. B. Babu"+STAND_ALONE+MECH_LAB_3,
                    "Mr. P. Saravanamuthukumar"+STAND_ALONE+MECH_LAB_4,
                    "Mr. K. Siva Subramanian"+STAND_ALONE+EEE_LAB_1,
                    "Mrs. K.S. Priya"+STAND_ALONE+MECH_LAB_2
            });

            put(MECH_III_A, new String[]{
                    "Mr. B. Babu",
                    "Mr. K. Aravinth",
                    "Mr. G. Saravanaram",
                    "Mr. M. Rajesh Kannan",
                    "Dr. M. Navaneetha Krishnan",
                    "Dr. M. Sivagamasundarie",
                    "Dr. M. Navaneetha Krishnan"+PAIRED+MECH_LAB_5,
                    "Mr. K. Aravinth"+STAND_ALONE+MECH_LAB_6,
                    "Mr. M. Rajesh Kannan"+PAIRED+MECH_LAB_7,
                    "Mr. G. Saravanaram"
            });

            put(MECH_IV_A, new String[]{
                    "Mr. M. Rajesh Kannan",
                    "Dr. Sampath Kumar",
                    "Mr. G. Saravanaram",
                    "Mr. A. Rajendran",
                    "Dr. M. Navaneetha Krishnan",
                    "Mr. B. Babu",
                    "Mr. P. Saravanamuthukumar",
                    "Mr. G. Saravanaram"+PAIRED+MECH_LAB_4,
                    "Mr. A. Rajendran"+PAIRED+MECH_LAB_8,
                    "Mr. K. Aravinth",
                    "Mr. P. Saravanamuthukumar"
            });

            put(EEE_IV_A, new String[]{
                    "Mr. A V Sree Kumar",
                    "Dr. M. Sivagamasundarie",
                    "Dr.K.R.Suja",
                    "Mr. J. Sevugan Rajesh",
                    "Dr. V Viswanath Shenoi",
                    "Mr.R.Vigneshwaran",
                    "Mrs. Archana V Nair SMr.G.V.Chidambarathanu",
                    "Dr.K.R.Suja"+STAND_ALONE+EEE_LAB_2,
                    "Mr. J. Sevugan Rajesh"+STAND_ALONE+EEE_LAB_3,
                    "Dr.K.R.Suja",
                    /*"NOSTAFFS",
                    "NOSTAFFS",
                    "NOSTAFFS"*/
            });

            put(EEE_III_A, new String[]{
                    "Mr.G.V.Chidambarathanu",
                    "Dr.K.R.Suja",
                    "Mr. Ramanathan",
                    "Dr. M. Sivagamasundarie",
                    "Ms. S. Maheswari",
                    "Mrs. Vidhya",
                    "Mr. A V Sree Kumar",
                    "Mr. J. Sevugan Rajesh"+STAND_ALONE+EEE_LAB_4,
                    "Ms. D. Sindhuja"+STAND_ALONE+MECH_LAB_2,
                    "Mrs. Vidhya"+STAND_ALONE+CSE_LAB_4,
                    "Mr. A V Sree Kumar",
                    /*NOSTAFFS*/
            });

            put(EEE_II_A, new String[]{
                    "Mr. K. Siva Subramanian",
                    "Mr. N. Vairavasamy",
                    "Mr.G.V.Chidambarathanu",
                    "Mr. K. Siva Subramanian",
                    "Mr. A V Sree Kumar",
                    "Mr. P. Srinivasan",
                    "Mr. K. Aravinth",
                    "Mrs. A. S. Sarika"+STAND_ALONE+EEE_LAB_4,
                    "Mr. A V Sree Kumar"+STAND_ALONE+FYR_LAB_1,
                    "Mr. K. Siva Subramanian",
                    /*"NOSTAFFS",
                    "NOSTAFFS"*/
            });

            put(CIV_IV_A, new String[]{
                    "Mr.D.Akhil Varma",
                    "Mr.S.Suresh",
                    "Mr.C.Ramesh Babu",
                    "Mr.D.Akhil Varma",
                    "Mr.R.Vigneshwaran",
                    "Mr. P. Saravanamuthukumar",
                    "Mr.S.Suresh",
                    "Mr.C.Ramesh Babu",
                    /*"NOSTAFFS",
                    "NOSTAFFS",
                    "NOSTAFFS",
                    "NOSTAFFS"*/
            });

            put(CIV_III_A, new String[]{
                    "Mr.M.S.Kishore",
                    "Mr.S.Suresh",
                    "Mr.C.Ramesh Babu",
                    "Mrs.Akiladevi",
                    "Mr.M.S.Kishore",
                    "Mr.R.Vigneshwaran",
                    "Mr.G.V.Chidambarathanu",
                    "Mr.M.S.Kishore"+STAND_ALONE+CIVIL_LAB_1,
                    "Mrs.Akiladevi"+STAND_ALONE+CIVIL_LAB_2,
                    /*NOSTAFFS*/
            });

            put(CIV_II_A, new String[]{
                    "Mr.S.Suresh",
                    "Mr. N. Vairavasamy",
                    "Mr. G. Saravanaram",
                    "Mr. M. Rajesh Kannan",
                    "Mr.D.Akhil Varma",
                    "Mrs.Akiladevi",
                    "Mr.M.S.Kishore",
                    "Mr.R.Vigneshwaran"+STAND_ALONE+CIVIL_LAB_1,
                    "Mr.D.Akhil Varma"+STAND_ALONE+CIVIL_LAB_2,
                    "Mrs. K.S. Priya"+STAND_ALONE+MECH_LAB_2,
                    /*"NOSTAFFS",
                    "NOSTAFFS",
                    "NOSTAFFS"*/
            });

            put(FYR_I_A, new String[]{
                    "Dr. S Anukumar KathirvelMrs. K.S. Priya",
                    "Mrs. K.S. Priya",
                    "Dr. S Anukumar Kathirvel",
                    "Dr. M. Kumara Dhas",
                    "Dr. A. Kumar",
                    "Mrs. S. Anjana",
                    "Dr. Sasikala T S"+STAND_ALONE+ADM_COM_LAB,
                    "Dr. M. Kumara Dhas"+PAIRED+ECE_LAB_4,
                    "Dr. A. Kumar"+PAIRED+FYR_I_CHE,
                    "Dr. S Anukumar Kathirvel",
                    "Mrs. K.S. Priya",
                    "Dr. A. Kumar"
            });

            put(FYR_I_B, new String[]{
                    "Dr. S. ArunaDr. S. Saravana Veni",
                    "Ms. D. Sindhuja",
                    "Dr. S. Aruna",
                    "Dr. S. Saravana Veni",
                    "Dr. Jebha Starling",
                    "Dr. Rajeesh Kumar",
                    "Dr. Rajeesh Kumar"+STAND_ALONE+ADM_COM_LAB,
                    "Dr. S. Saravana Veni"+PAIRED+ECE_LAB_4,
                    "Dr. Jebha Starling"+PAIRED+FYR_I_CHE,
                    "Dr. S. Aruna",
                    "Dr. S. Saravana Veni",
                    "Dr.M. Ayyappan"
            });

            put(FYR_I_C, new String[]{
                    "Mr. N. Vairavasamy",
                    "Ms. D. Sindhuja",
                    "Dr. S. Aruna",
                    "Dr. S. Saravana Veni",
                    "Dr. Jebha Starling",
                    "CSE-Dr. Rajeesh Kumar",
                    "CSE-Dr. Rajeesh Kumar"+STAND_ALONE+ADM_COM_LAB,
                    "Dr. S. Saravana Veni"+PAIRED+ECE_LAB_4,
                    "Dr. Jebha Starling"+PAIRED+FYR_I_CHE,
                    "Mr. N. Vairavasamy",
                    "Ms. D. Sindhuja",
                    "Mr. N. Vairavasamy"
            });

            put(FYR_I_D, new String[]{
                    "Dr. M. Kumara Dhas",
                    "Mrs. K.S. Priya",
                    "Dr. S Raja",
                    "Dr. M. Kumara Dhas",
                    "Dr. S. R.Kalaivani",
                    "Mrs. Thanammal Indu V",
                    "Mrs. Thanammal Indu V"+STAND_ALONE+ADM_COM_LAB,
                    "Dr. M. Kumara Dhas"+PAIRED+ECE_LAB_4,
                    "Dr. S. R.Kalaivani"+PAIRED+FYR_I_CHE,
                    "Dr. M. Kumara Dhas",
                    "Dr. S. R.Kalaivani",
                    "Dr. S Raja"
            });
        }
    };

    // ADMINS FOR THE APPLICATION
    public static Hashtable<String, Integer> ADMINS = new Hashtable<String, Integer>(){
        {
            put("Dr. V Viswanath Shenoi", 1);
            put("Mrs. S L Jothilakshmi", 1);
        }
    };

    public static String[] ADMINS_ARRAY = new String[]{
        "Dr. V Viswanath Shenoi",
        "Mrs. S L Jothilakshmi"
    };

    /***********************************************************************************************************************************/
    // getter and setter methods
    public static String[] getAllStaffs() {
        return ALL_STAFFS;
    }

    public static void setAllStaffs(String[] allStaffs) {
        ALL_STAFFS = allStaffs;
    }

    public static String[] getAdminsArray() {
        return ADMINS_ARRAY;
    }

    public static void setAdminsArray(String[] adminsArray) {
        ADMINS_ARRAY = adminsArray;
    }

    public static String[] getAllRooms() {
        return ALL_ROOMS;
    }

    public static void setAllRooms(String[] allRooms) {
        ALL_ROOMS = allRooms;
    }

    /**************************************************************************************************************************************/
    public static final String DEPARTMENT_CSE_TEXT = "DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING";
    public static final String DEPARTMENT_ECE_TEXT = "DEPARTMENT OF ELECTRONICS AND COMMUNICATION ENGINEERING";
    public static final String DEPARTMENT_MECH_TEXT = "DEPARTMENT OF MECHANICAL ENGINEERING";
    public static final String DEPARTMENT_EEE_TEXT = "DEPARTMENT OF ELECTRICAL AND ELECTRONICS ENGINEERING";
    public static final String DEPARTMENT_CIVIL_TEXT = "DEPARTMENT OF CIVIL ENGINEERING";
    public static final String DEPARTMENT_FIRST_YEAR_TEXT = "DEPARTMENT OF SCIENCE AND HUMANITIES";
    public static final String TIMETABLE_ODD_SEMESTER = "TIMETABLE ODD SEMESTER";
    public static final String YEAR = "Year";
    public static final String CLASS_NAME_DF = "Class Name";
    public static final String NAME_OF_THE_CLASS_ADVISOR = "Name of the Class Advisor";
    public static final String TIMETABLE_VERSION = "Timetable Version";
    public static final String COLON = ":";
    public static final String ACADEMIC_YEAR = "Academic Year";
    public static final String NUMBER_OF_STUDENTS = "Number of Students";
    public static final String GENERATED = "Generated On";
    public static final String NA = "N/A";
    public static final String OFFLINE_TIMETABLE = "OFFLINE TIMETABLE";
    public static final String DAYS = "Days";
    public static final String BREAK_TEXT = "BREAK : B/W 2nd and 3rd periods and B/W 6th and 7th periods";
    public static final String LUNCH = "LUNCH : After the 4th period, 12:40 - 13:20";
    public static final String SUBJECT_DETAILS = "SUBJECT DETAILS";
    public static final String S_NO = "S.No";
    public static final String SUBJECT_NAME = "Subjects";
    public static final String SUBJECT_STAFFS = "Subject Staffs";
    public static final String ABBREVIATION = "Abbreviation";
    public static final String SESSIONS = "Sessions";
    public static final String TIMETABLE_INCHARGE = "Timetable Incharge";
    public static final String HOD = "HOD";
    public static final String COORDINATOR = "Timetable Co-ordinator";
    public static final String PRINCIPAL = "Principal";
    public static final int PERMISSION_REQUEST_CODE = 7;
    public static final String COLLEGE_NAME = "AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY";
    public static final String AMRITA_INSTITUTIONS = "AMRITA INSTITUTIONS";
    public static final String COLLEGE_PLACE = "Erachakulam, Nagercoil";
    public static final String NAME_OF_THE_STAFF = "Name of the staff";
    public static final String DEPARTMENT = "Department";
    public static final String CLASS_ADVISOR_OF = "Class Advisor of";
    public static final String WORKING_HOURS = "Working hours";
    public static final String CLASSES_HANDLED = "CLASSES HANDLED";
    public static final String SUBJECTS_HANDLED = "SUBJECTS HANDLED";
    public static final String NULL = "null";
    public static final String WORKING_HOURS_INDICATOR = "Working Hours";
    public static final String FREE_HOURS_INDICATOR = "Leisure Hours";
    public static final String HYPHEN = "-";
    public static final String PERIODS_NUMBER = "Periods";
    public static final String COUNT = "Count";

    public static final String STATUS_ONLY_ONE = "Staff is the last admin.. Cannot be removed";
    public static final String STATUS_SUCCESS = "Success";
}
