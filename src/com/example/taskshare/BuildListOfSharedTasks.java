package com.example.taskshare;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;
import android.os.AsyncTask;


public class BuildListOfSharedTasks extends AsyncTask<ArrayList<Task>, Void, Boolean> {

	protected Boolean doInBackground(ArrayList<Task>... onlineTasks) {
		if(onlineTasks != null){
			HttpClient httpC = new DefaultHttpClient();
			HttpPost httpP = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T11/");
			Gson gson = new Gson();

			
			List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
			nvps.add(new BasicNameValuePair("action", "list"));

		
			try {
				httpP.setEntity(new UrlEncodedFormEntity(nvps));				
				HttpResponse response = httpC.execute(httpP);
				HttpEntity entity = response.getEntity();
				
				if (entity != null) {
			        InputStream is = entity.getContent();
			        String jsonStringVersion = convertStreamToString(is);
			        Task[] tasks = new Gson().fromJson(jsonStringVersion, Task[].class);
			        System.out.println(new Gson().toJson(tasks));
			          
			        int i = 0;
			        while(i < tasks.length){
			        	
			        	//String taskId = tasks[i].getId();
			        	List <BasicNameValuePair> get = new ArrayList <BasicNameValuePair>();
			    		get.add(new BasicNameValuePair("action", "get"));
			    		//get.add(new BasicNameValuePair("id", taskId));

			    		httpP.setEntity(new UrlEncodedFormEntity(get));
			    		HttpResponse responseGet = httpC.execute(httpP);

			    	    String status = responseGet.getStatusLine().toString();
			    	    HttpEntity entityGet = responseGet.getEntity();


			    	    if (entityGet != null) {
			    	        InputStream isGet = entity.getContent();
			    	        String jsonStringVersionGet = convertStreamToString(isGet);
			    	        Type taskType = Task.class;     
			    	        Task getTask = gson.fromJson(jsonStringVersionGet, taskType);
			    	        onlineTasks[0].add(getTask);
			    	    }
			        	
			        	i++;
			        }
			        
				}
				if (!onlineTasks[0].isEmpty()) return true;
				else return false;
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;				
			}
		}
		else {
			return false;
			}
	}
	private  String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				is.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}

