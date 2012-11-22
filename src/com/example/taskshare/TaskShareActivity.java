/** Main Screen
 * -Has a list of all the saved tasks
 * -Tasks on the list are selectable and take you to the view task screen for that task
 * -Only other working button is New Task*/

package com.example.taskshare;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    ArrayList<Task> listOfTasks = null;
    TaskShare ts = TaskShareApplication.getTaskShare();

    
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
        listOfTasks = ts.getMyTaskList(); 
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
        	@Override
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
        
    }
    
    public void onResume (){
    	super.onResume();
    	/** Reload task list when the activity is resumed */
        adapter.notifyDataSetChanged();  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_share, menu);
        return true;
    }
    public void setTaskList(){
    	//Need to figure out how to change the list of tasks according to the toggle buttons
        if(mytasks & stored){
        	ArrayList<Task> newlist = ts.getMyTaskList();
        	if (newlist.isEmpty()){
        		String message = "List Empty!";
	        	Toast.makeText(TaskShareActivity.this, message, Toast.LENGTH_SHORT).show();
        	}
        	for (Task item : newlist){
    			if (! listOfTasks.contains((Task) item)){
    				listOfTasks.add(item);			
    			}
    			
        	}
        	adapter.notifyDataSetChanged(); 
        	
        }
        else if(mytasks & shared){
        	//TODO
        	adapter.notifyDataSetChanged(); 
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
