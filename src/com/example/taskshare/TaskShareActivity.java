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


public class TaskShareActivity extends Activity {
	
	
    ArrayAdapter<Task> adapter = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_share);
        
        /** Setup list of Tasks*/
        TaskShare ts = TaskShareApplication.getTaskShare();				
        ArrayList<Task> listOfTasks = ts.getMyTaskList(); 
        
        /** Setup listView*/
        ListView taskList = (ListView) findViewById(R.id.taskList); 
        
        /** Setup List Adapter*/
        adapter = new ArrayAdapter<Task>(this,
        		android.R.layout.simple_expandable_list_item_1, listOfTasks);
        taskList.setAdapter(adapter);
        
        /** Make List items Clickable*/
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
        adapter.notifyDataSetChanged();  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_share, menu);
        return true;
    }
    
}
