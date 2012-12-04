package com.example.taskshare;


import java.io.File;

import android.app.Application;
import android.os.Environment;

/** This class acts as the application container. It holds
 *  the model and has methods to load and save persistent
 *  task data from a saved file.
 *  @author AJ
 */
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
    
    private static void save(){
    	taskShare.saveToFile();
    }
}
