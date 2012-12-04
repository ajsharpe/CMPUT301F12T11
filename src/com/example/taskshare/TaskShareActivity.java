/** Main Screen
 * -Has a list of all the saved tasks
 * -Tasks on the list are selectable and take you to the view task screen for that task
 * -Only other working button is New Task*/

package com.example.taskshare;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.ToggleButton;


public class TaskShareActivity extends Activity {
	
	
    ArrayAdapter<Task> adapter = null;
    boolean mytasks = true;
    boolean otherstasks = false;
    boolean shared = false;
    boolean stored = true;  
    TaskShare ts = TaskShareApplication.getTaskShare();
    ArrayList<Task> listOfTasks = new ArrayList<Task>(ts.getMyTaskList()); 
    static String emailvalue = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_share);  

        //Initiate the four top toggle buttons
        final ToggleButton myTasksButton = (ToggleButton) findViewById(R.id.myTasks);
        final ToggleButton othersTasksButton = (ToggleButton) findViewById(R.id.otherTasks);
        final ToggleButton sharedButton = (ToggleButton) findViewById(R.id.shared);
        final ToggleButton storedButton = (ToggleButton) findViewById(R.id.stored);
        //On launch it'll show stored tasks that you created
        myTasksButton.setChecked(true);
        storedButton.setChecked(true);

        /** Setup List Adapter*/
        adapter = new TaskArrayAdapter(this, listOfTasks);
        /** Setup listView*/
        ListView taskList = (ListView) findViewById(R.id.taskList); 
        taskList.setAdapter(adapter);
        
        //Clicking one button toggles the other off
        myTasksButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		mytasks = true;
        		otherstasks = false; 
        		myTasksButton.setChecked(true);
        		othersTasksButton.setChecked(false);
        		setTaskList();
        	}
        });
          
        othersTasksButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		mytasks = false;
        		otherstasks = true;   
        		myTasksButton.setChecked(false);
        		othersTasksButton.setChecked(true);
        		setTaskList();
        	}
        });
        
        sharedButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		shared = true;
        		stored = false;
        		sharedButton.setChecked(true);
        		storedButton.setChecked(false);
        		setTaskList();
        	}
        });

        storedButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		shared = false;
        		stored = true;
        		sharedButton.setChecked(false);
        		storedButton.setChecked(true);
        		setTaskList();
        	}
        });
                
        
                
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
  
        /** This is the button which takes you to the NEW TASK screen*/
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
    	setTaskList();
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
    public void setTaskList(){
    	//Need to figure out how to change the list of tasks according to the toggle buttons
        if(mytasks & stored){
        	listOfTasks.clear();
        	listOfTasks.addAll(ts.getMyTaskList());
        	adapter.notifyDataSetChanged(); 
        	ts.setCurrentTaskList(listOfTasks);
        	
        }
        else if(mytasks & shared){
        	ProgressDialog dialog = new ProgressDialog(this);
        	listOfTasks.clear();
        	BuildListOfSharedTasks blst = new BuildListOfSharedTasks(listOfTasks, this, dialog, adapter, ts);
    		blst.execute();
        }
        else if (otherstasks & shared){
        	//TODO
        	adapter.notifyDataSetChanged(); 
        }
        else if (otherstasks & stored){
        	//TODO
        	adapter.notifyDataSetChanged(); 
        }
    }
}
