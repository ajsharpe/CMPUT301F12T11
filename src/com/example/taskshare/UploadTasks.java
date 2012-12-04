package com.example.taskshare;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;

import android.os.AsyncTask;


public class UploadTasks extends AsyncTask<Task<?>, Void, Boolean> {

	protected Boolean doInBackground(Task<?>... taskP) {
		if(taskP != null){
			HttpClient httpC = new DefaultHttpClient();
			HttpPost httpP = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T11/");
			Gson gson = new Gson();
			
			List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
			nvps.add(new BasicNameValuePair("action", "post"));
			Task<?> task = taskP[0];
			nvps.add(new BasicNameValuePair("content", gson.toJson(task)));
		
			try {
				httpP.setEntity(new UrlEncodedFormEntity(nvps));				
				httpC.execute(httpP);
				return true;
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;				
			}
		}
		else {
			return false;
			}
	}
}
