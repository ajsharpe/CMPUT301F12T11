package com.example.taskshare;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class TaskShareActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_share);
        
        ListView taskList = (ListView) findViewById(R.id.taskList);
        Task[] listOfTasks = {}; 											/**NEED WAY TO GET A LIST OF ALL TASKS*/
        
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this,
        		android.R.layout.simple_expandable_list_item_1, listOfTasks);
        taskList.setAdapter(adapter);
        
        taskList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        		Intent myIntent = new Intent(view.getContext(),ViewTaskActivity.class);
        		int taskId = 0;			/**NEED WAY TO SEND TASK ID TO THE VIEW TASK SCREEN SO RIGHT TASK IS DISPLAYED*/
        		startActivityForResult(myIntent, taskId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_share, menu);
        return true;
    }
}
