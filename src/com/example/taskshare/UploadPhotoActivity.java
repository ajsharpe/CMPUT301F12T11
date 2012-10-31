package com.example.taskshare;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UploadPhotoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_upload_photo, menu);
        return true;
    }
}
