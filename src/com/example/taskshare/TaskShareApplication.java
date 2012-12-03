package com.example.taskshare;


import java.io.File;

import android.app.Application;
import android.os.Environment;

public class TaskShareApplication extends Application {
    // Singleton
    transient private static TaskShare taskShare = null;

    static TaskShare getTaskShare() {
        if (taskShare == null) {
            taskShare = new TaskShare();
            load();
        }
        
        return taskShare;
    }
    
    private static void load(){

        String path = Environment.getExternalStorageDirectory().getPath();
        File taskShareDirectory = new File(path + "/.taskshare");    
        taskShareDirectory.mkdirs();
        taskShare.loadFromFile();
    }
}
