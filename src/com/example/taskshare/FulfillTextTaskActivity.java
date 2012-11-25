/**Allows fulfilment of text based tasks
 * -allows you to keep adding text
 * -must click save to save your updates to the task*/

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
        //Error check and add text to local list of text objects        
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
        		//Create a Text object and put it in a list
        		Text Text = new Text(title, author, text);
        		ArrayOfTextUpdates.add(Text);
        		//Empty all the text fields
        		((EditText) findViewById(R.id.textTitle)).setText("");
        		((EditText) findViewById(R.id.textAuthor)).setText("");
        		((EditText) findViewById(R.id.addedText)).setText("");
        		findViewById(R.id.textTitle).requestFocus();
        		String message = getResources().getString(R.string.text_added_to_task);
            	Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_LONG).show();

        		if (addText() == true){
        			clearAllText();
        			String errorMessage = getResources().getString(R.string.text_added_to_task);
                	Toast.makeText(FulfillTextTaskActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        		}        		
        	}
        });         
        /** This is the update button which takes you to the NEW TASK screen*/
        Button updateTaskButton = (Button) findViewById(R.id.updateTask);
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {        		
        		//Updates current task with added text
        		if (ArrayOfTextUpdates != null && checkForText()== false){
        			currentTask.updateFulfillment(ArrayOfTextUpdates);
        			String message = "Updates Were Added To The Task!";
                	Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_SHORT).show();
                	finish();  
        		}        		     		
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
    //error checking and building the text object
    public Boolean addText(){
    
    	EditText editTitle = (EditText) findViewById(R.id.textTitle);
		EditText editAuthor = (EditText) findViewById(R.id.textAuthor);
		EditText editText = (EditText) findViewById(R.id.addedText);
    	
    	// Check title field is not empty
    	String title = editTitle.getText().toString();
    	if (title != null && title.trim().length() == 0){
    		Toast.makeText(FulfillTextTaskActivity.this, "Please Enter a Title", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	// Check author field is not empty
    	String author = editAuthor.getText().toString();
    	if (author != null && author.trim().length() == 0){
    		Toast.makeText(FulfillTextTaskActivity.this, "Please Enter a Author", Toast.LENGTH_SHORT).show();
    		return false;
    	}   	
    	// Check Text field is not empty
    	String text = editText.getText().toString();
    	if (text != null && text.trim().length() == 0){
    		Toast.makeText(FulfillTextTaskActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	else{
    		Time now = new Time();
    		now.setToNow();
    		//Create a Text object and put it in a list
    		Text Text = new Text(title, author, text);
    		ArrayOfTextUpdates.add(Text);
    		return true;
    	}    
    }
  //Empty all the text fields
    public void clearAllText(){
   		((EditText) findViewById(R.id.textTitle)).setText("");
		((EditText) findViewById(R.id.textAuthor)).setText("");
		((EditText) findViewById(R.id.addedText)).setText("");
		findViewById(R.id.textTitle).requestFocus();
    }
    //make sure there's no un-added text
    public Boolean checkForText(){
        
    	EditText editTitle = (EditText) findViewById(R.id.textTitle);
		EditText editAuthor = (EditText) findViewById(R.id.textAuthor);
		EditText editText = (EditText) findViewById(R.id.addedText);
    	String message = "Please Add Your Text Before Saving, Or Clear All Text Fields!";
    	// Check title field is not empty
    	String title = editTitle.getText().toString();
    	if (title != null && !(title.trim().length() == 0)){
    		Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_LONG).show();
    		return true;
    	}
    	// Check author field is not empty
    	String author = editAuthor.getText().toString();
    	if (author != null && !(author.trim().length() == 0)){
    		Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_LONG).show();
    		return true;
    	}   	
    	// Check Text field is not empty
    	String text = editText.getText().toString();
    	if (text != null && !(text.trim().length() == 0)){
    		Toast.makeText(FulfillTextTaskActivity.this, message, Toast.LENGTH_LONG).show();
    		return true;
    	}
    	else{
    		return false;
    	}    
    }
}
