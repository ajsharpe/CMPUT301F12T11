package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EditTaskActivity extends Activity {
	private Task currentTask;
	private Long index;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
        /** Load currentTask from index passed by main screen */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null)
            finish(); //no index for currentTask - abort
            
        
        /** Get currentTask via the index */
        index = extras.getLong("INDEX");

        if (index != null) {
        	TaskShare ts = TaskShareApplication.getTaskShare();
        	currentTask = ts.getMyTaskList().get(index.intValue());
        } else finish();
        
        
		/** Load name and description into EditText fields */
		EditText name = (EditText) findViewById(R.id.editTaskName);
		EditText description = (EditText) findViewById(R.id.editTaskDescription);
		name.setText(currentTask.getName());
		description.setText(currentTask.getDescription());
		
		
		/** Load task type into radio buttons */
		RadioGroup radioGroupTaskType = (RadioGroup) findViewById(R.id.radioGroupTaskType);
		radioGroupTaskType.clearCheck();
		if (currentTask.getClass() == TextTask.class){
			radioGroupTaskType.check(R.id.radioText);
		}
		else if (currentTask.getClass() == PhotoTask.class){
			radioGroupTaskType.check(R.id.radioPhoto);
		}
		else if (currentTask.getClass() == AudioTask.class){
			radioGroupTaskType.check(R.id.radioAudio);
		}
		else if (currentTask.getClass() == VideoTask.class){
			radioGroupTaskType.check(R.id.radioVideo);
		}
        
		/**Load online status into checkbox*/
		CheckBox storedOnline = (CheckBox) findViewById(R.id.checkBoxSharedOnline);
		storedOnline.setChecked(currentTask.getPrivacy());
		
        
        /** Saves edited task and adds it to the model, removes old task */
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (saveEditedTask() == true) finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_task, menu);
        return true;
    }
    
    public Boolean saveEditedTask(){
    	Task editedTask;
    	
    	EditText nameField = (EditText) findViewById(R.id.editTaskName);
    	EditText descriptionField = (EditText) findViewById(R.id.editTaskDescription);
    	
    	// Check name field is not empty
    	String name = nameField.getText().toString();
    	if (name != null && name.trim().length() == 0){
    		Toast.makeText(EditTaskActivity.this, "Please Enter a Title", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	// Check description field is not empty
    	String description = descriptionField.getText().toString();
    	if (description != null && description.trim().length() == 0){
    		Toast.makeText(EditTaskActivity.this, "Please Enter a Description", Toast.LENGTH_SHORT).show();
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
    		editedTask = new PhotoTask(name, description, sharedOnline);
    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioText)){
    		editedTask = new TextTask(name, description, sharedOnline);
    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioAudio)){
    		editedTask = new AudioTask(name, description, sharedOnline);
    	}
    	else if (taskType == (RadioButton) findViewById(R.id.radioVideo)){
    		editedTask = new VideoTask(name, description, sharedOnline);
    	}
    	else return false;
    	
    	
        if (sharedOnline){
        	
        	boolean upload = new UploadTasks().execute(editedTask) != null;
        	
        	if (!upload) Toast.makeText(EditTaskActivity.this, "Upload Unsucessful", Toast.LENGTH_SHORT).show();
        	if (upload) Toast.makeText(EditTaskActivity.this, "Upload Sucessful", Toast.LENGTH_SHORT).show();
        }
        
        /**TODO:																*
         * handle removing tasks from database if user deselects shared online 	*/
        
        
    	// Replace edited task in the model before returning
    	TaskShare ts = TaskShareApplication.getTaskShare();
    	ts.replaceMyTask(editedTask, index.intValue());
    	return true;
    	
    }
}
