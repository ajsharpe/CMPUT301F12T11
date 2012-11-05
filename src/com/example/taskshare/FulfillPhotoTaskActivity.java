package com.example.taskshare;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class FulfillPhotoTaskActivity extends Activity implements OnClickListener {
	
	
	ImageView iv;
	Uri outputFileUri;
	Button takePhoto;
	Button uploadPhoto;
	Intent cameraIntent;
	File file;
	final static int cameraData = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_photo_task);
        initialize();
    	

    }
    private void initialize() {
    	iv = (ImageView) findViewById(R.id.imageView1);
    	takePhoto = (Button) findViewById(R.id.takePhoto);
    	takePhoto.setOnClickListener(this);
    	
    }
    
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_photo_task, menu);
        return true;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp");
		String folder = root.toString(); 
	    file = new File(folder, "fileName" + ".jpg");
	    Uri outputFileUri = Uri.fromFile(file);

		cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		
		startActivityForResult(cameraIntent, cameraData);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RESULT_OK)
		{
			iv = (ImageView)findViewById(R.id.imageView1);
			iv.setImageDrawable(Drawable.createFromPath(outputFileUri.getPath()));
		
		}
	}

		
}
	
