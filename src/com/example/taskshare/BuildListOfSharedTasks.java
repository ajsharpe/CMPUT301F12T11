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
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;



public class BuildListOfSharedTasks extends AsyncTask<Void, Void, Boolean> {
	ArrayList<Task> onlineTasks = new ArrayList<Task>();
	ProgressDialog dialog;
	ArrayList<Task> appOnlineTasks;
	Context context;
	HttpClient httpC = new DefaultHttpClient();
	HttpPost httpP = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T11/");
	Gson gson = new Gson();
	ArrayAdapter<Task> adapter;
	 
	public BuildListOfSharedTasks(ArrayList<Task> appOnlineTasks, Context context, ProgressDialog dialog, ArrayAdapter<Task> adapter){
		this.appOnlineTasks = appOnlineTasks;
		this.context = context;
		this.dialog = dialog;
		this.adapter = adapter;
    }
	 @Override
	    protected void onPreExecute() {
	        dialog.setTitle("Fetching...");
	        dialog.setMessage("Please wait...");
	        dialog.setIndeterminate(true);
	        dialog.show();

	    }
	@Override
	protected Boolean doInBackground(Void...voids) {	
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "list"));

		try {
			httpP.setEntity(new UrlEncodedFormEntity(nvps));				
			HttpResponse response = httpC.execute(httpP);
			HttpEntity entity = response.getEntity();
			

			if (entity != null) {
		        InputStream is = entity.getContent();
		        String jsonStringVersion = convertStreamToString(is);
		        TaskId[] taskIds = new Gson().fromJson(jsonStringVersion, TaskId[].class);
		          
		        int i = 0;
		        while(i < taskIds.length){
		        	String taskId = taskIds[i].getId();
		        	Task<?> task = getTask(taskId);
		        	task.setId(taskId);
		   			onlineTasks.add(task);			
			        i++;
			    }      

			}
			return true;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;				
		}
	}
	@Override
	protected void onPostExecute(Boolean result) {
        dialog.dismiss();
        if (!onlineTasks.isEmpty()) Toast.makeText(context, "Download Successfull", Toast.LENGTH_SHORT).show();
		else Toast.makeText(context, "Download Unsuccessfull", Toast.LENGTH_SHORT).show();
        for (Task<?> task : onlineTasks){
        	String id = task.getId();
        	if (appOnlineTasks.isEmpty()) appOnlineTasks.add(task);
        	
        	for (Task<?> task2 : appOnlineTasks){
				String id2 = task2.getId();

				if(!id.equals(id2)){
					appOnlineTasks.add(task);
				/*if (task instanceof PhotoTask){
					PhotoTask newTask = (PhotoTask) task;
					appOnlineTasks.add(newTask);
				}
				else if (task instanceof TextTask){
					TextTask newTask = (TextTask) task;
					appOnlineTasks.add(newTask);
				}
				else if (task instanceof AudioTask){
					AudioTask newTask = (AudioTask) task;
					appOnlineTasks.add(newTask);
				}
				else if (task instanceof VideoTask){
					VideoTask newTask =  (VideoTask) task;
					appOnlineTasks.add(newTask);
				}*/
				}
			}
        }
        adapter.notifyDataSetChanged();
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
	
	public Task<?> getTask(String idP) throws Exception{
		Gson gson = new Gson();
		TaskId responseTaskId = new TaskId();
		
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "get"));
		nvps.add(new BasicNameValuePair("id", idP));

		httpP.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpC.execute(httpP);
	    HttpEntity entity = response.getEntity();
	    
	    if (entity != null) {
	        InputStream is = entity.getContent();
	        String jsonStringVersion = convertStreamToString(is);
	        responseTaskId = gson.fromJson(jsonStringVersion,  TaskId.class);
	    }
        return responseTaskId.getTask();

	}
}

