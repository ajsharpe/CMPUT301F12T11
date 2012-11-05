package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
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
        
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {
            @Override
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
    
    public Boolean saveNewTask(){
    	Task newTask;
    	
    	EditText nameField = (EditText) findViewById(R.id.editNewTaskName);
    	EditText descriptionField = (EditText) findViewById(R.id.editNewTaskDescription);
    	
    	String name = nameField.getText().toString();
    	if (name != null && name.trim().length() == 0){
    		Toast.makeText(NewTaskActivity.this, "Please Enter a Title", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	String description = descriptionField.getText().toString();
    	if (description != null && description.trim().length() == 0){
    		Toast.makeText(NewTaskActivity.this, "Please Enter a Description", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	
    	Boolean sharedOnline = false;
    	CheckBox checkBoxSharedOnline = (CheckBox) findViewById(R.id.checkBoxSharedOnline);
        if (checkBoxSharedOnline.isChecked())
            sharedOnline = true;
    	
        //determine whether it is a photo or text based task
    	RadioGroup radioGroupTaskType = (RadioGroup) findViewById(R.id.radioGroupTaskType);
    	int taskTypeIdSelected = radioGroupTaskType.getCheckedRadioButtonId();
    	RadioButton taskType = (RadioButton) findViewById(taskTypeIdSelected);
    	
    	if (taskType == (RadioButton) findViewById(R.id.radioPhoto)){
    		newTask = new PhotoTask(name, description, Integer.valueOf(1), sharedOnline);
    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioText)){
    		newTask = new TextTask(name, description, Integer.valueOf(1), sharedOnline);
    	}
    	else return false;
    	//add new task to the model before returning
    	TaskShare ts = TaskShareApplication.getTaskShare();
    	ts.addMyTask(newTask);
    	return true;
    	
    }
}
