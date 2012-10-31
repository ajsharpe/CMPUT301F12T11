package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FulfillPhotoTaskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_photo_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_photo_task, menu);
        return true;
    }
}
