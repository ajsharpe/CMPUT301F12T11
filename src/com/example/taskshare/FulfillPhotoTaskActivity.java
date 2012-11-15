/** Allows fulfilment of photo based tasks
 * -gives option to choose photo or to take photo*/

package com.example.taskshare;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class FulfillPhotoTaskActivity extends Activity implements OnClickListener {
	//MAKE PHOTO OBJECT
	private String name = null;
	private Photo newestPhoto = null;
	//
	private ArrayList<Photo> ArrayOfPhotoUpdates = null;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE = 110;
	ImageView iv;
	Uri outputFileUri;
	Button takePhoto;
	Button uploadPhoto;
	Button save;
	Intent cameraIntent;
	File file;
	final static int cameraData = 0;
	static Uri photoUri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 ArrayOfPhotoUpdates = new ArrayList<Photo>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_photo_task);
        initialize();
    	

    }
    private void initialize() {
    	iv = (ImageView) findViewById(R.id.imageView1);
    	takePhoto = (Button) findViewById(R.id.takePhoto);
    	takePhoto.setOnClickListener(this);
    	uploadPhoto = (Button) findViewById(R.id.uploadPhoto);
    	uploadPhoto.setOnClickListener(this);
    	save = (Button) findViewById(R.id.SavePhotoB);
    	save.setOnClickListener(this);
    }
    
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_photo_task, menu);
        return true;
    }
	public void onClick(View v) {
		if(v.equals(findViewById(R.id.takePhoto))){
			Intent sCamera; 
			Bundle bundle = getIntent().getExtras();
					
					sCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					//sCamera.putExtras(bundle);
					//photoUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);     // Create a file to store the image
//					DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm"); 
					//Date dDate = new Date();        // get a timestamp too!
					//time = new Timestamp(dDate.getTime());

					sCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);    // Sets the image file name
					//Log.d("take photo", photoUri.getPath());


					startActivityForResult(sCamera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); // Calls the camera application to get a Photo

		}
		if(v.equals(findViewById(R.id.uploadPhoto))){
			
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
		}
		if(v.equals(findViewById(R.id.SavePhotoB))){
			//NOT IMPLEMENTED YET
		}
	}
 

	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) 
			{
				if (resultCode == RESULT_OK) {
					// Image captured and saved to fileUri specified in the Intent
					/*if the program gets here, a picture was succesfully captured, call the method to tag the photo here */
					Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
					/* Construct a photo object from data */
					
					newestPhoto = new Photo("Stupid", name, thumbnail);
					ArrayOfPhotoUpdates.add(newestPhoto);
			    	iv.setImageBitmap(thumbnail);
			    	iv.invalidate();
					//final Context context = this;
					//Intent intent = new Intent(context, FulfillPhotoTaskActivity.class);
					
	    			//startActivity(intent);1

				} else if (resultCode == RESULT_CANCELED) {
					// User cancelled the image capture
					Toast.makeText(this, "You have canceled capturing a photo\n", Toast.LENGTH_LONG).show();
					Log.d("SKINOBSERVER", "User canceled taking a photo.");
					finish();
				} else {
					// Image capture failed, advise user1
					Log.d("SKINOBSERVER", "There was an error capturing the photo.");
				}
			}
			if (requestCode == UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE) 
			{
				  if (data != null && resultCode == RESULT_OK) 
		          {              
					Bitmap thumbnail = (Bitmap) data.getExtras().get("data"); //NOT WORKING
					newestPhoto = new Photo("Stupid", name, thumbnail);
					ArrayOfPhotoUpdates.add(newestPhoto);
			    	iv.setImageBitmap(thumbnail);
			    	iv.invalidate();
		          } else if (resultCode == RESULT_CANCELED) {
						// User cancelled the image capture
						Toast.makeText(this, "You have canceled capturing a photo\n", Toast.LENGTH_LONG).show();
						Log.d("SKINOBSERVER", "User canceled taking a photo.");
						finish();
					} else {
						// Image capture failed, advise user1
						Log.d("SKINOBSERVER", "There was an error capturing the photo.");
					}
			}

		}


}
