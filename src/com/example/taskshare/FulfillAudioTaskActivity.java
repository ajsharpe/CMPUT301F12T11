/* This is an activity class for fulfilling audio tasks.
 * It can record, playback, and save the file to the SDcard.
 * 
 * @author Derrick, Seoungyul
 * */
package com.example.taskshare;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class FulfillAudioTaskActivity extends Activity {
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill_audio_task);
        
        
        
        OUTPUT_FILE = Environment.getExternalStorageDirectory()+"/audiorecorder.3gpp";
        
    }
    //Button taps
    public void buttonTapped(View view){
    	switch(view.getId()) {
    	case R.id.startBtn:
    		try {
    			beginRecording();   		
    		}catch (Exception e){
    			e.printStackTrace();
    		}
    		break;
    	case R.id.finishBtn:
    		try {
    			stopRecording();
    			
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    		break;
    	case R.id.playBtn:
    		try {
    			playRecording();
    			
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    		break;
    	case R.id.stopBtn:
    		try {
    			stopPlayback();
    			
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    		break;
    	}
    }
    
    //stops playback
    private void stopPlayback() {
		if(mediaPlayer != null)
			mediaPlayer.stop();
		
	}
    //plays the recorded video
	private void playRecording() throws Exception{
		ditchMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
    private void ditchMediaPlayer() {
		if(mediaPlayer != null)
		{
			try {
				mediaPlayer.release();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	// stop recording if there's a recorder running
	private void stopRecording() {
		
		if (recorder != null)
			recorder.stop();
	}
	//begins to record 
	private void beginRecording() throws Exception {
		
		ditchMediaRecorder();
		File outFile = new File(OUTPUT_FILE);
		
		//check if there's a file already recorded, and if it is we want to get rid of it.
		if(outFile.exists())
			outFile.delete();
		
		//create a new MediaRecorder object.
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		recorder.setOutputFile(OUTPUT_FILE);
		recorder.prepare();
		recorder.start();
		
	}

	private void ditchMediaRecorder() {
		// TODO Auto-generated method stub
		if(recorder != null)
			recorder.release();
		
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fulfill_audio_task, menu);
        return true;
    }
}
