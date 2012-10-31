package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TakePhotoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_take_photo, menu);
        return true;
    }
}
