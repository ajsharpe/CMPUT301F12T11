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
}
