/**Class that allows the building of a list of tasks
 * -also allows the adding and removing of tasks from a list
 * -only makes offline lists as of now*/

package com.example.taskshare;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import android.content.Context;

public class TaskShare implements Serializable{

	private ArrayList<Task> myTaskList, currentTaskList;
	private String user;

	TaskShare(){
		this.myTaskList = new ArrayList<Task>();
		this.currentTaskList = new ArrayList<Task>();
	}

	public ArrayList<Task> getMyTaskList(){
		return this.myTaskList;
	}
	
	public void setCurrentTaskList(ArrayList<Task> listOfTasks){
		this.currentTaskList.clear();
		this.currentTaskList.addAll(listOfTasks);		
	}
	
	public ArrayList<Task> getCurrentTaskList(){
		return this.currentTaskList;
	}

	/* Adds a new task to taskList  *
	 * Returns true if successful,  *
	 * false if duplicate			*/
	public Boolean addMyTask(Task task){
		if (! myTaskList.contains(task)){
			myTaskList.add(task);
			saveToFile();
			return true;
		}
		return false;
	}

	/* Removes a task from  myTaskList  *
	 * Returns true if successful,      *
	 * false if not found			 	*/
	public Boolean removeMyTask(Task task){
		if (myTaskList.contains(task)){
			myTaskList.remove(task);
			saveToFile();
			return true;
		}
		return false;
	}

	/* Add a task from  myTaskList, and	*
	 * remove the task previously at 	*
	 * that index.						*
	 * Returns true if successful,      *
	 * false if not found			 	*/
	public void replaceMyTask(Task task, int index){
		myTaskList.add(index, task);
		myTaskList.remove(index+1);
		saveToFile();
	}

	public void setUser(String user){
		this.user = user;
		saveToFile();
	}

	public String getUser(){
		return this.user;
	}

	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.user);
		out.writeObject(this.myTaskList);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		this.user = (String) in.readObject();
		this.myTaskList = (ArrayList<Task>) in.readObject();
	}

	public void saveToFile(){
		String path = "/mnt/sdcard/.taskshare/taskshare";
		try {  
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path, false)); 
			this.writeObject(out);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	public void loadFromFile(){
		String path = "/mnt/sdcard/.taskshare/taskshare";
		try {  
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path)); 
			this.readObject(in);
			in.close();  
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		} 
	}
}
