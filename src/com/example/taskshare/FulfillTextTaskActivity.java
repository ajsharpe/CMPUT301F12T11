package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FulfillTextTaskActivity extends Activity implements FView<TaskShare>{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_text_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_text_task, menu);
        return true;
    }
    
    public void update(TaskShare taskshare){
    	
    }
}
