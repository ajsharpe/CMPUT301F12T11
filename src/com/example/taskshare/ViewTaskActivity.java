/** This activity is used to view details of a selected tasks, and determine if 
 * one would like to complete the given task
 * currently the only working button is fulfill task */
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
import android.widget.ToggleButton;

public class ViewTaskActivity extends Activity{
	private Task currentTask;
	private Long index;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);

		/** Load list index for currentTask passed by main screen */
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras == null)
			finish(); //no index for currentTask - abort
		index = extras.getLong("INDEX");
		//Task info is loaded into fields in onResume() method

		
		//Initiate onclick listeners
		ToggleButton buttonFavourite = (ToggleButton) findViewById(R.id.buttonFavourite);
		buttonFavourite.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				currentTask.toggleFavourite();
			}
		});
		
		
		/** Allows user to edit task */
		Button buttonEditTask = (Button) findViewById(R.id.buttonEditTask);
		buttonEditTask.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(ViewTaskActivity.this, EditTaskActivity.class);
				i.putExtra("INDEX", index);
				startActivity(i);
			}
		});
		
		/** Allows user to delete task */
		Button buttonDeleteTask = (Button) findViewById(R.id.buttonDeleteTask);
		buttonDeleteTask.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
		    	TaskShare ts = TaskShareApplication.getTaskShare();
		    	ts.removeMyTask(currentTask);
		    	finish();/**
				Intent i = new Intent(ViewTaskActivity.this, EditTaskActivity.class);
				startActivity(i); //a hack, but it works*/
			}
		});


		/** Allows user to upload/take pictures or add text depending on the task type */
		Button buttonFulfillTask = (Button) findViewById(R.id.buttonFulfillTask);
		buttonFulfillTask.setOnClickListener(new OnClickListener() {
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
				else if (currentTask.getClass() == AudioTask.class){
					Intent i = new Intent(ViewTaskActivity.this, FulfillAudioTaskActivity.class);
					i.putExtra("INDEX", index);
					startActivity(i);            	
				}
				else if (currentTask.getClass() == VideoTask.class){
					Intent i = new Intent(ViewTaskActivity.this, FulfillVideoTaskActivity.class);
					i.putExtra("INDEX", index);
					startActivity(i);            	
				}
			}
		});

		/** View previously added content to the fulfillment */
		Button buttonViewPreviousContent = (Button) findViewById(R.id.buttonViewPreviousContent);
		buttonViewPreviousContent.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//not yet implemented
				String message = getResources().getString(R.string.text_not_implemented);
				Toast.makeText(ViewTaskActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});

		/* Toggles online storage */
		Button buttonStoreOffline = (Button) findViewById(R.id.buttonStoreOffline);
		buttonStoreOffline.setOnClickListener(new OnClickListener() {
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
			public void onClick(View arg0) {
				//not yet implemented
				String message = getResources().getString(R.string.text_not_implemented);
				Toast.makeText(ViewTaskActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});        
	}
	
	
	public void onResume(){
		super.onResume();
		/**Load current task from index in the TaskList*/
		if (index != null) {
			TaskShare ts = TaskShareApplication.getTaskShare();
			currentTask = ts.getMyTaskList().get(index.intValue());
		} else finish();

		/** Load name and description into text fields */
		TextView name = (TextView) findViewById(R.id.textName);
		TextView description = (TextView) findViewById(R.id.textDescription);
		name.setText(currentTask.getName());
		description.setText(currentTask.getDescription());
		
		/** Set text of buttonStoreOffline depending on task privacy*/
		Button buttonStoreOffline = (Button) findViewById(R.id.buttonStoreOffline);
		if (currentTask.getPrivacy() == true)
			buttonStoreOffline.setText(R.string.text_store_offline);
		else
			buttonStoreOffline.setText(R.string.text_store_online);
		
		/** Do not show edit or delete buttons if task is not created by currentUser */
		if(currentTask.getUser() != TaskShareApplication.getTaskShare().getUser()){
			Button buttonEdit = (Button) findViewById(R.id.buttonEditTask);
			Button buttonDelete = (Button) findViewById(R.id.buttonDeleteTask);
			buttonEdit.setVisibility(View.INVISIBLE);
			buttonDelete.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

}
