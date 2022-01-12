package com.surya.scheduler.logic;

public class online {
    // class to generate time table for the full online mode
    // this class must be a singleton class
    private static online online;

    // method to return the instance of the online class
    public static online getInstance(){
        if(online == null){
            online = new online();
        }

        return online;
    }
}
