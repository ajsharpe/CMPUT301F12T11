/** Main Screen
 * -Has a list of all the saved tasks
 * -Tasks on the list are selectable and take you to the view task screen for that task
 * -Only other working button is New Task*/

package com.example.taskshare;
import java.net.InetAddress;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class TaskShareActivity extends Activity {

	ArrayAdapter<Task> adapter = null;
	static String emailvalue = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_share);

		/** Setup list of Tasks*/
		TaskShare ts = TaskShareApplication.getTaskShare();				
		ArrayList<Task> listOfTasks = ts.getMyTaskList(); 

		/** Setup List Adapter*/
		adapter = new TaskArrayAdapter(this, listOfTasks);

		/** Setup listView*/
		ListView taskList = (ListView) findViewById(R.id.taskList); 
		taskList.setAdapter(adapter);

		/** Make List items Click able*/
		taskList.setOnItemClickListener(new OnItemClickListener() {    
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Intent myIntent = new Intent(view.getContext(),ViewTaskActivity.class);
				Bundle extras = new Bundle();
				extras.putLong("INDEX", id);
				/** Switch to view the selected task, send index to next activity*/
				myIntent.putExtras(extras);
				startActivityForResult(myIntent, 0); 
			}
		});

		/** This is the first button which takes you to the NEW TASK screen*/
		Button newTaskButton = (Button) findViewById(R.id.newTask);
		newTaskButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), NewTaskActivity.class);
				startActivityForResult(myIntent, 0);
			}
		}); 

		if (ts.getUser() == null){
			/** 1 try getting user email */
			boolean notValid =true;

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Welcome");
			alert.setMessage("Please Type the email address!");

			// Set an EditText view to get user input 
			final EditText userEmail = new EditText(this);

			alert.setView(userEmail);
			alert.setCancelable(false);
			userEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//emailvalue = userEmail.getText().toString();
					if (isValidEmail(userEmail.getText().toString())){
						emailvalue = userEmail.getText().toString();
						TaskShare ts = TaskShareApplication.getTaskShare();
						ts.setUser(emailvalue);
					}else{
						//Todo: make this into a loop that keeps prompting the user until a valid email has been entered
						Toast.makeText(getApplicationContext(), "Wrong email input, please run the program again to input your correct email", Toast.LENGTH_SHORT).show();

					}
					// Do something with value!0sa
				}
			});


			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.djdkdjkl
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "Please run the program again to input your correct email", Toast.LENGTH_SHORT).show();


				}
			});

			// create alert dialog
			AlertDialog alertDialog = alert.create();

			// show it
			alertDialog.show();

		}
	}


	public void onResume (){
		super.onResume();
		/** Reload task list when the activity is resumed */
		adapter.notifyDataSetChanged();  
	}
	public static boolean isValidEmail(String value) {
		return value.matches("^([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)$");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_task_share, menu);
		return true;
	}



}
