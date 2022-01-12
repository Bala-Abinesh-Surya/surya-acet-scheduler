package com.surya.scheduler.logic;

public class online_offline_mode {
    // class to generate time table for the online-offline mode
    // this class must be a singleton class
    public static online_offline_mode online_offline_mode;

    // method to return the instance of the online_offline_mode
    public static online_offline_mode getInstance(){
        if(online_offline_mode == null){
            online_offline_mode = new online_offline_mode();
        }

        return online_offline_mode;
    }
}
