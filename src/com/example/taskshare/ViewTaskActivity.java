package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ViewTaskActivity extends Activity implements FView<TaskShare>{
	public final static String INDEX = "com.example.myfirstapp.MESSAGE";
	private Task currentTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            //no index for currentTask - abort
        	finish();
            }
        // Get currentTask via the index
        final int index = extras.getInt(INDEX);
        if ((Integer) index != null) {
        	TaskShare ts = TaskShareApplication.getTaskShare();
        	currentTask = ts.getMyTaskList().get(index);
        } else finish();
        
        
        //todo: set text of buttonStoreOffline depending on task privacy
        //todo: load name and description into text fields
        //load currentTask
        
        
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
            		Intent j = new Intent(ViewTaskActivity.this, FulfillPhotoTaskActivity.class);
            		j.putExtra("INDEX", index);
            		startActivity(j);
            	}
            }
        });
        
        Button buttonViewPreviousContent = (Button) findViewById(R.id.buttonViewPreviousContent);
        buttonViewPreviousContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	//not yet implemented
            	String message = getResources().getString(R.string.text_not_implemented);
            	Toast.makeText(ViewTaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        
        Button buttonStoreOffline = (Button) findViewById(R.id.buttonStoreOffline);
        buttonStoreOffline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
        
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
