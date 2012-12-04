/* This is an activity class for fulfilling video tasks.
 * It can record, playback, and save the file to the SDcard.
 * 
 * @author Derrick, Seoungyul
 * */
package com.example.taskshare;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class FulfillVideoTaskActivity extends Activity implements SurfaceHolder.Callback, OnInfoListener, OnErrorListener{
	
	private Button initBtn = null;
	private Button startBtn = null;
	private Button stopBtn = null;
	private Button playBtn = null;
	private Button stopPlayBtn = null;
	// save Button should be implemented
	private TextView recordingMsg = null;
	private VideoView videoView = null;
	private SurfaceHolder holder = null;
	private Camera camera = null;
	private static final String TAG ="RecordVideo";
	private MediaRecorder recorder = null;
	private String outputFileName;
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_video_task);
       
        // get references to UI elements
        initBtn = (Button) findViewById(R.id.initBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        stopPlayBtn = (Button) findViewById(R.id.stopPlayBtn);
        recordingMsg = (TextView) findViewById(R.id.recording);
        videoView = (VideoView)this.findViewById(R.id.videoView);
        
        
    }
    //Button taps
    public void buttonTapped(View view) {
    	switch(view.getId()) {
    	case R.id.initBtn:
    		initRecorder();
    		break;
    	case R.id.startBtn:
    		beginRecording();
    		break;
    	case R.id.stopBtn:
    		stopRecording();
    		break;
    	case R.id.playBtn:
    		playRecording();
    		break;
    	case R.id.stopPlayBtn:
    		stopPlayback();
    		break;
    	}
    }
    //stops the playback
    private void stopPlayback() {
		videoView.stopPlayback();
		
	}
    
    //plays the recorded video
	private void playRecording() {
		MediaController mc = new MediaController(this);
		videoView.setMediaController(mc);
		videoView.setVideoPath(outputFileName);
		videoView.start();
		stopPlayBtn.setEnabled(true);
				
	}
	//stops recording video
	private void stopRecording() {
		if(recorder != null) {
			recorder.setOnErrorListener(null);
			recorder.setOnInfoListener(null);
			try {
				recorder.stop();
				
			}
			catch(IllegalStateException e) {
				//this can happen if the recorder has already stopped.
				Log.e(TAG, "Got IllegalStateException in stopRecording");
				
			}
			releaseRecorder();
			recordingMsg.setText("");
			releaseCamera();
			startBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			playBtn.setEnabled(true);
			
		}
		
		
	}
	
	
	private void releaseCamera() {
		if(camera != null) {
			try {
				camera.reconnect();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			camera.release();
			camera = null;
			
		}
		
	}

	private void releaseRecorder() {
		if(recorder != null) {
			recorder.release();
			recorder = null;
		}
		
	}

	private void beginRecording() {
		recorder.setOnInfoListener(this);
		recorder.setOnErrorListener(this);
		recorder.start();
		recordingMsg.setText("RECORDING");
		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);
		
	}
	
	// Initialize the recorder
	private void initRecorder() {
		if(recorder != null) return;
		
		// The place where the video will be saved.
		outputFileName = Environment.getExternalStorageDirectory() + "/videooutput.mp4";
		
		File outFile = new File(outputFileName);
		//if File already exists, we delete it 
		if(outFile.exists())
			outFile.delete();
		
		try{
			camera.stopPreview();
			camera.unlock();
			recorder = new MediaRecorder();
			recorder.setCamera(camera);
			
			recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setVideoSize(176, 144);
			recorder.setVideoFrameRate(15);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setMaxDuration(10000); // limit to 10 seconds
			recorder.setPreviewDisplay(holder.getSurface());
			recorder.setOutputFile(outputFileName); // setting our output file to our FileName
			
			recorder.prepare();
			Log.v(TAG, "MediaRecorder initialized");
			initBtn.setEnabled(false);
			startBtn.setEnabled(true);
			
		}
		//error checking 
		catch(Exception e) {
			Log.v(TAG, "MediaRecorder failed to initialize");
			e.printStackTrace();
			
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_video_task, menu);
        return true;
    }

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(TAG, "in sufaceCreated");
		
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
			
		}catch (IOException e) {
			Log.v(TAG, "Could not start the preview");
			e.printStackTrace();
			
		}
		initBtn.setEnabled(true);
			
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	public void onError(MediaRecorder mr, int what, int extra) {
		Log.e(TAG, "got a recording error");
		stopRecording();
		Toast.makeText(this, "Recording error has occurred. Stopping the recording", Toast.LENGTH_SHORT).show();
		
	}

	public void onInfo(MediaRecorder mr, int what, int extra) {
		Log.i(TAG, "got a recording event");
		if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
			Log.i(TAG, "...max duration reached");
			stopRecording();
			Toast.makeText(this, "Recording limit has been reached. Stopping the recording", Toast.LENGTH_SHORT).show();
			
		}
		
		
	}
	
	// disable the buttons until the camera is initialized
	protected void onResume() {
		Log.v(TAG, "in onResume");
		super.onResume();
		initBtn.setEnabled(false);
		startBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		playBtn.setEnabled(false);
		stopPlayBtn.setEnabled(false);
		if(!initCamera())
			finish();
		
	}

	// initializes the camera 
	@SuppressWarnings("deprecation")
	private boolean initCamera() {
		try {
			camera = Camera.open();
			Camera.Parameters camParams = camera.getParameters();
			camera.lock();
			holder = videoView.getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			
		}
		catch(RuntimeException re) {
			Log.v(TAG, "Could not initialize the Camera");
			re.printStackTrace();
			return false;
		}
		return true;
	}
}
