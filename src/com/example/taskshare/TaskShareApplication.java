package com.example.taskshare;


import android.app.Application;

public class TaskShareApplication extends Application {
    // Singleton
    transient private static TaskShare taskShare = null;

    static TaskShare getTaskShare() {
        if (taskShare == null) {
            taskShare = new TaskShare();
        }
        return taskShare;
    }

    // Singleton
    transient private static Controller controller = null;

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller(getTaskShare());
        }
        return controller;
    }
}
