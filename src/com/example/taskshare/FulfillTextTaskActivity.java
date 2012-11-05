package com.example.taskshare;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FulfillTextTaskActivity extends Activity implements FView<TaskShare>{
	public final static String INDEX = "com.example.myfirstapp.MESSAGE";
	private Task<Text> currentTask;
	private ArrayList<Text> ArrayOfTextUpdates = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_text_task);
        
        ArrayOfTextUpdates = new ArrayList<Text>();
        
        //load currentTask from index passed by view task screen
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
                
        Button addTextButton = (Button) findViewById(R.id.addText);
        addTextButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		//Get all items of text-Error Checking will be added later
        		EditText editTitle = (EditText) findViewById(R.id.textTitle);
        		EditText editAuthor = (EditText) findViewById(R.id.textAuthor);
        		EditText editText = (EditText) findViewById(R.id.addedText);
        		String title = editTitle.getText().toString();
        		String author = editAuthor.getText().toString();
        		String text = editText.getText().toString();
        		Time now = new Time();
        		now.setToNow();
        		//Create a Text object and put it in a list
        		Text Text = new Text(title, author, text, now);
        		ArrayOfTextUpdates.add(Text);
        		//Empty all the text fields
        		((EditText) findViewById(R.id.textTitle)).setText("");
        		((EditText) findViewById(R.id.textAuthor)).setText("");
        		((EditText) findViewById(R.id.addedText)).setText("");
        		findViewById(R.id.textTitle).requestFocus();
        		String message = getResources().getString(R.string.text_added_to_task);
            	Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_LONG).show();
        	}
        }); 
        
        /** This is the update button which takes you to the NEW TASK screen*/
        Button updateTaskButton = (Button) findViewById(R.id.updateTask);
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {        		
        		//Updates current task with added text
        		if (ArrayOfTextUpdates != null ){
        			currentTask.updateFulfillment(ArrayOfTextUpdates);
        		}
        		finish();       		
        	}
        }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_text_task, menu);
        return true;
    }
    
    public void update(TaskShare taskshare){
    	
    }
}
