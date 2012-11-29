/** Allows fulfilment of photo based tasks
 * -gives option to choose photo or to take photo*/

package com.example.taskshare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	Bitmap myBit;
	String folder;
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

			Intent sCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
			File folderF = new File(folder);
			if (!folderF.exists()) {
			folderF.mkdir();
			}

			String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) +".jpg";
			File imageFile = new File(imageFilePath);
			outputFileUri = Uri.fromFile(imageFile);

			sCamera.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, outputFileUri);
			 
			
			startActivityForResult(sCamera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

		}
		if(v.equals(findViewById(R.id.uploadPhoto))){
			
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
		}
		if(v.equals(findViewById(R.id.SavePhotoB))){
			
	    	 ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	         myBit.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	         name =String.valueOf(System.currentTimeMillis()) +".jpg";
	         File file = new File(Environment.getExternalStorageDirectory()+File.separator + name);
	            try {
	                file.createNewFile();
	                FileOutputStream fo = new FileOutputStream(file);
	                fo.write(bytes.toByteArray());
	                fo.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	         
			
	    
	        // i.setType("message/rfc822");
	         Intent i = new Intent(android.content.Intent.ACTION_SEND); 
             String Email[] = { "seoungyu@ualberta.ca" }; 
             i.putExtra(android.content.Intent.EXTRA_EMAIL, Email); 
             i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Helloworld"); 
             i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ Environment.getExternalStorageDirectory())); 
             i.setType("plain/text"); 
          
	         try {
	             startActivity(Intent.createChooser(i, "Send mail..."));
	         } catch (android.content.ActivityNotFoundException ex) {
	             Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	         }
	         sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
/*			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailSignature);
	        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, TaskShareActivity.emailvalue);
	        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HELLLOOOOOOO");
	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "LETS GET THIS THING WORKING \n\n FML)"); 
	        emailIntent.setType("image/jpeg");
	        File bitmapFile = new File(Environment.getExternalStorageDirectory()+
	            "/"+folder+"/picture.jpg");
	        //myUri = Uri.fromFile(bitmapFile);
	        emailIntent.putExtra(Intent.EXTRA_STREAM, outputFileUri);


	        startActivity(Intent.createChooser(emailIntent, "Send your email in:"));*/
	       //eraseContent();
	       //sentMode = true;
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
					myBit=thumbnail;
					//newestPhoto = new Photo("Stupid", name, thumbnail);
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
					  outputFileUri = data.getData();  
					  Bitmap bitmap;
                      try {
                             bitmap = MediaStore.Images.Media.getBitmap(
                                           getContentResolver(), outputFileUri);
                             myBit=bitmap;
                             iv.setImageBitmap(bitmap);
                      } catch (FileNotFoundException e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                      } catch (IOException e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                      }
                      /*
					Bundle extras = data.getExtras();
					Bitmap thumbnail = (Bitmap) extras.get("data"); //NOT WORKING
					newestPhoto = new Photo("Stupid", name, thumbnail);
					ArrayOfPhotoUpdates.add(newestPhoto);
			    	iv.setImageBitmap(thumbnail);fd
			    	iv.invalidate();
			    	*/
					  
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
