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

public class FulfillTextTaskActivity extends Activity implements FView<TaskShare>{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_text_task);
        
        final ArrayList<Text> ArrayOfTextUpdates = new ArrayList<Text>();
        
        Button addTextButton = (Button) findViewById(R.id.addText);
        addTextButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		
        		EditText editTitle = (EditText) findViewById(R.id.textTitle);
        		EditText editAuthor = (EditText) findViewById(R.id.textAuthor);
        		EditText editText = (EditText) findViewById(R.id.addedText);
        		String title = editTitle.getText().toString();
        		String author = editAuthor.getText().toString();
        		String text = editText.getText().toString();
        		Time now = new Time();
        		now.setToNow();
        		Text Text = new Text(title, author, text, now);
        		ArrayOfTextUpdates.add(Text);
        		((EditText) findViewById(R.id.textTitle)).setText("");
        		((EditText) findViewById(R.id.textAuthor)).setText("");
        		((EditText) findViewById(R.id.addedText)).setText("");
        	}
        }); 
        
        /** This is the update button which takes you to the NEW TASK screen*/
        Button updateTaskButton = (Button) findViewById(R.id.updateTask);
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent myIntent = new Intent(view.getContext(), ViewTaskActivity.class);
        		startActivityForResult(myIntent, 0);
        		/**Also need to update the current task!!!!!!!*/
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
