package com.example.taskshare;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;


public class UploadTasks extends AsyncTask<Task, Void, Boolean> {

	protected Boolean doInBackground(Task... taskP) {
		if(taskP != null){
			HttpClient httpC = new DefaultHttpClient();
			HttpPost httpP = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T11/");
			
			List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
			nvps.add(new BasicNameValuePair("action", "post"));
			nvps.add(new BasicNameValuePair("summary", taskP[0].getName()));
			nvps.add(new BasicNameValuePair("description", taskP[0].getDescription()));
		
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
