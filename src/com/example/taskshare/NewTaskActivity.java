/** Allows creation of new task
 * -saves task to the task list*/

package com.example.taskshare;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class NewTaskActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        
        /** Saves a new task and adds it to the model */
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (saveNewTask() == true) finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_task, menu);
        return true;
    }
    /** Saves a new task and adds it to the model 		 /
     *  Returns true if task has be successfully saved 	 /
     *  or false otherwise.								*/
    public Boolean saveNewTask(){
    	Task newTask;
    	
    	EditText nameField = (EditText) findViewById(R.id.editNewTaskName);
    	EditText descriptionField = (EditText) findViewById(R.id.editNewTaskDescription);
    	
    	// Check name field is not empty
    	String name = nameField.getText().toString();
    	if (name != null && name.trim().length() == 0){
    		Toast.makeText(NewTaskActivity.this, "Please Enter a Title", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	// Check description field is not empty
    	String description = descriptionField.getText().toString();
    	if (description != null && description.trim().length() == 0){
    		Toast.makeText(NewTaskActivity.this, "Please Enter a Description", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	// Check whether or not to create the task with online storage
    	Boolean sharedOnline = false;
    	CheckBox checkBoxSharedOnline = (CheckBox) findViewById(R.id.checkBoxSharedOnline);
        if (checkBoxSharedOnline.isChecked())
            sharedOnline = true;

        // Determine whether it is a photo or text based task from the radio buttons
    	RadioGroup radioGroupTaskType = (RadioGroup) findViewById(R.id.radioGroupTaskType);
    	int taskTypeIdSelected = radioGroupTaskType.getCheckedRadioButtonId();
    	RadioButton taskType = (RadioButton) findViewById(taskTypeIdSelected);
    	
    	if (taskType == (RadioButton) findViewById(R.id.radioPhoto)){
    		newTask = new PhotoTask(name, description, sharedOnline);
    		newTask.setType("Photo");

    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioText)){

    		newTask = new TextTask(name, description, sharedOnline);
    		newTask.setType("Text");

    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioAudio)){

    		newTask = new AudioTask(name, description, sharedOnline);
    		newTask.setType("Audio");

    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioVideo)){

    		newTask = new VideoTask(name, description, sharedOnline);
    		newTask.setType("Video");

    	}
    	else return false;
    	
    	/**TODO:
         * if shared online is true, add the task to online database */
        if (sharedOnline){	
        	boolean upload = new UploadTasks().execute(newTask) != null;
        	if (!upload) {
        		Toast.makeText(NewTaskActivity.this, "Upload Unsucessful", Toast.LENGTH_SHORT).show();
        		return false;
        	}
        	else{
        		Toast.makeText(NewTaskActivity.this, "Upload Sucessful", Toast.LENGTH_SHORT).show();
        		return true;
        	}
        }
        else{
        	// Add new task to the model before returning
        	TaskShare ts = TaskShareApplication.getTaskShare();
        	ts.addMyTask(newTask);
        	return true;
        }
    }
}
