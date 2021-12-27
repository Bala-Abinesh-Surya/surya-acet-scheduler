package com.surya.scheduler.constants;

public class settings {
    // class which holds the constants that are kind of the settings for the app
    // these constants should not be final, as they are liable to change

    // number of periods per day
    public static int NUMBER_OF_PERIODS_PER_DAY = 7;

    // maximum number of periods for a staff per day
    public static int MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY_WITH_LAB = 4;
    public static int MAXIMUM_PERIODS_FOR_A_STAFF_PER_DAY = 3;

    // number of periods for a subject with LECTURE tag
    public static int NUMBER_OF_PERIODS_LECTURE = 5;

    // Laboratory Periods'
    // as of now, the periods are based on the 7 periods per day basis
    public static int[] LABORATORY_MORNING_SESSIONS_PERIODS = new int[]{0, 1, 2};
    public static int[] LABORATORY_AFTERNOON_SESSION_PERIODS = new int[]{4, 5, 6};

    // maximum attempts before going into omitted_subject array list
    public static int NUMBER_OF_ATTEMPTS_BEFORE_OMITTED = 150;
    public static int NUMBER_OF_ATTEMPTS_BEFORE_OMITTED_LAB = 50;
    public static int NUMBER_OF_ATTEMPTS_BEFORE_SPECIAL_ALLOCATION = 50;
}
