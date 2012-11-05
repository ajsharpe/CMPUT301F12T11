package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTaskActivity extends Activity implements FView<TaskShare>{
	private Task currentTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        
        /** Load currentTask from index passed by main screen */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null)
            finish(); //no index for currentTask - abort
            
        
        /** Get currentTask via the index */
        final Long index = extras.getLong("INDEX");

        if (index != null) {
        	TaskShare ts = TaskShareApplication.getTaskShare();
        	currentTask = ts.getMyTaskList().get(index.intValue());
        } else finish();
        
        
        /** Set text of buttonStoreOffline depending on task privacy*/
        Button buttonStoreOffline = (Button) findViewById(R.id.buttonStoreOffline);
        if (currentTask.getPrivacy() == true)
        	buttonStoreOffline.setText(R.string.text_store_offline);
        else
        	buttonStoreOffline.setText(R.string.text_store_online);
        	
        /** Load name and description into text fields */
        TextView name = (TextView) findViewById(R.id.textName);
        TextView description = (TextView) findViewById(R.id.textDescription);
        name.setText(currentTask.getName());
        description.setText(currentTask.getDescription());
        
        
        /** Allows user to upload/take pictures or add text depending on the task type */
        Button buttonFulfillTask = (Button) findViewById(R.id.buttonFulfillTask);
        buttonFulfillTask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	if (currentTask.getClass() == TextTask.class){
            		Intent i = new Intent(ViewTaskActivity.this, FulfillTextTaskActivity.class);
            		i.putExtra("INDEX", index);
            		startActivity(i);
            	}
            	else if (currentTask.getClass() == PhotoTask.class){
            		Intent i = new Intent(ViewTaskActivity.this, FulfillPhotoTaskActivity.class);
            		i.putExtra("INDEX", index);
            		startActivity(i);            	
            	}
            }
        });
        
        /** View previously added content to the fulfillment */
        Button buttonViewPreviousContent = (Button) findViewById(R.id.buttonViewPreviousContent);
        buttonViewPreviousContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	//not yet implemented
            	String message = getResources().getString(R.string.text_not_implemented);
            	Toast.makeText(ViewTaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        
        /* Toggles online storage */
        buttonStoreOffline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Button buttonStoreOffline = (Button) findViewById(R.id.buttonStoreOffline);
            	if (currentTask.getPrivacy() == true){
                	currentTask.setPrivacy(false);
                	buttonStoreOffline.postInvalidate();
            	}
                else {
                	currentTask.setPrivacy(true);
                	buttonStoreOffline.postInvalidate();
                }
            }
        });
        
        /** Sends updated fulfillment to task's creator */
        Button buttonNotifyCreator = (Button) findViewById(R.id.buttonNotifyCreator);
        buttonNotifyCreator.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	//not yet implemented
            	String message = getResources().getString(R.string.text_not_implemented);
            	Toast.makeText(ViewTaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_task, menu);
        return true;
    }
    
    public void update(TaskShare taskshare){
    	
    }
}
